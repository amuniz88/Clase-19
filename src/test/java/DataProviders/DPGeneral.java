package DataProviders;

import org.testng.annotations.DataProvider;

public class DPGeneral {

    @DataProvider(name="LoginFailedProvider")
    public Object[][] getDataLogin(){
        return new Object[][]{
                {"Jon Doe", "ThisIsNotAPassword"},
                {"Jhon Doe", "ThisIsNotAPasswor"},
                {"Jhon Doe", ""}
        };
    }

    @DataProvider(name="AppointmentProvider")
    public Object[][] getDataAppointment() {
        return new Object[][]{
                {"07/06/2019", "Comentario sobre la clase 19", "Medicaid", "Hongkong CURA Healthcare Center", true},
                {"08/06/2019", "Comentario 2", "Medicare", "Tokyo CURA Healthcare Center", true},
                {"09/06/2019", "Comentario 3", "Medicare", "Seoul CURA Healthcare Center", false}
        };
    }

}
