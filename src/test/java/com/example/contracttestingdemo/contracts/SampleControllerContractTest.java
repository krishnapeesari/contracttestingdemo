package com.example.contracttestingdemo.contracts;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

class SampleControllerContractTest extends ContractVerifierBase {

    @Test
    void testSampleApi() throws IOException {
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/users/2")).willReturn(
                WireMock.aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"data\":{"
                                + "\"id\":2,"
                                + "\"first_name\":\"Krishna\","
                                + "\"last_name\":\"Peesari\"}}")
        ));

        final MockMvcRequestSpecification request = given()
                .header("Content-Type", "application/json")
                .param("id", 2);

        final MockMvcResponse response = given().spec(request)
                .get("/sample/api");

        System.out.println(response.statusCode());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        validateResponse("/contract-response/sampleApiResponse.json", response.getBody().asString());
    }
}
