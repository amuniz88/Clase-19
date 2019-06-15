import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestClassCopy {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement btnAppointment;
    private String titleForm;
    private SoftAssert SA;

    @BeforeClass
    public void start(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        //maximisar pantalla en chrome
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start"); para Firefox
        options.addArguments("disable-infobars");

        driver  = new ChromeDriver(options);
        driver.get("https://katalon-demo-cura.herokuapp.com/");
        wait    = new WebDriverWait(driver, 10);
        SA = new SoftAssert();


//        options.addArguments()
    }

//    @BeforeMethod
//    public void startTest(){
    //options.addArguments("--headless"); para correr sin interfáz
    //para linux se necesita un paquete llamado xvFb
//    }

    @Test(priority = 0)
    public void login(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-make-appointment")));

        btnAppointment = driver.findElement(By.id("btn-make-appointment"));
        btnAppointment.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));

        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");

        driver.findElement(By.id("btn-login")).click();

        Assert.assertEquals(driver.getTitle(), "CURA Healthcare Service");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));

        titleForm = driver.findElement(By.tagName("h2")).getText();
//        titleForm = driver.findElement(By.cssSelector(".col-sm-12 h2")).getText();
        Assert.assertEquals(titleForm, "Make Appointment");
    }

    @Test(priority = 1)
    public void testAppointment(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("combo_facility")));

        Select combo = new Select(driver.findElement(By.cssSelector("[id=combo_facility]")));
        combo.selectByValue("Hongkong CURA Healthcare Center");

        //Check
        driver.findElement(By.id("chk_hospotal_readmission")).click();
        //Radio
        driver.findElement(By.id("radio_program_medicaid")).click();
        //Calendar
        driver.findElement(By.id("txt_visit_date")).sendKeys("07/06/2019");
        //Coment
        driver.findElement(By.id("txt_comment")).sendKeys("Comentario sobre la clase 19");
        //Click boton
        driver.findElement(By.id("btn-book-appointment")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));

        titleForm = driver.findElement(By.tagName("h2")).getText();
//        titleForm = driver.findElement(By.cssSelector(".col-xs-12 h2")).getText();
        Assert.assertEquals(titleForm, "Appointment Confirmation");

        //verificar los datos pero en caso de estar mal no detiene el test. Al final te indica cuales estan mal para corregir
        SA.assertEquals(driver.findElement(By.id("facility")).getText(), "Hongkong CURA Healthcare Center");
        SA.assertEquals(driver.findElement(By.id("hospital_readmission")).getText(), "YES");
        SA.assertEquals(driver.findElement(By.id("program")).getText(), "Medicaid");
        SA.assertEquals(driver.findElement(By.id("visit_date")).getText(),"07/06/2019");
        SA.assertEquals(driver.findElement(By.id("comment")).getText(), "Comentario sobre la clase 19");
    }

    @AfterClass
    public void tearDown(){
        SA.assertAll();
        driver.quit();
    }

}
