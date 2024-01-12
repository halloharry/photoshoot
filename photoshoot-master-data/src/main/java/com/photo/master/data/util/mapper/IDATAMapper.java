package com.photo.master.data.util.mapper;


import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;

import java.util.List;

public interface IDATAMapper<SOURCE, TARGET> extends Converter<SOURCE, TARGET> {
    List<TARGET> mapEntitiesIntoDTOs(Iterable<SOURCE> entities);

    Page<TARGET> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<SOURCE> source);

    Slice<TARGET> mapEntitySliceIntoDTOSlice(Slice<SOURCE> source);

    SOURCE convertReverse(TARGET p_TARGET);

    ConvertResponseEntityDto convertWithResponseEntity(SOURCE source);

    Page<TARGET> mapEntityPageIntoDTOPage(Page<SOURCE> data);

    List<SOURCE> mapEntitiesIntoDTOsReverse(List<TARGET> targets);
}

