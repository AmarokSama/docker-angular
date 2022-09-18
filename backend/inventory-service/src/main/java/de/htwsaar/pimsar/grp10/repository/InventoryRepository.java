package de.htwsaar.pimsar.grp10.repository;

import de.htwsaar.pimsar.grp10.domain.Inventory;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.transaction.annotation.ReadOnly;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 * repo to access{@link Inventory} logic
 */
@Repository
public abstract class InventoryRepository implements JpaRepository<Inventory, Long>
{
   @PersistenceContext
   private final EntityManager _EntityManager;

   /**
    * basic constructor
    *
    * @param p_EntityManager entity manager
    */
   protected InventoryRepository( final EntityManager p_EntityManager )
   {
      _EntityManager = p_EntityManager;
   }

   /**
    * save and merge inventory data
    *
    * @param p_Entity inventory
    * @return saved inventory
    */
   @Transactional
   public Inventory mergeAndSave( final Inventory p_Entity )
   {
      final Inventory c_Entity = _EntityManager.merge( p_Entity );
      return save( c_Entity );
   }


   /**
    * update average and count with a named query
    *
    * @param p_Id         poster id
    * @param p_Quantity   quantity to add/remove
    * @param p_Decrement true to remove
    */
   @Transactional
   public void incrementDecrementQuantity( final Long p_Id, final int p_Quantity, final boolean p_Decrement )
   {
      final Query c_Query;
      if( p_Decrement )
      {
         c_Query = _EntityManager.createNamedQuery( Inventory.DECREMENT_QUANTITY );
      }
      else
      {
         c_Query = _EntityManager.createNamedQuery( Inventory.INCREMENT_QUANTITY );
      }
      c_Query.setParameter( "id", p_Id );
      c_Query.setParameter( "quantity", p_Quantity );

      c_Query.executeUpdate();

      _EntityManager.flush();

   }

   /**
    * find all by ids
    *
    * @param p_Ids        poster ids
    */
   @ReadOnly
   public List<Inventory> findAllByIds( final Collection<Long> p_Ids )
   {
      final TypedQuery<Inventory> c_Query = _EntityManager.createNamedQuery( Inventory.FIND_BY_IDS, Inventory.class );
      c_Query.setParameter( "ids", p_Ids );

      return c_Query.getResultList();

   }
}
