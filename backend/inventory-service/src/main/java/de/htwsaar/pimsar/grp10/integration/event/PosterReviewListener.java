package de.htwsaar.pimsar.grp10.integration.event;

import de.htwsaar.pimsar.grp10.integration.dto.ChangedRatingDTO;
import de.htwsaar.pimsar.grp10.integration.dto.ObjectReviewDTO;
import de.htwsaar.pimsar.grp10.service.PosterService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import lombok.extern.slf4j.Slf4j;

/**
 * Listener for kafka poster review event
 */
@Slf4j
@KafkaListener( groupId = "inventory-service" )
public class PosterReviewListener
{
   private final PosterService _PS;


   /**
    * constructor
    *
    * @param p_PS {@link PosterService}
    */
   public PosterReviewListener( final PosterService p_PS )
   {
      _PS = p_PS;
   }

   /**
    * Received created poster review event
    *
    * @param p_DTO poster review dto
    */
   @Topic( "object-reviews-create" )
   public void receiveCreate( @MessageBody final ObjectReviewDTO p_Body )
   {
      log.debug( "Received: posterReview created -> {}", p_Body );
      try
      {
         _PS.updateAverageRating( p_Body.getObjectId(), p_Body.getRating() );
      }
      catch( Exception p_E )
      {
         log.error( "Exception occurred: {}", p_E.toString() );
      }
   }

   /**
    * Received created poster review event
    *
    * @param p_DTO poster review dto
    */
   @Topic( "object-reviews-update" )
   public void receiveUpdate( @MessageBody final ChangedRatingDTO p_Body )
   {
      log.debug( "Received: posterReview Changed -> {}", p_Body );
      try
      {
         _PS.updateAverageRating( p_Body.getObjectId(), p_Body.getOldValue(), p_Body.getNewValue() );
      }
      catch( Exception p_E )
      {
         log.error( "Exception occurred: {}", p_E.toString() );
      }
   }
}
