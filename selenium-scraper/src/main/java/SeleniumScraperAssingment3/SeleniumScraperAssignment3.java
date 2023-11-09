package SeleniumScraperAssingment3;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class SeleniumScraperAssignment3 {

    public static Workbook getWorkbook(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return new XSSFWorkbook(fis);
        } catch (IOException e) {
            // If the file doesn't exist, create a new workbook
            return new XSSFWorkbook();
        }
    }
    
	public static class Podcast {
		String name;
		String imageURL;
		String url;
		
		
	    public static void writeDataToExcel(ArrayList<Podcast> dataList, String filePath) {
	        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(filePath)) {
	            Sheet sheet = workbook.createSheet("Sheet1");

	            // Create header row
	            Row headerRow = sheet.createRow(0);
	            headerRow.createCell(0).setCellValue("name");
	            headerRow.createCell(1).setCellValue("imageURL");
	            headerRow.createCell(2).setCellValue("URL");

	            // Populate data rows
	            int rowNum = 1;
	            for (Podcast p : dataList) {
	                Row row = sheet.createRow(rowNum++);
	                row.createCell(0).setCellValue(p.name);
	                row.createCell(1).setCellValue(p.imageURL);
	                row.createCell(2).setCellValue(p.url);

	                // Add more cells for other properties
	            }

	            // Write the workbook to the output stream
	            workbook.write(fileOut);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	}
	

	
	public static class PodcastEpisode{
		String episodeTitle;
		String episodeDescription;
		String episodeDuration;
		
	    public static void writeDataToExcel(ArrayList<PodcastEpisode> dataList, String filePath) {
	        try (Workbook workbook = getWorkbook(filePath); FileOutputStream fileOut = new FileOutputStream(filePath)) {
	            Sheet sheet = workbook.createSheet("Sheet3");

	            // Create header row
	            Row headerRow = sheet.createRow(0);
	            headerRow.createCell(0).setCellValue("episodeTitle");
	            headerRow.createCell(2).setCellValue("episodeDuration");
	            headerRow.createCell(1).setCellValue("episodeDescription");

	            // Populate data rows
	            int rowNum = 1;
	            for (PodcastEpisode p : dataList) {
	                Row row = sheet.createRow(rowNum++);
	                row.createCell(0).setCellValue(p.episodeTitle);
	                row.createCell(2).setCellValue(p.episodeDuration);
	                row.createCell(1).setCellValue(p.episodeDescription);

	                // Add more cells for other properties
	            }

	            // Write the workbook to the output stream
	            workbook.write(fileOut);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
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
        
        
        List<WebElement> listOfPodcastURLS = driver.findElements(By.cssSelector("[jsname='X7oyne']"));
        
        for(int i = 0; i < podcastList.size(); i++)
        {
        	podcastList.get(i).url = listOfPodcastURLS.get(i).getAttribute("href");;
        }



        WebElement titleTextElement = driver.findElement(By.cssSelector("[aria-label='Popular & trending']"));
        String titleText = titleTextElement.getText();
        System.out.println(titleText);
        podcastList.get(0).writeDataToExcel(podcastList, "./test.xlsx");

	}
	
	public static void task2(WebDriver driver) {
		String url = "https://podcasts.google.com/feed/aHR0cHM6Ly93d3cub21ueWNvbnRlbnQuY29tL2QvcGxheWxpc3QvZTczYzk5OGUtNmU2MC00MzJmLTg2MTAtYWUyMTAxNDBjNWIxL2E5MTAxOGE0LWVhNGYtNDEzMC1iZjU1LWFlMjcwMTgwYzMyNy80NDcxMGVjYy0xMGJiLTQ4ZDEtOTNjNy1hZTI3MDE4MGMzM2UvcG9kY2FzdC5yc3M?sa=X&ved=0CDEQjs4CKAFqFwoTCLjJuvLvt4IDFQAAAAAdAAAAABAC";
		driver.get(url);
        int numberOfEpisode = 100;
		ArrayList<PodcastEpisode> episodeData = new ArrayList<PodcastEpisode>();
		
        List<WebElement> listOfPodcastEpisodes = driver.findElements(By.cssSelector(".LTUrYb"));
        
        for(int i = 1; i < numberOfEpisode; i++)
        {
        	PodcastEpisode pe = new PodcastEpisode();
        	pe.episodeTitle = listOfPodcastEpisodes.get(i).getText();
        	episodeData.add(pe);
        }

        
        List<WebElement> listOfPodcastDescriptions = driver.findElements(By.cssSelector(".LrApYe"));

        for(int i = 1; i < numberOfEpisode - 1 ; i++)
        {
        	episodeData.get(i).episodeDescription = listOfPodcastDescriptions.get(i).getText();
        	System.out.println(episodeData.get(i).episodeDescription);
        }
        
        List<WebElement> listOfPodcastDurations = driver.findElements(By.cssSelector(".gUJ0Wc"));

        for(int i = 1; i < numberOfEpisode - 1; i++)
        {
        	episodeData.get(i).episodeDuration = listOfPodcastDurations.get(i).getText();
        	System.out.println(episodeData.get(i).episodeDescription);
        }
		
        episodeData.get(0).writeDataToExcel(episodeData, "./test.xlsx");

	}
	
	public static void main(String[] args) {
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        
//        task1(driver);
        task2(driver);
//        // Close the browser
        driver.quit();

	}

}
