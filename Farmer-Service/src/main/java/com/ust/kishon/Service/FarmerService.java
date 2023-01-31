package com.ust.kishon.Service;

import com.ust.kishon.Entity.Farmer;
import com.ust.kishon.Entity.Product;
import com.ust.kishon.Exception.FarmerNotFoundException;
import com.ust.kishon.Repo.FarmerRepo;
import com.ust.kishon.Repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FarmerService {
    @Autowired
    FarmerRepo farmerRepo;
    @Autowired
    ProductRepo pRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(FarmerService.class);
    public Farmer addFarmer(Farmer farmer){
        logger.info("Inside the FarmerService and RegisterFarmer Method");
        Optional<Farmer> existingPhone = Optional.ofNullable(farmerRepo.findByPhone(farmer.getPhone()));
        Optional<Farmer> existingUserName = farmerRepo.findByUsername(farmer.getUsername());

        if (existingPhone.isPresent()) {
            throw new FarmerNotFoundException("farmer","Phone",farmer.getPhone());
        } else if (existingUserName.isPresent()){
            throw new FarmerNotFoundException("Farmer","Phone",farmer.getUsername());
        }
        farmer.setPassword(passwordEncoder.encode(farmer.getPassword()));
        return farmerRepo.save(farmer);
    }

    public String deleteFarmer(int id){
        logger.info("Inside the FarmerService and Delete Method");
         farmerRepo.deleteById(id);
        return "Farmer Deleted";
    }
    public Farmer getfarmerById(int farmerId){
        logger.info("Inside the FarmerService and getfarmerById Method");

        Farmer farmer=this.farmerRepo.findById(farmerId).orElseThrow(()-> new
                FarmerNotFoundException("Farmer","Id",farmerId));
        return farmer;
    }
    public List<Product> getProductDetailsUsingFarmerId(int farmerId) {
        logger.info("Inside the FarmerService and getProductDetailUsingFarmer Method");

        Farmer farmer=this.farmerRepo.findById(farmerId).
                orElseThrow(()-> new FarmerNotFoundException("Farmer","Id",farmerId));
        return pRepo.findAll();
    }
    public Farmer updateFarmer(int farmerId, Farmer farmer){
        logger.info("Inside the FarmerService and updateFarmer Method");
        Farmer f = new Farmer();
        Farmer findById = this.farmerRepo.findById(farmerId)
                .orElseThrow(()-> new FarmerNotFoundException("Farmer","id",farmerId));
            f.setId(farmerId);
            f.setAccountno(farmer.getAccountno());
            f.setAddress(farmer.getAddress());
            f.setEmail(farmer.getEmail());
            f.setAdharno(farmer.getAdharno());
            f.setEnabled(farmer.isEnabled());
            f.setIfcno(farmer.getIfcno());
            f.setPhone(farmer.getPhone());
            f.setRoles(farmer.getRoles());
            f.setUpi(farmer.getUpi());
            f.setUsername(farmer.getUsername());
            f.setPassword(farmer.getPassword());
        return  farmerRepo.save(f);
    }
  public  Farmer findFarmerByPhone(String phone){
        return farmerRepo.findByPhone(phone);

    }
}
