package de.htwsaar.pimsar.grp10.service.mapper;

import de.htwsaar.pimsar.grp10.domain.Inventory;
import de.htwsaar.pimsar.grp10.service.dto.InventoryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Inventory}
 * and its DTO {@link InventoryDTO}.
 */
@Mapper( componentModel = "jsr330" )
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory>
{
}
