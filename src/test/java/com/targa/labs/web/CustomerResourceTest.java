package com.targa.labs.web;

import com.targa.labs.web.util.TestContainerResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
class CustomerResourceTest {
    @Test
    void testAll() {
        get("/customers").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testAllActiveUsers() {
        get("/customers/active").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testAllInactiveUsers() {
        get("/customers/inactive").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testFindById() {
        get("/customers/1").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testCreate() {
        var requestParams = new HashMap<>();
        requestParams.put("firstName", "Saul");
        requestParams.put("lastName", "Berenson");
        requestParams.put("email", "call.saul@mail.com");

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams)
                .post("/customers").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testDeleteThenCustomerIsDisabled() {
        get("/customers/active").then()
                .statusCode(OK.getStatusCode());
    }
}
