package com.example.contracttestingdemo.contracts;

import com.example.contracttestingdemo.ContractTestingDemoApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @AfterEach
    public void tearDown() {
        RestAssuredMockMvc.reset();
    }

    void validateResponse(final String expectedResponseJsonPath, final String actualResponse) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String expectedResponse = IOUtils
                .toString(this.getClass().getResourceAsStream(expectedResponseJsonPath), StandardCharsets.UTF_8);
        Assertions.assertEquals(mapper.readTree(expectedResponse), mapper.readTree(actualResponse));
    }

}
