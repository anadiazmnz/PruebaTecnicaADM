package adm.blinklearning.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasicLogin: Class that will be used to control waiting timeouts
 */
public class Wait {

    /**
     * waitUntilElementAppears(): Waits until the specified element appears on the web page.
     *
     * @param dr   The WebDriver instance to use for waiting.
     * @param path The XPath locator of the element to wait for.
     */
    public static void waitUntilElementAppears(WebDriver dr, String path) {
        WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
    }

    /**
     * Pauses the execution for the specified number of milliseconds.
     *
     * @param millis The number of milliseconds to wait.
     * @throws RuntimeException If the thread is interrupted while sleeping.
     */
    public static void waiting(long millis) {
        try {
            Thread.sleep(millis); //Note that this is not the most accurate type of wait
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
