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
 * BasicLogin: Class that will be used to log in the application using basic email and password method
 */
public class BasicLogin {

    //Declaration of objects
    public static EdgeDriver dr;
    public static Properties pr;

    //Declaration and assignment of XPATH
    public static String XPATH_EMAIL = "//input[contains(@label,'Correo electr')]";
    public static String XPATH_TITLE = "//h1[contains(text(),'BlinkLearning')]";
    public static String XPATH_MSG_LOGININC = "//div[contains(text(),'El usuario o la contraseña son incorrectos')]";
    public static String XPATH_BTN_INICIARSES = "//div[contains(text(),'INICIAR SESI')]";
    public static String XPATH_PASSWD = "//input[@type='password']";

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
     * doLogin(): Method that will introduce the params to log in the application
     */
    @Test
    public void doLogin() {
        try {
            Wait.waitUntilElementAppears(dr, XPATH_TITLE);

            if (dr.findElement(new By.ByXPath(XPATH_TITLE)).isDisplayed()) {
                System.out.println("Page has loaded correctly");
            } else {
                fail( "The url has not been loaded correctly");
            }

            if (dr.findElement(new By.ByXPath(XPATH_EMAIL)).isDisplayed()) {
                dr.findElement(new By.ByXPath(XPATH_EMAIL)).click();
                dr.findElement(new By.ByXPath(XPATH_EMAIL)).sendKeys(pr.getProperty("email"));
                System.out.println("Email has been introduced");
            } else {
                fail( "Email field has not been displayed correctly");
            }

            if (dr.findElement(new By.ByXPath(XPATH_PASSWD)).isDisplayed()) {
                dr.findElement(new By.ByXPath(XPATH_PASSWD)).click();
                dr.findElement(new By.ByXPath(XPATH_PASSWD)).sendKeys(pr.getProperty("password"));
                System.out.println("Password has been introduced");
            } else {
                fail( "Password field has not been displayed correctly");
            }

            if (dr.findElement(new By.ByXPath(XPATH_BTN_INICIARSES)).isDisplayed()) {
                dr.findElement(new By.ByXPath(XPATH_BTN_INICIARSES)).click();
                Wait.waiting(5000); //As we don't know the following steps, we will use a sleep (it must be changed when the test is completed)
                System.out.println("'INICIAR SESION' button has been clicked on");
            } else {
                fail( "Login button has not been displayed correctly");
            }

            //Ideally, we should also check the following page but as credentials have not been provided,
            //we will presume that the login has been done correctly

            if (dr.findElement(new By.ByXPath(XPATH_MSG_LOGININC)).isDisplayed()) {
                fail("Error: Credentials are not correct. Login has failed");
            } else {
                System.out.println("User has logged on the application as expected");
            }
        } catch (NoSuchElementException e) {
            fail("Element not found");
        } finally {
            dr.close();
        }
    }
}
