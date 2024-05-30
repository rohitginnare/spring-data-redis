package com.javatechie.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.redis.entity.Product;
import com.javatechie.redis.repository.ProductDao;

@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {

	@Autowired
	private ProductDao dao;

	@PostMapping()
	public Product save(@RequestBody Product product) {
		return dao.save(product);
	}

	@GetMapping
	public List<Product> getAllProducts(){
		return dao.findAll();
	}
	
	@GetMapping("/{id}")
	@Cacheable(key="#id", value= "Product", unless="#result.price > 1000")
	public Product getProductById(@PathVariable int id) {
		return dao.findProductById(id);
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(key="#id", value= "Product")
	public void remove(@PathVariable int id) {
		dao.deleteProduct(id);
	}
}
