package de.htwsaar.pimsar.grp10.integration.event;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import io.micronaut.messaging.annotation.MessageHeader;

/**
 * event producer for order related event
 */
@KafkaClient
public interface OrderEvent
{

   @Topic( "order-created-response")
   void send( @MessageHeader(name = "orderId") final String p_OrderId, @MessageBody final String p_Done );
}
