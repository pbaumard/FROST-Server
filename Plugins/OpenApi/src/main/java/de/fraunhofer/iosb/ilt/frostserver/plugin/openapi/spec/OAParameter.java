/*
 * Copyright (C) 2019 Fraunhofer Institut IOSB, Fraunhoferstr. 1, D 76131
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
package de.fraunhofer.iosb.ilt.frostserver.plugin.openapi.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An OpenAPI parameter object.
 *
 * @author scf
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class OAParameter {

    public static enum In {
        @JsonProperty("query")
        QUERY,
        @JsonProperty("header")
        HEADER,
        @JsonProperty("path")
        PATH,
        @JsonProperty("cookie")
        COOKIE
    }
    @JsonProperty(value = "$ref")
    public String ref;
    public String name;
    public In in = In.PATH;
    public String description;
    public Boolean required = false;
    public OASchema schema;

    public OAParameter(String refName) {
        ref = "#/components/parameters/" + refName;
        in = null;
        required = null;
    }

    public OAParameter(String name, String description, OASchema schema) {
        this.name = name;
        this.description = description;
        this.schema = schema;
        this.required = true;
    }

    public OAParameter(String name, In in, String description, OASchema schema) {
        this.name = name;
        this.description = description;
        this.schema = schema;
        this.in = in;
    }

}