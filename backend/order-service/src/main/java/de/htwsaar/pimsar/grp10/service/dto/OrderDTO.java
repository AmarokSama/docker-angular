package de.htwsaar.pimsar.grp10.service.dto;

import de.htwsaar.pimsar.grp10.domain.OrderStatus;
import de.htwsaar.pimsar.grp10.domain.PaymentEnum;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Introspected
public class OrderDTO implements Serializable
{
    private Long id;
    private Long userId;
    private String address;
    private OrderStatus status;
    private int totalPrice;
    private PaymentEnum paymentMethod;
    private List<OrderContentDTO> contents;
}
