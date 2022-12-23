package Objects;

public class User {

    private String username;
    private String password;
    private boolean isAdmin;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public User(String usn, String pwd, boolean isAdmin){
        this.username = usn;
        this.password = pwd;
        this.isAdmin = isAdmin;
    }

}
