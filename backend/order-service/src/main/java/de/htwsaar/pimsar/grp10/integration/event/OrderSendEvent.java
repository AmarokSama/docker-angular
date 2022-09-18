package de.htwsaar.pimsar.grp10.integration.event;

import de.htwsaar.pimsar.grp10.service.dto.OrderContentDTO;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import io.micronaut.messaging.annotation.MessageHeader;

import java.util.List;

@KafkaClient
public interface OrderSendEvent
{

   @Topic("order-created")
   void send( @MessageHeader(name = "orderId") final String p_OrderId,
              @MessageBody final List<OrderContentDTO> p_Orders );
}
