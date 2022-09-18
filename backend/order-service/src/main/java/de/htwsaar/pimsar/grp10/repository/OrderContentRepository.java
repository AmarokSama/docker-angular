package de.htwsaar.pimsar.grp10.repository;

import de.htwsaar.pimsar.grp10.domain.Order;
import de.htwsaar.pimsar.grp10.domain.OrderContent;
import de.htwsaar.pimsar.grp10.domain.OrderContentId;
import de.htwsaar.pimsar.grp10.domain.OrderStatus;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
public abstract class OrderContentRepository implements JpaRepository<OrderContent, OrderContentId> {

    @PersistenceContext
    private final EntityManager _EntityManager;

    protected OrderContentRepository( final EntityManager p_EntityManager)
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
    public OrderContent mergeAndSave( final OrderContent p_Entity )
    {
        final OrderContent c_Entity = _EntityManager.merge( p_Entity );
        return save( c_Entity );
    }

}
