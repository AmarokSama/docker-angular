package de.htwsaar.pimsar.grp10.service.impl;

import de.htwsaar.pimsar.grp10.domain.Inventory;
import de.htwsaar.pimsar.grp10.repository.InventoryRepository;
import de.htwsaar.pimsar.grp10.service.InventoryService;
import de.htwsaar.pimsar.grp10.service.dto.InventoryDTO;
import de.htwsaar.pimsar.grp10.service.mapper.InventoryMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Transactional
@Slf4j
public class InventoryServiceImpl implements InventoryService
{

   private final InventoryRepository _InventoryRepository;
   private final InventoryMapper _InventoryMapper;

   /**
    * constructor
    *
    * @param p_InventoryRepository Inventory repo
    * @param p_InventoryMapper     inventory mapper
    */
   public InventoryServiceImpl( final InventoryRepository p_InventoryRepository, final InventoryMapper p_InventoryMapper )
   {
      _InventoryRepository = p_InventoryRepository;
      _InventoryMapper = p_InventoryMapper;
   }

   @Override
   public InventoryDTO findOne( final Long p_Id )
   {
      final Optional<Inventory> c_inventory = _InventoryRepository.findById( p_Id );
      if( c_inventory.isPresent() )
      {
         return _InventoryMapper.toDto( c_inventory.get() );
      }
      else
      {
         return new InventoryDTO( p_Id );
      }
   }

   @Override
   public InventoryDTO save( final InventoryDTO p_DTO )
   {
      final Inventory v_Entity = _InventoryMapper.toEntity( p_DTO );
      return _InventoryMapper.toDto( _InventoryRepository.mergeAndSave( v_Entity ) );
   }

   @Override
   public Boolean processQuantityCheck( final Map<Long, Integer> p_Map )
   {
      final List<Inventory> c_Inventories = _InventoryRepository.findAllByIds( p_Map.keySet() );
      if( c_Inventories.size() < p_Map.keySet().size() )
      {
         return Boolean.FALSE;
      }
      final Optional<Inventory> c_HasConflict = c_Inventories
         .stream().filter( p ->
                           {
                              final Integer c_WantedDecreased = p_Map.get( p.getPosterId() );
                              return p.getQuantity() < c_WantedDecreased;
                           } )
         .findAny();
      if( c_HasConflict.isPresent() )
      {
         return Boolean.FALSE;
      }
      p_Map.forEach( ( p_Key, p_Value ) -> _InventoryRepository.incrementDecrementQuantity( p_Key, p_Value, true ) );
      return Boolean.TRUE;
   }
}
