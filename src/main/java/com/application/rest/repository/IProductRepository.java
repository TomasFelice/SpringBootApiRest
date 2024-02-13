package com.application.rest.repository;

import com.application.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IProductRepository extends CrudRepository<Product, Long> {

    // Con notacion Query
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findProductsByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    //Con query methods
    List<Product> findProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
