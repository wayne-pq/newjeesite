package com.thinkgem.jeesite.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbctest {
	  //orcl为oracle数据库中的数据库名，localhost表示连接本机的oracle数据库     
	   //1521为连接的端口号     
	    private static String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";    
	    //system为登陆oracle数据库的用户名     
	    private static String user="jeesite";    
	    //manager为用户名system的密码     
	    private static String password="oracle";    
	    public static Connection conn;    
	    public static PreparedStatement ps;    
	    public static ResultSet rs;    
	    public static Statement st ;    
	    //连接数据库的方法     
	    public static Connection getConnection(){    
	        try {    
	            //初始化驱动包     
	            Class.forName("oracle.jdbc.driver.OracleDriver");    
	            //根据数据库连接字符，名称，密码给conn赋值     
	            conn=DriverManager.getConnection(url, user, password);    
	                
	        } catch (Exception e) {    
	            // TODO: handle exception     
	            e.printStackTrace();    
	        }
			return conn;    
	    }    
	    
	    
	    /* 查询数据库，输出符合要求的记录的情况*/    
	    public static void query() {    
	            
	        conn = getConnection(); //同样先要获取连接，即连接到数据库     
	        try {    
	            String sql = "select ID,NAME,CONTENT,cast(create_date as date) as create_date from cms_leavewords";     // 查询数据的sql语句     
	            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量     
	                
	            ResultSet rs = st.executeQuery(sql);    //执行sql查询语句，返回查询数据的结果集     
	            System.out.println("最后的查询结果为：");    
	            while (rs.next()) { // 判断是否还有下一个数据     
	                    
	                // 根据字段名获取相应的值     
	                String name = rs.getString("create_date");    
	               /* int age = rs.getInt("age");    
	                String sex = rs.getString("sex");    
	                String address = rs.getString("address");    
	                String depart = rs.getString("depart");    
	                String worklen = rs.getString("worklen");    
	                String wage = rs.getString("wage");    */
	                    
	                //输出查到的记录的各个字段的值     
	                System.out.println(name);    
	                
	            }    
	            conn.close();   //关闭数据库连接     
	                
	        } catch (SQLException e) {    
	            System.out.println("查询数据失败");    
	        }    
	    }    
	    
	    
	     //测试能否与oracle数据库连接成功     
	     public static void main(String[] args) {    
	    	 jdbctest basedao=new jdbctest();    
	        basedao.getConnection(); 
	        query();
	        if(conn==null){    
	            System.out.println("与oracle数据库连接失败！");    
	        }else{    
	            System.out.println("与oracle数据库连接成功！");    
	        }    
	     }    
}
