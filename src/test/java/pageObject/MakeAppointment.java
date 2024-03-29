package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class MakeAppointment extends BasePage{

    @FindBy(how = How.ID, using = "combo_facility")
    WebElement inputFacility;

    @FindBy(how = How.ID, using = "chk_hospotal_readmission")
    WebElement inputCheck;

    @FindBy(how = How.ID, using = "radio_program_medicare")
    WebElement inputRadioMedicare;

    @FindBy(how = How.ID, using = "radio_program_medicaid")
    WebElement inputRadioMedicaid;

    @FindBy(how = How.ID, using = "radio_program_none")
    WebElement inputRadioNone;

    @FindBy(how = How.ID, using = "txt_visit_date")
    WebElement inputDate;

    @FindBy(how = How.ID, using = "txt_comment")
    WebElement inputComment;

    @FindBy(how = How.ID, using = "btn-book-appointment")
    WebElement btnAppointment;

    public MakeAppointment(WebDriver driver){
        super(driver);
    }

    public FinalPage addAppointment(String calendar, String comment, String radio, String comboHospital, boolean check){
        Select combo = new Select(inputFacility);
        combo.selectByValue(comboHospital);

        if(check==true) {
            inputCheck.click();
        }

        if(radio.equals("Medicare")) {
            inputRadioMedicare.click();
        }else if(radio.equals("Medicaid")) {
            inputRadioMedicaid.click();
        }else {
            inputRadioNone.click();
        }

        inputDate.sendKeys(calendar);
        inputComment.sendKeys(comment);
        btnAppointment.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));

        return new FinalPage(driver);
    }
}
