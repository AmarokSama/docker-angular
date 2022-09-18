package de.htwsaar.pimsar.grp10.repository;

import de.htwsaar.pimsar.grp10.domain.PosterSize;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * repo to access poster size logic
 */
@Repository
public abstract class PosterSizeRepository implements JpaRepository<PosterSize, Long>
{
   @PersistenceContext
   private final EntityManager _EntityManager;

   /**
    * basic constructor
    *
    * @param p_EntityManager entity manager
    */
   protected PosterSizeRepository( final EntityManager p_EntityManager )
   {
      _EntityManager = p_EntityManager;
   }

   /**
    * save and merge poster size data
    *
    * @param p_Value poster size
    * @return saved poster size
    */
   @Transactional
   public PosterSize mergeAndSave( PosterSize p_Value )
   {
      p_Value = _EntityManager.merge( p_Value );
      return save( p_Value );
   }
}
