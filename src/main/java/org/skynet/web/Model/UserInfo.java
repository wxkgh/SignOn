package org.skynet.web.Model;

public class UserInfo {
    private long userid;
    private String name;
    private String sex;
    private String email;

    public long getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
