package com.louiskhanh.airbnb_clone_be.sharedkernel.domain;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity<T> implements Serializable{
    public abstract T getId();

    @CreatedDate
    @Column(nullable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();

    public Instant getCreatedDate(){
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate){
        this.createdDate = createdDate;
    }
 
    public Instant getLastModifiedDate(){
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate){
        this.lastModifiedDate = lastModifiedDate;
    }

}