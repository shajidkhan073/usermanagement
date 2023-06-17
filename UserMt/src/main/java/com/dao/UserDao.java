package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.User;

public class UserDao {
	private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	private String jdbcURL = "jdbc:mysql://localhost:3306/user_mgmt";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";
    
    private static final String INSERT_USERS_SQL = "INSERT INTO user" + "  (name, email, country) VALUES " +
            " (?, ?, ?);";

        private static final String SELECT_USER_BY_ID = "select * from user where id = ?";
        private static final String SELECT_ALL_USERS = "select * from user";
        private static final String DELETE_USERS_SQL = "delete from user where id = ?;";
        private static final String UPDATE_USERS_SQL = "update user set name = ?,email= ?,country=? where id = ?";
        
        
        protected Connection getConnection() {
        	Connection connection=null;
        	try {
				Class.forName(jdbcDriver);
				connection=DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return connection;
        }
        
        
      //create or insert user
        public void insertUser(User user) throws SQLException {
        	try(Connection connection=getConnection();
        			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USERS_SQL)){
        		preparedStatement.setString(1, user.getName());
        		preparedStatement.setString(2, user.getEmail());
        		preparedStatement.setString(3, user.getCountry());
        		preparedStatement.executeUpdate();		
        	}catch (Exception e) {
				e.printStackTrace();
			}
        	
        }
        //update user
        public int  userUpdate(User user) throws SQLException {
        	System.out.println("user comming data is "+user);
        	int rowUpdated;
        	try(Connection connection = getConnection();
        		PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_USERS_SQL)){
        		preparedStatement.setString(1, user.getName());
        		preparedStatement.setString(2, user.getEmail());
        		preparedStatement.setString(3, user.getCountry());
        		preparedStatement.setInt(4, user.getId());
        		rowUpdated=preparedStatement.executeUpdate();
        	}
			return rowUpdated;
        }
        
        
        //select user by  All Users  by sajid
        public List<User> getAllUsers() {
            List<User> list=new ArrayList<User>();  
        	try(Connection connection=getConnection();
        	PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_USERS)){
        		System.out.println(preparedStatement);
        		ResultSet resultSet=preparedStatement.executeQuery();
        		while(resultSet.next()) {
        			User e=new User();  
                    e.setId(resultSet.getInt(1));  
                    e.setName(resultSet.getString(2)); 
                    e.setEmail(resultSet.getString(3));  
                   e.setCountry(resultSet.getString(4)); 
                    list.add(e);
        	}
        }catch (SQLException e) {
			e.printStackTrace();
		}
			return list;
        }
        
        //delete user by sajid
				public boolean deleteUser(int id)throws SQLException {
					boolean rowDeleted;
					try(Connection connection=getConnection();
							PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USERS_SQL)){
						preparedStatement.setInt(1, id);
						rowDeleted=preparedStatement.executeUpdate()>0;
					}
					return rowDeleted;
				}

          //for edit form by userid   Sajid
				public User getUserId(int id) throws SQLException {
					
	        	System.out.println("dao in id "+ id);
					User users =new User();
			
					try(Connection connection=getConnection();
				        	PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID)){
				        		
				        		preparedStatement.setInt(1,id); 
				        		ResultSet rs=preparedStatement.executeQuery();
				        		System.out.println("data is coming "+rs );
				        		   
				                   
				                  if(rs.next()){  
				                	  users.setId(rs.getInt(1));  
				                	  users.setName(rs.getString(2));  
				                	  users.setEmail(rs.getString(3));  
				                	  users.setCountry(rs.getString(4)); 
							
				                  }
					                return users;
				}


					}}
				
        

