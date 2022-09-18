package de.htwsaar.pimsar.grp10.service.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.io.Serializable;

@Introspected
@Data
public class InventoryDTO implements Serializable
{
   private Long posterId;
   private Integer quantity;

   /**
    * constructor with zero quantity
    * @param p_posterId poster id
    */
   public InventoryDTO( final Long p_posterId )
   {
      posterId = p_posterId;
      quantity = 0;
   }
}
