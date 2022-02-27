package com.targa.labs.service;

import com.targa.labs.domain.OrderItem;
import com.targa.labs.dto.OrderItemDto;
import com.targa.labs.dto.mapping.OrderItemMapper;
import com.targa.labs.repository.OrderItemRepository;
import com.targa.labs.repository.OrderRepository;
import com.targa.labs.repository.ProductRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class OrderItemService {

    private static final Logger log=Logger.getLogger(OrderItemService.class);

    @Inject
    OrderItemRepository orderItemRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    OrderItemMapper mapper;

    public OrderItemDto create(OrderItemDto orderItemDto) {
        log.debugf("Request to create OrderItem : {}", orderItemDto);
        var order =
                this.orderRepository
                        .findById(orderItemDto.getOrderId())
                        .orElseThrow(() -> new IllegalStateException("The Order does not exist!"));

        var product =
                this.productRepository
                        .findById(orderItemDto.getProductId())
                        .orElseThrow(() -> new IllegalStateException("The Product does not exist!"));


        var orderItem = mapper.dtoToEntity(orderItemDto) ;
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem = orderItemRepository.save(orderItem);

        order.setPrice(order.getPrice().add(orderItem.getProduct().getPrice()));
        orderRepository.save(order);

        orderItemDto.setId(orderItem.getId());

        return orderItemDto;
    }

    public void delete(Long id) {
        log.debugf("Request to delete OrderItem : {}", id);

        var orderItem = this.orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("The OrderItem does not exist!"));

        var order = orderItem.getOrder();
        order.setPrice(
                order.getPrice().subtract(orderItem.getProduct().getPrice())
        );

        this.orderItemRepository.deleteById(id);

        order.getOrderItems().remove(orderItem);

        this.orderRepository.save(order);
    }

    public List<OrderItemDto> findByOrderId(Long id) {
        log.debugf("Request to get all OrderItems of OrderId {}", id);
        return mapper.entityListToDtoList(this.orderItemRepository.findAllByOrderId(id));

    }

    public OrderItemDto findById(Long id) {
        log.debugf("Request to get OrderItem : {}", id);
        return this.orderItemRepository.findById(id)
                .map(mapper::entityToDto)
                .orElse(null);
    }

}
