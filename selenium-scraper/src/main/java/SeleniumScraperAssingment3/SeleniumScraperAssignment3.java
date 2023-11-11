// Import statements for necessary libraries and classes
package SeleniumScraperAssingment3;
import java.io.FileInputStream;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Main class for the Selenium Scraper Assignment 3
public class SeleniumScraperAssignment3 {

    // Utility method to get a Workbook from a file path, creating a new one if the file doesn't exist
    public static Workbook getWorkbook(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return new XSSFWorkbook(fis);
        } catch (IOException e) {
            // If the file doesn't exist, create a new workbook
            return new XSSFWorkbook();
        }
    }

    // Inner class representing a Podcast
    public static class Podcast {
        String name;
        String imageURL;
        String url;

        // Method to write Podcast data to an Excel file
        public static void writeDataToExcel(ArrayList<Podcast> dataList, String sheetName) {
            try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream("./output.xlsx")) {
                Sheet sheet = workbook.createSheet(sheetName);

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
                }

                // Write the workbook to the output stream
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Inner class representing a Podcast Episode
    public static class PodcastEpisode {
        String episodeTitle;
        String episodeDescription;
        String episodeDuration;

        // Method to write Podcast Episode data to an Excel file
        public static void writeDataToExcel(ArrayList<PodcastEpisode> dataList, String sheetName) {
            try (Workbook workbook = getWorkbook("./output.xlsx"); FileOutputStream fileOut = new FileOutputStream("./output.xlsx")) {
                Sheet sheet = workbook.createSheet(sheetName);

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
                }

                // Write the workbook to the output stream
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Utility method to pause execution for a specified time
    public static void sleepforTime(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to perform Task 1: Scrape podcast information from a website
    public static ArrayList<Podcast> task1(WebDriver driver) {
        // Open the podcast website that has to be scraped
        String url = "https://podcasts.google.com/";
        driver.get(url);

        // Click the "Next" button multiple times to load more podcasts
        List<WebElement> elementsWithClass = driver.findElements(By.cssSelector("[aria-label='Next']"));
        elementsWithClass.get(1).click();
        sleepforTime(2000);
        elementsWithClass.get(1).click();
        sleepforTime(2000);
        elementsWithClass.get(1).click();

        // Extract podcast information and store it in a list
        ArrayList<Podcast> podcastList = new ArrayList<Podcast>();
        List<WebElement> listOfPodcast = driver.findElements(By.cssSelector(".eWeGpe"));
        for (WebElement podcast : listOfPodcast) {
            Podcast p = new Podcast();
            p.name = podcast.getText();
            podcastList.add(p);
        }

        // Extract podcast image URLs
        List<WebElement> listOfPodcastImages = driver.findElements(By.cssSelector(".BhVIWc"));
        for (int i = 0; i < podcastList.size(); i++) {
            podcastList.get(i).imageURL = listOfPodcastImages.get(i).getAttribute("src");
        }

        // Extract podcast URLs
        List<WebElement> listOfPodcastURLS = driver.findElements(By.cssSelector("[jsname='X7oyne']"));
        for (int i = 0; i < podcastList.size(); i++) {
            podcastList.get(i).url = listOfPodcastURLS.get(i).getAttribute("href");
        }

        // Print the title text and write podcast data to Excel
        WebElement titleTextElement = driver.findElement(By.cssSelector("[aria-label='Popular & trending']"));
        String titleText = titleTextElement.getText();
        System.out.println(titleText);
        podcastList.get(0).writeDataToExcel(podcastList, "Podcasts");
        return podcastList;
    }

    // Method to perform Task 2: Scrape podcast episode information
    public static void task2(WebDriver driver, ArrayList<Podcast> podcastList) {
        for (int i = 0; i < 10; i++) {

       	try {        		
       		task2helper(driver, podcastList.get(i));
    	}
    	catch (Exception e){
    		
    	}
    }
    }

    // Helper method for Task 2 to scrape podcast episode information for a specific podcast
    public static void task2helper(WebDriver driver, Podcast p) {
        driver.get(p.url);
        int numberOfEpisode = 100;
        ArrayList<PodcastEpisode> episodeData = new ArrayList<PodcastEpisode>();

        // Extract episode titles
        List<WebElement> listOfPodcastEpisodes = driver.findElements(By.cssSelector(".LTUrYb"));
        if (listOfPodcastEpisodes.size() < numberOfEpisode) numberOfEpisode = listOfPodcastEpisodes.size();
        for (int i = 1; i < numberOfEpisode; i++) {
            PodcastEpisode pe = new PodcastEpisode();
            pe.episodeTitle = listOfPodcastEpisodes.get(i).getText();
            episodeData.add(pe);
        }

        // Extract episode descriptions
        List<WebElement> listOfPodcastDescriptions = driver.findElements(By.cssSelector(".LrApYe"));
        for (int i = 1; i < numberOfEpisode - 1; i++) {
            episodeData.get(i).episodeDescription = listOfPodcastDescriptions.get(i).getText();
            System.out.println(episodeData.get(i).episodeDescription);
        }

        // Extract episode durations
        List<WebElement> listOfPodcastDurations = driver.findElements(By.cssSelector(".gUJ0Wc"));
        
        for (int i = 1; i < numberOfEpisode - 1; i++) {
            episodeData.get(i).episodeDuration = listOfPodcastDurations.get(i).getText();
            System.out.println(episodeData.get(i).episodeDescription);
        }
        
        // Write podcast episode data to Excel
        episodeData.get(0).writeDataToExcel(episodeData, p.name);
    }

    // Method to perform Task 3: Play the first podcast for each of the top 10 podcasts
    public static void task3(WebDriver driver, ArrayList<Podcast> podcastList) {
        for (int i = 0; i < 10; i++) {
        	try {        		
        		task3helper(driver, podcastList.get(i));
        	}
        	catch (Exception e){
        		
        	}
        }
    }

    // Helper method for Task 3 to play the first podcast for a specific podcast
    public static void task3helper(WebDriver driver, Podcast p) {
        driver.get(p.url);

        // Play the first podcast
        WebElement playPodcast = driver.findElement(By.cssSelector(".gUJ0Wc"));
        playPodcast.click();

        // Wait for the play slider to be visible
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By playingSliderLocator = By.cssSelector("[jsname='SxecR']");
//        wait.until(ExpectedConditions.elementToBeClickable(playingSliderLocator));
        wait.until(attributeGreaterThan(playingSliderLocator, "aria-valuenow", 5));


        // Click on the play/pause button
        WebElement playPauseButton = driver.findElement(By.cssSelector("[jsname='zTjYjc']"));
        playPauseButton.click();
    }

    // Main method
    public static void main(String[] args) {
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Perform Task 1
        ArrayList<Podcast> podcastList = task1(driver);

        // Perform Task 2
         task2(driver, podcastList);

        // Perform Task 3
        task3(driver, podcastList);

        // Close the browser
        driver.quit();
    }

//     Custom ExpectedCondition to wait until the specified attribute value of an element is greater than a given value.
    private static ExpectedCondition<Boolean> attributeGreaterThan(By locator, String attributeName, int value) {
        return driver -> {
            // Find the WebElement using the specified locator
            WebElement element = driver.findElement(locator);

            // Get the value of the specified attribute
            String attributeValue = element.getAttribute(attributeName);

            try {
                // Parse the attribute value as an integer
                int numericValue = Integer.parseInt(attributeValue);

                // Return true if the parsed value is greater than the specified threshold value
                return numericValue > value;
            } catch (NumberFormatException e) {
                // If the attribute value cannot be parsed as an integer, return false
                return false;
            }
        };
    }
}
