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
import eu.sig.safari.exercises.stubs.simianarmy.JdbcTemplate;
import eu.sig.safari.exercises.stubs.simianarmy.Logger;
import eu.sig.safari.exercises.stubs.simianarmy.LoggerFactory;
import eu.sig.safari.exercises.stubs.simianarmy.MonkeyRecorder;

/**
 * The Class RDSRecorder. Records events to and fetched events from a RDS table
 * (default SIMIAN_ARMY)
 */
public class RDSRecorder implements MonkeyRecorder {
	/** The Constant LOGGER. */
	@SuppressWarnings("unused") // (SIG: added by SIG to suppress warning in IDE)
	private static final Logger LOGGER = LoggerFactory.getLogger(RDSRecorder.class);

	@SuppressWarnings("unused") // (SIG: added by SIG to suppress warning in IDE)
	private final String region;

	/** The table. */
	@SuppressWarnings("unused") // (SIG: added by SIG to suppress warning in IDE)
	private final String table;

	/** the jdbcTemplate */
	JdbcTemplate jdbcTemplate = null;

	public static final String FIELD_ID = "eventId";
	public static final String FIELD_EVENT_TIME = "eventTime";
	public static final String FIELD_MONKEY_TYPE = "monkeyType";
	public static final String FIELD_EVENT_TYPE = "eventType";
	public static final String FIELD_REGION = "region";
	public static final String FIELD_DATA_JSON = "dataJson";

	/**
	 * Instantiates a new RDS recorder.
	 *
	 */
	public RDSRecorder(String dbDriver, String dbUser, String dbPass, String dbUrl, String dbTable, String region) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(dbDriver);
		dataSource.setJdbcUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPass);
		dataSource.setMaximumPoolSize(2);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.table = dbTable;
		this.region = region;
	}

	/**
	 * Instantiates a new RDS recorder. This constructor is intended for unit
	 * testing.
	 *
	 */
	public RDSRecorder(JdbcTemplate jdbcTemplate, String table, String region) {
		this.jdbcTemplate = jdbcTemplate;
		this.table = table;
		this.region = region;
	}
	
   	//SIG: other methods omitted
 
}
