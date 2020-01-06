package cc.vimc.bot.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Sha256 {

    private String sha256;

    public String get(){
        return sha256;
    }
    public  Sha256 (String password){
        this.sha256 = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

}
