package com.selenium;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.selenium.ClearImageHelper.cleanImage;
import static com.selenium.ClearImageHelper.gray;


@Listeners(TestNGListenerScreen.class)
@Test
public class seleniumTestDemo {
    public WebDriver driver;
    public String windowsHandle;
    public String Corporate_name;

    /**
     * 封装driver.findElement
     */
    public WebElement element(By by) {
        WebElement element = driver.findElement(by);
        return element;
    }

    /**
     * 获取网页url
     */
    public String geturl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    /*
    * 截图
    * */
    @Test
    public void takeScreenShot() {
//        long date = System.currentTimeMillis();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String path = format.format(new Date());
        String curPath = System.getProperty("user.dir");
        path = path + ".png";
        String screenPath = curPath + "/" + path;
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screen, new File(screenPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 鼠标事件
     */
    @Test
    public void mouseAction() {
        WebElement web = this.element(By.className("classifyTitlejxs"));
        Actions actions = new Actions(driver);
        actions.moveToElement(web).perform();
    }

    /**
     * cookie登录
     */
    @Test
    @Parameters({"name", "value"})
    public void cookie(String value) {
        driver.manage().addCookie(new Cookie("4202f448-3892-4411-a4bc-fd96819da7cc", value, "/", null));
        driver.navigate().refresh();
    }

    /**
     * 封装自己的等待
     */
    @Test
    public void wait(String s, String v) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                if (s.equals(v)) {
                    return false;
                } else {
                    return true;
                }
            }
        });
    }

    /*
    * 驱动谷歌
    * @param suite
    * */
    @Test
    @Parameters("tsetUrl")
    public void InitDriver(String tsetUrl) {
        System.setProperty("webdriver.chrome.driver", "E:\\webdrive\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(tsetUrl);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /*
    *跳转网址
    * */
    @Test
    @Parameters("Url")
    public void Webjump(String Url) {
        driver.get(Url);
    }

    /*
    * 登录弹窗列表
    * */
   /* @Test
    @Parameters({"mobilePhone", "password"})
    public void userLogin(String mobilePhone, String password) throws InterruptedException {
        driver.findElement(By.linkText("请登录")).click();
        List<WebElement> userLogin = driver.findElement(By.className("reg01")).findElements(By.tagName("input"));
        userLogin.get(0).sendKeys(mobilePhone);
        userLogin.get(1).sendKeys(password);
        Thread.sleep(500);
        driver.findElement(By.linkText("立即登录")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("hot-left")));
        List<WebElement> login = driver.findElement(By.className("top_right")).findElements(By.tagName("li"));
        String mobilePhonetest = login.get(0).findElement(By.className("ng-binding")).getText();
        try {
            Assert.assertEquals(mobilePhonetest,mobilePhone,"登陆成功");
        }catch (Exception e){
            System.out.println("登录失败");
        }
        Thread.sleep(1000);
    }*/

    @Test
    @Parameters({"mobilePhone", "password"})
    public void userLogin(String mobilePhone, String password) {
        this.element(By.linkText("请登录")).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.className("re_login_form")));
        this.element(By.id("logion_name")).sendKeys(mobilePhone);
        this.element(By.id("login_pwd")).sendKeys(password);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form_submit")));
        this.element(By.className("form_submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hot-left")));
        List<WebElement> login = this.element(By.className("top_right")).findElements(By.tagName("li"));
        String mobilePhonetest = login.get(0).findElement(By.className("ng-binding")).getText();
        if (mobilePhone.equals(mobilePhonetest)) {
            System.out.println(mobilePhone + " " + "登陆成功");
        } else {
            Assert.assertEquals(mobilePhonetest, mobilePhone, "登录失败");
        }
    }

    /*
    * 登录弹窗列表
    * */
    @Test
    @Parameters({"mobilePhone", "password"})
    public void Login(String mobilePhone, String password) {
        this.element(By.id("phone")).sendKeys(mobilePhone);
        this.element(By.id("pwd")).sendKeys(password);
        this.element(By.className("zr_submit")).click();
    }

    /*
    * 经销商列表加入购物车
    * */
    @Test
    public void Dealership() {
        this.element(By.linkText("经销馆")).click();
        this.element(By.linkText("快捷购物列表")).click();
        List<WebElement> shortcuts_listMain = this.element(By.className("shortcuts_listMain")).findElements(By.className("shortcuts_list"));
        shortcuts_listMain.get(0).findElement(By.className("w160")).click();
        this.element(By.linkText("去购物车结算")).click();
    }

    /**
     * 搜索有图片商品
     */
    @Test
    @Parameters("commodityname")
    public void search(String commodityname) {
        this.element(By.className("ng-untouched")).sendKeys(commodityname);
        this.element(By.tagName("button")).click();
    }

    /**
     * 搜索页加入购物车
     */
    @Test
    public void searchPurchase() {
        System.out.println("搜索頁商品");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product_addCart")));
        String commodityname = this.element(By.className("product_showlist_title")).getText();
        System.out.println(commodityname);
        this.element(By.className("product_addCart")).click();
    }

    /*
    * 经销商全选加入购物车
    * */
    @Test
    public void selectAddtocart() {
        this.element(By.linkText("经销馆")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("快捷购物列表")));
        this.element(By.linkText("快捷购物列表")).click();
        if (!this.element(By.id("allSelect")).isSelected()) {
            this.element(By.id("allSelect")).click();
        }
        wait.until(ExpectedConditions.elementSelectionStateToBe(By.id("allSelect"), true));
        this.element(By.className("selectAddCar")).click();
        this.element(By.linkText("去购物车结算")).click();
    }

    /**
     * 获取页码
     */
    public int Page_number() {
        int Page_number = Integer.valueOf(this.element(By.className("selected")).getText());
        return Page_number;
    }

    /**
     * 经销馆点击下一页
     */
    public void next_page() throws InterruptedException {
        int Page_numbers = Integer.valueOf(this.element(By.id("txtMaxPageNumber")).getAttribute("value"));
        for (; this.Page_number() < Page_numbers; ) {
            Thread.sleep(500);
            System.out.println(this.Page_number());
            this.element(By.className("next")).click();
            this.Page_number();
        }
    }

    /*
    * 首页商品进入产品详情页
    * */
    @Test
    @Parameters("discount")
    public void purchase() {
        List<WebElement> activity_home = this.element(By.id("hot-left")).findElements(By.tagName("li"));
        String name = activity_home.get(3).getText();
        System.out.println("首页商品：" + "\n" + name);
/*        List<WebElement> recommend_price = activity_home.get(1).findElement(By.className("priceBox")).findElements(By.tagName("div"));
        String Discountprice = recommend_price.get(0).findElement(By.className("price-money")).getText();
        System.out.println("经销商价：" + Discountprice);
        String Originalprice = recommend_price.get(4).findElement(By.className("price-money")).getText();
        System.out.println("原价：" + Originalprice);*/
        activity_home.get(3).click();
    }

    /*
    * 产品详情页加入购物车
    * */
    @Test
    public void Productpage() {
        String name = this.element(By.className("right_box")).findElement(By.className("ng-binding")).getText();
        System.out.println("产品详情页商品：" + name);
        String Discountprice = this.element(By.className("guigejiage1")).getText();
        System.out.println("经销商价：" + Discountprice);
        String Originalprice = this.element(By.className("guigejiage2")).getText();
        System.out.println("原价：" + Originalprice);
        String number = this.element(By.id("orderNumber")).getAttribute("value");
        System.out.println("数量：" + number);
        this.element(By.linkText("加入购物车")).click();
    }

    /*
    * 去购物车结算页
    * */
    @Test
    public void goSettlement() {
        String name = this.element(By.className("caringlist_d1")).getText();
        System.out.println("成功加入购物车页商品：" + name);
        String number = this.element(By.className("caringlist_d2")).getText();
        System.out.println(number);
        this.element(By.partialLinkText("去购物车结算")).click();
    }

    /*
    * 购物车页面
    * */
    @Test
    public void ShoppingCart() {
        List<WebElement> commodity = this.element(By.tagName("tbody")).findElements(By.tagName("tr"));
        String name = commodity.get(1).findElements(By.tagName("td")).get(1).getText();
        System.out.println("购物车页商品：" + name);
        String Specifications = commodity.get(1).findElements(By.tagName("td")).get(2).getText();
        System.out.println(Specifications);
        String price = commodity.get(1).findElement(By.className("money1")).getText();
        System.out.println("小计：" + price);
        String number = commodity.get(1).findElement(By.className("num")).getAttribute("value");
        System.out.println("数量：" + number);
        String Total = this.element(By.className("qianqian")).getText();
        System.out.println("总计：" + Total);
        try {
            this.element(By.partialLinkText("去结算")).click();
        } catch (Exception e) {
            this.element(By.partialLinkText("去结算")).click();
        }
    }

    /*
    * 提交订单
    * */
    @Test
    public void placeorder() {
        List<WebElement> right1 = this.element(By.className("right1")).findElements(By.className("jieShao"));
        String name = right1.get(0).findElement(By.className("miaoS")).getText();
        System.out.println("订单页商品：" + name);
        String address = this.element(By.className("address_check")).findElement(By.className("addr")).getText();
        System.out.println("收货地址：" + address);
        String Discountprice = right1.get(0).findElement(By.className("productjiage")).getText();
        System.out.println("经销商价：" + Discountprice);
        String Originalprice = right1.get(0).findElement(By.className("originalPrice")).getText();
        System.out.println("原价：" + Originalprice);
        String number = right1.get(0).findElement(By.className("productunm")).getText();
        System.out.println("数量：" + number);
        this.element(By.className("diJiao")).click();//提交订单
    }

    /*
    *授信
    * */
    @Test
    public void monthlyCalculation() {
        this.element(By.className("transferAccounts")).click();
    }

    /*
    * 授信继续购物
    * */
    @Test
    public void Monthlycontinueshopping() {
        this.element(By.className("buy03icon")).click();
        String orderNostate = this.element(By.className("buy03_top")).getText();
        System.out.println(orderNostate);
        List<WebElement> Continueshoppinglist = this.element(By.className("monthlysauto")).findElements(By.tagName("a"));
        Continueshoppinglist.get(0).click();
    }

    /*
    * 授信去议价
    * */
    @Test
    public void Monthlygobargaining() {
        this.element(By.className("buy03icon")).click();
        String orderNostate = this.element(By.className("buy03_top")).getText();
        System.out.println(orderNostate);
        List<WebElement> Continueshoppinglist = this.element(By.className("monthlysauto")).findElements(By.className("btn_newurlb"));
        Continueshoppinglist.get(0).click();
    }

    /*
    * 授信进入付款页
    * */
    @Test
    public void gotopaymentpage() {
        this.element(By.className("buy03icon")).click();
        String orderNostate = this.element(By.className("buy03_top")).getText();
        System.out.println(orderNostate);
        List<WebElement> Continueshoppinglist = this.element(By.className("monthlysauto")).findElements(By.className("btn_newurl"));
        Continueshoppinglist.get(0).click();
    }

    /*
    * 通联支付
    * */
    @Test
    public void tlpay() {
        Set<String> handles = driver.getWindowHandles();
        for (String s : handles) {
            if (s.equals(windowsHandle)) {
                continue;
            }
            System.out.println(s);
            driver.switchTo().window(s);
        }
        this.element(By.id("selectBankForm_vbank_00")).click();
        this.element(By.id("payMessageButton_2_2")).click();
        Set<String> handles1 = driver.getWindowHandles();
        for (String s : handles1) {
            System.out.println(s);
            if (s.equals(windowsHandle)) {
                continue;
            }
            driver.switchTo().window(s);
        }
        this.element(By.xpath(
                "/html/body/div/table[1]/tbody/tr[4]/td/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td[1]/form/table/tbody/tr[8]/td[2]/a[1]"))
                .click();
    }

    /*
    * 选择支付方式
    * */
    @Test
    public void payment() throws InterruptedException {
        windowsHandle = driver.getWindowHandle();
        System.out.println("选择支付方式页：");
        String orderNo = this.element(By.id("odernozd")).getAttribute("value");
        System.out.println("订单号：" + orderNo);
        String balance = this.element(By.className("buy03_aa")).getText();
        System.out.println(balance);
        String Orderamount = this.element(By.className("buy03_ab")).getText();
        System.out.println(Orderamount);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.className("buy03_conter")));
        List<WebElement> buy03_conter = this.element(By.className("buy03_conter")).findElements(By.tagName("li"));
        String class0 = buy03_conter.get(0).getAttribute("class");
        if (class0.equals("buy03tab_b active")) {
            this.element(By.partialLinkText("立即支付")).click();//余额支付
        } else {
            this.element(By.partialLinkText("立即支付")).click();//线上支付
            Set<String> handles = driver.getWindowHandles();
            for (String s : handles) {
                if (s.equals(windowsHandle)) {
                    continue;
                }
                driver.switchTo().window(s);
/*                if (this.element(By.id("payMessageButton_2_2")).isDisplayed()) {
                    driver.switchTo().window(windowsHandle);
                }*/
                this.element(By.id("selectBankForm_vbank_00")).click();
                this.element(By.id("payMessageButton_2_2")).click();
                Set<String> handles1 = driver.getWindowHandles();
                for (String s1 : handles1) {
                    if (s1.equals(windowsHandle) || s1.equals(s)) {
                        continue;
                    } else {
                        System.out.println(s1);
                        driver.switchTo().window(s1);
                    }
                    this.element(By.xpath(
                            "/html/body/div/table[1]/tbody/tr[4]/td/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td[1]/form/table/tbody/tr[8]/td[2]/a[1]"))
                            .click();
                }
            }
        }
    }

    /*
    * 暂不支付去议价
    * */
    @Test
    public void nonpayment() throws InterruptedException {
        System.out.println("选择支付方式页：");
        String orderNo = this.element(By.className("buy03listo")).getText();
        System.out.println(orderNo);
        this.element(By.partialLinkText("暂时不支付去议价")).click();
    }

    /*
    * 拆单继续购物
    * */
    @Test
    public void splitorder() {
        this.element(By.className("buy03_top")).getText();
        this.element(By.partialLinkText("继续购物 ")).click();
    }

    /*
    * 拆单到订单中心去支付
    * */
    @Test
    public void splitorder1() {
        this.element(By.className("buy03_top")).getText();
        this.element(By.className("btn_newurlb")).click();
    }

    /**
     * 个人中心首页邀请码
     */

    public String Invitation_code() {
        String Invitation_code = driver.findElements(By.id("user_font")).get(0).getAttribute("value");
        return Invitation_code;
    }

    /**
     * 个人中心首页邀请链接
     */
    public String Invitation_link() {
        String Invitation_link = this.element(By.id("textInput2")).getAttribute("value");
        return Invitation_link;
    }

    /**
     * 确认收货或提交议价
     */
    String ordernoNum;

    @Test
    @Parameters("bargaining")
    public void Orderlist(String bargaining) {
        System.out.println("我的订单页面：");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rightBar")));
        List<WebElement> Orderlist = driver.findElements(By.className("order-table"));
        String orderNo = Orderlist.get(0).findElement(By.tagName("caption")).getText();
        ordernoNum = Orderlist.get(0).findElements(By.className("date")).get(1).getText();
        System.out.println(orderNo);
        String name = Orderlist.get(0).findElement(By.className("pro-des")).getText();//商品名称
        System.out.println("商品名称：" + name);
        String number = Orderlist.get(0).findElement(By.className("t-quantity")).getText();//商品数量
        System.out.println("商品数量：" + number);
        String user = Orderlist.get(0).findElements(By.className("td-first")).get(0).getText();//收货人
        System.out.println("收货人：" + user);
        String Order_amount = Orderlist.get(0).findElements(By.className("td-first")).get(1).getText();//订单金额
        System.out.println("订单金额：" + Order_amount);
        String Order_status = Orderlist.get(0).findElements(By.className("td-last")).get(0).getText();//订单状态
        System.out.println("订单状态：" + Order_status);
        String ng_if = Orderlist.get(0).findElements(By.className("td-last")).get(0).getAttribute("ng-if");
        if (ng_if.equals("x.ostatus==2||x.ostatus==8")) {
            String bargainStatus = Orderlist.get(0).findElements(By.className("td-first")).get(1).findElement(By.tagName("span")).getAttribute("ng-if");
            if (bargainStatus.equals("x.order_pay_status==1")) {//授信未结清
                Orderlist.get(0).findElement(By.linkText("去支付")).click();
            } else {
                Orderlist.get(0).findElement(By.linkText("确认收货")).click();//确认收货
            }
        } else if (ng_if.equals("x.ostatus==1||x.ostatus==3")) {
            String bargainStatus = Orderlist.get(0).findElements(By.className("td-last")).get(1).findElement(By.tagName("div")).getAttribute("ng-if");
            if (bargainStatus.equals("x.bargainStatus==1&&(x.ostatus==1||x.ostatus==3)")) {//未提交议价
                Orderlist.get(0).findElement(By.linkText("去议价")).click();//去议价
                this.element(By.className("bargaining")).sendKeys(bargaining);
                this.element(By.id("functionnext")).click();
                this.element(By.id("zr_bargaining")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rightBar")));
                driver.navigate().refresh();
            } else if (bargainStatus.equals("x.bargainStatus==4")) {//已提交议价上级已回复
                Orderlist.get(0).findElement(By.linkText("去支付")).click();
            } else if (bargainStatus.equals("x.bargainStatus==2&&x.ostatus!=4")) {//已提交议价上级未回复
                Orderlist.get(0).findElement(By.linkText("取消议价")).click();
                Orderlist.get(0).findElement(By.linkText("去支付")).click();
            } else if (bargainStatus.equals("(x.bargainStatus==6)||(x.bargainStatus==1&&(x.ostatus==2||x.ostatus==8))")) {
                Orderlist.get(0).findElement(By.linkText("去支付")).click();
            }
        } else if (ng_if.equals("x.ostatus==7")) {
            Orderlist.get(0).findElement(By.linkText("再次购买")).click();
        }
    }

    /*
    * 上级确认议价
    * */
    @Test
    public void userbargaining() {
        System.out.println("客户议价页面：");
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.className("rightBar")));
        List<WebElement> Orderlist = this.element(By.className("rightBar")).findElements(By.tagName("table"));
        String orderNo = Orderlist.get(0).findElement(By.tagName("caption")).findElements(By.tagName("span")).get(1).getText();
        System.out.println(orderNo);
        String number = Orderlist.get(0).findElement(By.className("t-quantity")).getText();
        System.out.println("数量：" + number);
        List<WebElement> td_first = Orderlist.get(0).findElements(By.tagName("td"));
        String user = td_first.get(5).getText();
        System.out.println("收货人：" + user);
        String Total = td_first.get(4).findElements(By.className("text-h")).get(0).getText();
        System.out.println(Total);
        String Orderstatus = Orderlist.get(0).findElement(By.className("td-last")).getText();
        System.out.println("订单状态：" + Orderstatus);
        td_first.get(8).findElement(By.className("text-link")).click();
        List<WebElement> box_center = this.element(By.className("box_center")).findElements(By.tagName("div"));
        String Totaltest = box_center.get(1).getText();
        System.out.println(Totaltest);
        String copewith = box_center.get(2).getText();
        System.out.println(copewith);
        String bargaining = this.element(By.className("yijiafanwei_tishi")).getText();
        System.out.println(bargaining);
        String bargaining_number = this.element(By.className("zuidijiage")).getAttribute("value");
        this.element(By.className("bargaining")).sendKeys(bargaining_number);
        this.element(By.id("functionnext")).click();
        this.element(By.id("bargaining")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rightBar")));
    }

    /*
    *订单页去支付
    * */
    @Test
    public void gotopay() {
        List<WebElement> Orderlist = this.element(By.className("rightBar")).findElements(By.tagName("table"));
        String orderNo = Orderlist.get(0).findElement(By.tagName("caption")).findElements(By.tagName("span")).get(1).getText();
        System.out.println(orderNo);
        String number = Orderlist.get(0).findElement(By.className("t-quantity")).getText();
        System.out.println("数量：" + number);
        List<WebElement> td_first = Orderlist.get(0).findElements(By.tagName("td"));
        String user = td_first.get(3).getText();
        System.out.println("收货人：" + user);
        String Total = td_first.get(4).findElements(By.className("text-h")).get(0).getText();
        System.out.println(Total);
        String copewith = td_first.get(4).findElements(By.className("text-h")).get(1).getText();
        System.out.println(copewith);
        String Orderstatus = Orderlist.get(0).findElement(By.className("td-last")).getText();
        System.out.println("订单状态：" + Orderstatus);
        String bargainStatus = td_first.get(6).findElement(By.tagName("div")).getAttribute("ng-if");
        if (bargainStatus.equals("x.bargainStatus==5")) {
            String nobargaining = td_first.get(6).findElement(By.tagName("div")).getText();
            System.out.println("议价状态：" + nobargaining);
        } else {
            List<WebElement> p = td_first.get(6).findElements(By.tagName("p"));
            String Waitforareply = p.get(p.size() - 1).getText();
            System.out.println("议价状态：" + Waitforareply);
        }
        this.element(By.partialLinkText("去支付")).click();
    }

    /*
    * 支付成功页面
    * */
    @Test
    public void Paymentsuccess() throws InterruptedException {
        System.out.println("支付成功页面");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String Paymentsuccess = null;
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("reminder_tsd"), "订单金额"));
            Paymentsuccess = this.element(By.className("reminder_tsd")).getText();
        } catch (Exception e) {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("transfercgulli"), "订单金额"));
            Paymentsuccess = this.element(By.className("transfercgulli")).getText();
        }
//        Thread.sleep(6000);
        System.out.println(Paymentsuccess);
//        WebDriverWait wait = new WebDriverWait(driver, 10);
        Thread.sleep(1000);
        this.element(By.partialLinkText("查看订单列表")).click();
    }

    /*
    * 我的分成
    * */
    @Test
    public void Dividedinto() {
        this.element(By.linkText("我的分成")).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.className("or_List")));
        List<WebElement> ng_scope = this.element(By.className("or_List")).findElements(By.className("ng-scope"));
        ng_scope.get(ng_scope.size() - 1).findElement(By.className("specialfont")).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.className("rightBar")));
        List<WebElement> rightBar = this.element(By.className("rightBar")).findElements(By.tagName("table"));
        System.out.println(rightBar.get(1).getText());
    }

    /*
    *余额充值
    * */
    @Test
    public void Accountrecharge() {
        this.element(By.linkText("账号充值")).click();
        windowsHandle = driver.getWindowHandle();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.className("ng-pristine")));
        List<WebElement> ng_pristine = driver.findElements(By.className("ng-pristine"));
        ng_pristine.get(0).sendKeys("100");
        this.element(By.id("buy03_czid")).click();
        Set<String> handles = driver.getWindowHandles();
        for (String s : handles) {
            if (s.equals(windowsHandle)) {
                continue;
            }
            driver.switchTo().window(s);
        }
        if (this.element(By.id("payMessageButton_2_2")).isDisplayed()) {
            driver.switchTo().window(windowsHandle);
        }
        this.element(By.className("buy03tab_c")).click();//线下充值
        this.element(By.linkText("确认并获取汇款识别码")).click();
        this.element(By.id("prompt_box_tishi")).click();
    }

    /*
    * 新增收货地址
    * */
    @Test
    public void newaddress() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("收货地址")));
        this.element(By.linkText("收货地址")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("add")));
        try {
            while (driver.findElement(By.className("delete")).isDisplayed()) {
                List<WebElement> deletelist = driver.findElements(By.className("delete"));
                deletelist.get(0).click();
                this.element(By.id("functionnext")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("add")));
            }
        } catch (Exception e) {
            this.element(By.className("add")).click();
            class address {
                public address(String[] Receiving_address, String[] address) {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("defaultF09")));
                    List<WebElement> inputlist = driver.findElements(By.tagName("input"));
                    for (int i = 1; i < inputlist.size() - 1; i++) {
                        if (i == 2) {
                            inputlist.get(2).click();
                            for (int j = 0; j < address.length; j++) {
                                element(By.partialLinkText(address[j])).click();
                            }
                        } else {
                            inputlist.get(i).clear();
                            inputlist.get(i).sendKeys(Receiving_address[i - 1]);
                        }
                    }
                    while (!inputlist.get(7).findElement(By.id("defaultF09")).isSelected()) {
                        inputlist.get(7).findElement(By.id("defaultF09")).click();
                    }
                    driver.findElements(By.className("active")).get(1).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("container")));
                    String addresstest = element(By.className("panel")).getText();
                    System.out.println(addresstest.replace("编辑", "").replace("x", ""));
                }
            }
            String[] Receiving_address_0 = {"无敌", "", "就是这么无敌", "13397210001", "075523508532", "961573383@qq.com"};
            String[] address_0 = {"北京市", "市辖区", "东城区", "东华门街道",};
            new address(Receiving_address_0, address_0);
            this.element(By.className("edit")).click();
            String[] Receiving_address_1 = {"测试", "", "测试请勿发货", "13397210001", "075523508532", "961573383@qq.com"};
            String[] address_1 = {"天津市", "市辖区", "和平区", "南市街道",};
            new address(Receiving_address_1, address_1);
        }
    }

    /*
    *企业认证
    * */
    @Test
    @Parameters({"tsetUrl", "mobilePhone"})
    public void Enterprisecertification(String mobilePhone) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("企业认证")));
        this.element(By.linkText("企业认证")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_changebg")));
        this.element(By.id("user_changebg")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("certification")));
        List<WebElement> lilist = driver.findElements(By.className("input_key"));
        String[] a = {"无敌" + mobilePhone, "我的无敌你不懂", "测试请勿发货", "", mobilePhone, "621740004102842", "12345678901234567"};
        for (int i = 0; i < lilist.size(); i++) {
            if (i == 2) {
                lilist.get(2).click();
                this.element(By.linkText("天津市")).click();
                this.element(By.linkText("市辖区")).click();
                this.element(By.linkText("和平区")).click();
                this.element(By.linkText("南市街道")).click();
            } else {
                lilist.get(i).clear();
                lilist.get(i).sendKeys(a[i]);
            }
        }
        List<WebElement> list_button = driver.findElements(By.className("btn"));
        String btn_btn_active = list_button.get(1).getAttribute("class");
        if (!btn_btn_active.equals("btn btn_active")) {
            list_button.get(1).click();
        }
        String img = "C:\\Users\\user\\Desktop\\V1.4\\新建文件夹\\1521774774746.jpg";//图片地址
        if (this.element(By.className("mycenter_close")).isDisplayed()) {
            this.element(By.className("mycenter_close")).click();
            this.element(By.id("p4")).sendKeys(img);
        } else {
            this.element(By.id("p4")).sendKeys(img);
        }
        this.element(By.id("user_changebg")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rzTable")));
            System.out.println(this.element(By.id("rzTable")).getText());
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("success")));
            System.out.println(this.element(By.className("success")).getText());
        }
    }

    /**
     * 获取企业名称
     */

    public String Corporate_name() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("企业认证")));
        this.element(By.linkText("企业认证")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_changebg")));
        List<WebElement> list = driver.findElements(By.className("td2"));
        String Corporate_name = list.get(0).getText();
        return Corporate_name;
    }

    /*
    *从新认证
    * */
    @Test
    public void newEnterprisecertification() {
        this.element(By.linkText("企业认证")).click();
    }

    /*
    * 浏览器退出
    * */
    @Test
    public void quit() {
        driver.quit();
    }

    public String captureElement(WebElement element, int x, int y) throws Exception {
        WrapsDriver wrapsDriver = (WrapsDriver) element;
        // 截图整个页面
        File screen = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);
        // 获得元素的高度和宽度
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        // 创建一个矩形使用上面的高度，和宽度
        Rectangle rect = new Rectangle(width, height);
        // 得到元素的坐标
        Point p = element.getLocation();
        BufferedImage dest = img.getSubimage(p.getX() + x, p.getY() + y, rect.width, rect.height);
        //存为jpg格式
        ImageIO.write(dest, "jpg", screen);
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String path = format.format(new Date());
        String curPath = System.getProperty("user.dir");
        path = path + ".jpg";
        String screenPath = curPath + "/" + path;
        try {
            FileUtils.copyFile(screen, new File(screenPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        File imageFile = new File(screenPath);
        String destDir = "D:\\tmp\\tmp";
        String img_address = destDir + "\\" + path;
        cleanImage(imageFile, destDir);
        gray(img_address, img_address);
        imageFile = new File(img_address);
        Tesseract tessreact = new Tesseract();
        tessreact.setDatapath("D:\\Tesseract-OCR");
        String result = null;
        try {
            result = tessreact.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        return result.replace(" ", "");
    }

    /**
     * 忘记密码
     */
    @Test
    @Parameters("mobilePhone")
    public void findpassword(String mobilePhone) throws Exception {
        mysql qyy_sms_send_record = new mysql();
        String param = "param";
        String sql = "select param from qyy_sms_send_record WHERE phone_no=" + mobilePhone + " order by send_time DESC limit 1";
        String qyy_sms_send_record_param_0 = sql;
        List<String> list_0 = qyy_sms_send_record.getSelect(qyy_sms_send_record_param_0, param);
        this.element(By.id("tel")).sendKeys(mobilePhone);
        int x = 0, y = 0;
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(new ExpectedCondition<Object>() {
            public Boolean apply(WebDriver driver) {
                String src = driver.findElement(By.tagName("img")).getAttribute("src");
                if (src == null) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        WebElement element = this.element(By.tagName("img"));
        this.element(By.className("yanzhengma")).sendKeys(captureElement(element, x, y));
        try {
            this.element(By.className("next_step")).click();
        } catch (Exception e) {
            this.element(By.className("yanzhengma")).clear();
            this.element(By.className("yanzhengma")).sendKeys(captureElement(element, x, y).replace("O", "0"));
        }
        this.element(By.className("rehuoqu")).click();
        String qyy_sms_send_record_param = sql;
        List<String> list_1 = qyy_sms_send_record.getSelect(qyy_sms_send_record_param, param);
        while (list_1.isEmpty() || list_1.get(0).equals(list_0.get(0))) {
            list_1 = qyy_sms_send_record.getSelect(qyy_sms_send_record_param, param);
        }
        this.element(By.className("phone_yanzheng_sec")).sendKeys(list_1.get(0));
        this.element(By.className("next_step")).click();
        this.element(By.id("pwd")).sendKeys("123456.");
        this.element(By.id("repwd")).sendKeys("123456.");
        this.element(By.className("next_step")).click();
        this.element(By.className("remember_password")).findElement(By.tagName("a")).click();
    }

    /**
     * 免费注册
     */
    @Test
    @Parameters({"userName", "Invitation_code"})
    public String registration(String userName, String Invitation_code) throws InterruptedException {
        mysql qyy_sms_send_record = new mysql();
        String param = "param";
        String qyy_sms_send_record_param = "select param from qyy_sms_send_record WHERE phone_no=" + userName + " order by send_time DESC limit 1";
        List<String> list_0 = qyy_sms_send_record.getSelect(qyy_sms_send_record_param, param);
        this.element(By.id("userName")).sendKeys(userName);
        this.element(By.id("password")).sendKeys("123456.");
        this.element(By.id("confirmPassword")).sendKeys("123456.");
        this.element(By.id("sendSmsCode")).click();
        List<String> list_1 = qyy_sms_send_record.getSelect(qyy_sms_send_record_param, param);
        if (list_1.isEmpty()) {
            while (list_1.isEmpty()) {
                list_1 = qyy_sms_send_record.getSelect(qyy_sms_send_record_param, param);
            }
        } else if (list_0.get(0).equals(list_1.get(0))) {
            while (list_0.get(0).equals(list_1.get(0))) {
                list_1 = qyy_sms_send_record.getSelect(qyy_sms_send_record_param, param);
            }
        }
        this.element(By.id("smsCode")).sendKeys(list_1.get(0));
        this.element(By.id("smsCode")).click();
        this.element(By.id("referralCode")).sendKeys(Invitation_code);
        this.element(By.id("referralCode")).click();
        this.element(By.id("agreement")).click();
        this.element(By.className("form_submit")).click();
        return list_1.get(0);
    }

    /**
     * 秋叶原后台添加商品
     */
    @Test
    public void Add_product(String Commodity_category[], String attributeValue[], String img, String channel_name
            , String Price, String stock
    ) {
        this.element(By.className("curr")).click();
        this.element(By.className("btn3")).click();
        String[] name = {"name", "name2", "proModel"}; //商品名称,商品副名称,产品型号
        for (int i = 0; i < name.length; i++) {
            this.element(By.name(name[i])).sendKeys(attributeValue);
        }
        String[] id = {"fl_1_xz", "fl_2_xz", "fl_3_xz"};
        for (int i = 0; i < id.length; i++) {
            WebElement colspan_1 = driver.findElement(By.id(id[i]));
            List<WebElement> option = colspan_1.findElements(By.tagName("option"));
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < option.size(); j++) {
                String Value = option.get(j).getAttribute("Value");
                String name_0 = option.get(j).getText();
                map.put(name_0, Value);
            }
            Select fl_1_xz = new Select(colspan_1);
            fl_1_xz.selectByValue(map.get(Commodity_category[i]));
        }
        this.element(By.id("upload1")).sendKeys(img);
       /* List<WebElement> purposeSeiver = driver.findElement(By.id("purposeSeiver")).findElements(By.className("purpose"));
        for (int i = 0; i < purposeSeiver.size(); i++) {
            Select attributeValue_0 = new Select(purposeSeiver.get(i).findElement(By.tagName("select")));
            attributeValue_0.selectByValue(attributeValue[i]);
        }
        List<WebElement> purposeBasic = driver.findElement(By.id("purposeBasic")).findElements(By.className("purpose"));
        for (int i = 0; i < purposeBasic.size(); i++) {
            Select purposeBasic_attributeValue = new Select(purposeBasic.get(i).findElement(By.tagName("select")));
            purposeBasic_attributeValue.selectByValue(attributeValue[i]);
        }*/
        int v=0;
        for (int j = 0; j < 12; j++) {
            List<WebElement> purpose = driver.findElements(By.className("purpose"));
            if (v==6){
                v=0;
            }
            purpose.get(j).findElement(By.className("inputText")).sendKeys(attributeValue[v]);
            v++;
        }
        List<WebElement> ms_list = driver.findElements(By.className("ms-list"));
        Map<String, String> channel = new HashMap<>();
        List<WebElement> list_li = ms_list.get(0).findElements(By.tagName("li"));
        for (int i = 0; i < list_li.size(); i++) {
            String channel_name_0 = list_li.get(i).getText();
            String channel_id = list_li.get(i).getAttribute("id");
            channel.put(channel_name_0, channel_id);
        }
        this.element(By.id(channel.get(channel_name))).click();
        this.element(By.name("lsMoney")).sendKeys(Price);
        this.element(By.name("stockpile")).sendKeys(stock);
        this.element(By.className("sumbitForme")).click();
    }

    /**
     * 秋叶原后台系列商品列表
     */
    @Test
    public void series(String serieslist_name) throws InterruptedException {
        List<WebElement> serieslist = this.element(By.className("yhgl_table")).findElements(By.tagName("tr"));
        Map<String, String> series = new HashMap<>();
        for (int i = 1; i < serieslist.size(); i++) {
            String serieslistname = serieslist.get(i).findElements(By.tagName("td")).get(1).getText();
            String serieslistValue = serieslist.get(i).findElements(By.tagName("td")).get(0).getText();
            series.put(serieslistname, serieslistValue);
        }
        serieslist.get(Integer.valueOf(series.get(serieslist_name))).findElement(By.linkText("查看")).click();
        WebDriverWait wait = new WebDriverWait(driver, 8);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("atil")));
        List<WebElement> btn4list = driver.findElements(By.className("btn4"));
        btn4list.get(0).click();
        int Pagenumbers = Integer.valueOf(driver.findElements(By.className("page")).get(1).findElement(By.className("fl")).getText().replace("第1页/共", "").replace("页", ""));
        for (int i = 0; i < Pagenumbers; i++) {
            this.element(By.id("selectAll")).click();
            btn4list = driver.findElements(By.className("btn4"));
            btn4list.get(2).click();
            new Determine().loop();//循环点击
            driver.findElements(By.className("next")).get(1).click();
            int Page = Integer.valueOf(driver.findElement(By.id("goPage")).getAttribute("value"));
            /*ExpectedConditionSon a = new ExpectedConditionSon<Boolean>() {
                int _index = 0;

                public void setIndex(int _i) {
                    this._index = _i;
                }

                public Boolean apply(WebDriver driver) {
                    if (this._index + 2 == Page) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            a.setIndex(i);
            wait.until(a);*/
        }
    }

    /**
     * 秋叶原后台结算
     */
    public void Settlement() {
        this.element(By.id("textfield")).sendKeys(ordernoNum);
        this.element(By.linkText("结算")).click();
    }

    /**
     * 商品详情页点击
     */
    @Test
    public void Details_page_click(String attributeValue[]) throws InterruptedException {
        String url_0 = this.geturl();
        int[] a = new int[5];
        for (int i = 0; i < a.length; i++) {
            int n = (int) (1 + Math.random() * 5 + 0);
            boolean info = true;
            for (int j = 0; j < a.length; j++) {
                if (n == a[j]) {
                    info = false;
                    i--;
                    break;
                }
            }
            if (info) {
                a[i] = n;
            }
            if (!(a[4] == 0)) {
                break;
            }
        }
        for (int i = 0; i < a.length; i++) {
            this.element(By.linkText(attributeValue[a[i]])).click();
        }
        String url_1 = geturl();
        this.wait(url_1, url_0);
        /*WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String url_1 = geturl();
                if (url_0 == url_1) {
                    return false;
                } else {
                    return true;
                }
            }
        });*/
        url_1 = geturl();
        Assert.assertNotEquals(url_0, url_1, "两个值相等");
        /*WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String name_0 =driver.findElement(By.className("right_box")).findElements(By.className("ng-binding")).get(0).getText();
                if (name_0 == "") {
                    return false;
                } else {
                    return true;
                }
            }
        });*/
        String name_0 = driver.findElement(By.className("right_box")).findElements(By.className("ng-binding")).get(0).getText();
        this.wait(name_0, "");
        name_0 = driver.findElement(By.className("right_box")).findElements(By.className("ng-binding")).get(0).getText();
        StringBuilder name_1 = new StringBuilder(attributeValue[0]);
        for (int i = 1; i < attributeValue.length; i++) {
            name_1 = name_1.append(attributeValue[i]);
        }
        this.wait(name_0, name_1.toString());
        Assert.assertEquals(name_0, name_1, "两个值不相等");
    }


    /**
     * 帮助中心
     */
    @Test
    public void Help_center() throws InterruptedException {
        List<WebElement> helpList;
        helpList = driver.findElement(By.id("helpList")).findElements(By.tagName("h3"));
        List<WebElement> h_secList;
        h_secList = driver.findElement(By.id("helpList")).findElements(By.className("h_secList"));
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < helpList.size(); i++) {
            int n = h_secList.get(i).findElements(By.tagName("a")).size();
            List<String> list = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                list.add(h_secList.get(i).findElements(By.tagName("a")).get(j).getText());
            }
            map.put(helpList.get(i).getText(), list);
        }
        System.out.println(map);
    }

    /**
     * 后台帮助中心
     */
    @Test
    public void Backstage_Help_center(String classificationName, String image, String sort) {
        this.element(By.linkText("分类列表")).click();
        this.element(By.className("ico3")).click();
        this.element(By.name("classificationName")).sendKeys("classificationName");
        this.element(By.name("image")).sendKeys("image");
        this.element(By.name("sort")).sendKeys("sort");
        this.element(By.className("sumbitForme")).click();
        this.element(By.linkText("分类列表")).click();
        this.element(By.className("ico3")).click();
        this.element(By.name("title")).sendKeys("title");
    }

    /**
     * Add properties
     * */
    public void Addproperties(String name){
        this.element(By.linkText("添加")).click();
        this.element(By.name("attributeName")).sendKeys(name);
        driver.findElements(By.name("editMode")).get(1).click();
        driver.findElements(By.className("btn4")).get(1).click();
    }

    /**
     * 后台修改商品
     **/
    @Test
    public void modifyProduct(String type, String threeClassification) {
        driver.navigate().refresh();
        this.element(By.name("model")).sendKeys(type);
        this.element(By.className("btn2")).click();
        this.element(By.linkText("修改")).click();
        driver.navigate().refresh();
        List<WebElement> purpose = driver.findElements(By.className("purpose"));
        Map<String, String> map = new HashMap<>();
        map.put("产地", "");
        map.put("单个净重(Kg)", "");
        map.put("整件毛重(Kg)", "");
        map.put("整件数量(PCS)", "");
        map.put("产品类别", "");
        map.put("产品型号", "");
        map.put("产品名称", "");
        map.put("品牌", "");
        map.put("规格", "");
        map.put("颜色", "");
        for (int j = 0; j < purpose.size(); j++) {
            String name = purpose.get(j).findElement(By.className("attributeName")).getText();
            String value = purpose.get(j).findElement(By.className("inputText")).getAttribute("value");
            map.put(name, value);
        }
        int a = purpose.size();
        driver.navigate().back();
        this.element(By.linkText("修改")).click();
        driver.navigate().refresh();
        WebElement colspan_1 = driver.findElement(By.id("fl_3_xz"));
        List<WebElement> option = colspan_1.findElements(By.tagName("option"));
        Map<String, String> optionmap = new HashMap<>();
        for (int j = 0; j < option.size(); j++) {
            String name = option.get(j).getText();
            String Value = option.get(j).getAttribute("Value");
            optionmap.put(name, Value);
        }
        Select fl_1_xz = new Select(colspan_1);
        fl_1_xz.selectByIndex(0);
        fl_1_xz.selectByValue(optionmap.get(threeClassification));
        for (int j = 0; j < 12; j++) {
            purpose = driver.findElements(By.className("purpose"));
            String name = purpose.get(j).findElement(By.className("attributeName")).getText();
            purpose.get(j).findElement(By.className("inputText")).sendKeys(map.get(name));
        }
        this.element(By.className("sumbitForme")).click();
        driver.navigate().back();
        driver.navigate().refresh();
        List<WebElement> purpose_1 = driver.findElements(By.className("purpose"));
        Map<String, String> map_1 = new HashMap<>();
        for (int j = 0; j < purpose_1.size(); j++) {
            String name_1 = purpose_1.get(j).findElement(By.className("attributeName")).getText();
            String value_1 = purpose_1.get(j).findElement(By.className("inputText")).getAttribute("value");
            map_1.put(name_1, value_1);
        }
        int b = purpose_1.size();
        Assert.assertEquals(a, b, "两个值不相等");
        for (int i = 0; i < purpose_1.size(); i++) {
            String name = purpose_1.get(i).findElement(By.className("attributeName")).getText();
            Assert.assertEquals(map.get(name), map_1.get(name), "两个值不相等");
        }
    }

    /**
     * 点击js提示框
     * */
    @Test
    class Determine {
        public void loop() {
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
            } catch (Exception e) {
                new Determine().loop();
            }
        }
    }

    /**
     * 三级分类挂系列及属性
     **/
    public void addAttribute(String threeClassification) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        this.element(By.name("name")).sendKeys(threeClassification);
        this.element(By.className("btn2")).click();
        this.element(By.linkText("属性")).click();
        if (driver.findElement(By.id("groupTable")).findElements(By.tagName("tr")).size() > 1) {
            List<WebElement> ckGroupId = driver.findElements(By.name("ckGroupId"));
            for (int i = 0; i < ckGroupId.size(); i++) {
                ckGroupId.get(i).click();
            }
            driver.findElement(By.className("ico4")).click();
            new Determine().loop();
        }
        List<WebElement> addlist = driver.findElements(By.linkText("添加"));
        addlist.get(0).click();
        this.element(By.name("groupName")).sendKeys(threeClassification);
        this.element(By.className("sumbitForme")).click();
        addlist = driver.findElements(By.linkText("添加"));
        addlist.get(addlist.size() - 1).click();
        List<WebElement> checkAttribute = driver.findElements(By.name("purposeBasic"));
        for (int j = 0; j < checkAttribute.size(); j++) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("closeSearchAttibute")));
            addlist = driver.findElements(By.className("popup")).get(0).findElements(By.linkText("添加"));
            checkAttribute.get(j).click();
            if (j >= checkAttribute.size() - 2) {
                List<WebElement> checkSeries = driver.findElements(By.name("purposeSeries"));
                checkSeries.get(j).click();
            }
            addlist.get(j).click();
            new Determine().loop();//循环点击
            new Determine().loop();
        }
        this.element(By.id("closeSearchAttibute")).click();
    }

}





