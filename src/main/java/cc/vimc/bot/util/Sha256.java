package cc.vimc.bot.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Sha256 {
    public static String sha256(String password){

       return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
