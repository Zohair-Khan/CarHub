package carshop.carshop;

import java.io.Serializable;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String image;

    public User(){
        this.name = "";
        this.email = "";
        this.phone = "";
        this.password = "";
        this.image = "";
    }
    public User (String name, String email, String phone, String password, String image){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.image = image;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
