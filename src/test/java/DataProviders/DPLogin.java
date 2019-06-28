package DataProviders;

import org.testng.annotations.DataProvider;

public class DPLogin {

    @DataProvider(name="LoginFailedProvider")
    public Object[][] getDataLogin(){
        return new Object[][]{
                {"Jon Doe", "ThisIsNotAPassword"},
                {"Jhon Doe", "ThisIsNotAPasswor"},
                {"Jhon Doe", ""}
        };
    }
}
