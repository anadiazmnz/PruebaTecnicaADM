import adm.blinklearning.pages.Wait;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;

import static org.junit.Assert.fail;

/**
 * BasicLogin: Class that will be used to log in the application using Google OAuth
 */
public class GoogleLogin {

    //Declaration and instances (if proceed) of objects
    public static EdgeDriver dr;
    public static Properties pr;

    //Declaration and assignment of XPATH
    public static String XPATH_TITLE = "//h1[contains(text(),'BlinkLearning')]";
    public static String XPATH_BTN_GOOGLEAUTH = "//div[contains(text(),'Conectar con Google')]";
    public static String XPATH_INPUT_EMAIL = "//input[@type='email']";
    public static String XPATH_BTN_SIGUIENTE = "//span[contains(text(),'Siguiente')]/parent::button";

    /**
     * start(): Method that will establish the configuration of the driver and will launch it
     */
    @BeforeClass
    public static void start() {
        dr = new EdgeDriver();
        pr = new Properties();
        try {
            pr.load(new FileInputStream("src/main/java/adm/blinklearning/res/properties")); //Loading of properties
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Depending on the type of environment, the url could change. some of the following urls don't exist (testing)
        switch (pr.getProperty("environment")) {
            case "PRE":
                dr.get(pr.getProperty("pre-url"));
                break;
            case "INT":
                dr.get(pr.getProperty("int-url"));
                break;
            case "PRO":
                dr.get(pr.getProperty("pro-url"));
                break;
            default:
                dr.get(pr.getProperty("pro-url"));
                break;
        }
        dr.manage().window().maximize();
        dr.manage().deleteAllCookies();
    }

    /**
     * doLogin(): Method that will open Google OAuth
     */
    @Test
    public void doLogin() {
        try {
            Wait.waitUntilElementAppears(dr,XPATH_TITLE);

            if (dr.findElement(new By.ByXPath(XPATH_TITLE)).isDisplayed()) {
                System.out.println("Page has loaded correctly");
            } else {
                fail("The url has not been loaded correctly");
            }

            Wait.waitUntilElementAppears(dr, XPATH_BTN_GOOGLEAUTH);

            if (dr.findElement(new By.ByXPath(XPATH_BTN_GOOGLEAUTH)).isDisplayed()) {
                dr.findElement(new By.ByXPath(XPATH_BTN_GOOGLEAUTH)).click();
                System.out.println("Google Authentication button has been clicked");
            } else {
                fail("Google Authentication button is not displayed");
            }

            Wait.waitUntilElementAppears(dr,XPATH_INPUT_EMAIL);

            if (dr.findElement(new By.ByXPath(XPATH_INPUT_EMAIL)).isDisplayed()) {
                dr.findElement(new By.ByXPath(XPATH_INPUT_EMAIL)).click();
                dr.findElement(new By.ByXPath(XPATH_INPUT_EMAIL)).sendKeys("correo@correo.es");
                System.out.println("Email has been introduced");
            } else {
                fail("Email field has not been displayed correctly");
            }

            if (dr.findElement(new By.ByXPath(XPATH_BTN_SIGUIENTE)).isDisplayed()) {
                dr.findElement(new By.ByXPath(XPATH_BTN_SIGUIENTE)).click();
                Wait.waiting(5000); //As we don't know the following steps, we will use a sleep (it must be changed when the test is completed)
                System.out.println("'Siguiente' button has been clicked on");
            } else {
                fail("Login button has not been displayed correctly");
            }

            //As we dont have permissions, the following steps of the test will not be coded
            //TODO: Authentication process

        } catch (NoSuchElementException e) {
            fail("Element not found");
        } finally {
            dr.close();
        }
    }
}
