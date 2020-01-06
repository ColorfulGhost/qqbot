package cc.vimc.bot.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
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

}
