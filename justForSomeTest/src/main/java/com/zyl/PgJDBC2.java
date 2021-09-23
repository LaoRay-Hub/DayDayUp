package com.zyl;


import java.sql.*;


public class PgJDBC2 { public static void main(String[] args) {
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
        String sql ="select * from datex_web_test_source ";
        pstm = conn.prepareStatement(sql);

        pstm.execute();

        ResultSetMetaData metaData = pstm.getMetaData();
        ResultSet resultSet = pstm.getResultSet();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnTypeName = metaData.getColumnTypeName(i);
                String columnClassName = metaData.getColumnClassName(i);
                if (columnTypeName.equals("jsonb") || columnTypeName.equals("json")) {
                    System.out.println("json");
                    String object = resultSet.getString(i);
                    System.out.println("object+" + object);
//                break;
                }
                System.out.println(columnClassName);

                System.out.println(columnTypeName);
            }
        }

        conn.commit();

    } catch (Exception e) {
        e.printStackTrace();
    }finally{
        if(pstm!=null){
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
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
