package com.example.IoTQRDoorLockApp.dataType;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("member_num")
    int member_num;
    @SerializedName("outsider_name")
    String outsider_name;
    @SerializedName("note")
    String note;
    @SerializedName("admin_id")
    String admin_id;
    @SerializedName("device_id")
    String device_id;
    @SerializedName("start_date")
    String start_date;
    @SerializedName("end_date")
    String end_date;
    @SerializedName("authentication_code")
    String authentication_code;


    public User() {}

    public int getMember_num() { return this.member_num; }
    public String getOutsider_name() { return this.outsider_name; }
    public String getNote() { return this.note; }
    public String getAdmin_id() { return this.admin_id; }
    public String getDevice_id() { return this.device_id; }
    public String getStart_date() { return this.start_date; }
    public String getEnd_date() { return this.end_date; }
    public String getAuthentication_code() { return this.authentication_code; }

    public void setMember_num(int member_num) { this.member_num = member_num; }
    public void setOutsider_name(String outsider_name) { this.outsider_name = outsider_name; }
    public void setAdmin_id(String admin_id) { this.admin_id = admin_id; }
    public void setDevice_id(String device_id) { this.device_id = device_id; }
    public void setNote(String note) { this.note = note; }
    public void setStart_date(String start_date) { this.start_date = start_date; }
    public void setEnd_date(String end_date) { this.end_date = end_date; }
    public void setAuthentication_code(String authentication_code) { this.authentication_code = authentication_code; }
}
