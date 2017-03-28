package com.ybl.metrics.controller;

import javax.websocket.server.PathParam;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.ybl.metrics.model.Customer;
import com.ybl.metrics.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @RequestMapping("/{customerId}")
    @Timed(name = "get-customer-timed")
    @ExceptionMetered(name = "get-customer-exception-meter")
    public Customer getCustomer(@PathParam("customerId") final String customerId) {
        Customer customer = service.findCustomer(customerId);
        return customer;
    }

    @RequestMapping("")
    @Timed(name = "create-customer-timed")
    @Counted(name = "create-customer-counted")
    @Metered(name = "create-customer-metered")
    @ExceptionMetered(name = "create-customer-exception-meter")
    public Customer createCustomer(@RequestParam("id") String id, @RequestParam("name") String name) throws Exception {
        Customer createdCustomer = service.newCustomer(id, name);
        return createdCustomer;
    }

}
