package com.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
import com.model.User;


@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;
	
    public UserServlet() {
    	this.userDao=new UserDao();
    }
       
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action=request.getServletPath();
		switch(action) {
		case"/new":
			showNewForm(request,response);
		break;
		case"/insert":
			try {
				insertUser(request,response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
		break;
		case"/delete":
			try {
				deleteUser(request,response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
		break;
		case"/edit":
			try {
				showEditForm(request,response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
		break;
		
		case"/update":
			try {
				updateUser(request,response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
		break;
		default:
			listUser(request,response);
		break;
			
		}
	}
	private void showNewForm(HttpServletRequest request,HttpServletResponse response)
	 	throws ServletException, IOException{
		RequestDispatcher dispatcher=request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	private void insertUser(HttpServletRequest request,HttpServletResponse response)
		 	throws ServletException, IOException, SQLException{
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String country=request.getParameter("country");
		User newUser=new User(name,email,country);
		userDao.insertUser(newUser);
		response.sendRedirect("list");
	}
	private void deleteUser(HttpServletRequest request,HttpServletResponse response)
		 	throws ServletException, IOException, SQLException{
		int id=Integer.parseInt(request.getParameter("id"));
		userDao.deleteUser(id);
		response.sendRedirect("list");
	}
	private void showEditForm(HttpServletRequest request,HttpServletResponse response)
		 	throws ServletException, IOException, SQLException{
		int id=Integer.parseInt(request.getParameter("id"));
		System.out.println("id*********"+id);
		 User userByIdList=userDao.getUserId(id); 
		 System.out.println("userByIdList&&&&&&&&&&&&&&&&&&"+userByIdList);
		RequestDispatcher dispatcher=request.getRequestDispatcher("user-Edit-form.jsp");
		 request.setAttribute("user", userByIdList); 
		dispatcher.forward(request, response);
	}
	private void updateUser(HttpServletRequest request,HttpServletResponse response)
		 	throws ServletException, IOException, SQLException{
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String country=request.getParameter("country");
		System.out.println("name*"+name);
		System.out.println("email"+email);
		System.out.println("conutry"+country);
		/* User user=new User(id,name,email,country); */
		 User user=new User();  
		 user.setId(id);  
		 user.setName(name);     
		 user.setEmail(email);  
		 user.setCountry(country);  
		int row =userDao.userUpdate(user);
		if(row>0) {
			response.sendRedirect("list");
		}
		else {
			System.out.println("not data is Updated");
		}
		
}private void listUser(HttpServletRequest request,HttpServletResponse response)
	 	throws ServletException, IOException{
	List<User> listUser=userDao.getAllUsers();
	System.out.println("listUser*******"+listUser);
	request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher=request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
}
}