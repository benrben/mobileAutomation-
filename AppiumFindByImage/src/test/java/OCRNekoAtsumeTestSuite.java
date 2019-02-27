import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class OCRNekoAtsumeTestSuite {
    private AppiumDriver driver;
    private WebDriverWait wait;
    private OCR OCR;
    private File imgDir;
    public static String userName = "benreich2";
    public static String accessKey = "KxsSSvfQqR17qkgPxkPe";
    @Before
    public void setUp() throws Exception {

        //Appium setup for the app
        //needs to be installed on target device before the test
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("device", "Google Nexus 6");
        caps.setCapability("os_version", "6.0");
        caps.setCapability("app", "bs://4e17df6bb22f4d5d6159eea5757a0aa089a7b8e0");
        caps.setCapability("real_mobile", "true");
        //caps.setCapability("browserstack.local", "false");
        //caps.setCapability("browserstack.debug", "true");


        AndroidDriver driver = new AndroidDriver(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);
        WebElement idNumber = new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.id("tremorvideo.cesDemo:id/id_entry")));
        idNumber.sendKeys("https://raptor.tremorvideodsp.com/ws/rml/replacer?addTVEvents=true&sessionId=tvce&vastUri=https%3A%2F%2Fs.tremorvideodsp.com%2Fm%2F2019%2F02%2Fre684zMNs%2Fvast3.xml&app=true");

        WebElement loadAd = new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(By.id("tremorvideo.cesDemo:id/menubutton_loadad_frame")));
        loadAd.sendKeys("BrowserStack");
        loadAd.click();
        Thread.sleep(5000);


        WebElement allow1 =  new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.id("com.android.packageinstaller:id/permission_allow_button")));
        allow1.click();
        Thread.sleep(5000);

        WebElement allow2 =  new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.id("com.android.packageinstaller:id/permission_allow_button")));
        allow2.click();
        Thread.sleep(5000);

        WebElement allow3 =  new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.id("com.android.packageinstaller:id/permission_allow_button")));
        allow3.click();
        Thread.sleep(15000);

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        BufferedImage bi =  ImageIO.read(new File(scrFile.getAbsolutePath()));


       // FileUtils.copyFile(scrFile, new File("./screenShots/test.png"));
        //Sikuli settings
        OCR = new OCR(driver);
        Point2D xy = OCR.getCoords(bi , "/Users/taptica/Desktop/TremorAutomationTesting/mobileAutomation-/AppiumFindByImage/src/main/resources/ladybug.png");
        //OCR.clickByImage("lasybug.png");
        System.out.println(xy.getX() +"  " + xy.getY());
        OCR.clickByImage(xy);
        //location of screenshots
        File classpathRoot = new File(System.getProperty("user.dir"));
        imgDir = new File(classpathRoot, "src/main/resources");

        //switch to native app + portrait mode
        driver.context("NATIVE_APP");
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void print(){
        System.out.println("End Test");
    }

}
