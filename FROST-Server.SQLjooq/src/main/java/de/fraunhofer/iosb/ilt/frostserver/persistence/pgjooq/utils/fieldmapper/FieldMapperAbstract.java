/*
 * Copyright (C) 2021 Fraunhofer Institut IOSB, Fraunhoferstr. 1, D 76131
 * Karlsruhe, Germany.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.utils.fieldmapper;

import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.tables.StaTable;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hylke
 */
public abstract class FieldMapperAbstract implements FieldMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldMapperAbstract.class.getName());

    public static int getOrRegisterField(final String fieldName, Table dbTable, StaTable staTable) {
        int idx = staTable.indexOf(fieldName);
        if (idx >= 0) {
            return idx;
        }
        Field<?> dbField = dbTable.field(fieldName);
        if (dbField == null) {
            LOGGER.error("Could not find field {} on table {}.", fieldName, dbTable.getName());
            throw new IllegalArgumentException("Could not find field " + fieldName + " on table " + dbTable.getName());
        }
        DataType<?> dataType = dbField.getDataType();
        LOGGER.info("  Registering {} -> {}.{}", staTable.getName(), dbTable.getName(), fieldName);
        return staTable.registerField(fieldName, dataType);
    }

}
