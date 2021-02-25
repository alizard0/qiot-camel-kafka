# qiot-camel-kafka
Sample project that consumes from and publishes to a Kafka topic using Camel

### dependency
```xml
<dependency>
    <groupId>org.apache.camel.quarkus</groupId>
    <artifactId>camel-quarkus-kafka</artifactId>
</dependency>
```

### sample
1. Setting consumer topic
> from("kafka:" + topicName)
2. Setting producer topic
> from(...).to("kafka:" + topicName)
3. Then use Camel steps to unmarshal, process and marshal the messages.

```java
from("kafka:" + topicName)
    .log(LoggingLevel.INFO, "Message consumed ${body}")
    .unmarshal()
    .json(JsonLibrary.Jackson, KafkaMessage.class)
    .process(exchange -> {
        // do something
    })
    .marshal()
    .json(JsonLibrary.Jackson)
    .log(LoggingLevel.INFO, "Message published ${body}")
.to("kafka:" + topicName);
```
