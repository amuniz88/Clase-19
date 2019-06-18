package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

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

    public FinalPage(WebDriver driver){
        super(driver);
    }

    public boolean titleIdDisplayed(){
        return titleform.isDisplayed();
    }

    public boolean titleContains(){
        return titleform.getText().contains("Appointment Confirmation");
    }

    public boolean facilityContain(){
        return txtFacility.getText().contains("Hongkong CURA Healthcare Center");
    }

    public boolean hospitalContain(){
        return txthospital.getText().contains("Yes");
    }

    public boolean programContain(){
        return txtprogram.getText().contains("Medicaid");
    }

    public boolean visitDateContain(){
        return txtVisitDate.getText().contains("07/06/2019");
    }

    public boolean commentContain(){
        return txtComment.getText().contains("Comentario sobre la clase 19");
    }
}
