package com.product.crud.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.product.crud.model.Product;
import com.product.crud.model.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final DynamoDBMapper dynamoDBMapper;

    public ProductService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<ProductDTO> getAllProducts() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Product> products = dynamoDBMapper.scan(Product.class, scanExpression);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public ProductDTO getProductById(Long id) {
        Product product = dynamoDBMapper.load(Product.class, id);
        return convertToDTO(product);
    }

    public ProductDTO createNewProduct(ProductDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);

        dynamoDBMapper.save(product);

        return convertToDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = dynamoDBMapper.load(Product.class, id);
        BeanUtils.copyProperties(dto, product);

        dynamoDBMapper.save(product);

        return convertToDTO(product);
    }

    public void deleteProduct(Long id) {
        Product product = dynamoDBMapper.load(Product.class, id);
        if (product != null) {
            dynamoDBMapper.delete(product);
        }
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }
}