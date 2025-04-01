package com.sammysbrow.backend.entity.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sammysbrow.backend.dto.appointment.ServiceType;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment_details")
public class AppointmentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "data_insertion_date_time")
    private LocalDateTime data_insertion_date_time;

    @Column(name = "appointment_date_time_details", columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private String appointmentDateTimeDetails;

    @Column(name = "appointment_duration")
    private Long appointmentDuration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirmation_code", referencedColumnName = "id")
    private ConfirmationCodeSequence confirmationCode;

    @Column(name = "service_type", columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private String serviceTypeJson;

    @Column(name = "artist")
    private String artist = "Sammy";

    @Column (name = "client_name", length = 64)
    private String clientName;

    @Column (name = "client_email", length = 64)
    private String clientEmail;

    @Column (name = "client_phone", length = 16)
    private Long clientPhone;

    @Column (name = "appointment_notes", length = 256)
    private String appointmentNotes;

    @Column (name = "appointment_status")
    private String appointmentStatus;

    @Column(name = "booking_device", columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private String bookingDeviceType;


    // Helper to get `serviceType` as List
    public List<ServiceType> getServiceType() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(serviceTypeJson,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ServiceType.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse serviceTypeJson", e);
        }
    }

    // Setter for storing `serviceTypeJson` as JSON
    public void setServiceType(List<ServiceType> serviceTypes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.serviceTypeJson = objectMapper.writeValueAsString(serviceTypes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert serviceType to JSON", e);
        }
    }
}

