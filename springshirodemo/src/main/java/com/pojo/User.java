package com.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户类
 *
 * @author GC
 * @date 2020年 01月16日 08:48:39
 */
@Table(name = "users")
public class User implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String password_salt;

    @Id
   /* @Column(insertable = false,name = "id")//sqlserver 这个不加不行*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_salt() {
        return password_salt;
    }

    public void setPassword_salt(String password_salt) {
        this.password_salt = password_salt;
    }
}
