package com.example.IoTQRDoorLockApp.dataType;

public class Admin {
    int member_num;
    String admin_id;
    String user_password;
    String authentication_code;

    public Admin() {}

    public int getMember_num() { return this.member_num; }
    public String getAdmin_id() { return this.admin_id; }
    public String getUser_password() { return this.user_password; }
    public String getAuthentication_code() { return this.authentication_code; }

    public void setMember_num(int member_num) { this.member_num = member_num; }
    public void setadmin_id(String admin_id) { this.admin_id = admin_id; }
    public void setUser_password(String user_password) { this.user_password = user_password; }
    public void setAuthentication_code(String authentication_code) { this.authentication_code = authentication_code; }
}
