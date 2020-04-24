package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.domain.Supplier;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class SupplierServiceImpl implements   SupplierService{
    private final SupplierRepository supplierRepository;

    @Override
    public Set<Supplier> getListSupplier() {
        log.debug("get list supplier");
        Set<Supplier> supplierSet=new HashSet<>();
        supplierRepository.findAll().iterator().forEachRemaining(supplierSet::add);
        return supplierSet;
    }

    @Override
    public boolean deleteSupplier(String userName) {
        return false;
    }

    @Override
    public Supplier findSupplierById(String Id) {
        return supplierRepository.findById(Id).orElseThrow(() -> new NotFoundException("can find id " + Id));
    }

    @Override
    public Supplier createNewSupplier(Supplier supplier) {
        if(supplier.getSupplierID()!=null){
            Optional<Supplier> supplier1=supplierRepository.findById(supplier.getSupplierID());
            if(supplier1.isPresent()==true){
                log.error("conflix id");
                throw new ConflixIdException("conflix id");
            }
        }
        supplier.setSupplierID("SL"+ObjectId.get().toString());
        return  supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(String id, Supplier supplier) {
        Supplier supplierFind=supplierRepository.findById(id).orElseThrow(()->new NotFoundException("Not found id "+id));
        if(supplier.getAddress()!=null){
            supplierFind.setAddress(supplier.getAddress());
        }
        if(supplier.getCompanyName()!=null){
            supplierFind.setCompanyName(supplier.getCompanyName());
        }
        if(supplier.getPhone()!=null){
            supplierFind.setPhone(supplier.getPhone());
        }
        return supplierRepository.save(supplierFind);
    }
}
