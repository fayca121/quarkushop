package com.targa.labs.dto.mapping;

import com.targa.labs.domain.Cart;
import com.targa.labs.dto.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "cdi",uses = CustomerMapper.class)
public interface CartMapper {
    @Mappings({
            @Mapping(target = "createdDate",ignore = true),
            @Mapping(target = "lastModifiedDate",ignore = true)
    })
    Cart dtoToEntity(CartDto dto);

    CartDto entityToDto(Cart cart);

    List<Cart> dtoListToEntityList(List<CartDto> dtoList);
    List<CartDto> entityListToDto(List<Cart> carts);
}
