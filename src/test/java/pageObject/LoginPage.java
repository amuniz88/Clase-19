package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{

    @FindBy(how = How.ID, using = "txt-username")
    WebElement inputUser;

    @FindBy(how = How.ID, using = "txt-password")
    WebElement inputPassword;

    @FindBy(how = How.ID, using = "btn-login")
    WebElement inputButton;

    @FindBy(how = How.TAG_NAME, using = "h2")
    WebElement subTitle;

    //Constructor
    public LoginPage(WebDriver driver){
        super(driver);
    }

//    public void login(String usuario, String password){
//        inputUser.sendKeys(usuario);
//        inputPassword.sendKeys(password);
//        inputButton.click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("combo_facility")));
//    }

    public MakeAppointment loginRet(String usuario, String password){
        inputUser.sendKeys(usuario);
        inputPassword.sendKeys(password);
        inputButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("combo_facility")));

        return new MakeAppointment(driver);
    }

    public boolean Title(){
        return driver.getTitle().contains("CURA Healthcare Service");
    }

    public boolean subtitleAppointment(){
        return subTitle.getText().contains("Make Appointment");
    }
}
