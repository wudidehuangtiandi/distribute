package com.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色类
 *
 * @author GC
 * @date 2020年 01月17日 20:44:58
 */

@Table(name = "user_roles")
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String role_name;

    @Id
   /* @Column(insertable = false,name = "id")//sqlserver 这个不加不行*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
