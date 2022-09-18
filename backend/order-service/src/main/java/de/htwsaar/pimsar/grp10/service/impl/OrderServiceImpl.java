package de.htwsaar.pimsar.grp10.service.impl;

import de.htwsaar.pimsar.grp10.domain.Order;
import de.htwsaar.pimsar.grp10.domain.OrderContent;
import de.htwsaar.pimsar.grp10.domain.OrderContentId;
import de.htwsaar.pimsar.grp10.domain.OrderStatus;
import de.htwsaar.pimsar.grp10.integration.event.OrderSendEvent;
import de.htwsaar.pimsar.grp10.repository.OrderContentRepository;
import de.htwsaar.pimsar.grp10.repository.OrderRepository;
import de.htwsaar.pimsar.grp10.service.OrderService;
import de.htwsaar.pimsar.grp10.service.dto.OrderContentDTO;
import de.htwsaar.pimsar.grp10.service.dto.OrderDTO;
import de.htwsaar.pimsar.grp10.service.mapper.OrderContentMapper;
import de.htwsaar.pimsar.grp10.service.mapper.OrderMapper;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.transaction.annotation.ReadOnly;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService
{


   private final OrderRepository _OrderRepo;
   private final OrderMapper _OrderMapper;
   private final OrderContentMapper _OrderContentMapper;
   private final OrderContentRepository _OrderContentRepo;
   private final OrderSendEvent _OrderSendEvent;

   /**
    * constructor
    *
    * @param p_OrderRepo        order repo
    * @param p_OrderMapper      order mapper
    * @param p_OrderSendEvent   order event
    * @param p_OrderContentRepo order content repo
    */
   public OrderServiceImpl( final OrderRepository p_OrderRepo, final OrderMapper p_OrderMapper,
                            final OrderSendEvent p_OrderSendEvent,
                            final OrderContentMapper p_OrderContentMapper, final OrderContentRepository p_OrderContentRepo )
   {
      _OrderRepo = p_OrderRepo;
      _OrderMapper = p_OrderMapper;
      _OrderSendEvent = p_OrderSendEvent;
      _OrderContentMapper = p_OrderContentMapper;
      _OrderContentRepo = p_OrderContentRepo;
   }

   @Override
   public OrderDTO createOrder( final OrderDTO p_Order )
   {
      log.debug( "Request to create Order: {}", p_Order );
      p_Order.setStatus( OrderStatus.PENDING );
      final List<OrderContentDTO> c_Content = p_Order.getContents();
      p_Order.setContents( new ArrayList<>() );
      Order v_Order = _OrderMapper.toEntity( p_Order );
      v_Order = _OrderRepo.save( v_Order );
      final Long c_OrderId = v_Order.getId();
      final List<OrderContent> c_ContentEntities = c_Content
         .stream()
         .map( p_DTO ->
               {
                  final OrderContentId c_Id = new OrderContentId( c_OrderId, p_DTO.getPosterId());
                  final OrderContent c_Entity = _OrderContentMapper.toEntity( p_DTO );
                  c_Entity.setContentId( c_Id );
                  return c_Entity;
               } )
         .collect( Collectors.toList() );

      v_Order.setContents( new HashSet<>( _OrderContentRepo.saveAll( c_ContentEntities ) ) );
      v_Order = _OrderRepo.mergeAndSave( v_Order );
      final OrderDTO c_DTO = _OrderMapper.toDto( v_Order );
      _OrderSendEvent.send( c_OrderId.toString(), c_DTO.getContents() );
      return c_DTO;
   }

   @Override
   @Transactional
   @ReadOnly
   public Page<OrderDTO> findAll( final Pageable p_Page )
   {
      log.debug( "Request to find all Orders: {}", p_Page );
      return _OrderRepo.findAll( p_Page ).map( _OrderMapper::toDto );
   }

   @Override
   @Transactional
   @ReadOnly
   public Optional<OrderDTO> findOne( final Long p_Id )
   {
      log.debug( "Request to find a single Order: {}", p_Id );
      return _OrderRepo.findById( p_Id ).map( _OrderMapper::toDto );
   }

   @Override
   public void delete( final Long p_Id )
   {
      log.debug( "Request to delete Order: {}", p_Id );
      _OrderRepo.deleteById( p_Id );
   }

   @Override
   public void updateOrderStatus( final Long p_Id, final OrderStatus p_Status )
   {
      log.debug( "Request to update an order status => id:{}, status:{}", p_Id, p_Status );
      _OrderRepo.updateStatus( p_Id, p_Status );
   }
}
