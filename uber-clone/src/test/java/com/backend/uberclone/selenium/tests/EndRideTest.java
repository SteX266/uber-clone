package com.backend.uberclone.selenium.tests;

import com.backend.uberclone.selenium.pages.ClientHomePage;
import com.backend.uberclone.selenium.pages.DriverHomePage;
import com.backend.uberclone.selenium.pages.HomePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndRideTest extends TestBase{
    public static WebDriver otherDriver;


    @BeforeAll
    public static void initializeOtherWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        otherDriver = new ChromeDriver();

        otherDriver.manage().window().maximize();

//        otherDriver = new ChromeDriver();

        //driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }


    @Test
    public void testEndReservation() throws InterruptedException {

        HomePage homePage = new HomePage(driver);

        HomePage homePage1 = new HomePage(otherDriver);

        homePage1.successfulLogin("stevaszumza@gmail.com","perica00");

        homePage.successfulLogin("serfezev@gmail.com","perica00");

        ClientHomePage clientHomePage = new ClientHomePage(driver);

        clientHomePage.fillFields();
        clientHomePage.clickPreviewButton();
        clientHomePage.clickConfirmReservationButton();
        clientHomePage.clickConfirmPaymentButton();

        DriverHomePage driverHomePage = new DriverHomePage(otherDriver);
        driverHomePage.startRide();
        driverHomePage.endRide();

        driverHomePage.logout();

    }

    @AfterAll
    public static void quitOtherDriver() {
        otherDriver.quit();
    }
}
