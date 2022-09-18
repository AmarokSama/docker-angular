package de.htwsaar.pimsar.grp10.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries( value = {
   @NamedQuery( name = Inventory.INCREMENT_QUANTITY,
      query = "UPDATE Inventory i set i.quantity = i.quantity + :quantity where i.posterId = :id" ),

   @NamedQuery( name = Inventory.DECREMENT_QUANTITY,
      query = "UPDATE Inventory i set i.quantity = i.quantity - :quantity where i.posterId = :id" ),

   @NamedQuery( name = Inventory.FIND_BY_IDS,
      query = "SELECT i FROM Inventory i where i.posterId in (:ids)" )
} )
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table( name = "inventory", schema = "poster" )
public class Inventory implements Serializable
{

   private static final long serialVersionUID = 1L;

   public static final String INCREMENT_QUANTITY = "Inventory.INCREMENT_QUANTITY";
   public static final String FIND_BY_IDS = "Inventory.FIND_BY_IDS";
   public static final String DECREMENT_QUANTITY = "Inventory.DECREMENT_QUANTITY";

   @Id
   @Column( name = "posterid", nullable = false, unique = true )
   private Long posterId;

   @Column( name = "quantity" )
   private Integer quantity;

   @OneToOne( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
   @JoinColumn( name = "posterid", referencedColumnName = "id" )
   @ToString.Exclude
   private Poster poster;

   @Override
   public boolean equals( final Object p_Obj )
   {
      if( this == p_Obj )
      {
         return true;
      }
      if( p_Obj == null || Hibernate.getClass( this ) != Hibernate.getClass( p_Obj ) )
      {
         return false;
      }
      final Inventory c_That = (Inventory) p_Obj;
      return posterId != null && Objects.equals( posterId, c_That.posterId );
   }

   @Override
   public int hashCode()
   {
      return getClass().hashCode();
   }
}
