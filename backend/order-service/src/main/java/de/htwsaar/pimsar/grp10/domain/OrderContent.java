package de.htwsaar.pimsar.grp10.domain;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table( name = "bestellmenge", schema = "bestellung" )
public class OrderContent implements Serializable
{


    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderContentId contentId;

    @Column(name = "menge", nullable = false)
    private Integer quantity = 1;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "bestellungid", insertable = false, updatable = false)
    private Order order;

    @Override
    public boolean equals( final Object v_p_o )
    {
        if( this == v_p_o )
        {
            return true;
        }
        if( v_p_o == null || Hibernate.getClass( this ) != Hibernate.getClass( v_p_o ) )
        {
            return false;
        }
        final OrderContent v_that = (OrderContent) v_p_o;
        return contentId != null && Objects.equals( contentId, v_that.contentId );
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
