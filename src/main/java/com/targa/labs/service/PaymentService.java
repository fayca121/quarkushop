package com.targa.labs.service;

import com.targa.labs.domain.Order;
import com.targa.labs.domain.Payment;
import com.targa.labs.domain.enums.OrderStatus;
import com.targa.labs.dto.PaymentDto;
import com.targa.labs.dto.mapping.PaymentMapper;
import com.targa.labs.repository.OrderRepository;
import com.targa.labs.repository.PaymentRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class PaymentService {

    private static final Logger log=Logger.getLogger(PaymentService.class);

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    PaymentMapper mapper;

    public List<PaymentDto> findByPriceRange(Double max) {
        return this.paymentRepository
                .findAllByAmountBetween(BigDecimal.ZERO, BigDecimal.valueOf(max)).stream()
                .map(this::updatePaymentOrder)
                .collect(Collectors.toList());
    }

    public List<PaymentDto> findAll() {
        log.debug("Request to get all Payments");
        return this.paymentRepository.findAll().stream()
                .map(this::updatePaymentOrder)
                .collect(Collectors.toList());
    }

    public PaymentDto findById(Long id) {
        log.debugf("Request to get Payment : {}", id);
        return this.paymentRepository
                .findById(id)
                .map(this::updatePaymentOrder).orElse(null);
    }

    public PaymentDto create(PaymentDto paymentDto) {
        log.debugf("Request to create Payment : {}", paymentDto);

        var order =
                this.orderRepository
                        .findById(paymentDto.getOrderId())
                        .orElseThrow(() -> new IllegalStateException("The Order does not exist!"));

        order.setStatus(OrderStatus.PAID);

        Payment payment= mapper.dtoToEntity(paymentDto);
        payment.setAmount(order.getPrice());


        payment = paymentRepository.saveAndFlush(payment);
        order.setPayment(payment);
        orderRepository.saveAndFlush(order);
        paymentDto.setId(payment.getId());
        return paymentDto;
    }

    private Order findOrderByPaymentId(Long id) {
        return this.orderRepository.findByPaymentId(id)
                .orElseThrow(() -> new IllegalStateException("No Order exists for the Payment ID " + id));
    }

    public void delete(Long id) {
        log.debugf("Request to delete Payment : {}", id);
        Order order = findOrderByPaymentId(id);
        order.setPayment(null);
        order.setStatus(OrderStatus.CREATION);
        orderRepository.save(order);
        this.paymentRepository.deleteById(id);
    }

    private PaymentDto updatePaymentOrder(Payment payment) {
        if(payment == null)
            return null;
        PaymentDto paymentDto = mapper.entityToDto(payment);
        var order = findOrderByPaymentId(payment.getId());
        paymentDto.setOrderId(order.getId());
        return paymentDto;
    }
}
