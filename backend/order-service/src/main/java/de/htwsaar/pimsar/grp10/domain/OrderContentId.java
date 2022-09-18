package de.htwsaar.pimsar.grp10.domain;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class OrderContentId implements Serializable
{

   @Column( name = "bestellungid", nullable = false )
   private Long orderId;
   @Column( name = "posterid", nullable = false )
   private Long posterId;

   /**
    * constructor
    * @param p_orderId order Id
    * @param p_posterId poster id
    */
   public OrderContentId( final Long p_orderId, final Long p_posterId )
   {
      orderId = p_orderId;
      posterId = p_posterId;
   }

   public OrderContentId()
   {

   }
}
