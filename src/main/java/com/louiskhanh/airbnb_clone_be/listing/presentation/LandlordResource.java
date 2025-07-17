package com.louiskhanh.airbnb_clone_be.listing.presentation;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louiskhanh.airbnb_clone_be.listing.application.LandlordService;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.CreatedListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.SaveListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.PictureDTO;
import com.louiskhanh.airbnb_clone_be.user.application.UserException;
import com.louiskhanh.airbnb_clone_be.user.application.UserService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import com.louiskhanh.airbnb_clone_be.infrastructure.config.SecurityUtils;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.*;
import com.louiskhanh.airbnb_clone_be.user.application.dto.ReadUserDTO;
import com.louiskhanh.airbnb_clone_be.sharedkernel.service.State;
import com.louiskhanh.airbnb_clone_be.sharedkernel.service.StatusNotification;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/landlord-listing")
public class LandlordResource {
    private final LandlordService landlordService;

    private final Validator validator;

    private final UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public LandlordResource(LandlordService landlordService, Validator validator, UserService userService){
        this.landlordService = landlordService;
        this.validator = validator;
        this.userService = userService;
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CreatedListingDTO> create(MultipartHttpServletRequest request, 
        @RequestPart(name="dto") String saveListingDTOString) throws IOException{
            List<PictureDTO> pictures = request.getFileMap().values().stream()
                .map(mapMultipartFileToPictureDTO())
                .toList();
            
            SaveListingDTO saveListingDTO = objectMapper.readValue(saveListingDTOString, SaveListingDTO.class);
            saveListingDTO.setPictures(pictures);

            Set<ConstraintViolation<SaveListingDTO>> violations = validator.validate(saveListingDTO);

            if(!violations.isEmpty()){
                String violationsJoined = violations.stream()
                    .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                    .collect(Collectors.joining(", "));
                ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, violationsJoined);
                return ResponseEntity.of(problemDetail).build();
            }else{
                return ResponseEntity.ok(landlordService.create(saveListingDTO));
            }
            
            
        };

    private static Function<MultipartFile, PictureDTO> mapMultipartFileToPictureDTO(){
        return multipartFile -> {
            try{
                return new PictureDTO(multipartFile.getBytes(), multipartFile.getContentType(), false);
            } catch(IOException ioe){
                throw new UserException(String.format("Cannot parse multipart file %s", multipartFile.getOriginalFilename()));
            }
        };
    }

    @GetMapping(value = "/get-all")
    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
    public ResponseEntity<List<DisplayCardListingDTO>> getAll() {
        ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        List<DisplayCardListingDTO> allProperties = landlordService.getAllProperties(connectedUser);
        return ResponseEntity.ok(allProperties);
    }
    
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
    public ResponseEntity<UUID> delete(@RequestParam UUID publicId) {
        ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        State<UUID, String> deleteState = landlordService.delete(publicId, connectedUser);
        if (deleteState.getStatus().equals(StatusNotification.OK)) {
            return ResponseEntity.ok(deleteState.getValue());
        } else if (deleteState.getStatus().equals(StatusNotification.UNAUTHORIZED)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
