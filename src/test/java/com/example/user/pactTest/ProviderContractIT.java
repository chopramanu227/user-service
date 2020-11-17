package com.example.user.pactTest;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.TargetRequestFilter;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import org.apache.http.HttpRequest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(SpringRestPactRunner.class)
@Provider("userServiceProvider")
@PactFolder("pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderContractIT {

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @TargetRequestFilter
    public void exampleRequestFilter(HttpRequest request) {
        request.addHeader("Authorization", "Basic MTAwMTp0ZXN0");
    }

    @State("get user")
    public void getUser() {
        // nothing to do, real service is used
    }
    @State("get users")
    public void getUsers() {
        // nothing to do, real service is used
    }

    @State("saved user")
    public void savedUser() {
        // nothing to do, real service is used
    }
}
