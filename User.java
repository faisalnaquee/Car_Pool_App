public class User {
    String name; 
    int user_id;
    String password, emailId;

    public User(String name, int user_id, String password, String emailId) {
        this.name = name;
        this.emailId = emailId;
        this.user_id = user_id;
        this.password = password;
    }

    @Override
    public String toString() {
        return  "User_Id : " + user_id +
                "\nName : " + name + 
                "\nEmailId : "+ emailId;
    }
}