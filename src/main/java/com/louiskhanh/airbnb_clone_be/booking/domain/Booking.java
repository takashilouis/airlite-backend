package com.louiskhanh.airbnb_clone_be.booking.domain;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.louiskhanh.airbnb_clone_be.sharedkernel.domain.AbstractAuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking extends AbstractAuditingEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingSequenceGenerator")
    @SequenceGenerator(name = "bookingSequenceGenerator", sequenceName = "booking_generator", allocationSize = 1)
    private Long id;

    @UuidGenerator
    @Column(name="public_id", nullable= false)
    private UUID publicId;

    @Column(name="start_date", nullable= false)
    private OffsetDateTime startDate;

    @Column(name="end_date", nullable= false)
    private OffsetDateTime endDate;

    @Column(name="total_price", nullable= false)
    private int totalPrice;

    @Column(name="nb_of_travelers", nullable= false)
    private int numberOfTravelers;

    @Column(name="fk_tenant", nullable= false)
    private UUID fkTenant;

    @Column(name="fk_listing", nullable= false)
    private UUID fkListing;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(int numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public UUID getFkTenant() {
        return fkTenant;
    }

    public void setFkTenant(UUID fkTenant) {
        this.fkTenant = fkTenant;
    }

    public UUID getFkListing() {
        return fkListing;
    }

    public void setFkListing(UUID fkListing) {
        this.fkListing = fkListing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return totalPrice == booking.totalPrice && numberOfTravelers == booking.numberOfTravelers && Objects.equals(startDate, booking.startDate) && Objects.equals(endDate, booking.endDate) && Objects.equals(fkTenant, booking.fkTenant) && Objects.equals(fkListing, booking.fkListing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, totalPrice, numberOfTravelers, fkTenant, fkListing);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPrice=" + totalPrice +
                ", numberOfTravelers=" + numberOfTravelers +
                ", fkTenant=" + fkTenant +
                ", fkListing=" + fkListing +
                '}';
    }
}