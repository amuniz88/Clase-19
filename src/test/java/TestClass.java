import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObject.FinalPage;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MakeAppointment;

public class TestClass {

    private WebDriver driver;
//    private WebDriverWait wait;
//    private WebElement btnAppointment;
//    private String titleForm;
    private SoftAssert SA;
    private HomePage homePage;
    private LoginPage loginPage;
    private MakeAppointment makeApp;
    private FinalPage finalPage;

    @BeforeClass
    public void start(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        //maximisar pantalla en chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");

        driver  = new ChromeDriver(options);
        driver.get("https://katalon-demo-cura.herokuapp.com/");

        SA = new SoftAssert();

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        makeApp = new MakeAppointment(driver);
        finalPage = new FinalPage(driver);

    }

    @Test(priority = 0)
    public void login(){
        loginPage = homePage.clickMakeAppointment();
        loginPage.loginRet("John Doe", "ThisIsNotAPassword");
        Assert.assertTrue(loginPage.Title());
        Assert.assertTrue(loginPage.subtitleAppointment());
    }

    @Test(priority = 1)
    public void testAppointment(){
        loginPage = homePage.clickMakeAppointment();
        makeApp = loginPage.loginRet("John Doe", "ThisIsNotAPassword");

        finalPage = makeApp.addAppointment("07/06/2019", "Comentario sobre la clase 19");

        Assert.assertTrue(finalPage.titleIdDisplayed());
        Assert.assertTrue(finalPage.titleContains());

        SA.assertTrue(finalPage.facilityContain());
        SA.assertTrue(finalPage.hospitalContain());
        SA.assertTrue(finalPage.programContain());
        SA.assertTrue(finalPage.visitDateContain());
        SA.assertTrue(finalPage.commentContain());
        SA.assertAll();
    }

    @AfterClass
    public void tearDown(){

        driver.quit();
    }
}
