Feature: User can create and edit posts

  Scenario: User creates a post
    When user creates a post with "This is my new post"
    Then the post is successfully created

  Scenario: User edits the post
    When user edits the post with updated body "This is my updated post"
    Then the post is successfully edited
