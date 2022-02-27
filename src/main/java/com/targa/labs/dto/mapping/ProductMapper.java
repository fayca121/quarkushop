package com.targa.labs.dto.mapping;

import com.targa.labs.domain.Product;
import com.targa.labs.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "cdi",uses = ReviewMapper.class)
public interface ProductMapper {
    @Mappings({
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "category",ignore = true)
    })
    Product dtoToEntity(ProductDto dto);

    @Mapping(target = "categoryId", expression = "java(product.getCategory().getId())")
    ProductDto entityToDto(Product product);

    List<Product> dtoListToEntityList(List<ProductDto> dtoList);

    List<ProductDto> entityListToDtoList(List<Product> products);
}
