package demo;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class ChromeApplication {
    public static void main(String[] args) {
        String baseUrl = "https://www.yunpian.com/entry/register";
        System.setProperty("webdriver.chrome.driver",
                "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl + "/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement inputBox = driver.findElement(By.className("rect"));
//        Assert.assertTrue(inputBox.isDisplayed());
        inputBox.sendKeys("123");
        WebElement verify = driver.findElement(By.className("verify-iconfont"));
        verify.click();
        WebElement slider = driver.findElement(By.className("yp-riddler-slider-btn"));
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(slider,100,0).perform();
    }
}
