package cc.vimc.bot.dto;

import java.math.BigDecimal;
import java.util.Date;

public class UserDTO {
    private Integer id;
    private String username;
    private String realname;
    private String password;
    private String ip;
    private BigDecimal lastlogin;
    private String world;
    private String email;
    private Integer isLogged;

    public Integer getId() {
        return id;
    }

    public void setId(int Integer) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public BigDecimal getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(BigDecimal lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Integer isLogged) {
        this.isLogged = isLogged;
    }


}
