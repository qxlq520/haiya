package com.haiya.mesh.dto;

public class ServiceResponseDTO {
    private Object data;
    private boolean success;
    private String message;
    private int statusCode;
    private long responseTime;
    private String fallbackData;

    // Constructors
    public ServiceResponseDTO() {
    }

    public ServiceResponseDTO(Object data, boolean success, String message, int statusCode) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.responseTime = System.currentTimeMillis();
    }

    // Getters and Setters
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getFallbackData() {
        return fallbackData;
    }

    public void setFallbackData(String fallbackData) {
        this.fallbackData = fallbackData;
    }

    @Override
    public String toString() {
        return "ServiceResponseDTO{" +
                "data=" + data +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", responseTime=" + responseTime +
                ", fallbackData='" + fallbackData + '\'' +
                '}';
    }
}