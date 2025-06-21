package com.example.IoTQRDoorLockApp.dataType;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListResponse {
    @SerializedName("content")
    private List<User> content;

    public UserListResponse() {}
    public List<User> getContent() { return content; }
    public void setContent(List<User> content) { this.content = content; }
}
