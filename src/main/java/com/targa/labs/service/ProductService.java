package com.targa.labs.service;

import com.targa.labs.domain.Product;
import com.targa.labs.dto.ProductDto;
import com.targa.labs.dto.mapping.ProductMapper;
import com.targa.labs.repository.CategoryRepository;
import com.targa.labs.repository.ProductRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class ProductService {

    private static final Logger log=Logger.getLogger(ProductService.class);

    @Inject
    ProductRepository productRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    ProductMapper mapper;

    public List<ProductDto> findAll() {
        log.debug("Request to get all Products");
        return mapper.entityListToDtoList(this.productRepository.findAll());

    }

    public ProductDto findById(Long id) {
        log.debugf("Request to get Product : {}", id);
        return this.productRepository.findById(id)
                .map(mapper::entityToDto)
                .orElse(null);
    }

    public ProductDto create(ProductDto productDto) {
        log.debugf("Request to create Product : {}", productDto);
        Product product = mapper.dtoToEntity(productDto);
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()).orElse(null));
        return mapper.entityToDto(productRepository.save(product));
    }

    public void delete(Long id) {
        log.debugf("Request to delete Product : {}", id);
        this.productRepository.deleteById(id);
    }

    public List<ProductDto> findByCategoryId(Long id) {
        return mapper.entityListToDtoList(this.productRepository.findByCategoryId(id));
    }

    public Long countAll() {
        return this.productRepository.count();
    }

    public Long countByCategoryId(Long id) {
        return this.productRepository.countAllByCategoryId(id);
    }
}
