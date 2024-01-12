package com.photo.master.data.util;

import java.util.Map;

public interface IResultDto<T> {
    T getResult();

    AResponseDto getResponseData();

    Map<String, String> getMetaData();
}
