package com.sendly.models;

public class ListVerificationsRequest {
    private Integer limit;
    private String status;

    public Integer getLimit() { return limit; }
    public ListVerificationsRequest setLimit(Integer limit) { this.limit = limit; return this; }
    public String getStatus() { return status; }
    public ListVerificationsRequest setStatus(String status) { this.status = status; return this; }
}
