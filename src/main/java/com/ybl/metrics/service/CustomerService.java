package com.ybl.metrics.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.annotation.CachedGauge;
import com.codahale.metrics.annotation.Timed;
import com.ybl.metrics.model.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    private static final Log LOG = LogFactory.getLog(CustomerService.class);

    private final Map<String, Customer> customerMap = Collections.synchronizedMap(new HashMap<String, Customer>());

    public CustomerService() {
        createAndStoreDefaultCustomer();
    }

    // 定期跑这个数据(方法不能有参数)
    @CachedGauge(name = "customerNum", timeout = 30, timeoutUnit = TimeUnit.SECONDS)
    public int customerNum() {
        return customerMap.size();
    }

    @Timed(name = "new-customer-timed")
    public Customer newCustomer(String id, String name) {
        if (customerMap.containsKey(id)) {
            throw new IllegalArgumentException("Customer already exists for id: " + id);
        }

        Customer customer = storeCustomer(id, name);

        return customer;
    }

    @Timed(name = "find-customer-timed")
    public Customer findCustomer(String id) {
        return customerMap.get(id);
    }

    private void createAndStoreDefaultCustomer() {
        storeCustomer("1", "Bob");
    }

    private Customer storeCustomer(String id, String name) {
        Customer customer = new Customer(id, name);
        customerMap.put(id, customer);
        LOG.debug("Customer map: " + customerMap);
        return customer;
    }

}
