package cc.vimc.bot.config;

import com.xxl.conf.core.annotation.XxlConf;

public class ServerConf {
    @XxlConf("qqbot.mc.mysql.url")
    public String mcMySQLUrl;
    @XxlConf("qqbot.mc.mysql.password")
    public String mysqlPassword;
    @XxlConf("qqbot.mc.mysql.user")
    public String mysqlUser;


    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public void setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
    }

    public void setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
    }

    public String getMcMySQLUrl() {
        return mcMySQLUrl;
    }

    public void setMcMySQLUrl(String mcMySQLUrl) {
        this.mcMySQLUrl = mcMySQLUrl;
    }
}
