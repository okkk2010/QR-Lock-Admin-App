package com.example.IoTQRDoorLockApp.dataType;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("success")
    private boolean success;
    @SerializedName("content")
    private T content;
    private ApiError error;

    public ApiResponse() {}

    public boolean isSuccess() { return success; }
    public T getContent() { return content; }
    public ApiError getApiError() { return error; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setContent(T content) { this.content = content; }
    public void setApiError(ApiError error) { this.error = error; }

    public static class ApiError {
        private String code;
        private String message;

        public ApiError() {}

        public String getCode() { return code; }
        public String getMessage() { return message; }
        public void setCode(String code) { this.code = code; }
        public void setMessage(String message) { this.message = message; }
    }
}
