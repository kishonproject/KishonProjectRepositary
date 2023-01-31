package com.ust.app.model;

import com.ust.app.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Data
public class Cart {
    private List<Product> products;
    private double totalPrice;

    public Cart() {
        this.products = new ArrayList<>();
        this.totalPrice = 0.0;
    }


    public Product addProduct(Product product) {
        this.products.add(product);
        this.totalPrice += product.getProductPrice() * product.getProductQty();
        return product;
    }

    public Product removeProduct(Product product) {
        this.products.remove(product);
        this.totalPrice -= product.getProductPrice() * product.getProductQty();
        return product;
    }

}
