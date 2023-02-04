package com.backend.uberclone.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    private static String PAGE_URL = "http://localhost:4200/";

    @FindBy(id="loginButton")
    WebElement loginButton;

    @FindBy(id="email")
    WebElement emailTextBox;

    @FindBy(id="password")
    WebElement passwordTextBox;

    @FindBy(id="submitLogin")
    WebElement signInButton;

    @FindBy(id="logOutButton")
    WebElement logoutButton;
    @FindBy(xpath = "//*[@id=\"cdk-overlay-1\"]/mat-snack-bar-container/div/div/div/div/simple-snack-bar/div[1]")
    WebElement snackBarText;


    @FindBy(xpath="cdk-overlay-1")
    WebElement snackBarTwo;
    public HomePage(WebDriver driver){
        this.driver = driver;
        driver.get(PAGE_URL);

        PageFactory.initElements(driver,this);
    }

    public String successfulLogin(String username, String password) throws InterruptedException {
        this.login(username, password);
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(logoutButton));
        return driver.getCurrentUrl();

    }

    public String unsuccessfulLogin(String username, String password) {
        this.login(username,password);
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(snackBarText));
        return snackBarText.getText();


    }

    public void login(String username, String password){
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();

        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.visibilityOf(emailTextBox));
        emailTextBox.sendKeys(username);
        passwordTextBox.sendKeys(password);
        signInButton.click();

    }

}
