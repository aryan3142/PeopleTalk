<%@page import="java.util.HashMap"%>
<%@page  errorPage="Error.jsp" %>
<%
    HashMap UserDetails=(HashMap)session.getAttribute("UserDetails");
    if(UserDetails!=null){ 
            session.invalidate();
            response.sendRedirect("home.jsp");
    }else{
        session.setAttribute("msg","Plz Login first");
        response.sendRedirect("home.jsp");
    }
%>