package com.example.IoTQRDoorLockApp.dataType;

import com.google.gson.annotations.SerializedName;

public class VisitLog {
    @SerializedName("log_num")
    int log_num;
    @SerializedName("member_num")
    int member_num;
    @SerializedName("device_id")
    String device_id;
    @SerializedName("visit_time")
    String visit_time;

    public VisitLog(int log_num, int member_num, String device_id, String log_time) {
        this.log_num = log_num;
        this.member_num = member_num;
        this.device_id = device_id;
        this.visit_time = log_time;
    }

    public void setLog_num(int log_num) { this.log_num = log_num; }
    public void setMember_num(int member_num) { this.member_num = member_num; }
    public void setDevice_id(String device_id) { this.device_id = device_id; }
    public void setVisit_time(String visit_time) { this.visit_time = visit_time; }

    public int getLog_num() { return log_num; }
    public int getMember_num() { return member_num; }
    public String getDevice_id() { return device_id; }
    public String getVisit_time() { return visit_time; }
}
