/**
 * NOTICE: - This file has been heavily modified by the Software Improvement Group (SIG) to
 * adapt it for training purposes - The original file can be found here:
 * https://github.com/Netflix/SimianArmy - All dependencies in this file have been stubbed,
 * some methods and/or their implementations in this file have been omitted, modified or
 * removed
 * 
 * ORIGINAL FILE HEADER:
 */
/*
 *
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */

package eu.sig.safari.exercises.unit_complexity.simianarmy;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import eu.sig.safari.exercises.stubs.simianarmy.AWSEmailNotifier;
import eu.sig.safari.exercises.stubs.simianarmy.AmazonSimpleEmailServiceClient;
import eu.sig.safari.exercises.stubs.simianarmy.Cluster;
import eu.sig.safari.exercises.stubs.simianarmy.ConformityClusterTracker;
import eu.sig.safari.exercises.stubs.simianarmy.ConformityEmailBuilder;
import eu.sig.safari.exercises.stubs.simianarmy.ConformityRule;
import eu.sig.safari.exercises.stubs.simianarmy.DateTime;
import eu.sig.safari.exercises.stubs.simianarmy.Lists;
import eu.sig.safari.exercises.stubs.simianarmy.Logger;
import eu.sig.safari.exercises.stubs.simianarmy.LoggerFactory;
import eu.sig.safari.exercises.stubs.simianarmy.Maps;
import eu.sig.safari.exercises.stubs.simianarmy.Validate;

/**
* The email notifier implemented for Janitor Monkey.
*/
public class ConformityEmailNotifier extends AWSEmailNotifier {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConformityEmailNotifier.class);
    private static final String UNKNOWN_EMAIL = "UNKNOWN";

    private final Collection<String> regions = Lists.newArrayList();
    private final String defaultEmail;
    private final List<String> ccEmails = Lists.newArrayList();
    private final ConformityClusterTracker clusterTracker;
    private final ConformityEmailBuilder emailBuilder;

    private final Map<String, Collection<Cluster>> invalidEmailToClusters = Maps.newHashMap();
    private final Collection<ConformityRule> rules = Lists.newArrayList();
    private final int openHour;
    private final int closeHour;

    /**
    * The Interface Context.
    */
    public interface Context {
        /**
        * Gets the Amazon Simple Email Service client.
        * @return the Amazon Simple Email Service client
        */
        AmazonSimpleEmailServiceClient sesClient();

        /**
        * Gets the open hour the email notifications are sent.
        * @return
        *      the open hour the email notifications are sent
        */
        int openHour();

        /**
        * Gets the close hour the email notifications are sent.
        * @return
        *      the close hour the email notifications are sent
        */
        int closeHour();

        /**
        * Gets the source email the notifier uses to send email.
        * @return the source email
        */
        String sourceEmail();

        /**
        * Gets the default email the notifier sends to when there is no owner specified for a cluster.
        * @return the default email
        */
        String defaultEmail();

        /**
        * Gets the regions the notifier is running in.
        * @return the regions the notifier is running in.
        */
        Collection<String> regions();

        /** Gets the Conformity Monkey's cluster tracker.
        * @return the Conformity Monkey's cluster tracker
        */
        ConformityClusterTracker clusterTracker();

        /** Gets the Conformity email builder.
        * @return the Conformity email builder
        */
        ConformityEmailBuilder emailBuilder();

        /** Gets the cc email addresses.
        * @return the cc email addresses
        */
        String[] ccEmails();

        /**
        * Gets all the conformity rules.
        * @return all conformity rules.
        */
        Collection<ConformityRule> rules();
    }

    /**
    * Constructor.
    * @param ctx the context.
    */
    public ConformityEmailNotifier(Context ctx) {
        super(ctx.sesClient());
        this.openHour = ctx.openHour();
        this.closeHour = ctx.closeHour();
        for (String region : ctx.regions()) {
            this.regions.add(region);
        }
        this.defaultEmail = ctx.defaultEmail();
        this.clusterTracker = ctx.clusterTracker();
        this.emailBuilder = ctx.emailBuilder();
        String[] ctxCCs = ctx.ccEmails();
        if (ctxCCs != null) {
            for (String ccEmail : ctxCCs) {
                this.ccEmails.add(ccEmail);
            }
        }
        Validate.notNull(ctx.rules());
        for (ConformityRule rule : ctx.rules()) {
            rules.add(rule);
        }
    }

    //--------------------- SIG ------------------
    // EDIT THIS METHOD
    //--------------------------------------------

    /**
    * Gets all the clusters that are not conforming and sends email notifications to the owners.
    */
    public void sendNotifications() {
        int currentHour = DateTime.now().getHourOfDay();
        if (currentHour < openHour || currentHour > closeHour) {
            LOGGER.info("It is not the time for Conformity Monkey to send notifications. You can change "
                + "simianarmy.conformity.notification.openHour and simianarmy.conformity.notification.openHour"
                + " to make it work at this hour.");
            return;
        }

        validateEmails();
        Map<String, Collection<Cluster>> emailToClusters = Maps.newHashMap();
        for (Cluster cluster : clusterTracker.getNonconformingClusters(regions.toArray(new String[regions.size()]))) {
            if (cluster.isOptOutOfConformity()) {
                LOGGER.info(String.format("Cluster %s is opted out of Conformity Monkey so no notification is sent.",
                    cluster.getName()));
                continue;
            }
            if (!cluster.isConforming()) {
                String email = cluster.getOwnerEmail();
                if (!isValidEmail(email)) {
                    if (defaultEmail != null) {
                        LOGGER.info(String.format("Email %s is not valid, send to the default email address %s", email,
                            defaultEmail));
                        putEmailAndCluster(emailToClusters, defaultEmail, cluster);
                    } else {
                        if (email == null) {
                            email = UNKNOWN_EMAIL;
                        }
                        LOGGER.info(String.format("Email %s is not valid and default email is not set for cluster %s",
                            email, cluster.getName()));
                        putEmailAndCluster(invalidEmailToClusters, email, cluster);
                    }
                } else {
                    putEmailAndCluster(emailToClusters, email, cluster);
                }
            } else {
                LOGGER.debug(
                    String.format("Cluster %s is conforming so no notification needs to be sent.", cluster.getName()));
            }
        }
        emailBuilder.setEmailToClusters(emailToClusters, rules);
        for (Map.Entry<String, Collection<Cluster>> entry : emailToClusters.entrySet()) {
            String email = entry.getKey();
            String emailBody = emailBuilder.buildEmailBody(email);
            String subject = buildEmailSubject(email);
            sendEmail(email, subject, emailBody);
            for (Cluster cluster : entry.getValue()) {
                LOGGER.debug(String.format("Notification is sent for cluster %s to %s", cluster.getName(), email));
            }
            LOGGER.info(String.format("Email notification has been sent to %s for %d clusters.", email,
                entry.getValue().size()));
        }
    }

    private void validateEmails() {
        //SIG: contents omitted
    }

    private void putEmailAndCluster(Map<String, Collection<Cluster>> map, String email, Cluster cluster) {
        //SIG: contents omitted
    }
}