package com.targa.labs.service;

import com.targa.labs.domain.Order;
import com.targa.labs.domain.enums.OrderStatus;
import com.targa.labs.dto.OrderDto;
import com.targa.labs.dto.mapping.OrderMapper;
import com.targa.labs.repository.CartRepository;
import com.targa.labs.repository.OrderRepository;
import com.targa.labs.repository.PaymentRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class OrderService {

    private static final Logger log=Logger.getLogger(OrderService.class);

    @Inject
    OrderRepository orderRepository;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    CartRepository cartRepository;

    @Inject
    OrderMapper mapper;

    public List<OrderDto> findAll() {
        log.debug("Request to get all Orders");
        return mapper.entityListToDtoList(this.orderRepository.findAll());
    }

    public OrderDto findById(Long id) {
        log.debugf("Request to get Order : {}", id);
        return this.orderRepository.findById(id)
                .map(mapper::entityToDto)
                .orElse(null);
    }

    public List<OrderDto> findAllByUser(Long id) {
        return mapper.entityListToDtoList(this.orderRepository.findByCartCustomerId(id));
    }

    public OrderDto create(OrderDto orderDto) {
        log.debugf("Request to create Order : {}", orderDto);

        var cartId = orderDto.getCart().getId();
        var cart = this.cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new IllegalStateException("The Cart with ID[" + cartId + "] was not found !"));
        Order order = mapper.dtoToEntity(orderDto);
        order.setCart(cart);
        order.setStatus(OrderStatus.CREATION);
        return mapper.entityToDto(this.orderRepository.save(order));
    }

    public void delete(Long id) {
        log.debugf("Request to delete Order : {}", id);

        var order = this.orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Order with ID[" + id + "] cannot be found!"));

        Optional.ofNullable(order.getPayment()).ifPresent(paymentRepository::delete);

        orderRepository.delete(order);
    }

    public boolean existsById(Long id) {
        return this.orderRepository.existsById(id);
    }

}
