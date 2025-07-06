package com.louiskhanh.airbnb_clone_be.listing.application.dto;

import java.util.List;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.DescriptionDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.ListingInfoDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.PictureDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.PriceVO;
import com.louiskhanh.airbnb_clone_be.listing.domain.BookingCategory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class SaveListingDTO {
    @NotNull
    BookingCategory category;

    @NotNull String location;

    @NotNull @Valid
    ListingInfoDTO infos;

    @NotNull @Valid
    DescriptionDTO description;

    @NotNull @Valid
    PriceVO price;

    @NotNull
    List<PictureDTO> pictures;

    public BookingCategory getCategory() {
        return category;
    }
    
    public void setCategory(BookingCategory category){
        this.category = category;
    }

    public String getLocation(){
        return location;
    }
    
    public void setLocation(String location){
        this.location = location;
    }

    public ListingInfoDTO getInfos(){
        return infos;
    }

    public void setInfos(ListingInfoDTO infos){
        this.infos = infos;
    }

    public DescriptionDTO getDescription(){
        return description;
    }

    public void setDescription(DescriptionDTO description){
        this.description = description;
    }

    public PriceVO getPrice(){
        return price;
    }

    public void setPrice(PriceVO price){
        this.price = price;
    }

    public List<PictureDTO> getPictures(){
        return pictures;
    }

    public void setPictures(List<PictureDTO> pictures){
        this.pictures = pictures;
    }
}
