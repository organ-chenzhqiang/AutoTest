package com.selenium;

import java.sql.SQLException;
import java.util.List;

public class TestDao {
    public static void main(String[] args) throws SQLException {
        mysql qyy_sms_send_record = new mysql();
//新建一个list得到查询方法中返回的集合
        String qyy_sms_send_record_param = "select param from qyy_sms_send_record WHERE phone_no=13397210009 order by send_time desc limit 1";
        String param = "param";
        List<String> list = qyy_sms_send_record.getSelect(qyy_sms_send_record_param, param);
//        System.out.println(list.get(0));
//对得到的list进行遍历输出到控制台中
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}