package de.htwsaar.pimsar.grp10.repository;

import de.htwsaar.pimsar.grp10.domain.Poster;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * repo to access poster logic
 */
@Repository
public abstract class PosterRepository implements JpaRepository<Poster, Long>
{
   @PersistenceContext
   private final EntityManager _EntityManager;

   /**
    * basic constructor
    * @param p_EntityManager entity manager
    */
   protected PosterRepository( final EntityManager p_EntityManager )
   {
      _EntityManager = p_EntityManager;
   }

   /**
    * save and merge poster data
    * @param p_Poster poster
    * @return saved poster
    */
   @Transactional
   public Poster mergeAndSave( Poster p_Poster )
   {
      p_Poster = _EntityManager.merge( p_Poster );
      return save( p_Poster );
   }

   /**
    * update average and count with a named query
    * @param p_Poster poster id
    * @param p_NewAvgRating new average
    * @param p_NewRatingCount new count
    */
   @Transactional
   public void updateAvrAndCount( final Long p_Poster, final Double p_NewAvgRating, final int p_NewRatingCount )
   {
      final Query c_Query = _EntityManager.createNamedQuery( Poster.UPDATE_AVERAGE_AND_COUNT );
      c_Query.setParameter( "posterId", p_Poster );
      c_Query.setParameter( "newAvr", p_NewAvgRating );
      c_Query.setParameter( "newCount", p_NewRatingCount );

      c_Query.executeUpdate();

      _EntityManager.flush();

   }

   /**
    * update average with a named query
    * @param p_Poster poster id
    * @param p_NewAvgRating new average
    */
   @Transactional
   public void updateAvr( final Long p_Poster, final Double p_NewAvgRating )
   {
      final Query c_Query = _EntityManager.createNamedQuery( Poster.UPDATE_AVERAGE );
      c_Query.setParameter( "posterId", p_Poster );
      c_Query.setParameter( "newAvr", p_NewAvgRating );

      c_Query.executeUpdate();

      _EntityManager.flush();

   }
}
