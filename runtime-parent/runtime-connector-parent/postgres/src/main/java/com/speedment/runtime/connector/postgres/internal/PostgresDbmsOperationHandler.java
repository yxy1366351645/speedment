/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.connector.postgres.internal;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.internal.db.AbstractDbmsOperationHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongConsumer;

/**
 *
 * @author  Emil Forslund
 * @author  Dan Lawesson
 * @since   3.0.0
 */
public final class PostgresDbmsOperationHandler extends AbstractDbmsOperationHandler {

    private static final int FETCH_SIZE = 4096;
    
    // Five elements - list is surely more efficient than hash set
    private static List<Integer> LONG_GETTABLE_TYPES = Arrays.asList(
        Types.TINYINT,
        Types.SMALLINT,
        Types.INTEGER,
        Types.BIGINT,
        Types.NUMERIC
    );

    protected PostgresDbmsOperationHandler(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final TransactionComponent transactionComponent
    ) {
        super(connectionPoolComponent, dbmsHandlerComponent, transactionComponent);
    }

    @Override
    public void handleGeneratedKeys(PreparedStatement ps, LongConsumer longConsumer) throws SQLException {
        /*
         * There does not seem to be any way to find the generated keys from a Postgres JDBC driver
         * since getGeneratedKeys() returns the whole set of columns. This causes
         * bug #293 "The postgresql throws an exception when the PRIMARY KEY is not type long."
         *
         * See http://stackoverflow.com/questions/19766816/postgresql-jdbc-getgeneratedkeys-returns-all-columns
         *
         * Below we instead handle auto generated fields that can be retrieved as Long. This fix clearly only
         * works for generated fields that are also auto generated.
         */

        try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
            while (generatedKeys.next()) {
                final int columnType = generatedKeys.getMetaData().getColumnType(1);
                if (generatedKeys.getMetaData().isAutoIncrement(1) && LONG_GETTABLE_TYPES.contains(columnType)) {
                    longConsumer.accept(generatedKeys.getLong(1));
                    //sqlStatement.addGeneratedKey(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void configureSelect(PreparedStatement statement) throws SQLException {
        statement.setFetchSize(FETCH_SIZE);
    }

    @Override
    public void configureSelect(ResultSet resultSet) throws SQLException {
        resultSet.setFetchSize(FETCH_SIZE);
    }
    
}