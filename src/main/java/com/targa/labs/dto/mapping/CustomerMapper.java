package com.targa.labs.dto.mapping;

import com.targa.labs.domain.Customer;
import com.targa.labs.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CustomerMapper {

    @Mappings({
            @Mapping(target = "enabled", constant = "true"),
            @Mapping(target = "carts",ignore = true),
            @Mapping(target = "createdDate",ignore = true),
            @Mapping(target = "lastModifiedDate",ignore = true)
    })
    Customer dtoToEntity(CustomerDto dto);

    CustomerDto entityToDto(Customer customer);

    List<Customer> dtoListToEntityList(List<CustomerDto> dtoList);

    List<CustomerDto> entityListToDtoList(List<Customer> customers);
}
