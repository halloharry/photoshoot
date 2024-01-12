package com.photo.master.data.util.mapper;

import org.springframework.http.ResponseEntity;

public class ConvertResponseEntityDto<TARGET> {
    private TARGET target;
    private ResponseEntity responseEntity;

    public ConvertResponseEntityDto() {
    }

    public TARGET getTarget() {
        return this.target;
    }

    public ResponseEntity getResponseEntity() {
        return this.responseEntity;
    }

    public void setTarget(final TARGET target) {
        this.target = target;
    }

    public void setResponseEntity(final ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }
}
