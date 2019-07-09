package test;

import javaFiles.SaveToCSV;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static test.Constants.*;

class MainTest {

    private static ChromeDriver chromeDriver;

    @BeforeAll
    public static void initialize() {
        System.setProperty(SYSTEM_PROPERTY_1, SYSTEM_PROPERTY_2);

        chromeDriver = new ChromeDriver();
        chromeDriver.get(FIRST_URL);

        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

    } // initialize



    @Test
    public void seleniumTest() {

        ArrayList<String> linkList = new ArrayList<>();
        List<WebElement> webElementsList = chromeDriver.findElements(By.cssSelector(".r a "));


        int linksNumber =  webElementsList.size() > 10 ? 10 : webElementsList.size();

        for(int i = 0; i < linksNumber; i++) {
            linkList.add('"' + webElementsList.get(i).getAttribute("href") + '"');
        }

        SaveToCSV saver = new SaveToCSV(FILE_NAME);
        saver.saveData(linkList);



        //      WYSYŁANIE MAILA Z ZAŁĄCZNIKIEM

        chromeDriver.get(SECOND_URL);

        WebElement mailInput = chromeDriver.findElement(By.cssSelector(".Xb9hP input"));
        mailInput.sendKeys(MY_MAIL, Keys.ENTER);

        WebElement passwordInput = chromeDriver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"));
        passwordInput.sendKeys(MY_PASSWORD, Keys.ENTER);

        // click to create mail
        chromeDriver.findElement(By.cssSelector(".aic .z0 div")).click();
        // change address
        chromeDriver.findElement(By.cssSelector("tr.bzf td:nth-of-type(2) div div textarea")).sendKeys(DESTINATION_MAIL);
        // change subject
        chromeDriver.findElement(By.cssSelector(".aoT")).sendKeys(MAIL_TITLE);
        // open attach file window
        chromeDriver.findElement(By.cssSelector("tr.btC td:nth-of-type(4) input")).sendKeys(FILE_PATH);
        // click SEND button
        chromeDriver.findElement(By.cssSelector("tr.btC td div div:nth-of-type(2) div")).click();



        //              SKRYPCIK

//        chromeDriver.findElement((By.xpath("//*[@id=\":9q\"]"))).click();
//
//        // enter file name
//        try {
//            Runtime.getRuntime().exec("C:\\Users\\mikolaj.kasperek\\IdeaProjects\\selenium-try-2\\FileUploadScript\\FileUpliadScript.exe");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    } // seleniumTest



} // class





























