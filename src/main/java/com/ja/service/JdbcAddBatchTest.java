package com.ja.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 
public class JdbcAddBatchTest {
 
    /*
      
    #해당 테이블이 생성 됬다고 가정한다.
    CREATE TABLE first_step (
    step_name VARCHAR(20) NOT NULL,
    step_info VARCHAR(20) NOT NULL
    )
 
    CREATE TABLE second_step (
    step_name VARCHAR(20) NOT NULL,
    step_info VARCHAR(20) NOT NULL
    )
    */
     
    public static final String insertFrist = "INSERT INTO first_step (step_name, step_info)  VALUES (?, ?)";
    public static final String insertSecond = "INSERT INTO second_step (step_name, step_info)  VALUES (?, ?)";
     
    public static void main(String[] args) {
         
        Connection connection = null;
        PreparedStatement firstPreparedStatement = null;
        PreparedStatement secondPreparedStatement = null;
         
        try {
             
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sports", "root", "#######");
             
            firstPreparedStatement = connection.prepareStatement(insertFrist);
            secondPreparedStatement = connection.prepareStatement(insertSecond);
             
            for( int i = 0 ; i < 200 ; i++ ){
                 
                firstPreparedStatement.setString(1, String.format("first%s", i)); // 첫번째 ?에 맵핑
                firstPreparedStatement.setString(2, String.format("first%s", i)); // 두번째 ?에 맵핑 
                firstPreparedStatement.addBatch();          // 배치로 처리 하기 위해서 
                firstPreparedStatement.clearParameters();   // 재활용하기 위해 파라메터를 클리어 한다.
     
                secondPreparedStatement.setString(1, String.format("second%s", i)); // 첫번째 ?에 맵핑
                secondPreparedStatement.setString(2, String.format("second%s", i)); // 두번째 ?에 맵핑
                secondPreparedStatement.addBatch();         // 배치로 처리 하기 위해서
                secondPreparedStatement.clearParameters();  // 재활용하기 위해 파라메터를 클리어 한다.
                 
            }
             
            // 일괄로 insert query를 실행한다.
            firstPreparedStatement.executeBatch();
            secondPreparedStatement.executeBatch();
             
            // 트랜잭션을 위해 커밋을 한다.
            connection.commit();
             
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // db 관련 처리를 하다가 에러가 난 경우에 한해서 rollback을 하는 코드를 실행시킨다.
            e.printStackTrace();
            if(connection != null) try { connection.rollback(); } catch (SQLException sube) { sube.printStackTrace(); }
        } catch (Exception e) {
            // db 관련 처리를 하다가 에러가 난 경우에 한해서 rollback을 하는 코드를 실행시킨다.
            e.printStackTrace();
            if(connection != null) try { connection.rollback(); } catch (SQLException sube) { sube.printStackTrace(); }
        } finally {
            // 실패를 하든 성공을 하든 커넥션을 닫아 준다.
            if(firstPreparedStatement != null) try { firstPreparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if(secondPreparedStatement != null) try { secondPreparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if(connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
             
        }
         
    }
     
}