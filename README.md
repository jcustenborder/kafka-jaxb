# Introduction

Sometimes you just need XML. This project is for one of those times where you just need some xml support in Apache Kafka.
The goal of this project is to aid with storing XML data in [Apache Kafka](http://kafka.apache.org).


[![Maven Central](https://img.shields.io/maven-central/v/com.github.jcustenborder.kafka/kafka-jaxb-parent.svg)](http://search.maven.org/#artifactdetails%7Ccom.github.jcustenborder%7Cmaven-central-parent)

# Producing Data

```java
Properties properties = new Properties();
properties.put(JAXBSerializerConfig.JAXB_FORMATTED_OUTPUT_CONF, "true");
properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.toString());
properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JAXBSerializer.class.toString());
Producer<Integer, TestPojo> producer = new KafkaProducer<>(properties);
```

# Consuming Data

```java
Properties properties = new Properties();
properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.toString());
properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JAXBDeserializer.class.toString());
Consumer<Integer, TestPojo> consumer = new KafkaConsumer<>(properties);
```

# Example Model

```java
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestPojo {
  String name;
  int age;
  int id;

  public String getName() {
    return name;
  }

  @XmlElement
  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  @XmlElement
  public void setAge(int age) {
    this.age = age;
  }

  public int getId() {
    return id;
  }

  @XmlAttribute
  public void setId(int id) {
    this.id = id;
  }
}
```