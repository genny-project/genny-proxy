package test.blackbox.life.gennyproxy;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.either;


import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;;import java.io.IOException;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
public class AbnLookupEndpointsTest {

    private static String accessToken;

    @BeforeAll
    public static void beforeALL() throws IOException {
        String response =  given()
                .contentType(ContentType.URLENC)
                .port(8180)
                .log().all()
                .when()
                .body("username=alice&password=alice&grant_type=password&client_id=backend-service&client_secret=secret&scope=openid")
                .header("content-type", "application/x-www-form-urlencoded")
                .post("/auth/realms/internmatch/protocol/openid-connect/token")
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


          given()
                .log().all()
                .when()
                .header("Authorization","Bearer "+accessToken )
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