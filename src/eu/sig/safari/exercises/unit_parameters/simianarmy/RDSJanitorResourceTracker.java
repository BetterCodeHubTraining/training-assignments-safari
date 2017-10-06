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
*  Copyright 2012 Netflix, Inc.
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
package eu.sig.safari.exercises.unit_parameters.simianarmy;

import eu.sig.safari.exercises.stubs.simianarmy.HikariDataSource;
import eu.sig.safari.exercises.stubs.simianarmy.JanitorResourceTracker;
import eu.sig.safari.exercises.stubs.simianarmy.JdbcTemplate;
import eu.sig.safari.exercises.stubs.simianarmy.Logger;
import eu.sig.safari.exercises.stubs.simianarmy.LoggerFactory;

/**
* The JanitorResourceTracker implementation in AWS RDS.
*/
public class RDSJanitorResourceTracker implements JanitorResourceTracker {

   /** The Constant LOGGER. */
   public static final Logger LOGGER = LoggerFactory.getLogger(RDSJanitorResourceTracker.class);

   /** The table. */
   @SuppressWarnings("unused")//(SIG: added by SIG to suppress warning in IDE)
   private final String table;
   
   /** the jdbcTemplate  */
   JdbcTemplate jdbcTemplate = null;
   
   /**
    * Instantiates a new RDS janitor resource tracker.
    *
    */
   public RDSJanitorResourceTracker(String dbDriver, String dbUser,
			String dbPass, String dbUrl, String dbTable) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(dbDriver);
		dataSource.setJdbcUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPass);
		dataSource.setMaximumPoolSize(2);
   	this.jdbcTemplate = new JdbcTemplate(dataSource);
   	this.table = dbTable;
	}
   
   /**
    * Instantiates a new RDS janitor resource tracker.  This constructor is intended
    * for unit testing.
    *
    */
   public RDSJanitorResourceTracker(JdbcTemplate jdbcTemplate, String table) {
   	this.jdbcTemplate = jdbcTemplate;
   	this.table = table;
   }
   
   	//SIG: other methods omitted
 
}