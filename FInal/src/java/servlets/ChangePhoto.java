/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class ChangePhoto extends HttpServlet {

    
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=null;
                try{
session=request.getSession();
HashMap UserDetails=(HashMap)session.getAttribute("UserDetails");
if(UserDetails!=null){
        Part p=request.getPart("photo");
        java.io.InputStream is;
        if(p!=null){
            is = p.getInputStream();
        }else{
            is=null;
        }
        db.DbConnection db=(db.DbConnection)session.getAttribute("db");
        if(db==null){
            db=new db.DbConnection();
            session.setAttribute("db",db);
        }
        String s=db.changePhoto((String)UserDetails.get("email"), is);
        if(s.equalsIgnoreCase("Error")){
            session.setAttribute("msg","Photo Updation Failed.");
        }
        response.sendRedirect("profile.jsp");
}else{
    session.setAttribute("msg","Plz Login First!");
    response.sendRedirect("home.jsp");
}
    }catch(Exception ex){
            session.setAttribute("msg",ex.getMessage());
                response.sendRedirect("editprofile.jsp");
        }
    }

}
