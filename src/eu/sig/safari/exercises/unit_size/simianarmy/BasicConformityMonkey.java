/** 
 * NOTICE: 
 * - This file has been heavily modified by the Software Improvement Group (SIG) to adapt it for training purposes
 * - The original file can be found here: https://github.com/Netflix/SimianArmy
 * - All dependencies in this file have been stubbed, some methods and/or their implementations in this file have been omitted, modified or removed
 * 
 * ORIGINAL FILE HEADER: 
 */
/*
*
*  Copyright 2013 Netflix, Inc.
*
*     Licensed under the Apache License, Version 2.0 (the "License");
*     you may not use this file except in compliance with the License.
*     You may obtain a copy of the License at
*
*         http://www.apache.org/licenses/LICENSE-2.0
*
*     Unless required by applicable law or agreed to in writing, software
*     distributed under the License is distributed on an "AS IS" BASIS,
*     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*     See the License for the specific language governing permissions and
*     limitations under the License.
*
*/
package eu.sig.safari.exercises.unit_size.simianarmy;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.sig.safari.exercises.stubs.simianarmy.Cluster;
import eu.sig.safari.exercises.stubs.simianarmy.ClusterCrawler;
import eu.sig.safari.exercises.stubs.simianarmy.ConformityClusterTracker;
import eu.sig.safari.exercises.stubs.simianarmy.ConformityEmailNotifier;
import eu.sig.safari.exercises.stubs.simianarmy.ConformityMonkey;
import eu.sig.safari.exercises.stubs.simianarmy.ConformityRuleEngine;
import eu.sig.safari.exercises.stubs.simianarmy.Context;
import eu.sig.safari.exercises.stubs.simianarmy.Lists;
import eu.sig.safari.exercises.stubs.simianarmy.Logger;
import eu.sig.safari.exercises.stubs.simianarmy.LoggerFactory;
import eu.sig.safari.exercises.stubs.simianarmy.Maps;
import eu.sig.safari.exercises.stubs.simianarmy.MonkeyCalendar;
import eu.sig.safari.exercises.stubs.simianarmy.MonkeyConfiguration;

/** The basic implementation of Conformity Monkey. */
public class BasicConformityMonkey extends ConformityMonkey {

   /** The Constant LOGGER. */
   private static final Logger LOGGER = LoggerFactory.getLogger(BasicConformityMonkey.class);

   /** The Constant NS. */
   private static final String NS = "simianarmy.conformity.";

   /** The cfg. */
   private final MonkeyConfiguration cfg;

   private final ClusterCrawler crawler;

   private final ConformityEmailNotifier emailNotifier;

   private final Collection<String> regions = Lists.newArrayList();

   private final ConformityClusterTracker clusterTracker;

   private final MonkeyCalendar calendar;

   private final ConformityRuleEngine ruleEngine;

   /** Flag to indicate whether the monkey is leashed. */
   private boolean leashed;

   /**
    * Clusters that are not conforming in the last check.
    */
   private final Map<String, Collection<Cluster>> nonconformingClusters = Maps.newHashMap();

   /**
    * Clusters that are conforming in the last check.
    */
   private final Map<String, Collection<Cluster>> conformingClusters = Maps.newHashMap();

   /**
    * Clusters that the monkey failed to check for some reason.
    */
   private final Map<String, Collection<Cluster>> failedClusters = Maps.newHashMap();

   /**
    * Clusters that do not exist in the cloud anymore.
    */
   private final Map<String, Collection<Cluster>> nonexistentClusters = Maps.newHashMap();

   /**
    * Instantiates a new basic conformity monkey.
    *
    * @param ctx
    *            the ctx
    */
   public BasicConformityMonkey(Context ctx) {
       super(ctx);
       cfg = ctx.configuration();
       crawler = ctx.clusterCrawler();
       ruleEngine = ctx.ruleEngine();
       emailNotifier = ctx.emailNotifier();
       for (String region : ctx.regions()) {
           regions.add(region);
       }
       clusterTracker = ctx.clusterTracker();
       calendar = ctx.calendar();
       leashed = ctx.isLeashed();
   }

   //--------------------- SIG ------------------
   // EDIT THIS METHOD
   //--------------------------------------------
   
   /** {@inheritDoc} */
   @Override
   public void doMonkeyBusiness() {
       cfg.reload();
       context().resetEventReport();

       if (isConformityMonkeyEnabled()) {
           nonconformingClusters.clear();
           conformingClusters.clear();
           failedClusters.clear();
           nonexistentClusters.clear();

           List<Cluster> clusters = crawler.clusters();
           Map<String, Set<String>> existingClusterNamesByRegion = Maps.newHashMap();
           for (String region : regions) {
               existingClusterNamesByRegion.put(region, new HashSet<String>());
           }
           for (Cluster cluster : clusters) {
               existingClusterNamesByRegion.get(cluster.getRegion()).add(cluster.getName());
           }
           List<Cluster> trackedClusters = clusterTracker.getAllClusters(regions.toArray(new String[regions.size()]));
           for (Cluster trackedCluster : trackedClusters) {
               if (!existingClusterNamesByRegion.get(trackedCluster.getRegion()).contains(trackedCluster.getName())) {
                   addCluster(nonexistentClusters, trackedCluster);
               }
           }
           for (String region : regions) {
               Collection<Cluster> toDelete = nonexistentClusters.get(region);
               if (toDelete != null) {
                   clusterTracker.deleteClusters(toDelete.toArray(new Cluster[toDelete.size()]));
               }
           }

           LOGGER.info(String.format("Performing conformity check for %d crawled clusters.", clusters.size()));
           Date now = calendar.now().getTime();
           for (Cluster cluster : clusters) {
               boolean conforming;
               try {
                   conforming = ruleEngine.check(cluster);
               } catch (Exception e) {
                   LOGGER.error(String.format("Failed to perform conformity check for cluster %s", cluster.getName()),
                           e);
                   addCluster(failedClusters, cluster);
                   continue;
               }
               cluster.setUpdateTime(now);
               cluster.setConforming(conforming);
               if (conforming) {
                   LOGGER.info(String.format("Cluster %s is conforming", cluster.getName()));
                   addCluster(conformingClusters, cluster);
               } else {
                   LOGGER.info(String.format("Cluster %s is not conforming", cluster.getName()));
                   addCluster(nonconformingClusters, cluster);
               }
               if (!leashed) {
                   LOGGER.info(String.format("Saving cluster %s", cluster.getName()));
                   clusterTracker.addOrUpdate(cluster);
               } else {
                   LOGGER.info(String.format(
                           "The conformity monkey is leashed, no data change is made for cluster %s.",
                           cluster.getName()));
               }
           }
           if (!leashed) {
               emailNotifier.sendNotifications();
           } else {
               LOGGER.info("Conformity monkey is leashed, no notification is sent.");
           }
           if (cfg.getBoolOrElse(NS + "summaryEmail.enabled", true)) {
               sendConformitySummaryEmail();
           }
       }
   }



   private static void addCluster(Map<String, Collection<Cluster>> map, Cluster cluster) {
       //SIG: original implementation omitted for brevity
   }

   /**
    * Send a summary email with about the last run of the conformity monkey.
    */
   protected void sendConformitySummaryEmail() {
	   //SIG: original implementation omitted for brevity
   }

  
   private boolean isConformityMonkeyEnabled() {
       String prop = NS + "enabled";
       if (cfg.getBoolOrElse(prop, true)) {
           return true;
       }
       LOGGER.info("Conformity Monkey is disabled, set {}=true", prop);
       return false;
   }
}