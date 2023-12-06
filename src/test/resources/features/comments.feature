Feature: User can comment on posts

  Scenario: User comments on a post
    When user comments on a post with "I like it"
    Then the comment is successfully created
