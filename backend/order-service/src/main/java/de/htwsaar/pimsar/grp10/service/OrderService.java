package de.htwsaar.pimsar.grp10.service;

import de.htwsaar.pimsar.grp10.domain.OrderStatus;
import de.htwsaar.pimsar.grp10.service.dto.OrderDTO;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.Optional;

/**
 * Service interface for managing {@link de.htwsaar.pimsar.grp10.domain.Order}
 */
public interface OrderService {


    /**
     * Create a new Order
     *
     * @param p_Order order to create
     * @return created order
     */
    OrderDTO createOrder(OrderDTO p_Order);

    /**
     * find all orders
     *
     * @param p_Page paging
     * @return page of orders
     */
    Page<OrderDTO> findAll(Pageable p_Page);

    /**
     * find a specific order
     *
     * @param p_Id of order
     * @return wanted order
     */
    Optional<OrderDTO> findOne(Long p_Id);

    /**
     * delete a specific order
     *
     * @param p_Id id of order
     */
    void delete(Long p_Id);

    /**
     * update the order status for a given order
     * @param p_Id id of order
     * @param p_Status order status
     */
    void updateOrderStatus( Long p_Id, OrderStatus p_Status );
}
