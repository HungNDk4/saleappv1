package com.example.saleappv1.controller;

import com.example.saleappv1.model.Product;
import com.example.saleappv1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double fromPrice,
            @RequestParam(required = false) Double toPrice,
            Model model) {
        model.addAttribute("products", productService.getProducts(categoryId, keyword, fromPrice, toPrice));
        return "products";
    }

    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable Integer productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "product-detail";
    }
}
