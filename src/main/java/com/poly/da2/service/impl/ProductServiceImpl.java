package com.poly.da2.service.impl;

import com.poly.da2.entity.Category;
import com.poly.da2.repository.ProductRepository;
import com.poly.da2.entity.Product;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
    ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Product findById(Integer id) {
		return productRepository.findById(id).get();
	}

	@Override
	public Page<Product> findByCategoryId(String cid, Pageable pageable) {
		return productRepository.getByCategoryId(cid,pageable);
	}

	@Override
	public Product create(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void delete(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public Page<Product> findByPrice(double min, double max, Pageable pageable) {
		return productRepository.findByPrice(min,max,pageable);
	}

	@Override
	public Page<Product> searchProducts(String name, Pageable pageable) {
		return productRepository.getByName(name,pageable);
	}

	@Override
	public int getPageCount(String searchTerm) {
		long productCount = searchTerm != null && !searchTerm.isBlank()
				? productRepository.countByNameContainingIgnoreCase(searchTerm)
				: productRepository.count();
		return (int) Math.ceil((double) productCount / 10);
	}

	@Override
	//@Transactional(readOnly = true)
	public List<Product> sanphambanchay() {

//		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_SpDuoc_Mua_nhieu");
//		query.execute();
//		List<Object[]> result = query.getResultList();
//		List<Product> products = new ArrayList<>();
//		for (Object[] obj : result) {
//			Product p = new Product();
//			p.setId((Integer) obj[0]);
//			p.setDescription((String) obj[1]);
//			p.setName((String) obj[2]);
//			p.setPrice((Double) obj[3]);
//			p.setThumbnail_url((String) obj[4]);
//			p.setCreateDate((Date) obj[5]);
//			p.setUpdateDate((Date) obj[6]);
//			p.setImage_urls((String) obj[7]);
//			p.setRating_average((Double) obj[8]);
//			p.setCategory((Category) obj[9]);
//			p.setAvailable((Boolean) obj[10]);
//			p.setReview_count((Integer) obj[11]);
//			products.add(p);
//		}
//		return products;
		return productRepository.sanphambanchay();
	}

	@Override
	public Page<Product> filterProducts(String name, String cid, Pageable pageable) {
//		return productRepository.filterProduct(name, cid, pageable);
		return null;
	}


}
