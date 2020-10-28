package de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.tables;

import de.fraunhofer.iosb.ilt.frostserver.model.EntityType;
import de.fraunhofer.iosb.ilt.frostserver.persistence.IdManager;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.bindings.JsonBinding;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.bindings.JsonValue;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.factories.EntityFactories;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.relations.RelationOneToMany;
import de.fraunhofer.iosb.ilt.frostserver.property.EntityPropertyMain;
import de.fraunhofer.iosb.ilt.frostserver.property.NavigationPropertyMain;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;

public abstract class AbstractTableActuators<J extends Comparable> extends StaTableAbstract<J, AbstractTableActuators<J>> {

    private static final long serialVersionUID = 1850108682;

    /**
     * The column <code>public.ACTUATORS.DESCRIPTION</code>.
     */
    public final TableField<Record, String> colDescription = createField(DSL.name("DESCRIPTION"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.ACTUATORS.ENCODING_TYPE</code>.
     */
    public final TableField<Record, String> colEncodingType = createField(DSL.name("ENCODING_TYPE"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.ACTUATORS.METADATA</code>.
     */
    public final TableField<Record, String> colMetadata = createField(DSL.name("METADATA"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.ACTUATORS.NAME</code>.
     */
    public final TableField<Record, String> colName = createField(DSL.name("NAME"), SQLDataType.CLOB.defaultValue(DSL.field("'no name'::text", SQLDataType.CLOB)), this, "");

    /**
     * The column <code>public.ACTUATORS.PROPERTIES</code>.
     */
    public final TableField<Record, JsonValue> colProperties = createField(DSL.name("PROPERTIES"), DefaultDataType.getDefaultDataType(TYPE_JSONB), this, "", new JsonBinding());

    /**
     * Create a <code>public.ACTUATORS</code> table reference
     */
    protected AbstractTableActuators() {
        this(DSL.name("ACTUATORS"), null);
    }

    protected AbstractTableActuators(Name alias, AbstractTableActuators<J> aliased) {
        this(alias, aliased, null);
    }

    protected AbstractTableActuators(Name alias, AbstractTableActuators<J> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    @Override
    public void initRelations() {
        final TableCollection<J> tables = getTables();
        registerRelation(
                new RelationOneToMany<>(this, tables.getTableTaskingCapabilities(), EntityType.TASKING_CAPABILITY, true)
                        .setSourceFieldAccessor(AbstractTableActuators::getId)
                        .setTargetFieldAccessor(AbstractTableTaskingCapabilities::getActuatorId)
        );
    }

    @Override
    public void initProperties(final EntityFactories<J> entityFactories) {
        final IdManager idManager = entityFactories.idManager;
        pfReg.addEntryId(idManager, AbstractTableActuators::getId);
        pfReg.addEntryString(EntityPropertyMain.NAME, table -> table.colName);
        pfReg.addEntryString(EntityPropertyMain.DESCRIPTION, table -> table.colDescription);
        pfReg.addEntryString(EntityPropertyMain.ENCODINGTYPE, table -> table.colEncodingType);
        pfReg.addEntryString(EntityPropertyMain.METADATA, table -> table.colMetadata);
        pfReg.addEntryMap(EntityPropertyMain.PROPERTIES, table -> table.colProperties);
        pfReg.addEntry(NavigationPropertyMain.TASKINGCAPABILITIES, AbstractTableActuators::getId, idManager);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ACTUATOR;
    }

    @Override
    public abstract TableField<Record, J> getId();

    @Override
    public abstract AbstractTableActuators<J> as(Name as);

    @Override
    public abstract AbstractTableActuators<J> as(String alias);

    @Override
    public AbstractTableActuators<J> getThis() {
        return this;
    }

}
