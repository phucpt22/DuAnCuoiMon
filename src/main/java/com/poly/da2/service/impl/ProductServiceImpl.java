package com.poly.da2.service.impl;

import com.poly.da2.entity.TopProduct;
import com.poly.da2.model.ProductPageOutPut;
import com.poly.da2.repository.ProductRepository;
import com.poly.da2.entity.Product;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
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
	public List<Product> sanPhamLienQuan(String cid, Pageable pageable) {
		return productRepository.SanPhamLienQuan(cid,pageable);
	}

	@Override
	public ProductPageOutPut filterProducts(String name, String cid, Double min_price, Double max_price,Pageable pageable) {
		ProductPageOutPut productPageOutPut = new ProductPageOutPut();
		List<Product> products = productRepository.filterProduct(name, cid, min_price,max_price);
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Product> paginatedList;
		if (products.size() < startItem) {
			paginatedList = List.of();
		} else {
			int toIndex = Math.min(startItem + pageSize, products.size());
			paginatedList = products.subList(startItem, toIndex);
		}
		productPageOutPut.setProducts(paginatedList);
		productPageOutPut.setTotalPage((int) Math.ceil((double)products.size()/pageSize));
		return productPageOutPut;
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@GetMapping("top-product")
	public List<TopProduct> getTopProduct(){
		return productRepository.getTopProduct();
	}
	@Override
	public List<Product> sanPhamMoiNhat() {
		return productRepository.sanPhamMoiNhat();
	}

	@Override
	public List<Product> sanPhamMoiNhatCate1() {
		return productRepository.sanPhamMoiCate1();
	}

	@Override
	public List<Product> sanPhamMoiNhatCate2() {
		return productRepository.sanPhamMoiCate2();
	}

	@Override
	public List<Product> sanPhamMoiNhatCate6() {
		return productRepository.sanPhamMoiCate6();
	}

}
