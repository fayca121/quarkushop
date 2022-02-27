package com.targa.labs.service;

import com.targa.labs.domain.Customer;
import com.targa.labs.dto.CustomerDto;
import com.targa.labs.dto.mapping.CustomerMapper;
import com.targa.labs.repository.CustomerRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class CustomerService {

    public static final Logger log = Logger.getLogger(CustomerService.class);

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerMapper customerMapper;

    public CustomerDto create(CustomerDto customerDto) {
        log.debugf("Request to create Customer : {}", customerDto);
        Customer customer = customerMapper.dtoToEntity(customerDto);
        return customerMapper.entityToDto(customerRepository.save(customer));
    }

    public List<CustomerDto> findAll() {
        log.debug("Request to get all Customers");
        return customerMapper.entityListToDtoList(this.customerRepository.findAll());
    }

    public CustomerDto findById(Long id) {
        log.debugf("Request to get Customer : {}", id);
        return this.customerRepository.findById(id)
                .map(customerMapper::entityToDto)
                .orElse(null);
    }

    public List<CustomerDto> findAllActive() {
        log.debug("Request to get all active customers");
        return customerMapper.entityListToDtoList(customerRepository.findByEnabled(true));
    }

    public List<CustomerDto> findAllInactive() {
        log.debug("Request to get all inactive customers");
        return customerMapper.entityListToDtoList(customerRepository.findByEnabled(false));
    }

    public void delete(Long id) {
        log.debugf("Request to delete Customer : {}", id);
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalStateException("Cannot find Customer with id " + id));
        customer.setEnabled(false);
        this.customerRepository.save(customer);
    }
}
