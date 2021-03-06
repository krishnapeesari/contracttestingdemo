package com.example.contracttestingdemo.contracts;

import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

class SampleControllerContractTest extends ContractVerifierBase {

    @Test
    void testIsNumberPrime() {
        final MockMvcRequestSpecification request = given()
                .header("Content-Type", "application/json")
                .param("id", 2);

        MockMvcResponse response = given().spec(request)
                .get("/sample/api");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody().asString()).isEqualTo("Krishna Peesari");
    }
}
