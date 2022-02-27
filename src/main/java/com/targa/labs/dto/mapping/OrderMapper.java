package com.targa.labs.dto.mapping;

import com.targa.labs.domain.Order;
import com.targa.labs.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "cdi",uses = AddressMapper.class)
public interface OrderMapper {

    @Mappings({
    @Mapping(target = "price", source = "totalPrice",defaultValue = "0"),
    @Mapping(target = "payment", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true),
    @Mapping(target = "createdDate", ignore = true)})
    Order dtoToEntity(OrderDto dto);

    @Mappings({
    @Mapping(target = "totalPrice", source = "price"),
    @Mapping(target = "paymentId",
            expression = "java(order.getPayment()!=null?order.getPayment().getId():null)")})
    OrderDto entityToDto(Order order);

    List<Order> dtoListToEntityList(List<OrderDto> orderDtoList);
    List<OrderDto> entityListToDtoList(List<Order> orders);

}
