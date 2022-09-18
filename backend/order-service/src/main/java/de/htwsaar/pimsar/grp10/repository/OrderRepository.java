package de.htwsaar.pimsar.grp10.repository;

import de.htwsaar.pimsar.grp10.domain.Order;
import de.htwsaar.pimsar.grp10.domain.OrderStatus;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public abstract class OrderRepository implements JpaRepository<Order, Long>
{

   @PersistenceContext
   private final EntityManager _EntityManager;

   protected OrderRepository( final EntityManager p_EntityManager )
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
   public Order mergeAndSave( final Order p_Entity )
   {
      final Order c_Entity = _EntityManager.merge( p_Entity );
      return save( c_Entity );
   }


   /**
    * Update the status of a given order
    *
    * @param p_Id     id of order
    * @param p_Status status
    */
   @Transactional
   public void updateStatus( final Long p_Id, final OrderStatus p_Status )
   {
      final Query c_Query = _EntityManager.createNamedQuery( Order.UPDATE_STATUS );

      c_Query.setParameter( "id", p_Id );
      c_Query.setParameter( "status", p_Status );

      c_Query.executeUpdate();
      _EntityManager.flush();
   }
}
