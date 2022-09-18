package de.htwsaar.pimsar.grp10.service.mapper;

import de.htwsaar.pimsar.grp10.domain.Order;
import de.htwsaar.pimsar.grp10.service.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper( componentModel = "jsr330", uses = {OrderContentMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
}
