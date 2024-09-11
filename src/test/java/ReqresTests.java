import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresTests extends TestBase{

    @Test
    void countOfUsersTest() {
        RestAssured.when()
                .get("/users?page=2")
                .then()
                .log().all()
                .body("data.size()", is(6));
    }

    @Test
    void userDataTest() {
        RestAssured.when()
                .get("/users/2")
                .then()
                .log().all()
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"));
    }

    @Test
    void deleteStatusCodeTest() {
        RestAssured.when()
                .delete("/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    void registerUserTest() {
        String userData = "{\"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"}";
        RestAssured.given()
                .body(userData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().all()
                .body("id", is(4))
                .body("token", is(notNullValue()));
    }

    @Test
    void createUserTest() {
        String userData = "{\"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"}";
        RestAssured.given()
                .body(userData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/users")
                .then()
                .log().all()
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", is(notNullValue()));
    }
}
