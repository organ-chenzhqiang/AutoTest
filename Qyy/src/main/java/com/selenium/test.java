package com.selenium;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.testng.annotations.Test;

import java.io.File;

public class test {

    @Test
    public void test() {
        File imageFile = new File("D:\\Selenium\\20180307171435.jpg");
        Tesseract tessreact = new Tesseract();
        tessreact.setDatapath("D:\\Tesseract-OCR");
        try {
            String result = tessreact.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        //数据库
/*        mysql dao = new mysql();
        List<String> list = dao.getSelect();
        System.out.println(list.get(0));
        double Originalprice = Double.valueOf(list.get(0).toString());
        double Discountprice = Originalprice * 0.45;
        double bargaining = Discountprice - 1;*/

        //前台聚合商品随机点击
       /* seleniumTestDemo test = new seleniumTestDemo();
        String tsetUrl = "http://qyy.91qyy.com";
        String mobilePhone2 = "13397210003", password2 = "123456";
        test.InitDriver(tsetUrl);
        test.userLogin(mobilePhone2, password2);
        test.Webjump(tsetUrl + "#/detail/3702");
        for (int i = 0; i < ArraySortUtil.aaList().size(); i++) {
            test.Details_page_click(ArraySortUtil.aaList().get(i));
        }*/

       /* seleniumTestDemo test = new seleniumTestDemo();
        String tsetUrl = "http://www.91qyy.com/#/helpCenter/shouye";
        test.InitDriver(tsetUrl);
        test.Help_center();*/

        /*seleniumTestDemo test = new seleniumTestDemo();
        String tsetUrl = "http://qyy.91qyy.com";
        test.InitDriver(tsetUrl);
        String mobilePhone2 = "13397210003", password2 = "123456";
        *//*test.Webjump(tsetUrl + "#/registe"); //免费注册
        test.registration(mobilePhone2, "434082");
        test.Enterprisecertification(mobilePhone2);
        test.newaddress();*//*
        test.userLogin(mobilePhone2, password2);
        test.Webjump(tsetUrl + "#/order");
        test.Orderlist("4");*/
        /*test.userLogin(mobilePhone2, password2);
        test.Webjump("http://qyy.91qyy.com/#/user");
        for (int i = 0; i < 3; i++) {
            test.newaddress();
        }*/

        /*test.Webjump("http://qyy.91qyy.com/#/jxs_new_list/1//");//经销馆无图片
        test.next_page();
        test.Webjump("http://qyy.91qyy.com/#/user");
        test.Enterprisecertification(tsetUrl, mobilePhone2);*/

        //后台
        seleniumTestDemo test = new seleniumTestDemo();
        String tsetUrl = "http://qyy.91qyy.com/qeybp";
        test.InitDriver(tsetUrl);
        test.cookie("9a7b22df-03aa-46d3-a9ec-35ae78957f9d");
        test.Webjump(tsetUrl + "/sp/seriesgl/proSeriesList.htm");
        /*test.Settlement();*/
        test.series("苹果");
        /*String Price;
        for (int i = 0; i < ArraySortUtil.aaList().size(); i++) {
            test.Webjump(tsetUrl + "/sp/spgl/spList.htm");
            String Commodity_category[]= {"数码","手机 ","iphone "};
            String img="C:\\Users\\user\\Desktop\\v2.3.0\\7679.jpg";
            String channel_name="国内营销部";
            Price=String.valueOf(3627+i);
            String stock="567";
            test.Add_product(Commodity_category,ArraySortUtil.aaList().get(i),img,channel_name,Price,stock);
        }*/
       /*String[] arr={"型号","内存","通讯","大小","颜色"};
        for (int i = 0; i < arr.length; i++) {
            test.Webjump(tsetUrl + "/sp/sxgl/categoryAttributeList.htm");
            test.Addproperties(arr[i]);
        }*/

        /*String filePath = "C:\\Users\\user\\Documents\\WeChat Files\\wxid_o2t83pvwj6o712\\Files\\789.xlsx";
        ExcelRead excelRead = new ExcelRead();
        for (int i = 0; i < excelRead.getValues(filePath).size(); i++) {
            test.Webjump(tsetUrl + "/sp/fl/spflList.htm");
            test.addAttribute(excelRead.getValues(filePath).get(i)[0]);
        }*/

        /*String filePath_1 = "C:\\Users\\user\\Desktop\\商品聚合.xlsx";
        ExcelRead excelRead_1 = new ExcelRead();
        for (int i = 1; i < excelRead_1.getValues(filePath_1).size(); i++) {
            test.Webjump(tsetUrl + "/sp/spgl/spList.htm");
            test.modifyProduct(excelRead_1.getValues(filePath_1).get(i)[4],excelRead_1.getValues(filePath_1).get(i)[8]);
        }*/
    }
}
