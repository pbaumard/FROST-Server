/*
 * Copyright (C) 2016 Fraunhofer Institut IOSB, Fraunhoferstr. 1, D 76131
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.fraunhofer.iosb.ilt.frostserver.model;

import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.DATASTREAM;
import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.FEATURE_OF_INTEREST;
import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.HISTORICAL_LOCATION;
import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.LOCATION;
import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.MULTI_DATASTREAM;
import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.OBSERVATION;
import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.OBSERVED_PROPERTY;
import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.SENSOR;
import static de.fraunhofer.iosb.ilt.frostserver.model.EntityType.THING;
import de.fraunhofer.iosb.ilt.frostserver.model.core.Entity;
import de.fraunhofer.iosb.ilt.frostserver.model.core.EntitySetImpl;
import de.fraunhofer.iosb.ilt.frostserver.model.core.IdLong;
import de.fraunhofer.iosb.ilt.frostserver.model.ext.TimeInstant;
import de.fraunhofer.iosb.ilt.frostserver.model.ext.TimeInterval;
import de.fraunhofer.iosb.ilt.frostserver.model.ext.UnitOfMeasurement;
import de.fraunhofer.iosb.ilt.frostserver.property.EntityPropertyMain;
import de.fraunhofer.iosb.ilt.frostserver.property.NavigationPropertyMain;
import de.fraunhofer.iosb.ilt.frostserver.property.Property;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author scf
 */
public class EntityBuilderTest {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityBuilderTest.class);
    private final Map<Property, Object> propertyValues = new HashMap<>();
    private final Map<Property, Object> propertyValuesAlternative = new HashMap<>();

    @Before
    public void setUp() {
        propertyValues.put(EntityPropertyMain.CREATIONTIME, TimeInstant.now());
        propertyValues.put(EntityPropertyMain.DEFINITION, "MyDefinition");
        propertyValues.put(EntityPropertyMain.DESCRIPTION, "My description");
        propertyValues.put(EntityPropertyMain.ENCODINGTYPE, "My EncodingType");
        propertyValues.put(EntityPropertyMain.FEATURE, new Point(8, 42));
        propertyValues.put(EntityPropertyMain.ID, new IdLong(1));
        propertyValues.put(EntityPropertyMain.LOCATION, new Point(9, 43));
        propertyValues.put(EntityPropertyMain.METADATA, "my meta data");
        propertyValues.put(EntityPropertyMain.MULTIOBSERVATIONDATATYPES, Arrays.asList("Type 1", "Type 2"));
        propertyValues.put(EntityPropertyMain.NAME, "myName");
        propertyValues.put(EntityPropertyMain.OBSERVATIONTYPE, "my Type");
        propertyValues.put(EntityPropertyMain.OBSERVEDAREA, new Polygon(new LngLatAlt(0, 0), new LngLatAlt(1, 0), new LngLatAlt(1, 1)));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key1", "value1");
        parameters.put("key2", 2);
        propertyValues.put(EntityPropertyMain.PARAMETERS, parameters);
        propertyValues.put(EntityPropertyMain.PHENOMENONTIME, TimeInstant.now());
        propertyValuesAlternative.put(EntityPropertyMain.PHENOMENONTIME, TimeInterval.parse("2014-03-02T13:00:00Z/2014-05-11T15:30:00Z"));
        propertyValues.put(EntityPropertyMain.PROPERTIES, parameters);
        propertyValues.put(EntityPropertyMain.RESULT, 42);
        propertyValues.put(EntityPropertyMain.RESULTQUALITY, "myQuality");
        propertyValues.put(EntityPropertyMain.RESULTTIME, TimeInstant.now());
        propertyValuesAlternative.put(EntityPropertyMain.RESULTTIME, TimeInterval.parse("2014-03-01T13:00:00Z/2014-05-11T15:30:00Z"));
        propertyValues.put(EntityPropertyMain.SELFLINK, "http://my.self/link");
        propertyValues.put(EntityPropertyMain.TIME, TimeInstant.now());
        UnitOfMeasurement unit1 = new UnitOfMeasurement("unitName", "unitSymbol", "unitDefinition");
        UnitOfMeasurement unit2 = new UnitOfMeasurement("unitName2", "unitSymbol2", "unitDefinition2");
        propertyValues.put(EntityPropertyMain.UNITOFMEASUREMENT, unit1);
        propertyValues.put(EntityPropertyMain.UNITOFMEASUREMENTS, Arrays.asList(unit1, unit2));
        propertyValues.put(EntityPropertyMain.VALIDTIME, TimeInterval.parse("2014-03-01T13:00:00Z/2015-05-11T15:30:00Z"));

        for (EntityPropertyMain ep : EntityPropertyMain.values()) {
            Assert.assertTrue("Missing value for " + ep, propertyValues.containsKey(ep));
        }

        int nextId = 100;
        propertyValues.put(NavigationPropertyMain.DATASTREAM, new DefaultEntity(DATASTREAM, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.FEATUREOFINTEREST, new DefaultEntity(FEATURE_OF_INTEREST, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.LOCATION, new DefaultEntity(LOCATION, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.MULTIDATASTREAM, new DefaultEntity(MULTI_DATASTREAM, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.OBSERVEDPROPERTY, new DefaultEntity(OBSERVED_PROPERTY, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.SENSOR, new DefaultEntity(SENSOR, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.THING, new DefaultEntity(THING, new IdLong(nextId++)));

        EntitySetImpl datastreams = new EntitySetImpl(EntityType.DATASTREAM);
        datastreams.add(new DefaultEntity(DATASTREAM, new IdLong(nextId++)));
        datastreams.add(new DefaultEntity(DATASTREAM, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.DATASTREAMS, datastreams);

        EntitySetImpl histLocations = new EntitySetImpl(EntityType.HISTORICAL_LOCATION);
        histLocations.add(new DefaultEntity(HISTORICAL_LOCATION, new IdLong(nextId++)));
        histLocations.add(new DefaultEntity(HISTORICAL_LOCATION, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.HISTORICALLOCATIONS, histLocations);

        EntitySetImpl locations = new EntitySetImpl(EntityType.LOCATION);
        locations.add(new DefaultEntity(LOCATION, new IdLong(nextId++)));
        locations.add(new DefaultEntity(LOCATION, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.LOCATIONS, locations);

        EntitySetImpl multiDatastreams = new EntitySetImpl(EntityType.MULTI_DATASTREAM);
        multiDatastreams.add(new DefaultEntity(MULTI_DATASTREAM, new IdLong(nextId++)));
        multiDatastreams.add(new DefaultEntity(MULTI_DATASTREAM, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.MULTIDATASTREAMS, multiDatastreams);

        EntitySetImpl observations = new EntitySetImpl(EntityType.OBSERVATION);
        observations.add(new DefaultEntity(OBSERVATION, new IdLong(nextId++)));
        observations.add(new DefaultEntity(OBSERVATION, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.OBSERVATIONS, observations);

        EntitySetImpl obsProperties = new EntitySetImpl(EntityType.OBSERVED_PROPERTY);
        obsProperties.add(new DefaultEntity(OBSERVED_PROPERTY, new IdLong(nextId++)));
        obsProperties.add(new DefaultEntity(OBSERVED_PROPERTY, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.OBSERVEDPROPERTIES, obsProperties);

        EntitySetImpl things = new EntitySetImpl(EntityType.THING);
        things.add(new DefaultEntity(THING, new IdLong(nextId++)));
        things.add(new DefaultEntity(THING, new IdLong(nextId++)));
        propertyValues.put(NavigationPropertyMain.THINGS, things);

        for (NavigationPropertyMain np : NavigationPropertyMain.values()) {
            Assert.assertTrue("Missing value for " + np, propertyValues.containsKey(np));
        }

    }

    @Test
    public void testEntityBuilders() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (EntityType type : EntityType.getEntityTypes()) {
            testEntityType(type, type.getPropertySet());
        }
    }

    private void testEntityType(EntityType type, Set<Property> collectedProperties) {
        String pName = "";
        try {

            Entity entity = new DefaultEntity(type);
            Entity entity2 = new DefaultEntity(type);
            for (Property p : collectedProperties) {
                pName = p.toString();
                addPropertyToObject(entity, p);
                Assert.assertNotEquals("Property " + pName + " should influence equals.", entity, entity2);

                addPropertyToObject(entity2, p);
                Assert.assertEquals("Entities should be the same after adding " + pName + " to both.", entity, entity2);

                getPropertyFromObject(entity, p);
            }
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Failed create entity.", ex);
            Assert.fail("Failed create entity: " + ex.getMessage());
        }
    }

    private void addPropertyToObject(Entity entity, Property property) {
        try {
            addPropertyToObject(entity, property, propertyValues);
        } catch (IllegalArgumentException ex) {
            addPropertyToObject(entity, property, propertyValuesAlternative);
        }
    }

    private void addPropertyToObject(Entity entity, Property property, Map<Property, Object> valuesToUse) {
        Object value = valuesToUse.get(property);
        try {
            property.setOn(entity, value);
        } catch (NullPointerException ex) {
            LOGGER.error("Failed to set property " + property, ex);
            Assert.fail("Failed to set property " + property + ": " + ex.getMessage());
        }
    }

    private void getPropertyFromObject(Entity entity, Property property) {
        try {
            if (!(property instanceof NavigationPropertyMain) && !entity.isSetProperty(property)) {
                Assert.fail("Property " + property + " returned false for isSet on entity type " + entity.getEntityType());
            }
            Object value = propertyValues.get(property);
            Object value2 = propertyValuesAlternative.get(property);
            Object setValue = property.getFrom(entity);

            if (!(Objects.equals(value, setValue) || Objects.equals(value2, setValue))) {
                Assert.fail("Getter did not return set value for property " + property + " on entity type " + entity.getEntityType());
            }
        } catch (SecurityException | IllegalArgumentException ex) {
            LOGGER.error("Failed to set property", ex);
            Assert.fail("Failed to set property: " + ex.getMessage());
        }
    }

}
