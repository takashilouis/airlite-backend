package com.louiskhanh.airbnb_clone_be.booking.domain;

import com.louiskhanh.airbnb_clone_be.sharedkernel.domain.AbstractAuditingEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking extends AbstractAuditingEntity<Long>{
    
}
