package com.sammysbrow.backend.mapper.appointment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sammysbrow.backend.dto.appointment.*;
import com.sammysbrow.backend.entity.appointment.AppointmentDetails;
import com.sammysbrow.backend.entity.appointment.ConfirmationCodeSequence;

import java.util.List;

public class AppointmentDetailsMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    // Map entity to DTO
    public static AppointmentDetailsDto mapToAppointmentDetailsDto(AppointmentDetails appointmentDetails) {
        List<ServiceType> serviceTypes;
        DeviceTypeDto deviceTypeDto;
        AppointmentDetailsDateTimeDto dateTimeDto;

        try {
            serviceTypes = objectMapper.readValue(
                    appointmentDetails.getServiceTypeJson(),
                    new TypeReference<List<ServiceType>>() {}
            );
            deviceTypeDto = objectMapper.readValue(
                    appointmentDetails.getBookingDeviceType(),
                    DeviceTypeDto.class
            );
            dateTimeDto = objectMapper.readValue(
                    appointmentDetails.getAppointmentDateTimeDetails(),
                    AppointmentDetailsDateTimeDto.class
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing JSON: " + e.getMessage(), e);
        }

        AppointmentDetailsDto dto = new AppointmentDetailsDto();
        dto.setId(appointmentDetails.getId());
        dto.setData_insertion_date_time(appointmentDetails.getData_insertion_date_time());
        dto.setAppointmentDateTimeDetails(dateTimeDto);
        dto.setAppointmentDuration(appointmentDetails.getAppointmentDuration());
        if (appointmentDetails.getConfirmationCode() != null) {
            dto.setConfirmationCode(appointmentDetails.getConfirmationCode().getId());
        }
        dto.setServiceType(serviceTypes);
        dto.setArtist(appointmentDetails.getArtist());
        dto.setClientName(appointmentDetails.getClientName());
        dto.setClientEmail(appointmentDetails.getClientEmail());
        dto.setClientPhone(appointmentDetails.getClientPhone());
        dto.setAppointmentNotes(appointmentDetails.getAppointmentNotes());
        dto.setAppointmentStatus(appointmentDetails.getAppointmentStatus());
        dto.setBookingDeviceType(deviceTypeDto);

        return dto;
    }

    // Map DTO to entity
    public static AppointmentDetails mapToAppointmentDetails(AppointmentDetailsDto dto) {
        String serviceTypeJson;
        String bookingDeviceTypeJson;
        String dateTimeJson;

        try {
            serviceTypeJson = objectMapper.writeValueAsString(dto.getServiceType());
            bookingDeviceTypeJson = objectMapper.writeValueAsString(dto.getBookingDeviceType());
            dateTimeJson = objectMapper.writeValueAsString(dto.getAppointmentDateTimeDetails());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing to JSON: " + e.getMessage(), e);
        }

        AppointmentDetails entity = new AppointmentDetails();
        entity.setId(dto.getId());
        entity.setData_insertion_date_time(dto.getData_insertion_date_time());
        entity.setAppointmentDateTimeDetails(dateTimeJson);
        entity.setAppointmentDuration(dto.getAppointmentDuration());
        if (dto.getConfirmationCode() != null) {
            entity.setConfirmationCode(new ConfirmationCodeSequence(dto.getConfirmationCode()));
        }
        entity.setServiceTypeJson(serviceTypeJson);
        entity.setArtist(dto.getArtist());
        entity.setClientName(dto.getClientName());
        entity.setClientEmail(dto.getClientEmail());
        entity.setClientPhone(dto.getClientPhone());
        entity.setAppointmentNotes(dto.getAppointmentNotes());
        entity.setAppointmentStatus(dto.getAppointmentStatus());
        entity.setBookingDeviceType(bookingDeviceTypeJson);

        return entity;
    }
}