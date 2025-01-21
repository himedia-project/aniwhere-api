package com.aniwhere.aniwhereapi.domain.product.service;


import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import com.aniwhere.aniwhereapi.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AdminProductService adminProductService;

    @Override
    public List<ProductDTO> searchProducts(String searchKeyword) {
        List<Product> productList = productRepository.findListBySearchKeyword(searchKeyword);
        if (productList.isEmpty()) {
            throw new EntityNotFoundException("검색에 대한 상품이 없습니다.");
        }
        List<ProductDTO> productDTOList = productList.stream().map(
                product -> adminProductService.entityToDTO(product)).collect(Collectors.toList());
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new EntityNotFoundException("해당하는 상품이 존재하지 않습니다.");
        }
        List<ProductDTO> productDTOList = productList.stream().map(product ->
                adminProductService.entityToDTO(product)).collect(Collectors.toList());
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getYearProducts(String branch) {
        List<Product> productList = productRepository.findListByYear(branch);
        if (productList.isEmpty()) {
            throw new EntityNotFoundException("해당 분기에 대한 상품이 없습니다.");
        }
        List<ProductDTO> productDTOList = productList.stream().map(product ->
                adminProductService.entityToDTO(product)).collect(Collectors.toList());
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getAdultProducts() {
        List<Product> productList = productRepository.findListByAdult();
        if (productList.isEmpty()) {
            throw new EntityNotFoundException("성인용 상품이 없습니다.");
        }
        List<ProductDTO> productDTOList = productList.stream().map(product ->
                adminProductService.entityToDTO(product)).collect(Collectors.toList());
        return productDTOList;
    }

}
