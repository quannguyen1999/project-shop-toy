package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Category;
import com.springframework.projectshoptoy.domain.Order;
import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.domain.Supplier;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.CategoryRepository;
import com.springframework.projectshoptoy.repositories.ProductRepository;
import com.springframework.projectshoptoy.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public Set<Product> getListProduct() {
        log.debug("get list product");
        Set<Product> productSet=new HashSet<>();
        productRepository.findAll().iterator().forEachRemaining(productSet::add);
        return productSet;
    }

    @Override
    public boolean deleteProduct(String userName) {
        return false;
    }

    @Override
    public Product findProductByID(String id) {
        return productRepository.findById(id).orElseThrow(()->new NotFoundException("can find id "+id));
    }

    @Override
    public Product createNewProduct(Product product) {
        if(product.getProductID()!=null){
            Optional<Product> productFind=productRepository.findById(product.getProductID());
            if(productFind!=null){
                throw  new ConflixIdException("conflix id product id "+productFind.get().getProductID());
            }
        }
        if(product.getCategory()==null || product.getCategory().getCategoryID()==null){
            throw  new NotFoundException("ID category can't be null");
        }
        Category category=categoryRepository.findById(product.getCategory().getCategoryID()).orElseThrow(()->new NotFoundException("Not found id category "+product.getCategory().getCategoryID()));
        product.setCategory(category);
        if(product.getSupplier()==null || product.getSupplier().getSupplierID()==null){
            throw new NotFoundException("ID supplier can't be null");
        }
        Supplier supplier=supplierRepository.findById(product.getSupplier().getSupplierID()).orElseThrow(()->new NotFoundException("Not found id supplier "+product.getSupplier().getSupplierID()));
        product.setSupplier(supplier);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        Product productFind=productRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
        if(product.isDiscontinued()==true){
            productFind.setDiscontinued(product.isDiscontinued());
        }
        if(product.getMoTa()!=null){
            productFind.setMoTa(product.getMoTa());
        }
        if(product.getProductName()!=null){
            productFind.setProductName(product.getProductName());
        }
        if(product.getQuantityInStock()!=productFind.getQuantityInStock()){
            productFind.setQuantityInStock(product.getQuantityInStock());
        }
        if(product.getUnitPrice()!=productFind.getUnitPrice()){
            productFind.setUnitPrice(product.getUnitPrice());
        }
        return productRepository.save(productFind);
    }
}
