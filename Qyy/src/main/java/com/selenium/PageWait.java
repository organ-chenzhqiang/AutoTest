package com.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import javax.annotation.Nullable;

public class PageWait implements ExpectedCondition {
    public WebDriver driver;

    @Nullable
    @Override
    public Object apply(@Nullable Object o) {
        String src = driver.findElement(By.tagName("img")).getAttribute("src");
        if (src == null) {
            return false;
        } else {
            return true;
        }
    }
}
