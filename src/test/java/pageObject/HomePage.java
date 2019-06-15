package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;

    @FindBy(how = How.ID, using = "user")
    WebElement inputUser;

    @FindBy(how = How.ID, using = "pass")
    WebElement inputPassword;

//    @FindBy(how = How.ID, using = "inputButton")  esta comentado porque se debe hacer con esta línea pero es lo mismo no hacerlo, te toma el valor inputButton y lo primero que recorre son todos los ID buscando, si no lo encuentra sigue con los name y luego con class, etc
    WebElement inputButton;

    //Constructor
    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //método que se puede utilizar en varios lugares
    public void login(String usuario, String password){
        inputUser.sendKeys(usuario);
        inputPassword.sendKeys(password);
        inputButton.click();
    }
}
