package com.application.rest.controllers;

import com.application.rest.controllers.dto.ProductDTO;
import com.application.rest.entities.Product;
import com.application.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isEmpty())
            return ResponseEntity.notFound().build();

        Product product = productOptional.get();

        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .maker(product.getMaker())
                .build();

        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAll() {
        List<ProductDTO> productDTOList = productService.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build())
                .toList();

        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        if(productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null)
            return ResponseEntity.badRequest().build();

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker())
                .build();

        productService.save(product);

        return ResponseEntity.created(new URI("/api/product/save")).build();
    }

    @PutMapping("/save/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isEmpty())
            return ResponseEntity.notFound().build();

        Product product = productOptional.get();

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setMaker(productDTO.getMaker());
        productService.save(product);

        return ResponseEntity.ok("Product updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletById(@PathVariable Long id) {
        if(id == null)
            return ResponseEntity.badRequest().build();

        productService.deleteById(id);
        return ResponseEntity.ok("Product deleted");
    }

    @GetMapping("/find/priceBetween/{minPrice}/{maxPrice}")
    public ResponseEntity<?> findByPriceInRange(@PathVariable BigDecimal minPrice, @PathVariable BigDecimal maxPrice) {
        List<ProductDTO> productDTOList = productService.findByPriceInRange(minPrice, maxPrice)
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build())
                .toList();

        return ResponseEntity.ok(productDTOList);
    }

}
