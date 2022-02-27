package com.targa.labs.repository;

import com.targa.labs.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByEnabled(Boolean enabled);
}
