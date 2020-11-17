package com.example.user.pactTest;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.user.model.Address;
import com.example.user.model.User;
import com.google.gson.Gson;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
//@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class ConsumerContractIT {

    @Autowired
    private UserServiceClient userServiceClient;

    @Rule
    public PactProviderRuleMk2 mockProvider
            = new PactProviderRuleMk2("userServiceProvider", "localhost", 8081, this);

    @Pact(provider = "userServiceProvider",
            consumer = "userServiceClient")
    public RequestResponsePact fetchAllUserCollectionResourcePact(PactDslWithProvider builder) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("get users")
                .uponReceiving("a request to the user retrieval resource")
                .path("/users")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(LambdaDsl.newJsonArray(rootArray -> {
                    rootArray.object(a -> a
                            .stringType("title", "title1")
                            .stringType("firstName", "firstName1")
                            .stringType("lastName", "lastName1")
                            .stringType("gender", "gender1")
                            .object("address", addObj -> addObj
                                    .stringType("street","street1")
                                    .stringType("city","city1")
                                    .stringType("state","state1")
                                    .stringType("postcode","postcode1")
                            )
                    );
                    rootArray.object(a -> a
                            .stringType("title", "title2")
                            .stringType("firstName", "firstName2")
                            .stringType("lastName", "lastName2")
                            .stringType("gender", "gender2")
                            .object("address", addObj -> addObj
                                    .stringType("street","street2")
                                    .stringType("city","city2")
                                    .stringType("state","state2")
                                    .stringType("postcode","postcode2")
                            )
                    );
                    rootArray.object(a -> a
                            .stringType("title", "title3")
                            .stringType("firstName", "firstName3")
                            .stringType("lastName", "lastName3")
                            .stringType("gender", "gender3")
                            .object("address", addObj -> addObj
                                    .stringType("street","street3")
                                    .stringType("city","city3")
                                    .stringType("state","state3")
                                    .stringType("postcode","postcode3")
                            )
                    );
                    rootArray.object(a -> a
                            .stringType("title", "title4")
                            .stringType("firstName", "firstName4")
                            .stringType("lastName", "lastName4")
                            .stringType("gender", "gender4")
                            .object("address", addObj -> addObj
                                    .stringType("street","street4")
                                    .stringType("city","city4")
                                    .stringType("state","state4")
                                    .stringType("postcode","postcode4")
                            )
                    );
                    rootArray.object(a -> a
                            .stringType("title", "title5")
                            .stringType("firstName", "firstName5")
                            .stringType("lastName", "lastName5")
                            .stringType("gender", "gender5")
                            .object("address", addObj -> addObj
                                    .stringType("street","street5")
                                    .stringType("city","city5")
                                    .stringType("state","state5")
                                    .stringType("postcode","postcode5")
                            )
                    );
                }).build())
                .toPact();
    }

    @Pact(provider = "userServiceProvider",
            consumer = "userServiceClient")
    public RequestResponsePact fetchUserResourcePact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        DslPart buildBody = LambdaDsl.newJsonBody(obj -> obj
                .stringType("firstName", "firstName")
                .stringType("lastName", "lastName"))
                .build();

        return builder
                .given("get user")
                .uponReceiving("a request to the user retrieval resource")
                .path("/users/1001")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(buildBody)
                .toPact();
    }

    @Pact(provider = "userServiceProvider",
            consumer = "userServiceClient")
    public RequestResponsePact saveUserResourcePact(PactDslWithProvider builder) {
        Gson gson= new Gson();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
       // headers.put("Authorization"," Basic MTAwMTp0ZXN0");
        return builder
                .given("saved user")
                .uponReceiving("a request to the user save resource")
                .path("/users/1001")
                .method("PUT")
                .body(gson.toJson(mockUser()))
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(LambdaDsl.newJsonBody(obj -> obj
                        .stringType("firstName", "newFirstName")
                        .stringType("lastName", "newLastName"))
                        .build())
                .toPact();
    }

    @Test
    @PactVerification(fragment = "fetchAllUserCollectionResourcePact")
    public void verifyUsersCollectionPact() {
        List<User> users = userServiceClient.getUsers();
        assertThat(users).hasSize(5);
        assertThat(users.get(0).getFirstName()).isEqualTo("firstName1");
        assertThat(users.get(0).getLastName()).isEqualTo("lastName1");
        assertThat(users.get(1).getFirstName()).isEqualTo("firstName2");
        assertThat(users.get(1).getLastName()).isEqualTo("lastName2");
    }

    @Test
    @PactVerification(fragment = "fetchUserResourcePact")
    public void verifyUserPact() {
        User user = userServiceClient.getUser(Long.valueOf("1001"));
        assertNotNull(user);
        assertThat(user.getFirstName()).isEqualTo("firstName");
        assertThat(user.getLastName()).isEqualTo("lastName");
    }

    @Test
    @PactVerification(fragment = "saveUserResourcePact")
    public void verifyUserSavePact() {
        User user = userServiceClient.updateUser(Long.valueOf("1001"),mockUser());
        assertNotNull(user);
        assertThat(user.getFirstName()).isEqualTo("newFirstName");
        assertThat(user.getLastName()).isEqualTo("newLastName");
    }

    private User mockUser() {
        return User.builder()
                .firstName("fname")
                .lastName("lastname")
                .gender("male")
                .title("Mr")
                .address(mockAddress())
                .build();
    }

    private Address mockAddress() {
        return Address.builder()
                .city("Sydney")
                .postcode("2000")
                .state("NSW")
                .street("Test street")
                .build();
    }
}
