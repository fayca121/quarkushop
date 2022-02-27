package com.targa.labs.dto.mapping;

import com.targa.labs.domain.Category;
import com.targa.labs.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CategoryMapper {

    @Mappings({
            @Mapping(target = "createdDate",ignore = true),
            @Mapping(target = "lastModifiedDate",ignore = true)
    })
    Category dtoToEntity(CategoryDto dto);

    @Mapping(target = "products",ignore = true)
    CategoryDto entityToDto(Category category);

    List<Category> dtoListToEntityList(List<CategoryDto> categoryDtoList);
    List<CategoryDto> entityListToDtoList(List<Category> categories);
}
