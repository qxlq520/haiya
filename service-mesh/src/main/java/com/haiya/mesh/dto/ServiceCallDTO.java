package com.haiya.mesh.dto;

import java.util.Map;

public class ServiceCallDTO {
    private String serviceId;
    private String uri;
    private String method;
    private Map<String, String> headers;
    private Map<String, Object> parameters;
    private Object body;
    private long timestamp;

    // Constructors
    public ServiceCallDTO() {
    }

    public ServiceCallDTO(String serviceId, String uri, String method) {
        this.serviceId = serviceId;
        this.uri = uri;
        this.method = method;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ServiceCallDTO{" +
                "serviceId='" + serviceId + '\'' +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                ", body=" + body +
                ", timestamp=" + timestamp +
                '}';
    }
}