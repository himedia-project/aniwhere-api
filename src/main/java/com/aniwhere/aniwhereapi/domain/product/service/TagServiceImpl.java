package com.aniwhere.aniwhereapi.domain.product.service;

import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import com.aniwhere.aniwhereapi.domain.product.repository.ProductRepository;
import com.aniwhere.aniwhereapi.domain.product.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;
    private final AdminProductService adminProductService;

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> list(Long id) {
        List<Long> productIds = tagRepository.findByTag(id);
        if (productIds == null || productIds.isEmpty()) {
            throw new EntityNotFoundException("해당 엔티티가 없습니다.");
        }

        return productIds.stream().map(productId -> {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("해당 엔티티가 없습니다."));
            ProductDTO productDTO = adminProductService.entityToDTO(product);
            return productDTO;
        }).toList();
    }
}
