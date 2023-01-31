package com.ust.kishon.Service;
import com.ust.kishon.Entity.Product;
import com.ust.kishon.Exception.FarmerNotFoundException;
import com.ust.kishon.Exception.ProductNotFoundException;
import com.ust.kishon.Repo.FarmerRepo;
import com.ust.kishon.Repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
      private FarmerRepo farmerRepo;
    @Autowired
    private ProductRepo productRepo;

    public Product submitProduct( Product product,int farmerId){
        logger.info("Inside the ProductService and submitProduct Method");
        Product prd= farmerRepo.findById(farmerId).map(farmer -> {
            product.setFarmer(farmer);
            return   productRepo.save(product);
        }).orElseThrow(() -> new FarmerNotFoundException("Farmer","Id", farmerId));
        return prd;

    }

    public List<Product> getProductDetails() {
        logger.info("Inside the ProductService and getProductProduct Method");
        return productRepo.findAll();
    }

    public Product getproductById(int productId){
        logger.info("Inside the ProductService and getProductById Method");
        return productRepo.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product","Id",productId));}

    public Product updateProduct(int productId, Product productDao){
        logger.info("Inside the ProductService and updateProduct Method");
        Product product= productRepo.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product","Id" ,productId));
        product.setProductName(productDao.getProductName());
        product.setOrganic(productDao.isOrganic());
        product.setInStock(productDao.isInStock());
        product.setProductPrice(productDao.getProductPrice());
        product.setUsedBy(productDao.getUsedBy());
        product.setProductQty(productDao.getProductQty());
        return  productRepo.save(product);
    }

    public String deleteProduct(int productId) {
        logger.info("Inside the ProductService and deletProduct Method");
        productRepo.deleteById(productId);
        return "Product Deleted";
    }
}
