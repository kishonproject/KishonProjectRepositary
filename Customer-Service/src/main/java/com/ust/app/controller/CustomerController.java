package com.ust.app.controller;

import com.ust.app.CustomerServiceApplication;
import com.ust.app.model.*;
import com.ust.app.entity.Customer;
import com.ust.app.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/customer")

//@SessionAttributes("cart")
public class CustomerController {

    private Cart cart;


    @Autowired
    public CustomerController(Cart cart) {
        this.cart = cart;

    }



    @Autowired
    private CustomerService customerService;

    @Autowired
    private FarmerRestConsumer consumer;


    @PostMapping("/register")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer1)
    {
        Customer _customer = customerService.registerCustomer(customer1);
         return new ResponseEntity<>(_customer, HttpStatus.CREATED);
    }

    @GetMapping("/getallcustomers")
    public ResponseEntity<List<Customer>> getcustomers( )
    {
        List<Customer> _customer= customerService.getAllCustomers();
        return new ResponseEntity<>(_customer,HttpStatus.OK);
    }

    @GetMapping("/getcustomer/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId) {
        Customer _customer = customerService.getCustomer(customerId);
        return new ResponseEntity<>(_customer, HttpStatus.OK);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable int customerId)
    {
        Customer _customer= customerService.updateCustomerDet(customer,customerId);
        return new ResponseEntity<>(_customer,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    //@PreAuthorize("hasAuthority('CUS_ROLE')")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId)
    {
       String value=  customerService.deleteCustomer(customerId);
         return new ResponseEntity<>(value,HttpStatus.OK);
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<Farmer> getFarmerInfo(@PathVariable Integer farmerId) {
        Farmer _farmer=  consumer.getFarmerData(farmerId);
        return new ResponseEntity<>(_farmer,HttpStatus.OK);
    }

    @GetMapping("/farmer/products/{farmerId}")
    public ResponseEntity<List<Product>> getProductsByFarmerId(@PathVariable Integer farmerId) {
          List<Product> _product=consumer.getProductDataById(farmerId);
          return new ResponseEntity<>(_product,HttpStatus.OK);
    }

    @GetMapping("/product/allproducts")
    public ResponseEntity<List<Product>> getallProducts() {
        List<Product> _products= consumer.getproductDetails();
        return new ResponseEntity<>(_products,HttpStatus.OK);
    }

    @GetMapping("/product/getProductById/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId){
        Product _product= consumer.getProductById(productId);
        return new ResponseEntity<>(_product,HttpStatus.OK);
    }



    @GetMapping("/addToCart/{productId}/{formerId}/{customerId}")
        public ResponseEntity<CartDetails> addToCart( @PathVariable("productId") int productId,@PathVariable("formerId") int formerId ,@PathVariable("customerId") int customerId) {
        CartDetails cartDetails=new CartDetails();
        cartDetails.setFarmer(consumer.getFarmerData(formerId));
        cartDetails.setCustomer(customerService.getCustomer(customerId));
        cartDetails.setProduct ( consumer.getProductById(productId));
        Product product =consumer.getProductById(productId);
        cart.addProduct(product);
        return new ResponseEntity<>(cartDetails,HttpStatus.OK);
    }

    @GetMapping("/removeFromCart/{productId}")
    public ResponseEntity<String> removeFromCart(  @PathVariable("productId") int productId) {
        Product product = consumer.getProductById(productId);
          cart.removeProduct(product);
          return new ResponseEntity<>( "Product Removed you can check in cart",HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> viewCart() {
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }



}
