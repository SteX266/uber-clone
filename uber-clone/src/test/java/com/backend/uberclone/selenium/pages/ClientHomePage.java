package com.backend.uberclone.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ClientHomePage {

    private WebDriver driver;

    @FindBy(id="start")
    WebElement startTextField;

    @FindBy(id="destination")
    WebElement destinationTextField;

    @FindBy(id="calculateButton")
    WebElement calculateButton;

    @FindBy(id="previewButton")
    WebElement previewButton;

    @FindBy(id="confirmReservationButton")
    WebElement confirmReservationButton;

    @FindBy(id="confirmPaymentButton")
    WebElement confirmPaymentButton;

    @FindBy(xpath = "//*[@id=\"cdk-overlay-5\"]/mat-snack-bar-container/div/div/div/div/simple-snack-bar/div[1]")
    WebElement snackBarText;

    @FindBy(id="rejectRideButton")
    WebElement rejectRideButton;

    @FindBy(xpath = "//*[@id=\"cdk-overlay-4\"]/mat-snack-bar-container/div/div/div/div/simple-snack-bar/div[1]")
    WebElement paymentCanceledSnackBar;

    @FindBy(id="logOutButton")
    WebElement logoutButton;


    public ClientHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void rejectRide() throws InterruptedException {
        Thread.sleep(800);

        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(rejectRideButton));
        this.rejectRideButton.click();
    }

    public void fillFields() throws InterruptedException {
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(startTextField));
        String start = "Alekse Santica 4 Novi Sad";
        String destination = "Gogoljeva 23 Novi Sad";


        startTextField.click();

        for(int i = 0;i<start.length();i++){
            Thread.sleep(200);

            String letter = String.valueOf(start.charAt(i));
            startTextField.sendKeys(letter);

        }

        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(destinationTextField));
        destinationTextField.click();

        for(int i = 0;i<destination.length();i++){
            Thread.sleep(200);

            String letter = String.valueOf(destination.charAt(i));
            destinationTextField.sendKeys(letter);
        }
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(calculateButton));
        Thread.sleep(800);



        calculateButton.click();

    }

    public void clickPreviewButton() throws InterruptedException {
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.elementToBeClickable(previewButton));
        Thread.sleep(1000);


        this.previewButton.click();
    }

    public void clickConfirmReservationButton() throws InterruptedException {
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.elementToBeClickable(confirmReservationButton));
        Thread.sleep(1000);


        this.confirmReservationButton.click();
    }

    public boolean isConfirmPaymentPresent(){
        return this.driver.findElements(By.id("confirmPaymentButton")).size() != 0;
    }

    public void clickConfirmPaymentButton() throws InterruptedException {
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.elementToBeClickable(confirmPaymentButton));
        Thread.sleep(1000);


        this.confirmPaymentButton.click();

    }

    public String getCurrentUrl() throws InterruptedException {
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.urlContains("http://localhost:4200/client/ride"));
        Thread.sleep(800);


        return this.driver.getCurrentUrl();
    }

    public String getPaymentCanceledText(){
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(paymentCanceledSnackBar));
        return this.paymentCanceledSnackBar.getText();

    }

    public void logout(){
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(logoutButton));

        this.logoutButton.click();
    }

}
