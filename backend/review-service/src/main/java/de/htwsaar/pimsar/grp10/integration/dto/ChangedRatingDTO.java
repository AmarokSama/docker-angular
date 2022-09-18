package de.htwsaar.pimsar.grp10.integration.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Introspected
public class ChangedRatingDTO implements Serializable
{
   private Long objectId;
   private Double oldValue;
   private Double newValue;

   /**
    * constructor with both values
    *
    * @param posterId   poster id
    * @param p_oldValue old rating
    * @param p_newValue new rating
    */
   public ChangedRatingDTO( final Long p_objectId, final Double p_oldValue, final Double p_newValue )
   {
      objectId = p_objectId;
      oldValue = p_oldValue;
      newValue = p_newValue;
   }
}
