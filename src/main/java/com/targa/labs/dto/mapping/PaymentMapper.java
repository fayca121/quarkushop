package com.targa.labs.dto.mapping;

import com.targa.labs.domain.Payment;
import com.targa.labs.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface PaymentMapper {

    @Mappings({
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "amount", ignore = true),
    })
    Payment dtoToEntity(PaymentDto dto);

    @Mapping(target = "orderId", ignore = true)
    PaymentDto entityToDto(Payment payment);

    List<Payment> dtoListToEntityList(List<PaymentDto> dtoList);

    List<PaymentDto> entityListToDtoList(List<Payment> payments);
}
