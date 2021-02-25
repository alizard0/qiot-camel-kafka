package org.alizardo.rest;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaToKafkaRouteBuilder extends RouteBuilder {

    /**
     * for consumer topic name and is defined at {@code src/main/resources/application.properties}
     */
    @ConfigProperty(name = "kafka.topic.from")
    private String fromTopic;

    /**
     * for producer topic name and is defined at {@code src/main/resources/application.properties}
     */
    @ConfigProperty(name = "kafka.topic.to")
    private String toTopic;

    @Override
    public void configure() {
        from(buildKafkaRoute(fromTopic))
                .log(LoggingLevel.INFO, "Message consumed ${body}")
                .unmarshal()
                .json(JsonLibrary.Jackson, KafkaMessage.class)
                .process(exchange -> {
                    // do something
                })
                .marshal()
                .json(JsonLibrary.Jackson)
                .log(LoggingLevel.INFO, "Message published ${body}")
                .to(buildKafkaRoute(toTopic));
    }

    private String buildKafkaRoute(final String topicName) {
        return "kafka:" + topicName;
    }
}
