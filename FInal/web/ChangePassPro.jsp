<%@page import="java.util.HashMap"%>
<%
    HashMap UserDetails=(HashMap)session.getAttribute("UserDetails");
    if(UserDetails!=null){ 
        String op=request.getParameter("oldpassword");
        String np=request.getParameter("newpassword");
        String cp=request.getParameter("confirmpassword");
        if(np.equals(cp)){
            db.DbConnection db=(db.DbConnection)session.getAttribute("db");
            if(db==null){
                db=new db.DbConnection();
                session.setAttribute("db",db);
            }
            String s=db.changePass(np, (String)UserDetails.get("email"), op);
            if(s.equals("Done")){
                    session.setAttribute("msg","Password Updated!");
                    response.sendRedirect("changepassword.jsp");
            }else{
                    session.setAttribute("msg","Password Updation Failed!");
                    response.sendRedirect("changepassword.jsp");
            }
        }else{
            session.setAttribute("msg","New Password Mismatch!");
            response.sendRedirect("changepassword.jsp");
        }
    }else{
        session.setAttribute("msg","Plz Login first");
        response.sendRedirect("home.jsp");
    }
%>