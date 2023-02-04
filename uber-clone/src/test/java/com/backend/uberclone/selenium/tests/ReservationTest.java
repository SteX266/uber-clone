package com.backend.uberclone.selenium.tests;

import com.backend.uberclone.selenium.pages.ClientHomePage;
import com.backend.uberclone.selenium.pages.HomePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;


public class ReservationTest extends TestBase{

    public static WebDriver otherDriver;

    @BeforeAll
    public static void initializeOtherWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        otherDriver = new ChromeDriver();

        otherDriver.manage().window().maximize();

//        otherDriver = new ChromeDriver();

        //driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }


    @RepeatedTest(10)
    public void testCreateReservation() throws InterruptedException {

        HomePage homePage = new HomePage(driver);

        HomePage homePage1 = new HomePage(otherDriver);

        homePage1.successfulLogin("stevaszumza@gmail.com","perica00");

        homePage.successfulLogin("serfezev@gmail.com","perica00");

        ClientHomePage clientHomePage = new ClientHomePage(driver);

        clientHomePage.fillFields();
        clientHomePage.clickPreviewButton();
        clientHomePage.clickConfirmReservationButton();
        clientHomePage.clickConfirmPaymentButton();
        String url = clientHomePage.getCurrentUrl();

        ClientHomePage driverHomePage = new ClientHomePage(otherDriver);


        assertTrue(url.startsWith("http://localhost:4200/client/ride"));
        driverHomePage.rejectRide();
        driverHomePage.logout();

    }

    @RepeatedTest(10)
    public void testCreateReservationNoCoins() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        HomePage homePage1 = new HomePage(otherDriver);
        homePage1.successfulLogin("stevaszumza@gmail.com","perica00");
        homePage.successfulLogin("esteban@gmail.com","perica00");
        ClientHomePage clientHomePage = new ClientHomePage(driver);
        clientHomePage.fillFields();
        clientHomePage.clickPreviewButton();
        clientHomePage.clickConfirmReservationButton();
        assertFalse(clientHomePage.isConfirmPaymentPresent());
        ClientHomePage driverHomePage = new ClientHomePage(otherDriver);

        driverHomePage.logout();

    }

    @Test
    public void testCreateReservationNoDriver() throws InterruptedException {
        HomePage homePage = new HomePage(driver);

        homePage.successfulLogin("serfezev@gmail.com","perica00");

        ClientHomePage clientHomePage = new ClientHomePage(driver);

        clientHomePage.fillFields();
        clientHomePage.clickPreviewButton();
        clientHomePage.clickConfirmReservationButton();
        clientHomePage.clickConfirmPaymentButton();
        String text = clientHomePage.getPaymentCanceledText();
        assertEquals("Payment canceled", text);

    }


    @AfterAll
    public static void quitOtherDriver() {
        otherDriver.quit();
    }

}
