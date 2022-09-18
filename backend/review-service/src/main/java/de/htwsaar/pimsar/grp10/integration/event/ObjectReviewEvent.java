package de.htwsaar.pimsar.grp10.integration.event;

import de.htwsaar.pimsar.grp10.integration.dto.ChangedRatingDTO;
import de.htwsaar.pimsar.grp10.service.dto.ObjectReviewDTO;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageBody;

/**
 * event trigger for object review
 */
@KafkaClient()
public interface ObjectReviewEvent
{
   /**
    * sent a rating create event
    *
    * @param p_ObjectId object id
    * @param p_Body     create as body
    */
   @Topic( "object-reviews-create" )
   void sendCreate( @MessageBody final ObjectReviewDTO p_Body );

   /**
    * send a rating change event
    *
    * @param p_ObjectId id of object
    * @param p_Body     change as body
    */
   @Topic( "object-reviews-update" )
   void sendUpdate( @MessageBody final ChangedRatingDTO p_Body );
}
