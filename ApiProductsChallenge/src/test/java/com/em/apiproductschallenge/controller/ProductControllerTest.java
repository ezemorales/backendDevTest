package com.em.apiproductschallenge.controller;

import com.em.apiproductschallenge.model.ProductDetail;
import com.em.apiproductschallenge.utils.errors.ApiError;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    protected static final String URL = "http://localhost";
    private static final String PRODUCT = "/product";
    private static final String SIMILAR = "/similar";
    private static final String SIMILAR_IDS = "/similarids";

    private static WireMockServer server;

    @LocalServerPort
    private int port;

    @BeforeAll
    static void config() {
        server = new WireMockServer(3001);
        server.start();
    }

    @AfterAll
    static void stop() {
        server.stop();
    }

    @BeforeEach
    void init() {
        RestAssured.baseURI = URL;
        RestAssured.port = port;
    }

    protected WireMockServer getServer() {
        return server;
    }


    // MOCKS
    protected void mockGetSimilarProducts200ok(String productId, String uriResponse) {
        getServer().stubFor(get(urlEqualTo(PRODUCT + "/" + productId + SIMILAR_IDS))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withStatus(200)
                        .withBodyFile(uriResponse)));
    }

    protected void mockGetSimilarProducts404NotFound(String productId) {
        getServer().stubFor(get(urlEqualTo(PRODUCT + "/" + productId + SIMILAR_IDS))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withStatus(404)
                ));
    }

    protected void mockGetProductDetail200ok(String productId, String uriResponse) {
        getServer().stubFor(get(urlEqualTo(PRODUCT + "/" + productId))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withStatus(200)
                        .withBodyFile(uriResponse)));
    }

    protected void mockGetProductDetail404NotFound(String productId) {
        getServer().stubFor(get(urlEqualTo(PRODUCT + "/" + productId))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withStatus(404)
                ));
    }

    protected void mock500GetProduct(String productId) {
        getServer().stubFor(get(urlEqualTo(PRODUCT + "/" + productId))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withStatus(500)
                ));
    }

    // TESTS

    @Test
    void should_get_similar_products_list_ok() {
        mockGetSimilarProducts200ok("1", "/responses/similar_id_1_response.json");
        mockGetProductDetail200ok("2", "/responses/product_detail_2_response.json");
        mockGetProductDetail200ok("3", "/responses/product_detail_3_response.json");
        mockGetProductDetail200ok("4", "/responses/product_detail_4_response.json");

        List<ProductDetail> productDetails = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(URL + PRODUCT + "/1" + SIMILAR)
                .then().statusCode(HttpStatus.OK.value())
                .extract()
                .body().jsonPath().getList(".", ProductDetail.class);

        assertEquals("2", productDetails.get(0).getId());
        assertEquals("Dress", productDetails.get(0).getName());
        assertEquals(19.99, productDetails.get(0).getPrice());
        assertTrue(productDetails.get(0).isAvailability());

        assertEquals("3", productDetails.get(1).getId());

        assertEquals("4", productDetails.get(2).getId());

        assertEquals(3, productDetails.size());

    }

    @Test
    void should_return_404_similar_product_not_found() {
        mockGetSimilarProducts404NotFound("12");

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(URL + PRODUCT + "/12" + SIMILAR)
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_return_404_product_not_found() {
        mockGetSimilarProducts200ok("1", "/responses/similar_id_1_response.json");
        mockGetProductDetail200ok("2", "/responses/product_detail_2_response.json");
        mockGetProductDetail200ok("3", "/responses/product_detail_3_response.json");
        mockGetProductDetail404NotFound("4");


        ApiError apiError = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(URL + PRODUCT + "/1" + SIMILAR)
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ApiError.class);

        assertEquals("Product not found", apiError.getErrors().get(0).getMessage());
    }

    @Test
    void should_return_500_api_not_responding() {
        mockGetSimilarProducts200ok("1", "/responses/similar_id_1_response.json");
        mockGetProductDetail200ok("2", "/responses/product_detail_2_response.json");
        mockGetProductDetail200ok("3", "/responses/product_detail_3_response.json");
        mock500GetProduct("4");


        ApiError apiError = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(URL + PRODUCT + "/1" + SIMILAR)
                .then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .extract()
                .as(ApiError.class);

        assertEquals("Server API MOCK CLIENT not responding", apiError.getErrors().get(0).getMessage());
    }

}
