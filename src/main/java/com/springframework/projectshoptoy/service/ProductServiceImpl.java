package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Product;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

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
        return null;
    }

    @Override
    public Product updateProduct(String id, Product product) {
        return null;
    }
}
