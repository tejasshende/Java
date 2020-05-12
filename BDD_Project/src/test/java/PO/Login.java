package PO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Login {

    //Login screen objects
    By emailField = By.xpath("//input[@type='email']");
    By passwordField = By.xpath("//input[@type='password']");
    By loginBtn = By.xpath("//input[@value='Log In']");
    WebDriver driver;
    DesiredCapabilities dc;

    public void launchBrowser(String browser) {
        try {
            if(browser.equalsIgnoreCase("IE")) {
                System.setProperty("webdriver.ie.driver", "src/test/resources/References/IEDriverServer.exe");
                dc = DesiredCapabilities.internetExplorer();
                dc.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
                dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                dc.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
                dc.setCapability("ignoreZoomSetting", true);
                dc.setJavascriptEnabled(true);
                driver = new InternetExplorerDriver(dc);
                driver.manage().window().maximize();
                driver.get("https://www.facebook.com");
            } else {
                System.setProperty("webdriver.chrome.driver", "src/test/resources/References/chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.get("https://www.facebook.com");
            }
            System.out.println("browser launch successfully...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterFBCredentials(){
        try {
            driver.findElement(emailField).sendKeys("XXXXXXX");
            driver.findElement(passwordField).sendKeys("XXXXXXX");
            driver.findElement(loginBtn).click();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void validateLogin(){
        System.out.println("Login successful...");
    }



}
