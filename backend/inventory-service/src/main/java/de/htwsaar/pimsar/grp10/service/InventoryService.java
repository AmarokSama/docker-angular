package de.htwsaar.pimsar.grp10.service;

import de.htwsaar.pimsar.grp10.service.dto.InventoryDTO;

import java.util.Map;

public interface InventoryService
{
   /**
    * find a poster inventory
    *
    * @param p_Id id of poster
    * @return wanted poster inventory or null
    */
   InventoryDTO findOne( Long p_Id );

   /**
    * Save a poster object
    *
    * @param p_DTO dto
    * @return save dto
    */
   InventoryDTO save( InventoryDTO p_DTO );


   Boolean processQuantityCheck( Map<Long, Integer> p_Map );

}
