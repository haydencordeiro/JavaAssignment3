package SeleniumScraperAssingment3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class SeleniumScraperAssignment3 {

	public static void main(String[] args) {
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to a website
        driver.get("https://www.google.com/");

        // Print the title
        System.out.println("Page Title: " + driver.getTitle());

        // Close the browser
        driver.quit();

	}

}
