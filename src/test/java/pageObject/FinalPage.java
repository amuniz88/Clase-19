package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FinalPage extends BasePage{

    @FindBy(how = How.ID, using = "facility")
    WebElement txtFacility;

    @FindBy(how = How.ID, using = "hospital_readmission")
    WebElement txthospital;

    @FindBy(how = How.ID, using = "program")
    WebElement txtprogram;

    @FindBy(how = How.ID, using = "visit_date")
    WebElement txtVisitDate;

    @FindBy(how = How.ID, using = "comment")
    WebElement txtComment;

    @FindBy(how = How.TAG_NAME, using = "h2")
    WebElement titleform;

    @FindBy(css = ".btn-default")
    WebElement btn_HomePage;

    public FinalPage(WebDriver driver){
        super(driver);
    }

    public void goToHomepage(){
        btn_HomePage.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
    }

    public boolean titleIdDisplayed(){
        return titleform.isDisplayed();
    }

    public boolean titleContains(String valor){
        return titleform.getText().contains(valor);
    }

    public boolean facilityContain(String valor){
        return txtFacility.getText().contains(valor);
    }

    public boolean hospitalContain(boolean check){
        if(check) {
            System.out.println("YES");
            return txthospital.getText().contains("Yes");
        }else{
            System.out.println("NO");
            return txthospital.getText().contains("No");
        }
    }

    public boolean programContain(String valor){
        return txtprogram.getText().contains(valor);
    }

    public boolean visitDateContain(String valor){
        return txtVisitDate.getText().contains(valor);
    }

    public boolean commentContain(String valor){
        return txtComment.getText().contains(valor);
    }
}
