import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestClass {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement btnAppointment, check, radio, coment, calendar;
    private String titleForm;

    @BeforeClass
    public void start(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver  = new ChromeDriver();
        driver.get("https://katalon-demo-cura.herokuapp.com/");
    }

    @BeforeMethod
    public void startTest(){
        wait    = new WebDriverWait(driver, 10);
        //options.addArguments("--headless"); para correr sin interfáz
        //para linux se necesita un paquete llamado xvFb
    }

    @Test(priority = 0)
    public void login(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-make-appointment")));

        btnAppointment = driver.findElement(By.id("btn-make-appointment"));
        btnAppointment.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-sm-12 h2")));

        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");

        driver.findElement(By.id("btn-login")).click();

        Assert.assertEquals(driver.getTitle(), "CURA Healthcare Service");

        titleForm = driver.findElement(By.cssSelector(".col-sm-12 h2")).getText();
        Assert.assertEquals(titleForm, "Make Appointment");
    }

    @Test(priority = 1)
    public void form(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("combo_facility")));

        Select combo = new Select(driver.findElement(By.id("id=combo_facility")));
        combo.selectByIndex(2);

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

        titleForm = driver.findElement(By.cssSelector(".col-xs-12 h2")).getText();
        Assert.assertEquals(titleForm, "Appointment Confirmation");


        //Probando che, modificate

    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
