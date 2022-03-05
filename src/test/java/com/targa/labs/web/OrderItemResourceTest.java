package com.targa.labs.web;

import com.targa.labs.web.util.TestContainerResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
class OrderItemResourceTest {
    @Test
    void testFindByOrderId() {
        get("/order-items/order/1").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testFindById() {
        get("/order-items/1").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testCreate() {
        get("/orders/3").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testDelete() {
        get("/orders/1").then()
                .statusCode(OK.getStatusCode());
    }

}
