package test.blackbox.life.gennyproxy;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.either;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import java.util.LinkedList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
public class AbnLookupEndpointsTest {

    private static String accessToken;

    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    Optional<String> keycloakUrl;

    @ConfigProperty(name = "quarkus.oidc.client-id")
    Optional<String> clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    Optional<String> secret;

    static {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @BeforeEach
    public void beforeALL() {

       // RestAssured.baseURI ="https://keycloak.gada.io";
        RestAssured.port = -1;
        String response =  given()
                .log().all()
                .param("grant_type", "password")
                .param("username", "test1234@gmail.com")
                .param("password", "alice")
                .param("client_id", clientId.get())
                .param("client_secret", secret.get())
                .when()
                //.body("username=test1234@gmail.com&password=alice&grant_type=password&client_id=internmatch&client_secret=dc7d0960-2e1d-4a78-9eef-77678066dbd3&scope=openid")
                .header("content-type", "application/x-www-form-urlencoded")
                .post(keycloakUrl.get()+ "/protocol/openid-connect/token")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        JSONObject json = new JSONObject(response);
        System.out.println(json.get("access_token"));
        accessToken = (String)json.get("access_token");
    }


    @Test
    public void retrieveCompanyAbn_passNoParameter_return200() {
//        given()
//          .when().get("/json?name=gada&size=1")
//          .then()
//             .statusCode(200)
//             .body(
//                 // Covers case when the ABN_KEY is no present with the right value
//                 either(startsWith("{\"Message\":\"There was a problem completing your request.\""))
//                 // Covers case when the ABN_KEY is present
//                 .or(startsWith("{\"Message\":\"\",\"Names\":[{"))
//             );


          given().auth().oauth2(accessToken)
                .log().all()
                .when()
                  .port(8081)
                .get("/json?name=gada&size=1")
                .then()
                .log().all()
                .statusCode(200)

                .body(
                 // Covers case when the ABN_KEY is no present with the right value
                 either(startsWith("{\"Message\":\"There was a problem completing your request.\""))
                 // Covers case when the ABN_KEY is present
                 .or(startsWith("{\"message\":\"\",\"names\":[{"))
             );

       // System.out.println(response);
    }

}