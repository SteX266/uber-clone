package com.backend.uberclone.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverHomePage {
    private WebDriver driver;


    @FindBy(id="logOutButton")
    WebElement logoutButton;

    @FindBy(id="endRideButton")
    WebElement endRideButton;

    @FindBy(id="startRideButton")
    WebElement startRideButton;

    public DriverHomePage(WebDriver driver){
        this.driver = driver;
    }

    public void logout(){
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.elementToBeClickable(By.id("logOutButton")));

        this.driver.findElement(By.id("logOutButton")).click();

    }

    public void startRide(){
        (new WebDriverWait(driver, Duration.ofSeconds(180))).until(ExpectedConditions.elementToBeClickable(By.id("startRideButton")));

        this.driver.findElement(By.id("startRideButton")).click();

    }

    public void endRide(){
        (new WebDriverWait(driver, Duration.ofSeconds(180))).until(ExpectedConditions.elementToBeClickable(By.id("endRideButton")));


        this.driver.findElement(By.id("endRideButton")).click();
    }
}
