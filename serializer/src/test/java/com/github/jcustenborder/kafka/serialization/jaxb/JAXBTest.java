/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.serialization.jaxb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.Marshaller;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class JAXBTest {
  private static final Logger log = LoggerFactory.getLogger(JAXBTest.class);
  JAXBSerializer serializer;
  JAXBDeserializer<TestPojo> deserializer;

  @BeforeEach
  public void beforeEach() {
    this.serializer = new JAXBSerializer();
    this.deserializer = new JAXBDeserializer<>();
    Map<String, String> settings = new LinkedHashMap<>();
    settings.put(JAXBDeserializerConfig.DESERIALIZED_CLASS_CONF, TestPojo.class.getName());
    settings.put(Marshaller.JAXB_FORMATTED_OUTPUT, "true");
    this.serializer.configure(settings, false);
    this.deserializer.configure(settings, false);
  }


  @Test
  public void roundTrip() {
    final TestPojo expected = new TestPojo();
    expected.age = 6;
    expected.id = 13412;
    expected.name = "Testing User";

    final byte[] buffer = this.serializer.serialize("testing", expected);
    log.info("Serialized as\n{}", new String(buffer, Charset.forName("UTF-8")));

    final TestPojo actual = this.deserializer.deserialize("testing", buffer);
    assertNotNull(actual);
    assertEquals(expected.age, actual.age);
    assertEquals(expected.id, actual.id);
    assertEquals(expected.name, actual.name);
  }

}
