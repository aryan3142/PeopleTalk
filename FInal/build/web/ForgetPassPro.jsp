<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.PasswordAuthentication"%>
<%@page import="javax.mail.Session"%>
<%@page import="java.util.Properties"%>
<%
    String e=request.getParameter("email");
    db.DbConnection db=(db.DbConnection)session.getAttribute("db");
    if(db==null){
        db=new db.DbConnection();
        session.setAttribute("db",db);
    }
    String pass=db.getPassByEmail(e);
    if(pass!=null){
            final String SEmail="testincapp@gmail.com";
            final String SPass="incapp@123";
            final String REmail=e;
            final String Sub="Your Password is Here from PTALK!";
            final String Body="Your Email Id: "+e+" and Password: "+pass;
            //mail send Code
            Properties props=new Properties();
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port","465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.port","465");
            Session ses=Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(SEmail,SPass);
                }
            }
            );
            Message message=new MimeMessage(ses);
            message.setFrom(new InternetAddress(SEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(REmail));
            message.setSubject(Sub);
            message.setContent(Body,"text/html" );
            Transport.send(message);
            session.setAttribute("msg","Mail Sent successfully.");
            response.sendRedirect("home.jsp");
    }else{
            session.setAttribute("msg","Invalid Email ID!");
            response.sendRedirect("forgetpassword.jsp");
    }
%>