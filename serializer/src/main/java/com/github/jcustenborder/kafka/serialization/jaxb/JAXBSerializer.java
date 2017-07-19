/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.serialization.jaxb;

import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.Serializer;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class JAXBSerializer extends BaseJAXB implements Serializer<Object> {
  JAXBSerializerConfig config;

  @Override
  public void configure(Map<String, ?> map, boolean b) {
    this.config = new JAXBSerializerConfig(map);
  }

  @Override
  public byte[] serialize(String topic, Object value) {
    if (null == value) {
      return null;
    }

    Marshaller marshaller = createMarshaller(value.getClass(), this.config);
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      marshaller.marshal(value, outputStream);
      return outputStream.toByteArray();
    } catch (IOException | JAXBException ex) {
      throw new KafkaException("Exception thrown while serializing value", ex);
    }
  }

  @Override
  public void close() {

  }
}
