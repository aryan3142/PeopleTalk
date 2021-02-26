/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class SendMessage extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        String temail=request.getParameter("temail");
        try{
        HashMap UserDetails=(HashMap)session.getAttribute("UserDetails");
        if(UserDetails!=null){ 
            String semail=(String)UserDetails.get("email");
            String message=request.getParameter("message");
            Part p=request.getPart("ufile");
            java.io.InputStream in;
            String fname;
            if(p!=null){
                fname=p.getSubmittedFileName();
                in = p.getInputStream();
            }else{
                fname=null;
                in=null;
            }
            db.DbConnection db=(db.DbConnection)session.getAttribute("db");
            if(db==null){
                db=new db.DbConnection();
                session.setAttribute("db",db);
            }
            String m=db.sendMessage(semail,temail,message, fname, in);
            if(m.equalsIgnoreCase("Done")){
                session.setAttribute("msg","Message Sent Successfully.");
                response.sendRedirect("talk.jsp?temail="+temail);
            }else{
                session.setAttribute("msg","Message Sending Failed.");
                response.sendRedirect("talk.jsp?temail="+temail);
            }
        }else{
        session.setAttribute("msg","Plz Login first");
        response.sendRedirect("home.jsp");
}
        }catch(Exception ex){
            session.setAttribute("msg",ex.getMessage());
                response.sendRedirect("talk.jsp?temail="+temail);
        }
    }

}
