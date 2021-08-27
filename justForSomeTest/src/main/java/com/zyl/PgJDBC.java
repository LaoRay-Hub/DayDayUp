package com.zyl;

import java.sql.*;
import java.util.Random;

import static com.zyl.NameRandom.getChineseFamilyName;
import static com.zyl.NameRandom.getChineseGivenName;

public class PgJDBC {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstm = null;
        // 参数：
        // jdbc协议:postgresql子协议://主机地址:数据库端口号/要连接的数据库名
        String url = "jdbc:postgresql://192.168.200.52:5432/postgres?rewriteBatchedStatements=true";
        // 数据库用户名
        String user = "postgres";
        // 数据库密码
        String password = "firestone2020";

        // 2. 连接数据库，返回连接对象
        try {

            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            String sql = "insert into datex_web_test_source values(?,?,?,?) ";
            pstm = conn.prepareStatement(sql);
            int n = 5001;
            while (true) {
                for (int i = n; i <= n + 1000; i++) {

                    String name;
                    String familyNameStr;
                    Random random = new Random(System.currentTimeMillis());
                    Boolean flag = random.nextBoolean();
                    familyNameStr = getChineseFamilyName();
                    name = familyNameStr;
                    //true,则名2个汉字,false,则名1个汉字
                    if (flag) {
                        name += getChineseGivenName() + getChineseGivenName();
                    } else {
                        name += getChineseGivenName();
                    }

                    long time = System.currentTimeMillis();
                    Timestamp timestamp = new Timestamp(time + 1000 * i);

                    pstm.setObject(1, i);
                    pstm.setObject(2, name);
                    pstm.setObject(3, (int) (Math.random() * 100 + 1));
                    pstm.setTimestamp(4, timestamp);
                    pstm.addBatch();

                }

                n = n + 1000;
                pstm.executeBatch();
                conn.commit();
                Thread.sleep(1000 * 120);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
