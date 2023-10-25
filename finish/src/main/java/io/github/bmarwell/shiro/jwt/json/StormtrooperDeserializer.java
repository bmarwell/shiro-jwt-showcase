/*
 * Copyright (C) 2022-2023 The shiro-jjwt-showcase team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.bmarwell.shiro.jwt.json;

import io.github.bmarwell.shiro.jwt.dto.Stormtrooper;
import java.lang.reflect.Type;
import javax.json.JsonObject;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

public class StormtrooperDeserializer implements JsonbDeserializer<Stormtrooper>, JsonbSerializer<Stormtrooper> {

  @Override
  public Stormtrooper deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
    final JsonObject object = parser.getObject();
    final String id = object.getString("id", "");
    final String planetOfOrigin = object.getString("planet_of_origin");
    final String species = object.getString("species");
    final String type = object.getString("type");

    return new Stormtrooper(id, planetOfOrigin, species, type);
  }

  @Override
  public void serialize(Stormtrooper trooper, JsonGenerator generator, SerializationContext ctx) {
    generator.writeStartObject();
    generator.write("id", trooper.id().isEmpty() ? null : trooper.id());
    generator.write("planet_of_origin", trooper.planetOfOrigin());
    generator.write("species", trooper.species());
    generator.write("type", trooper.type());
    generator.writeEnd();
  }
}
