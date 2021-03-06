package com.example.contracttestingdemo.contracts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.support.TestPropertySourceUtils;

public class ContractTestApplicationContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    /**
     * Initialize the given application context.
     *
     * @param applicationContext the application to configure
     */
    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        TestPropertySourceUtils.addPropertiesFilesToEnvironment(
                applicationContext, "classpath:application.properties");
        String environment = System.getProperty("contractTest.environment");
        if (StringUtils.isEmpty(environment)) {
            environment = "dev";
        }
        final String profileProperties = "classpath:application-" + environment + ".properties";
        if(applicationContext.getResource(profileProperties).exists()) {
            TestPropertySourceUtils.addPropertiesFilesToEnvironment(applicationContext, profileProperties);
        }
        // Override properties for stubbing
        TestPropertySourceUtils.addPropertiesFilesToEnvironment(
                applicationContext, "classpath:stub.properties");

    }
}