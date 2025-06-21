package com.example.IoTQRDoorLockApp.network;

import com.example.IoTQRDoorLockApp.dataType.Admin;
import com.example.IoTQRDoorLockApp.dataType.ApiResponse;
import com.example.IoTQRDoorLockApp.dataType.User;
import com.example.IoTQRDoorLockApp.dataType.UserListResponse;
import com.example.IoTQRDoorLockApp.dataType.VisitLog;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {
    @POST("user/get-my-outsiders")
    public Call<ApiResponse<UserListResponse>> getUsersByDevice_(
            @Body Admin admin
    );

    @POST("user/get-my-outsiders")
    public Call<ApiResponse<List<User>>> getUsersByDevice(
            @Body Admin admin
    );


    @POST("user/get-my-outsiders")
    public Call<ResponseBody> getUsersByDevices(
            @Body Admin admin
    );

    @POST("user/admin-sign-up")
    public Call<Void> signUpAdmin(
            @Body Admin admin
    );

    @POST("user/remove-outsider")
    public Call<Void> removeUserById(
            @Body User user
    );

    @POST("user/add-outsider")
    public Call<Void> addOutside(
            @Body User user
    );

    @POST("user/record-visit-time")
    public Call<Void> recordVisitTime(
            @Body VisitLog visitLog
    );

    @POST("user/get-visit-logs")
    public Call<ApiResponse<List<VisitLog>>> getVisitLog(
            @Body User user
    );
}
