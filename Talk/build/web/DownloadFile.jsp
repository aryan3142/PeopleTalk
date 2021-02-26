<%
    String p=request.getParameter("pid");
    db.DbConnection db=(db.DbConnection)session.getAttribute("db");
    if(db==null){
        db=new db.DbConnection();
        session.setAttribute("db",db);
    }
    int pid=Integer.parseInt(p);
    java.util.ArrayList fileData=db.getFile(pid);
    if(fileData!=null){
    response.setContentType("APPLICATION/OCTET-STREAM");   
    response.setHeader("Content-Disposition","attachment; filename="+fileData.get(0)); 
    response.getOutputStream().write((byte[])fileData.get(1));
    }
%>