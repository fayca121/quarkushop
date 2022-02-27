package com.targa.labs.service;

import com.targa.labs.domain.Cart;
import com.targa.labs.domain.Customer;
import com.targa.labs.domain.enums.CartStatus;
import com.targa.labs.dto.CartDto;
import com.targa.labs.dto.mapping.CartMapper;
import com.targa.labs.repository.CartRepository;
import com.targa.labs.repository.CustomerRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CartService {

    private static final Logger log = Logger.getLogger(CartService.class);

    @Inject
    CartRepository cartRepository;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CartMapper cartMapper;

    public List<CartDto> findAll() {
        log.debug("Request to get all Carts");
        return cartMapper.entityListToDto(cartRepository.findAll());
    }

    public List<CartDto> findAllActiveCarts() {
        log.debug("Request to get all activated Carts");
        return cartMapper.entityListToDto(cartRepository.findByStatus(CartStatus.NEW));
    }

    public Cart create(Long customerId) {
        if (getActiveCart(customerId) == null) {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new IllegalStateException("The Customer does not exist!"));
            Cart cart = new Cart(customer, CartStatus.NEW);
            return cartRepository.save(cart);
        } else {
            throw new IllegalStateException("There is already an active cart");
        }
    }

    public CartDto createDto(Long customerId) {
        return cartMapper.entityToDto(create(customerId));
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public CartDto findById(Long id) {
        log.debugf("Request to get Cart : {}", id);
        Cart cart = cartRepository.findById(id).orElse(null);
        return cartMapper.entityToDto(cart);
    }

    public void delete(Long id) {
        log.debugf("Request to delete Cart : {}", id);
        Cart cart = cartRepository.findById(id).orElseThrow(()->{
            throw new IllegalStateException("Cannot find cart with id " + id);
        });
        cart.setStatus(CartStatus.CANCELED);
        cartRepository.save(cart);
    }

    public CartDto getActiveCart(Long customerId) {
        var carts = cartRepository
                .findByStatusAndCustomerId(CartStatus.NEW, customerId);
        if (carts != null) {
            if (carts.size() == 1)
                return cartMapper.entityToDto(carts.get(0));
            if (carts.size() > 1)
                throw new IllegalStateException("Many active carts detected !!!");
        }
        return null;
    }


}
