
package db;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class DbConnection {
    private Connection con;
    private Statement st;
    private PreparedStatement changePass,changePhoto,insertUser,checkLogin,getUserPhoto,searchUsers,
            getUserByEmail,sendMessage,getMessages,getPassByEmail,getFile;
    public DbConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ptalk",
                "root","incapp");
        st=con.createStatement();
 changePhoto=con.prepareStatement("update userinfo set photo=? where email=?");
 changePass=con.prepareStatement("update userinfo set pass=? where email=? and pass=?");
 insertUser=con.prepareStatement(
                "insert into userinfo values (?,?,?,?,?,?,?,?,?,?)");
        checkLogin=con.prepareStatement(
                "select * from userinfo where email=? and pass=?");
        getUserPhoto=con.prepareStatement(
                "select photo from userinfo where email=?");
        searchUsers=con.prepareStatement(
 "select * from userinfo where state=? and city=? and email!=? and area like ? ");
        getUserByEmail=con.prepareStatement(
                "select * from userinfo where email=?");
        getPassByEmail=con.prepareStatement(
                "select pass from userinfo where email=?");
        sendMessage=con.prepareStatement(
    "insert into peoplemsg  (sid,rid,msg,filename,ufile,udate) values (?,?,?,?,?,now())");
        getMessages=con.prepareStatement(
                "select * from peoplemsg where sid=? and rid=?");
        getFile=con.prepareStatement(
                "select filename,ufile from peoplemsg where pid=?");
    }
    public String changePhoto(String e,java.io.InputStream in) throws SQLException {        
        changePhoto.setString(2, e);
        changePhoto.setBinaryStream(1, in);
       int x=changePhoto.executeUpdate();
       if(x==1)
        return "Done";
       else 
        return "Error";
    }
    public String changePass(String np,String e,String op) throws SQLException {        
        changePass.setString(1, np);
        changePass.setString(2, e);
        changePass.setString(3, op);
       int x=changePass.executeUpdate();
       if(x==1)
        return "Done";
       else 
        return "Error";
    }
    public String sendMessage(String s,String r,String m,String f,java.io.InputStream in) throws SQLException {        
        sendMessage.setString(1, s);
        sendMessage.setString(2, r);
        sendMessage.setString(3, m);
        sendMessage.setString(4, f);
        sendMessage.setBinaryStream(5, in);
        int x=sendMessage.executeUpdate();
        if(x==1)
         return "Done";
        else 
         return "Error";
    }
    public String insertUser(String e,String p,String n,String ph,String gen,java.sql.Date d,String s,String c,String a,java.io.InputStream in) throws SQLException {
        try{        
 insertUser.setString(1, e);
 insertUser.setString(2, p);
 insertUser.setString(3, n);
 insertUser.setString(4, ph);
 insertUser.setString(5, gen);
 insertUser.setDate(6, d);
 insertUser.setString(7, s);
 insertUser.setString(8, c);
 insertUser.setString(9, a);
 insertUser.setBinaryStream(10, in);
int x=insertUser.executeUpdate();
if(x==1)
 return "Done";
else 
 return "Error";
        }catch(java.sql.SQLIntegrityConstraintViolationException ex){
            ex.printStackTrace();
            return "Already";
        }
    }
    public HashMap checkLogin(String e,String p) throws SQLException{     
checkLogin.setString(1, e);
checkLogin.setString(2, p);
ResultSet r=checkLogin.executeQuery();
if(r.next()){
    java.util.HashMap UserDetails=new java.util.HashMap();
    UserDetails.put("email",r.getString("email"));
    UserDetails.put("name",r.getString("name"));
    UserDetails.put("phone",r.getString("phone"));
    UserDetails.put("gender",r.getString("gender"));
    UserDetails.put("dob",r.getDate("dob"));
    UserDetails.put("state",r.getString("state"));
    UserDetails.put("city",r.getString("city"));
    UserDetails.put("area",r.getString("area"));
    return UserDetails;
}else{
    return null;
}
    }
    public byte[] getUserPhoto(String e) throws SQLException{     
        getUserPhoto.setString(1, e);
        ResultSet r=getUserPhoto.executeQuery();
        if(r.next()){
            return r.getBytes("photo");
        }else{
            return null;
        }
    }
    public java.util.ArrayList getFile(int p) throws SQLException{     
        getFile.setInt(1, p);
        ResultSet r=getFile.executeQuery();
        java.util.ArrayList fileData;
        if(r.next()){
            fileData=new java.util.ArrayList();
            fileData.add(r.getString("filename"));
            fileData.add(r.getBytes("ufile"));
            return fileData;
        }else{
            return null;
        }
    }
    public java.util.HashSet<java.util.HashMap> searchUsers(String s,String c,String a,String e) throws SQLException{     
searchUsers.setString(1, s);
searchUsers.setString(2, c);
searchUsers.setString(3, e);
searchUsers.setString(4, "%"+a+"%");
ResultSet r=searchUsers.executeQuery();
java.util.HashSet<java.util.HashMap> allUserDetails=new java.util.HashSet();
while(r.next()){
    java.util.HashMap UserDetails=new java.util.HashMap();
    UserDetails.put("email",r.getString("email"));
    UserDetails.put("name",r.getString("name"));
    UserDetails.put("phone",r.getString("phone"));
    UserDetails.put("gender",r.getString("gender"));
    UserDetails.put("dob",r.getDate("dob"));
    UserDetails.put("state",r.getString("state"));
    UserDetails.put("city",r.getString("city"));
    UserDetails.put("area",r.getString("area"));
    allUserDetails.add(UserDetails);
    }
return allUserDetails;
    }
    public HashMap getUserByEmail(String e) throws SQLException{     
        getUserByEmail.setString(1, e);
        ResultSet r=getUserByEmail.executeQuery();
        if(r.next()){
            java.util.HashMap UserDetails=new java.util.HashMap();
            UserDetails.put("email",r.getString("email"));
            UserDetails.put("name",r.getString("name"));
            UserDetails.put("phone",r.getString("phone"));
            UserDetails.put("gender",r.getString("gender"));
            UserDetails.put("dob",r.getDate("dob"));
            UserDetails.put("state",r.getString("state"));
            UserDetails.put("city",r.getString("city"));
            UserDetails.put("area",r.getString("area"));
            return UserDetails;
        }else{
            return null;
        }
    }
    public String getPassByEmail(String e) throws SQLException{     
        getPassByEmail.setString(1, e);
        ResultSet r=getPassByEmail.executeQuery();
        if(r.next()){
            return r.getString("pass");
        }else{
            return null;
        }
    }
    public java.util.HashSet<java.util.HashMap> getMessages(String s,String r) throws SQLException{     
getMessages.setString(1, s);
getMessages.setString(2, r);
ResultSet rs=getMessages.executeQuery();
java.util.HashSet<java.util.HashMap> allMessages=new java.util.HashSet();
while(rs.next()){
    java.util.HashMap message=new java.util.HashMap();
    message.put("message",rs.getString("msg"));
    message.put("filename",rs.getString("filename"));
    message.put("udate",rs.getString("udate"));
    message.put("pid",rs.getInt("pid"));
    allMessages.add(message);
    }
return allMessages;
    }
}
