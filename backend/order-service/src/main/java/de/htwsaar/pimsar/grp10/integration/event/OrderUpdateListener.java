package de.htwsaar.pimsar.grp10.integration.event;

import de.htwsaar.pimsar.grp10.domain.OrderStatus;
import de.htwsaar.pimsar.grp10.service.OrderService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import io.micronaut.messaging.annotation.MessageHeader;
import lombok.extern.slf4j.Slf4j;

@KafkaListener("order-service")
@Slf4j
public class OrderUpdateListener
{

   private final OrderService _Service;

   /**
    * constructor
    * @param p_Service order service
    */
   public OrderUpdateListener( final OrderService p_Service )
   {
      _Service = p_Service;
   }

   @Topic("order-created-response")
   public void updateOrderStatus( @MessageHeader( name = "orderId" ) final String p_OrderId,
                                  @MessageBody final String p_Done )
   {
      log.debug( "Received: update inventory -> id: {}, success: {}", p_OrderId, p_Done );
      final Boolean c_Done = Boolean.valueOf( p_Done );
      final Long c_Id = Long.parseLong( p_OrderId );
      try
      {
         _Service.updateOrderStatus( c_Id, c_Done.equals( Boolean.TRUE ) ? OrderStatus.APPROVED : OrderStatus.REJECTED );
      }
      catch( Exception p_Ex )
      {
         log.error( "Error occurred: {}", p_Ex.toString() );
      }
   }
}
