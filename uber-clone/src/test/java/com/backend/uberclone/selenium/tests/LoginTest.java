package com.backend.uberclone.selenium.tests;

import com.backend.uberclone.selenium.pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends TestBase{

    @Test
    public void checkCustomerLogin() throws InterruptedException{
        HomePage homePage = new HomePage(driver);
        String urlCustomer = homePage.successfulLogin("serfezev@gmail.com","perica00");
        assertEquals(urlCustomer,"http://localhost:4200/client");
    }

    @Test
    public void checkDriverLogin() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        String urlDriver = homePage.successfulLogin("stevaszumza@gmail.com","perica00");
        assertEquals(urlDriver,"http://localhost:4200/driver");
    }

    @Test
    public void checkAdminLogin() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        String urlDriver = homePage.successfulLogin("bubibubisa@gmail.com","perica00");
        assertEquals(urlDriver,"http://localhost:4200/admin");
    }
    @Test
    public void checkWrongCredentialsLogin(){
        HomePage homePage = new HomePage(driver);
        String text = homePage.unsuccessfulLogin("serfezev@gmail.com","perica11");
        assertEquals("Wrong credentials! Try again.", text);
    }

    @Test
    public void checkEmptyCredentialsLogin(){
        HomePage homePage = new HomePage(driver);
        String text = homePage.unsuccessfulLogin("","");
        assertEquals("Fields must be filled!", text);
    }



}
