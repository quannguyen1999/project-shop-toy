package com.springframework.projectshoptoy.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.springframework.stereotype.Component;
//@Component đăng ký bean trong spring container
@Component
public class MyEntityManager {
	//EntitityManager để dùng thêm,xóa,sửa
	private EntityManager em;

	public MyEntityManager() {
		this.em =Persistence.createEntityManagerFactory("project-shop-toy").createEntityManager();
	}
	
	//tối ưu singleton và thread perfomanrce thấp 
	private static class SingletonHelper{
		private static MyEntityManager instance=new MyEntityManager();
	}
	
	public static MyEntityManager getInstance() {
		return SingletonHelper.instance;
	}

	public EntityManager getEm() {
		return em;
	}
	
	//Tạo query cho class,T là class abstract
	@SuppressWarnings("unchecked")
	public <T> List<T> query(String query,T t){
		return em.createNativeQuery(query,t.getClass()).getResultList();
	}
	
	//Tạo query cho class,T là class abstract
	@SuppressWarnings("unchecked")
	public <T> List<T> query(String query) {
		return em.createNativeQuery(query).getResultList();
	}
	
	//lấy tất cả dữ liệu
	//Tạo query cho class,T là class abstract
	@SuppressWarnings("unchecked")
	public <T> List<T> getAllData(T t){
		System.out.println("Query:"+"db."+t.getClass().getSimpleName().toLowerCase()+"s.find({})");
		 return  em.createNativeQuery("db."+t.getClass().getSimpleName().toLowerCase()+"s.find({})",t.getClass())
				 	.getResultList();
	}

	//thêm dữ liệu
	//Tạo query cho class,T là class abstract
	public <T> boolean addT(T t,String id) {
		boolean result=false;
		if(em.find(t.getClass(), id)!=null) {
			return result;
		}
		return functionT(t, 1);
	}

	//Tạo query cho class,T là class abstract
	//Optinal để tra về empty hoặc object tồn tại
	public <T> Optional<T> updateT(T t,String id) {
		T updateO=null;
		if(em.find( t.getClass(), id)==null) {
			return Optional.empty();
		}
		EntityTransaction tr=em.getTransaction();
		try {
				tr.begin();
				updateO=em.merge(t);
				tr.commit();
				em.clear();
		} catch (Exception e) {
			tr.rollback();
		}
		Optional<T> x=Optional.of(updateO);
		return x;
	}
	
	//xóa data
	//Tạo query cho class,T là class abstract
	public <T> boolean deleteT(T t,String id) {
		boolean result=false;
		Object findT=em.find(t.getClass(), id);
		if(findT==null) {
			System.out.println("can'y");
			return result;
		}
		return functionT(findT, 2);
	}

	//tìm data
	////Tạo query cho class,T là class abstract
	@SuppressWarnings("unchecked")
	public <T> Optional<Object> findById(T t,String id){
		T x=(T) em.find( t.getClass(),id);
		if(x!=null) {
			Optional<T> xFind=Optional.of(x);
			return  (Optional<Object>) xFind;
		}
		return Optional.empty();
	}
	
	
	//Tạo query cho class,T là class abstract
	//các chức năng chung:thêm,xóa,sửa
	//1 thêm
	//2 xóa
	//3 cập nhập
	private <T> boolean functionT(T t,int type) {
		boolean result=false;
		EntityTransaction tr=em.getTransaction();
		
		try {
			tr.begin();
			if(type==1) {
				//add
				em.persist(t);
			}else if(type==2){
				//remove
				em.remove(t);
			}else {
				//update
				em.merge(t);
			}
			tr.commit();
			result=true;
			em.clear();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return result;
	}

}
