package cnsukidayo.com.gitee.model.pojo;

/**
 * @author sukidayo
 * @date 2023/5/17 19:30
 */
public class User {
    private int id;
    private String name;
    private String password;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
