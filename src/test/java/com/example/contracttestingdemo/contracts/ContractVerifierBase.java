package com.example.contracttestingdemo.contracts;

import com.example.contracttestingdemo.ContractTestingDemoApplication;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWireMock(port = 8081)
@ContextConfiguration(classes = ContractTestingDemoApplication.class,
        initializers = ContractTestApplicationContextInitializer.class)
public abstract class ContractVerifierBase {
    @Autowired
    private WebApplicationContext context;
    @BeforeEach
    public void setUp() {
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/users/2")).willReturn(
                WireMock.aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"data\":{\"id\":2,\"email\":\"testAccount@gmail.com\",\"first_name\":\"Krishna\",\"last_name\":\"Peesari\"}}")));

        RestAssuredMockMvc.webAppContextSetup(context);

    }

    @AfterEach
    public void tearDown() {
        RestAssuredMockMvc.reset();
    }

}
