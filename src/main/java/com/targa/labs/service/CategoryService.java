package com.targa.labs.service;

import com.targa.labs.domain.Category;
import com.targa.labs.dto.CategoryDto;
import com.targa.labs.dto.ProductDto;
import com.targa.labs.dto.mapping.CategoryMapper;
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
public class CategoryService {
    public static final Logger log = Logger.getLogger(CategoryService.class);

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    CategoryMapper categoryMapper;

    @Inject
    ProductMapper productMapper;

    public List<CategoryDto> findAll() {
        log.debug("Request to get all Categories");
        List<CategoryDto> categoryDtoList = categoryMapper.entityListToDtoList(categoryRepository.findAll());
        categoryDtoList.forEach(dto -> {
            Long count = productRepository.countAllByCategoryId(dto.getId());
            dto.setProducts(count);
        });
        return categoryDtoList;
    }

    public CategoryDto findById(Long id) {
        log.debugf("Request to get Category : {}", id);
        return categoryRepository.findById(id)
                .map(category -> {
                   CategoryDto dto = categoryMapper.entityToDto(category);
                   Long productCount = productRepository.countAllByCategoryId(dto.getId());
                   dto.setProducts(productCount);
                   return dto;
                }).orElse(null);
    }

    public CategoryDto create(CategoryDto categoryDto) {
        log.debugf("Request to create Category : {}", categoryDto);
        Category category = categoryMapper.dtoToEntity(categoryDto);
        return categoryMapper.entityToDto(categoryRepository.save(category));
    }

    public void delete(Long id) {
        log.debugf("Request to delete Category : {}", id);
        log.debugf("Deleting all products for the Category : {}", id);
        productRepository.deleteAllByCategoryId(id);
        categoryRepository.deleteById(id);
    }

    public List<ProductDto> findProductsByCategoryId(Long id) {
        return productMapper.entityListToDtoList(productRepository.findByCategoryId(id));
    }



}
