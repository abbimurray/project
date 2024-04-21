package OLD_DB_IGNORE.Admin;

public class UserAdmin {

    private int userID;
    private String username;
    private String  password;
    private String email;

    //constructors
    //getter and setters
    public UserAdmin(){

    }
    public UserAdmin(int userID, String username, String password, String email){
        this.userID= userID;
        this.username= username;
        this.password= password;
        this.email = email;
    }

    //userid
    public int getUserID(){
        return userID;
    }
    public void setUserID(int userID){
        this.userID= userID;
    }
    //username
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username= username;
    }
    //password
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password= password;
    }
    //email
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email= email;
    }
}
