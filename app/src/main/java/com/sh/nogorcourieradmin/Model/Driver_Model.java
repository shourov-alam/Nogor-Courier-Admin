package com.sh.nogorcourieradmin.Model;

public class Driver_Model {

    String name,phone,uid;


    public Driver_Model() {

    }


    public Driver_Model(String name, String phone, String uid) {
        this.name = name;
        this.phone = phone;
        this.uid = uid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
