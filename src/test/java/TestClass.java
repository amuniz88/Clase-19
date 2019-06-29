import DataProviders.DPGeneral;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObject.FinalPage;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MakeAppointment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestClass {

    String user = "John Doe";
    String pass = "ThisIsNotAPassword";
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
    @Parameters({"usuario", "password", "browser"})
    public void start(String usuario, String password, String browser){
        if(browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

            //maximisar pantalla en chrome
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            options.addArguments("disable-infobars");
        }else if (browser.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        }

        Properties properties = new Properties();
        InputStream in = getClass().getResourceAsStream("/config.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.get(properties.getProperty("URL"));
        //No usamos esta línea en este ejercicio pero a modo de explicación la colocamos
        String a = properties.getProperty("db.connection");

        this.user = usuario;
        this.pass = password;

        SA = new SoftAssert();

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        makeApp = new MakeAppointment(driver);
        finalPage = new FinalPage(driver);

    }
//Si lo ejecutamos desde el método es necesario los Optional porque no pasa por el xml
//de lo contrario tiene que ser ejecutado desde el xml
//Ejemplo de un la parametrización por archivo.xml
//    public void login(@Optional("John Doe") String us, @Optional("ThisIsNotAPassword") String pa){
    @Test
    public void login(){
        loginPage = homePage.clickMakeAppointment();
        loginPage.loginRet(user, pass);

        Assert.assertTrue(loginPage.Title());
        Assert.assertTrue(loginPage.subtitleAppointment());
    }

    @Test(dataProvider = "LoginFailedProvider", dataProviderClass = DPGeneral.class)
    public void loginFailed(String user, String pass){
        loginPage = homePage.clickMakeAppointment();
        loginPage.loginFailed(user, pass);

        Assert.assertTrue(loginPage.msgErrorIsDisplayed());
        Assert.assertTrue(loginPage.msgErrorContains(msgError));
    }

    @Test(dataProvider = "AppointmentProvider", dataProviderClass = DPGeneral.class)
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
