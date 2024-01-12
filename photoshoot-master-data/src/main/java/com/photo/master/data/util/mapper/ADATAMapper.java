package com.photo.master.data.util.mapper;


import org.springframework.data.domain.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ADATAMapper<SOURCE, TARGET> implements IDATAMapper<SOURCE, TARGET> {
    protected boolean isBatch = false;

    public ADATAMapper() {
    }

    protected void prepareData(List<SOURCE> sources) {
    }

    public List<TARGET> mapEntitiesIntoDTOs(Iterable<SOURCE> entities) {
        List<SOURCE> sources = new ArrayList();
        Objects.requireNonNull(sources);
        entities.forEach(sources::add);
        this.prepareData(sources);
        this.isBatch = true;
        List<TARGET> listTarget = new ArrayList();
        Iterator var4 = entities.iterator();

        while(var4.hasNext()) {
            SOURCE source = (SOURCE) var4.next();
            listTarget.add(this.convert(source));
        }

        this.isBatch = false;
        return listTarget;
    }

    public Page<TARGET> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<SOURCE> source) {
        List<TARGET> targetList = this.mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl(targetList, pageRequest, source.getTotalElements());
    }

    public Slice<TARGET> mapEntitySliceIntoDTOSlice(Slice<SOURCE> source) {
        List<TARGET> targetList = this.mapEntitiesIntoDTOs(source.getContent());
        return new SliceImpl(targetList, source.getPageable(), source.hasNext());
    }

    public ConvertResponseEntityDto convertWithResponseEntity(SOURCE source) {
        return null;
    }

    public abstract TARGET convert(SOURCE source);

    public SOURCE convertReverse(TARGET p_TARGET) {
        return null;
    }

    public Page<TARGET> mapEntityPageIntoDTOPage(Page<SOURCE> data) {
        return new PageImpl(this.mapEntitiesIntoDTOs(data.getContent()), data.getPageable(), data.getTotalElements());
    }

    public List<SOURCE> mapEntitiesIntoDTOsReverse(List<TARGET> targets) {
        return (List)targets.stream().map(this::convertReverse).collect(Collectors.toList());
    }

    public boolean isBatch() {
        return this.isBatch;
    }
}

