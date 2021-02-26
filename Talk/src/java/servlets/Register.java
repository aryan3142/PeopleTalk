/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class Register extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         HttpSession session=request.getSession();
        try{
    String email=request.getParameter("email");
    String name=request.getParameter("name");
    String phone=request.getParameter("phone");
    String gender=request.getParameter("gender");
    String d=request.getParameter("dob");
    java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
    java.util.Date dt=sdf.parse(d);
    java.sql.Date dob=new java.sql.Date(dt.getTime());
    String state=request.getParameter("state");
    String city=request.getParameter("city");
    String area=request.getParameter("area");
    String pass=request.getParameter("password");
    Part p=request.getPart("photo");
    java.io.InputStream in;
    if(p!=null){
        in = p.getInputStream();
    }else{
        in=null;
    }
    db.DbConnection db=(db.DbConnection)session.getAttribute("db");
    if(db==null){
        db=new db.DbConnection();
        session.setAttribute("db",db);
    }
    String m=db.insertUser(email, pass, name, phone, gender, dob, state, city, area, in);
    if(m.equalsIgnoreCase("Done")){
        java.util.HashMap UserDetails=new java.util.HashMap();
        UserDetails.put("email",email);
        UserDetails.put("name",name);
        UserDetails.put("phone",phone);
        UserDetails.put("gender",gender);
        UserDetails.put("dob",dob);
        UserDetails.put("state",state);
        UserDetails.put("city",city);
        UserDetails.put("area",area);
        session.setAttribute("UserDetails", UserDetails);
        response.sendRedirect("profile.jsp");
    }else if(m.equalsIgnoreCase("Already")){
        session.setAttribute("msg","Email ID Already Registered.");
        response.sendRedirect("home.jsp");
    }else{
        session.setAttribute("msg","Registration Failed.");
        response.sendRedirect("home.jsp");
    }
        }catch(Exception ex){
            session.setAttribute("msg",ex.getMessage());
            response.sendRedirect("home.jsp");
        }
    }
}