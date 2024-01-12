package com.photo.master.data.util.core;


import com.photo.master.data.util.AResponseDto;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

public class ResponseAPIErrorDto extends AResponseDto implements Serializable {
    private static final long serialVersionUID = -3568494878981770758L;
    private Date timestamp;
    /**
     * @deprecated
     */
    @Deprecated
    private HttpStatus status;
    private String exception;
    /**
     * @deprecated
     */
    @Deprecated
    private String message;
    private String path;
    private String error;

    public Integer getResponseCode() {
        return this.getStatus().value();
    }

    public String getResponseMsg() {
        return this.getMessage();
    }

    public String toString() {
        Date var10000 = this.getTimestamp();
        return "ResponseAPIErrorDto(timestamp=" + var10000 + ", status=" + this.getStatus() + ", exception=" + this.getException() + ", message=" + this.getMessage() + ", path=" + this.getPath() + ", error=" + this.getError() + ")";
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public void setException(final String exception) {
        this.exception = exception;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setMessage(final String message) {
        this.message = message;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public HttpStatus getStatus() {
        return this.status;
    }

    public String getException() {
        return this.exception;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public String getMessage() {
        return this.message;
    }

    public String getPath() {
        return this.path;
    }

    public String getError() {
        return this.error;
    }

    public ResponseAPIErrorDto(final Date timestamp, final HttpStatus status, final String exception, final String message, final String path, final String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.exception = exception;
        this.message = message;
        this.path = path;
        this.error = error;
    }

    public ResponseAPIErrorDto() {
    }
}
