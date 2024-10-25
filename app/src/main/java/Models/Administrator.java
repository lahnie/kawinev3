package Models;

public class Administrator {
    private String user;
    private String password;

    public Administrator() {
        user = "auto";
        password = "2024";
    }

    public String getUser(){
        return user;
    }

    public String getPassword(){
        return password;
    }

}
