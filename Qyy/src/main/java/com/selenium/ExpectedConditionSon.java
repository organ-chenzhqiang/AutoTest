package com.selenium;

import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;


public interface ExpectedConditionSon<T> extends Function<WebDriver, T> {
    void setIndex(int _i);
}
