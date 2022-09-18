package de.htwsaar.pimsar.grp10.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class for Poster sizes
 *
 * @author ngrus
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table( name = "postersize", schema = "poster" )
public class PosterSize implements Serializable
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue( strategy = GenerationType.IDENTITY )
   @Column( name = "id", nullable = false )
   private Long id;

   @Column( name = "name", nullable = false)
   private String name;

   @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
   @ToString.Exclude
   private Set<Poster> poster = new HashSet<>();

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
      final PosterSize v_that = (PosterSize) v_p_o;
      return id != null && Objects.equals( id, v_that.id );
   }

   @Override
   public int hashCode()
   {
      return getClass().hashCode();
   }
}
