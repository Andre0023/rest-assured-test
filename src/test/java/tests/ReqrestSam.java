package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqrestSam {

    @Test
    void singleUser() {
        /*
       https://reqres.in/api/users/2
Response:
{
    "data": {
        "id": 2,
        "email": "janet.weaver@reqres.in",
        "first_name": "Janet",
        "last_name": "Weaver",
        "avatar": "https://reqres.in/img/faces/2-image.jpg"
    },
    "support": {
        "url": "https://reqres.in/#support-heading",
        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
    }
}
        */

        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));



    }
    @Test

    void notFound() {
        /*
       https://reqres.in/api/users/2

        */

        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);

    }

    @Test
    void listReourse(){
        /*
        https://reqres.in/api/unknown
         */

        given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("data.id[3]", is(4))
                .body("data.pantone_value[3]", is("14-4811"))
                .body("data.id[0]", is(1))
                .body("data.pantone_value[3]", is("14-4811"));



    }

    @Test
    void singleReourse(){
        /*
        https://reqres.in/api/unknown/2

        {
    "data": {
        "id": 2,
        "name": "fuchsia rose",
        "year": 2001,
        "color": "#C74375",
        "pantone_value": "17-2031"
    },
    "support": {
        "url": "https://reqres.in/#support-heading",
        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
    }
}
         */

        given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.color", is("#C74375"))
                .body("data.year", is(2001))
                .body("data.pantone_value", is("17-2031"));



    }

    @Test
    void create() {
        /*
        Request url:
        https://reqres.in/api/users
        Data:
        {
            "email": "eve.holt@reqres.in",
            "password": "cityslicka"
        }
        Response:
        {
            "token": "QpwL5tke4Pnpja7X4"
        }
         */

        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }
    @Test
    void updatePatchUser() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void deleteUser() {

        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void listResourceWithAssertThat() {
        Integer response =
                given()
                        .when()
                        .get("https://reqres.in/api/unknown")
                        .then()
                        .statusCode(200)
                        .extract().path("total");

        assertThat(response).isEqualTo(12);
    }
}
