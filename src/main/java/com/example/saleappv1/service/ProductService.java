package com.example.saleappv1.service;

import com.example.saleappv1.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("data/products.json").getInputStream();
            products = mapper.readValue(inputStream, new TypeReference<List<Product>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        return products.stream().filter(p -> {
            if (categoryId != null && !p.getCategoryId().equals(categoryId)) return false;
            if (keyword != null && !keyword.trim().isEmpty() && !p.getName().toLowerCase().contains(keyword.toLowerCase())) return false;
            if (fromPrice != null && p.getPrice() < fromPrice) return false;
            if (toPrice != null && p.getPrice() > toPrice) return false;
            return true;
        }).collect(Collectors.toList());
    }

    public Product getProductById(Integer id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
