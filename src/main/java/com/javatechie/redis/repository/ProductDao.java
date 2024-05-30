package com.javatechie.redis.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.javatechie.redis.entity.Product;

@Repository
public class ProductDao {
	
	
	private static final String HASH_KEY = "Product";
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate template;
	
	public Product save(Product product)
	{
		template.opsForHash().put(HASH_KEY, product.getId(), product);
		return product;
	}
	
//	public boolean save(Product product) {
//	    template.opsForHash().put(HASH_KEY, product.getId(), product);
//
//	    // Check if the data is saved
//	    Boolean isSaved = template.opsForHash().hasKey(HASH_KEY, product.getId());
//
//	    return isSaved != null && isSaved; // Return true if data is saved, false otherwise
//	}
	
	public List<Product> findAll(){
		return template.opsForHash().values(HASH_KEY);
	}
	
	
	public Product findProductById(int id) {
		System.out.println("Called findProductById() from DB.");
		return (Product) template.opsForHash().get(HASH_KEY, id);
	}
	
	public String deleteProduct(int id)
	{
		template.opsForHash().delete(HASH_KEY, id);
		return "Product Removed...";
	}
}
