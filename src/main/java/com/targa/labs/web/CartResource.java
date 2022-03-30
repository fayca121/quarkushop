package com.targa.labs.web;

import com.targa.labs.dto.CartDto;
import com.targa.labs.service.CartService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Authenticated
@Path("/carts")
@Tag(name = "Cart",description = "All the cart methods")
public class CartResource {
    @Inject
    CartService cartService;

    @GET
    public List<CartDto> findAll(){
        return cartService.findAll();
    }

    @GET
    @Path("/active")
    public List<CartDto> findAllActiveCarts(){
        return cartService.findAllActiveCarts();
    }

    @GET
    @Path("/customer/{id}")
    public CartDto getActiveCartForCustomer(@PathParam("id") Long customerId){
        return cartService.getActiveCart(customerId);
    }

    @GET @Path("/{id}")
    public CartDto findById(@PathParam("id") Long id) {
        return cartService.findById(id);
    }

    @POST
    @Path("/customer/{id}")
    public CartDto create(@PathParam("id") Long customerId){
        return cartService.createDto(customerId);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        cartService.delete(id);
    }

}
