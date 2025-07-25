package com.louiskhanh.airbnb_clone_be.listing.presentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.louiskhanh.airbnb_clone_be.listing.application.TenantService;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.DisplayCardListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.domain.BookingCategory;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.DisplayListingDTO;
import com.louiskhanh.airbnb_clone_be.sharedkernel.service.State;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import com.louiskhanh.airbnb_clone_be.sharedkernel.service.StatusNotification;

@RestController
@RequestMapping("/api/tenant-listing")
public class TenantResource {
    private final TenantService tenantService;

    public TenantResource(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/get-all-by-category")
    public ResponseEntity<Page<DisplayCardListingDTO>> findAllByBookingCategory(Pageable pageable,
                                                                                @RequestParam BookingCategory category) {
        return ResponseEntity.ok(tenantService.getAllByCategory(pageable, category));
    }

    @GetMapping("/get-one")
    public ResponseEntity<DisplayListingDTO> getOne(@RequestParam UUID publicId) {
        State<DisplayListingDTO, String> displayListingState = tenantService.getOne(publicId);
        if (displayListingState.getStatus().equals(StatusNotification.OK)) {
            return ResponseEntity.ok(displayListingState.getValue());
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, displayListingState.getError());
            return ResponseEntity.of(problemDetail).build();
        }
    }

}
