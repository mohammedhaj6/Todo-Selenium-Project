package com.qacart.todo.api;

import com.qacart.todo.config.EndPoint;
import com.qacart.todo.object.User;
import com.qacart.todo.utils.UserUtils;
import io.restassured.http.Cookie;
import io.restassured.response.Response;


import java.util.List;

import static io.restassured.RestAssured.given;

public class RegisterApi {
    private static List<Cookie> restAssuredCookies;
    private static String accessToken;
    private static   String userId;
    private static String firstName;
    public  String getToken(){
        return accessToken;
    }
    public List<Cookie> getCookies(){
        return restAssuredCookies;
    }
    public String getUserId(){
        return userId;
    }
    public String getFirstName(){
        return firstName;
    }
    public void register(){
        User user =UserUtils.generateRandomUser();
        Response response=
         given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .header("Content-Type","application/json")
                .body(user)

         .when()
                .post(EndPoint.API_REGISTER_ENDPOINT)
          .then()

                .extract().response();
        if(response.statusCode()!=201) {
            throw  new RuntimeException("Something went wrong with the request");
        }
        restAssuredCookies = response.detailedCookies().asList();
        accessToken = response.path("access_token");
        userId = response.path("userID");
        firstName = response.path("firstName");

    }
}