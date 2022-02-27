package com.targa.labs.dto.mapping;

import com.targa.labs.domain.Address;
import com.targa.labs.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AddressMapper {

    @Mapping(target = "postCode", source = "postcode")
    Address dtoToEntity(AddressDto dto);

    @Mapping(target = "postcode", source = "postCode")
    AddressDto entityToDto(Address address);
}
