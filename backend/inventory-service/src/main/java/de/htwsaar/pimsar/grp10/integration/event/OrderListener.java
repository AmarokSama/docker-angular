package de.htwsaar.pimsar.grp10.integration.event;

import de.htwsaar.pimsar.grp10.integration.dto.OrderContentDTO;
import de.htwsaar.pimsar.grp10.service.InventoryService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import io.micronaut.messaging.annotation.MessageHeader;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * event listener for order related event
 */
@Slf4j
@KafkaListener( groupId = "inventory-service" )
public class OrderListener
{

   private final InventoryService _InventoryService;
   private final OrderEvent _Event;


   /**
    * constructor
    * @param p_InventoryService inventory service
    * @param p_Event kafka order event
    */
   public OrderListener( final InventoryService p_InventoryService, final OrderEvent p_Event )
   {
      _InventoryService = p_InventoryService;
      _Event = p_Event;
   }


   @Topic( "order-created" )
   public void getQuantityToChange( @MessageHeader( name = "orderId" ) final String p_OrderId,
                                     @MessageBody final List<OrderContentDTO> p_Orders )
   {
      log.debug( "Received: inventory quantity Changed -> {}", p_OrderId );
      try
      {
         final Map<Long, Integer> v_QuantityToObjectIdMap = p_Orders.stream()
                                                      .collect( Collectors.toMap( OrderContentDTO::getPosterId,
                                                                                  OrderContentDTO::getQuantity ) );
         final Boolean v_Response = _InventoryService.processQuantityCheck( v_QuantityToObjectIdMap );
         _Event.send( p_OrderId, v_Response.toString() );
      }
      catch( Exception p_E )
      {
         _Event.send( p_OrderId, Boolean.FALSE.toString() );
         log.error( "error occurred: {}", p_E.toString() );
      }
   }



}
