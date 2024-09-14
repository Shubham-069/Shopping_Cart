package com.ecom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ecom.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

	List<Product> findByIsActiveTrue();

	List<Product> findByCategory(String category);
	
	List<Product> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch1, String ch2);

	Page<Product> findByIsActiveTrue(Pageable pageable);

	Page<Product> findByCategory(String category, Pageable pageable);

	Page<Product> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2, Pageable pageable);

	Page<Product> findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2,
			Pageable pageable);

}
