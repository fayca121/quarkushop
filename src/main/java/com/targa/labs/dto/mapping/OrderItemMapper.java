package com.targa.labs.dto.mapping;

import com.targa.labs.domain.OrderItem;
import com.targa.labs.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface OrderItemMapper {

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem dtoToEntity(OrderItemDto dto);

    @Mapping(target = "productId", expression = "java(orderItem.getProduct().getId())")
    @Mapping(target = "orderId", expression = "java(orderItem.getOrder().getId())")
    OrderItemDto entityToDto(OrderItem orderItem);

    List<OrderItem> dtoListToEntityList(List<OrderItemDto> dtoList);

    List<OrderItemDto> entityListToDtoList(List<OrderItem> orderItems);
}
