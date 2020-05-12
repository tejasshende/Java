package StepDefinations;

import PO.Login;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStepDef {
    Login login=new Login();

    @Given("^User launch the browser and hit the desired URL$")
    public void userLaunchTheBrowserAndHitTheDesiredURL() {
        login.launchBrowser("Chrome");
    }

    @When("^User enters valid credentials for FB account and hit the login button$")
    public void userEntersValidCredentialsForFBAccountAndHitTheLoginButton() {
        login.enterFBCredentials();
    }

    @Then("^User should be able to login and should see the FB landing page$")
    public void userShouldBeAbleToLoginAndShouldSeeTheFBLandingPage() {
        login.validateLogin();
    }
}
