package com.ust.app.service;

import com.ust.app.Exception.CustomerNotFoundException;
import com.ust.app.entity.Customer;
import com.ust.app.repositary.CustomerRepositary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepositary customerRepositary;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Customer registerCustomer(Customer customerRegistration) {
        logger.info("Inside the CustomerService and RegisterCustomer Method");
       Optional<Customer> existingMobile = Optional.ofNullable(customerRepositary.findBymobileNo(customerRegistration.getMobileNo()));
        Optional<Customer> existingUserName = customerRepositary.findByUserName(customerRegistration.getUserName());
 if (existingUserName.isPresent()) {
 throw new CustomerNotFoundException("Customer","Mobile",customerRegistration.getMobileNo());
 } else if (existingUserName.isPresent()){
 throw new CustomerNotFoundException("Customer","Name",customerRegistration.getCustomerName());
}
        customerRegistration.setPassword(passwordEncoder.encode(customerRegistration.getPassword()));
 return customerRepositary.save(customerRegistration);
}
    public List<Customer> getAllCustomers() {
        logger.info("Inside the CustomerService and getAllCustomer Method");
        return customerRepositary.findAll();
    }

    public Customer getCustomer(int customerId) {
        logger.info("Inside the CustomerService and getCustomer Method");
        Customer farmer=this.customerRepositary.findById(customerId).orElseThrow(()-> new
                CustomerNotFoundException("Customer","Id",customerId));
        return customerRepositary.findById(customerId).get();
    }

    public Customer updateCustomerDet(Customer customer,int customerId) {
        logger.info("Inside the CustomerService and updateCustomerDet Method");
        Customer cus= customerRepositary.findById(customerId).orElseThrow(
                ()-> new CustomerNotFoundException("Customer","Id", customerId));
            cus.setCustomerId(customer.getCustomerId());
            cus.setCustomerName(customer.getCustomerName());
            cus.setAddress(customer.getAddress());
            cus.setPincode(customer.getPincode());
            cus.setPassword(customer.getPassword());
            cus.setAadharNo(customer.getAadharNo());
            cus.setMobileNo(customer.getMobileNo());
            cus.setEmailId(customer.getEmailId());
            cus.setUserName(customer.getUserName());
        return  customerRepositary.save(cus);
    }

    public String deleteCustomer(int customerId) {
        logger.info("Inside the CustomerService and deleteCustomer Method");
        Customer cust= customerRepositary.findById(customerId).orElseThrow( ()->
                new CustomerNotFoundException("Customer","Id" ,customerId));
         customerRepositary.deleteById(customerId);
         return "Customer Deleted SuccessFully";
    }
}
