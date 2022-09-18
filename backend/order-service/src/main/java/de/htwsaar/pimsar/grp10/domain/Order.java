package de.htwsaar.pimsar.grp10.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@NamedQueries( value = {
   @NamedQuery( name = Order.UPDATE_STATUS, query = "UPDATE Order o SET o.status = :status WHERE o.id = :id")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "bestellungen", schema = "bestellung")
public class Order implements Serializable
{


    private static final long serialVersionUID = 1L;

    public static final String UPDATE_STATUS =  "Order.UPDATE_STATUS";

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", nullable = false )
    private Long id;

    @Column( name = "userid", nullable = false )
    private Long userId;

    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "totalprice", nullable = false)
    private Double totalPrice;

    @Enumerated( EnumType.STRING )
    @Column(name = "paymentmethod", nullable = false)
    private PaymentEnum paymentMethod;

    @OneToMany( mappedBy = "order", orphanRemoval = true)
    @ToString.Exclude
    private Set<OrderContent> contents = new HashSet<>();

    public void setContents( final Set<OrderContent> p_contents )
    {
        contents = p_contents;
        final Double c_Total = p_contents.stream().map( p -> p.getQuantity() * p.getPrice() )
                                          .reduce( 0D, Double::sum );
        totalPrice = c_Total;
    }

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
        final Order v_v_order = (Order) v_p_o;
        return id != null && Objects.equals( id, v_v_order.id );
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
