package locations;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationsControllerRestAssuredIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LocationsService locationsService;

    @BeforeEach
    void init () {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.requestSpecification =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON);

        locationsService.deleteAllLocations();
    }

    @Test
    public void testListLocations() {
        with()
                .body(new CreateLocationCommand("Fót", 1.1, 2.1))
                .post("/locations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Fót"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"))
                .log();
        with()
                .get("/locations")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("[0].name", equalTo("Fót"))
                .body("size()", equalTo(1));
    }

    @Test
    public void testListLocations2() {
        with()
                .body(new CreateLocationCommand("Fót", 1.1, 2.1))
                .post("/locations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Fót"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"))
                .log();
        with()
                .get("/locations2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("locations[0].name", equalTo("Fót"))
                .body("size()", equalTo(1));
    }

    @Test
    public void testFindLocationById() {
        with()
                .body(new CreateLocationCommand("Fót", 1.1, 2.1))
                .post("/locations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Fót"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"))
                .log();

        with()
                .body(new CreateLocationCommand("Dunakeszi", 2.1, 3.1))
                .post("/locations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Dunakeszi"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"))
                .log();

        with()
                .get("/locations/{id}",1)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Dunakeszi"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"));
    }

    @Test
    public void testCreateLocations() {
        with()
                .body(new CreateLocationCommand("Dunakeszi", 1.1, 2.1))
                .post("/locations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Dunakeszi"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"))
                .log();
    }

    @Test
    public void testUpdateLocation() {
        with()
                .body(new CreateLocationCommand("Dunakeszi", 1.1, 2.1))
                .post("/locations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Dunakeszi"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"))
                .log();

        with()
                .body(new UpdateLocationCommand("Fót", 1.1, 2.1))
                .put("/locations/{id}", 0)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("name", equalTo("Fót"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"))
                .log();
    }

    @Test
    public void testDeleteLocation() {
        with()
                .body(new CreateLocationCommand("Dunakeszi", 1.1, 2.1))
                .post("/locations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Dunakeszi"))
                .log();

        with()
                .delete("/locations/{id}", 0)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .log();

    }

    @Test
    public void validate() {
        with()
                .body(new CreateLocationCommand("Dunakeszi", 1.1, 2.1))
                .post("/locations")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"));
    }


}
