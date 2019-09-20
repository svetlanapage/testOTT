import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;
import io.restassured.matcher.RestAssuredMatchers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiTest {
    @Test()
    public void testGet() {
        String url = "https://youdo.com/api/tasks/suggest/";


    //    Response getSuggests =
                given()
                        .param("query", "поч")
                        .when()
                        .get(url)
                        .then()
                        .statusCode(200)
                        .contentType("application/json")
                        .assertThat()
                        //  .body("error", equalTo(null))
                        .body("ResultObject", not(emptyArray()))
                        .body("ResultObject.Text", hasItem("починить стиральную машину"));
     //                   .extract()
       //                 .response();
    }

    @Test()
    public void testRedirect() {

        String url="http://youdo.ru";

        Response isRedirect =
                given()
                        .when()
                        .get(url)
                        .then()
                        .statusCode(301)
                        .header("location","https://www.youdo.com/")
                        //.get("https://www.youdo.com/")
                        .extract()
                        .response();
    }
}


