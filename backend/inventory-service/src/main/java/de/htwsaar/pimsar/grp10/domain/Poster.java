package de.htwsaar.pimsar.grp10.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity class for Posters
 * @author ngrus
 */
@NamedQueries( value = {
   @NamedQuery( name = Poster.UPDATE_AVERAGE, query =
      "UPDATE Poster p set p.averageRating = :newAvr where p.id = :posterId"
   ),
   @NamedQuery( name = Poster.UPDATE_AVERAGE_AND_COUNT, query =
      "UPDATE Poster p set p.averageRating = :newAvr, p.ratingCount = :newCount where p.id = :posterId"
   )
})
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table( name = "poster", schema = "poster" )
public class Poster implements Serializable
{

   private static final long serialVersionUID = 1L;

   public final static String UPDATE_AVERAGE = "Poster.UPDATE_AVERAGE";
   public final static String UPDATE_AVERAGE_AND_COUNT = "Poster.UPDATE_AVERAGE_AND_COUNT";

   @Id
   @GeneratedValue( strategy = GenerationType.IDENTITY )
   @Column( name = "id", nullable = false )
   private Long id;

   @Column(name = "title", nullable = false)
   private String title;


   @Column( name = "category" )
   private String category;

   @Column( name = "description" )
   private String description;

   @Column( name = "price", nullable = false )
   private Double price;

   @Column( name = "rating_count" )
   private Integer ratingCount;

   @Column( name = "average_rating" )
   private Double averageRating;

   @ManyToOne
   @JoinColumn(name = "size_id")
   private PosterSize size;

   @JoinColumn(name = "image")
   private String image;

   @OneToOne(mappedBy = "poster", orphanRemoval = true)
   private Inventory inventory;

   @Override
   public boolean equals( final Object v_p_o )
   {
      if( this == v_p_o )
      {
         return true;
      }
      if( v_p_o == null || Hibernate.getClass( this ) != Hibernate.getClass( v_p_o ) )
      {
         return false;
      }
      final Poster v_v_poster = (Poster) v_p_o;
      return id != null && Objects.equals( id, v_v_poster.id );
   }

   @Override
   public int hashCode()
   {
      return getClass().hashCode();
   }
}
