package sampleapp.wolken.com.sampleappopen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseOpenIdAuth {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("d-zz-a")
    private String cookie;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ResponseOpenIdAuth{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", cookie='" + cookie + '\'' +
                '}';
    }
}