package com.louiskhanh.airbnb_clone_be.listing.application.dto.sub;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public record PictureDTO (
    @NotNull byte[] file,
    @NotNull String fileContentType,
    @NotNull boolean isCover
){
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureDTO that = (PictureDTO) o;
        return isCover == that.isCover && Objects.equals(fileContentType, that.fileContentType);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(fileContentType, isCover);
    }
} 
