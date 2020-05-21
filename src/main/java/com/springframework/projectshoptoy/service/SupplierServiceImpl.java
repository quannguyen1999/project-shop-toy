package com.springframework.projectshoptoy.service;

import com.springframework.projectshoptoy.api.commandObject.SupplierCommand;
import com.springframework.projectshoptoy.api.domain.Order;
import com.springframework.projectshoptoy.api.domain.Product;
import com.springframework.projectshoptoy.api.domain.Supplier;
import com.springframework.projectshoptoy.api.mapper.SupplierMapper;
import com.springframework.projectshoptoy.dao.MyEntityManager;
import com.springframework.projectshoptoy.exception.ConflixIdException;
import com.springframework.projectshoptoy.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//Đảm bảo tính trừu tượng,kế thừa,bao đóng,Hợp thành
@Slf4j
@Service
public class SupplierServiceImpl implements   SupplierService{
	@Autowired
	private MyEntityManager myEntityManager;
	
	@Autowired
	private SupplierMapper supplierMapper;
	
	public SupplierServiceImpl() {
		myEntityManager=new MyEntityManager();
	}

    @Override
    public Set<SupplierCommand> getListSupplier() {
        log.debug("get list Supplier");
		Set<SupplierCommand> supplierSet=new HashSet<>();
		System.out.println(supplierSet.size());
		myEntityManager.getAllData(new Supplier()).forEach(t->{
			System.out.println(t.getProducts().size());
			supplierSet.add(supplierMapper.supplierToSupplierCommand(t));
		});
        return supplierSet;
    }

    @Override
    public boolean deleteSupplier(String idSupplier) {
    	if(idSupplier==null) {
			throw new NotFoundException("Id Supplier can't be null");
		}
		Supplier Supplier=supplierMapper.supplierCommandToSupplier(findSupplierById(idSupplier));
		List<Product> listProduct=myEntityManager.query("db.products.find({'supplierID':'"+idSupplier+"'})",new Product());
		listProduct.forEach(t->{
			Set<Order> orderSet=new HashSet<>();
			myEntityManager.getAllData(new Order()).forEach(orderSet::add);
			orderSet.forEach(order->{
				for(int i=0;i<order.getOrderDetails().size();i++) {
					if(order.getOrderDetails().get(i).getProduct().getProductID().equals(t.getProductID())) {
						order.getOrderDetails().remove(order.getOrderDetails().get(i));
					}
				}
				if(order.getOrderDetails().size()<=0) {
					myEntityManager.deleteT(order, order.getOrderID());
				}else {
					myEntityManager.updateT(order,order.getOrderID());
				}
			});
			myEntityManager.deleteT(t, t.getProductID());
		});
		return myEntityManager.deleteT(Supplier,Supplier.getSupplierID());
    }

    @Override
    public SupplierCommand findSupplierById(String Id) {
        return supplierMapper.supplierToSupplierCommand((Supplier) myEntityManager.findById(new Supplier(), Id).orElseThrow(()->new NotFoundException("can find id Supplier"+Id)));
    }

    @Override
    public SupplierCommand createNewSupplier(Supplier supplier) {
        if(supplier.getSupplierID()!=null){
        	if(myEntityManager.findById(new Supplier(),supplier.getSupplierID()).isPresent()) {
				throw new ConflixIdException("supplier id had exists");
			}
        }
        supplier.setSupplierID("SL"+ObjectId.get().toString());
        boolean result=myEntityManager.addT(supplier,supplier.getSupplierID());
		if(result==false) {
			return null;
		}
		return	supplierMapper.supplierToSupplierCommand(supplier);
    }

    @Override
    public SupplierCommand updateSupplier(String id, Supplier supplier) {
        Supplier supplierFind=supplierMapper.supplierCommandToSupplier(findSupplierById(id));
        if(supplierFind==null) {
        	throw new NotFoundException("Not found that id supplier");
        }
        if(supplier.getAddress()!=null){
            supplierFind.setAddress(supplier.getAddress());
        }
        if(supplier.getCompanyName()!=null){
            supplierFind.setCompanyName(supplier.getCompanyName());
        }
        if(supplier.getPhone()!=null){
            supplierFind.setPhone(supplier.getPhone());
        }
        return supplierMapper.supplierToSupplierCommand(myEntityManager.updateT(supplierFind, supplierFind.getSupplierID()).get());
    }

	
}
