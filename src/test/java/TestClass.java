import DataProviders.DPLogin;
import DataProviders.DPMakeAppointment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageObject.FinalPage;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MakeAppointment;

public class TestClass {

    String user = "John Doe";
    String pass = "ThisIsNotAPassword";
//    String comment = "Comentario sobre la clase 19";
//    String cal = "07/06/2019";
//    String facility = "Hongkong CURA Healthcare Center";
//    boolean check = true;
//    String radio = "Medicaid";
    String appoint = "Appointment Confirmation";
    String msgError = "Login failed! Please ensure the username and password are valid.";
    int contador = 0;

    private WebDriver driver;
    private SoftAssert SA;
    private HomePage homePage;
    private LoginPage loginPage;
    private MakeAppointment makeApp;
    private FinalPage finalPage;

    //alwaysRun = true -- Para indicar que siempre se ejecute para cada clase o methodo
    @BeforeMethod(alwaysRun = true)
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
    //Si lo ejecutamos desde el método es necesario los Optional porque no pasa por el xml
    //de lo contrario tiene que ser ejecutado desde el xml
    @Test//(priority = 0)
    @Parameters({"user","pass"})
    public void login(@Optional("John Doe") String us, @Optional("ThisIsNotAPassword") String pa){
        loginPage = homePage.clickMakeAppointment();
        loginPage.loginRet(us, pa);

        Assert.assertTrue(loginPage.Title());
        Assert.assertTrue(loginPage.subtitleAppointment());
    }

    @Test(dataProvider = "LoginFailedProvider", dataProviderClass = DPLogin.class)
    public void loginFailed(String user, String pass){
        loginPage = homePage.clickMakeAppointment();
        loginPage.loginFailed(user, pass);

        Assert.assertTrue(loginPage.msgErrorIsDisplayed());
        Assert.assertTrue(loginPage.msgErrorContains(msgError));
    }

    @Test(dataProvider = "AppointmentProvider", dataProviderClass = DPMakeAppointment.class)
    public void testAppointment(String cal, String comment, String radio, String facility, boolean check){
        contador = contador + 1;

        loginPage = homePage.clickMakeAppointment();
        makeApp = loginPage.loginRet(user, pass);

        finalPage = makeApp.addAppointment(cal, comment, radio, facility, check);

        Assert.assertTrue(finalPage.titleIdDisplayed());
        Assert.assertTrue(finalPage.titleContains(appoint));

        SA.assertTrue(finalPage.facilityContain(facility));
        System.out.println(check);
        SA.assertTrue(finalPage.hospitalContain(check));
        SA.assertTrue(finalPage.programContain(radio));
        SA.assertTrue(finalPage.visitDateContain(cal));
        SA.assertTrue(finalPage.commentContain(comment));
        SA.assertAll();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
