package com.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class mysql {
    public Connection getCon() {
        //数据库连接名称
        String username = "root";
        //数据库连接密码
        String password = "maZ0AlPB";
        String driver = "com.mysql.cj.jdbc.Driver";
        //其中test为数据库名称
        String url = "jdbc:mysql://192.168.100.12:3307/s90";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public List<String> getSelect(String sql,String wordname) {
// sql语句
//        String sql = "select f08 from t9011 WHERE f01=1169";
//        String qyy_sms_send_record_param="select param from qyy_sms_send_record WHERE phone_no=13397210009 order by send_time desc limit 1";
// 获取到连接
        Connection conn = getCon();
        PreparedStatement pst;
        // 定义一个list用于接受数据库查询到的内容
        List<String> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                // 将查询出的内容添加到list中，其中userName为数据库中的字段名称
//                list.add(rs.getString("f08"));
                list.add(rs.getString(wordname));
            }
        } catch (Exception e) {

        }
        return list;
    }
}
