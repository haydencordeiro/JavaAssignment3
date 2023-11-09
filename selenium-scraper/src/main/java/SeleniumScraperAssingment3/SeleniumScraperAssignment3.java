package SeleniumScraperAssingment3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class SeleniumScraperAssignment3 {

	
	public static class Podcast {
		String name;
		String imageURL;
	}
	public static void sleepforTime(int time) {
        try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	
	public static void task1(WebDriver driver) {
//		open the podcast website that has to be scraped
		String url = "https://podcasts.google.com/";
		driver.get(url);
		

        List<WebElement> elementsWithClass = driver.findElements(By.cssSelector("[aria-label='Next']"));

        elementsWithClass.get(1).click();
        sleepforTime(2000);
        elementsWithClass.get(1).click();
        sleepforTime(2000);
        elementsWithClass.get(1).click();
        
        ArrayList<Podcast> podcastList = new ArrayList<Podcast>();
        List<WebElement> listOfPodcast = driver.findElements(By.cssSelector(".eWeGpe"));
        
        for (WebElement podcast : listOfPodcast) {
        	Podcast p = new Podcast();
        	p.name = podcast.getText();
        	podcastList.add(p);
        }
        
        
        List<WebElement> listOfPodcastImages = driver.findElements(By.cssSelector(".BhVIWc"));
        
        for(int i = 0; i < podcastList.size(); i++)
        {
        	podcastList.get(i).imageURL = listOfPodcastImages.get(i).getAttribute("src");;
        }

        System.out.println(podcastList);
        System.out.println(podcastList.get(0).name);
        System.out.println(podcastList.get(0).imageURL);

        System.out.println(podcastList);

        System.out.println(podcastList.size());
        
//        .BhVIWc
        
//        

		
		
        WebElement titleTextElement = driver.findElement(By.cssSelector("[aria-label='Popular & trending']"));
        String titleText = titleTextElement.getText();
        System.out.println(titleText);
//        elementsWithClass.get(1).click();

	}
	
	public static void main(String[] args) {
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        
        task1(driver);
//        // Close the browser
        driver.quit();

	}

}
