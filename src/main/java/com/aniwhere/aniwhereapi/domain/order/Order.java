package com.aniwhere.aniwhereapi.domain.order;


import com.aniwhere.aniwhereapi.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberEmail")
    private Member member;
    //member 랑 manytoone

//
    // 총합 계산
//    public int getCalcTotalPrice() {
//        return orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();
//    }

    // 주문 취소
//    public void cancelOrder() {
//        orderItems.forEach(OrderItem::cancel);
//        this.orderStatus = OrderStatus.CANCEL;
//    }

}
