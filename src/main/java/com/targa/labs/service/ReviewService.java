package com.targa.labs.service;

import com.targa.labs.domain.Review;
import com.targa.labs.dto.ReviewDto;
import com.targa.labs.dto.mapping.ReviewMapper;
import com.targa.labs.repository.ProductRepository;
import com.targa.labs.repository.ReviewRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class ReviewService {

    private static final Logger log=Logger.getLogger(ReviewService.class);

    @Inject
    ReviewRepository reviewRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    ReviewMapper mapper;

    public List<ReviewDto> findReviewsByProductId(Long id) {
        log.debug("Request to get all Reviews");
        return mapper.entityListToDtoList(this.reviewRepository.findReviewsByProductId(id));
    }

    public ReviewDto findById(Long id) {
        log.debugf("Request to get Review : {}", id);
        return this.reviewRepository.findById(id)
                .map(mapper::entityToDto)
                .orElse(null);
    }

    public ReviewDto create(ReviewDto reviewDto, Long productId) {
        log.debugf("Request to create Review : {} ofr the Product {}", reviewDto, productId);

        var product = this.productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with ID:" + productId + " was not found !"));

        var savedReview = this.reviewRepository.saveAndFlush(
                new Review(
                        reviewDto.getTitle(),
                        reviewDto.getDescription(),
                        reviewDto.getRating()
                )
        );

        product.getReviews().add(savedReview);
        this.productRepository.saveAndFlush(product);

        return mapper.entityToDto(savedReview);
    }

    public void delete(Long reviewId) {
        log.debugf("Request to delete Review : {}", reviewId);

        var review = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalStateException("Product with ID:" + reviewId + " was not found !"));

        var product = this.productRepository.findProductByReviewId(reviewId);

        product.getReviews().remove(review);

        this.productRepository.saveAndFlush(product);
        this.reviewRepository.delete(review);
    }
}
