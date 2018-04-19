package com.selenium;


public class SeleniumTest {
    public static void main(String[] args) throws InterruptedException {
        seleniumTestDemo test=new seleniumTestDemo();
        String tsetUrl= "http://qyy.91qyy.com/";
        test.InitDriver(tsetUrl);
        String mobilePhone2 = "13397210003",password2 = "123456";
        test.userLogin(mobilePhone2,password2);
        test.purchase();
        test.Productpage();
        test.goSettlement();
        test.ShoppingCart();
        test.placeorder();
        test.nonpayment();
        test.Orderlist("4");
        test.Orderlist("4");
        test.quit();
        test.InitDriver(tsetUrl);
        String mobilePhone1 = "13397210000",password = "123456";
        test.userLogin(mobilePhone1,password);
        test.Webjump(tsetUrl+"#/myBargainingOrder");
        test.userbargaining();
        test.quit();
        test.InitDriver(tsetUrl);
        test.userLogin(mobilePhone2,password2);
        test.Webjump(tsetUrl+"#/order");
        test.gotopay();
        test.payment();
        test.Orderlist("4");
        test.quit();
    }
}
