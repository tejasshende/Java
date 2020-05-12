Feature: Validation of Login functionality of FB
  Background: User has valid FB credentials. User launched the browser. This scenario will verify the login functionality of FB

    @Sanity
    Scenario: Login to FB
      Given User launch the browser and hit the desired URL
      When User enters valid credentials for FB account and hit the login button
      Then User should be able to login and should see the FB landing page

