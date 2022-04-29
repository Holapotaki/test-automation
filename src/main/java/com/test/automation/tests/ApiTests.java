package com.test.automation.tests;

import com.test.automation.model.EmailValidation;
import com.test.automation.model.Comment;
import com.test.automation.model.Post;
import com.test.automation.model.User;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

	@Test
    public void emailsInComments_ForDolphinePosts_ShouldBeInProperFormat() {

        JsonPath jsonPath = given()
                .when()
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .extract().body().jsonPath();

        List<User> users = jsonPath.getList("", User.class);
        User delphineUser = users.stream()
                .filter(u -> "Delphine".equals(u.getUsername()))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(delphineUser);

        jsonPath = given()
                .when()
                .get("/posts?userId=" + delphineUser.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .extract().body().jsonPath();

        List<Post> delphinePosts = jsonPath.getList("", Post.class);

        for (Post post : delphinePosts) {
            jsonPath = given()
                    .when()
                    .get("/comments?postId=" + post.getId())
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .assertThat()
                    .extract().body().jsonPath();

            List<Comment> postComments = jsonPath.getList("", Comment.class);

            for (Comment comment : postComments) {

                Assertions.assertTrue(EmailValidation.validateUsingRFC5322Regex(comment.getEmail()));
            }
        }
    }
}