package com.rewe.customerbookingservice.services.impl;

import com.rewe.customerbookingservice.data.entities.Customer;
import com.rewe.customerbookingservice.data.repositories.CustomerRepository;
import com.rewe.customerbookingservice.dtos.CustomerDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private final CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, modelMapper);

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customerDTO = modelMapper.map(customer, CustomerDTO.class);
    }

    @Test
    void saveCustomer_shouldReturnSavedCustomerDTO_whenCustomerIsValid() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.saveCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO.getId(), result.getId());
        assertEquals(customerDTO.getName(), result.getName());
    }

    @Test
    void deleteCustomer_shouldReturnTrue_whenCustomerIsDeleted() {
        when(customerRepository.existsById(1L)).thenReturn(false);

        boolean result = customerService.deleteCustomer(1L);

        assertTrue(result);
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomerDTO_whenCustomerExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO updatedDetails = new CustomerDTO();
        updatedDetails.setId(1L);
        updatedDetails.setName("Jane Doe");

        CustomerDTO result = customerService.updateCustomer(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void updateCustomer_shouldReturnException_whenCustomerDoesNotExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        CustomerDTO updatedDetails = new CustomerDTO();
        updatedDetails.setId(1L);
        updatedDetails.setName("Jane Doe");

        // Act and Assert
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> customerService.updateCustomer(1L, updatedDetails)
        );

        assertEquals("Customer with id 1 not found", exception.getMessage());
    }
}