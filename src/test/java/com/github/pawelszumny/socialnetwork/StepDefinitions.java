package com.github.pawelszumny.socialnetwork;

import com.github.pawelszumny.socialnetwork.requestbodies.CreateCommentRequestBody;
import com.github.pawelszumny.socialnetwork.requestbodies.CreatePostRequestBody;
import com.github.pawelszumny.socialnetwork.requestbodies.EditPostRequestBody;
import com.github.pawelszumny.socialnetwork.utils.UserDeserializer;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;

import static com.github.pawelszumny.socialnetwork.utils.JsonAssertUtils.assertJsonEquals;
import static com.github.pawelszumny.socialnetwork.utils.RequestSerializer.serialize;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StepDefinitions {
    private Response response;
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";
    CreatePostRequestBody createPostRequestBody;
    CreateCommentRequestBody createCommentRequestBody;
    private int createdPostId;
    private String postContent;

    @When("user creates a post with {string}")
    public void userCreatesAPost(String content) {
        postContent = content;
        createPostRequestBody = new CreatePostRequestBody(1, 101, "My title", postContent);

        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/posts")
                .contentType("application/json")
                .body(createPostRequestBody)
                .post()
                .then().extract().response();

        createdPostId = response.jsonPath().getInt("id");
    }

    @Then("the post is successfully created")
    public void assertIsPostSuccessfullyCreated() {
        Assert.assertEquals("Unexpected status code", 201, response.getStatusCode());
        String requestBody = serialize(createPostRequestBody);
        String responseBody = response.getBody().asString();
        assertJsonEquals("Post response body does not match its request body",
                requestBody, responseBody);
    }

    @When("user edits the post with updated body {string}")
    public void userEditsThePost(String content) {
        postContent = content;
        EditPostRequestBody editPostRequestBody = new EditPostRequestBody();
        editPostRequestBody.setBody(postContent);

        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/posts/" + createdPostId)
                .contentType("application/json")
                .body(postContent)
                .patch()
                .then().extract().response();
    }

    @Then("the post is successfully edited")
    public void assertIsPostSuccessfullyEdited() {
        Assert.assertEquals("Unexpected status code", 200, response.getStatusCode());
        assertEquals(
                "Post content after edit does not match",
                postContent, response.jsonPath().getString("body"));
    }

    @When("user comments on a post with {string}")
    public void userCommentsOnAPost(String content) {
        createCommentRequestBody = new CreateCommentRequestBody(
                1, 501, "comment name", "test@test.com", content);

        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/comments")
                .contentType("application/json")
                .body(createCommentRequestBody)
                .post()
                .then().extract().response();
    }

    @Then("the comment is successfully created")
    public void assertIsCommentSuccessfullyCreated() {
        Assert.assertEquals("Unexpected status code", 201, response.getStatusCode());
        String requestBody = serialize(createCommentRequestBody);
        String responseBody = response.getBody().asString();
        assertJsonEquals("Comment response body does not match its request body",
                requestBody, responseBody);
    }

    @When("user requests the list of users")
    public void userRequestsListOfUsers() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/users")
                .contentType("application/json")
                .get()
                .then().extract().response();
    }

    @Then("the list of users is successfully displayed")
    public void assertIsListOfUsersIsSuccessfullyDisplayed() {
        Assert.assertEquals("Unexpected status code", 200, response.getStatusCode());
        List<User> userList = UserDeserializer.deserializeUsers(response);
        assertFalse("User list should not be empty", userList.isEmpty());
        assertThat("User list should contain user with name 'Leanne Graham'",
                userList, hasItem(hasProperty("name", equalTo("Leanne Graham"))));
        assertThat("User list should contain user with email 'Sincere@april.biz'",
                userList, hasItem(hasProperty("email", equalTo("Sincere@april.biz"))));
    }

}
