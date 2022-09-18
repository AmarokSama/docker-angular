package de.htwsaar.pimsar.grp10.integration.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.io.Serializable;

@Data
@Introspected
public class OrderContentDTO implements Serializable {
    private Long posterId;
    private Integer quantity;
    private Double price;
}
