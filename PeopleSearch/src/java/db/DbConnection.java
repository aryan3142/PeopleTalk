
package db;

import java.sql.*;
import java.util.HashMap;

public class DbConnection {
    private Connection con;
    private Statement st;
    private PreparedStatement insertUser,checkLogin,getUserPhoto,searchUsers;
    public DbConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ptalk",
                "root","incapp");
        st=con.createStatement();
        insertUser=con.prepareStatement(
                "insert into userinfo values (?,?,?,?,?,?,?,?,?,?)");
        checkLogin=con.prepareStatement(
                "select * from userinfo where email=? and pass=?");
        getUserPhoto=con.prepareStatement(
                "select photo from userinfo where email=?");
        searchUsers=con.prepareStatement(
 "select * from userinfo where state=? and city=? and email!=? and area like ? ");
 
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
    public java.util.HashSet searchUsers(String s,String c,String a,String e) throws SQLException{     
searchUsers.setString(1, s);
searchUsers.setString(2, c);
searchUsers.setString(3, e);
searchUsers.setString(4, "%"+a+"%");
ResultSet r=searchUsers.executeQuery();
java.util.HashSet allUserDetails=new java.util.HashSet();
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
}
