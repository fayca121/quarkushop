package com.targa.labs.dto.mapping;

import com.targa.labs.domain.Review;
import com.targa.labs.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "cdi")
public interface ReviewMapper {

    @Mappings({
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true)
    })
    Review dtoToEntity(ReviewDto dto);

    ReviewDto entityToDto(Review review);

    List<Review> dtoListToEntityList(List<ReviewDto> dtoList);

    List<ReviewDto> entityListToDtoList(List<Review> reviews);

    Set<Review> dtoSetToEntitySet(Set<ReviewDto> dtoSet);

    Set<ReviewDto> entitySetToDtoSet(Set<Review> reviews);

}
