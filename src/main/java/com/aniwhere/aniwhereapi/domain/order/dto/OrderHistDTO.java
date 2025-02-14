package com.aniwhere.aniwhereapi.domain.order.dto;

import com.aniwhere.aniwhereapi.domain.order.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.aniwhere.aniwhereapi.domain.order.entity.Order;
import com.aniwhere.aniwhereapi.domain.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderHistDTO {

    private Long orderId;       // 주문 아이디
    private String orderCode;   // 주문 코드
    private String email;       // 주문자 이메일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDate;    // 주문 날짜
    private OrderStatus orderStatus;    // 주문 상태
    private Integer totalPrice;         // 주문 총 금액

    @Builder.Default
    private List<OrderItemDTO> orderItems = new ArrayList<>();

    // order -> orderHistDTO 변환
    public static OrderHistDTO from(Order order) {
        OrderHistDTO dto = OrderHistDTO.builder()
                .orderId(order.getId())
                .orderCode(order.getCode())
                .email(order.getMember().getEmail())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .build();

        // 주문 아이템을 OrderItemDTO로 변환하여 추가
        for (OrderItem item : order.getOrderItems()) {
            String imgUrl = item.getProduct().getImageList().isEmpty() ? null : item.getProduct().getImageList().get(0).getImageName();
            dto.addOrderItemDto(OrderItemDTO.from(item, imgUrl));
        }

        return dto;
    }

    // 주문 상품 리스트 추가
    public void addOrderItemDto(OrderItemDTO orderItemDto) {
        orderItems.add(orderItemDto);
    }
}

