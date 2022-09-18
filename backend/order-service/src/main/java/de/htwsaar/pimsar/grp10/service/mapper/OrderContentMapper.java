package de.htwsaar.pimsar.grp10.service.mapper;

import de.htwsaar.pimsar.grp10.domain.OrderContent;
import de.htwsaar.pimsar.grp10.service.dto.OrderContentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper( componentModel = "jsr330" )
public interface OrderContentMapper extends EntityMapper<OrderContentDTO, OrderContent>
{

   @Mappings( {
      @Mapping( target = "posterId", source = "contentId.posterId" )
   } )
   @Override
   OrderContentDTO toDto( OrderContent entity );
}
