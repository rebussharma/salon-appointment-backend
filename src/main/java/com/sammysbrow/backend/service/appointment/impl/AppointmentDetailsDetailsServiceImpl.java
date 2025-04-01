package com.sammysbrow.backend.service.appointment.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sammysbrow.backend.dto.appointment.AppointmentDetailsArtistDateTimeDto;
import com.sammysbrow.backend.dto.appointment.AppointmentDetailsDateTimeDto;
import com.sammysbrow.backend.dto.appointment.AppointmentDetailsDto;
import com.sammysbrow.backend.entity.appointment.AppointmentDetails;
import com.sammysbrow.backend.entity.appointment.ConfirmationCodeSequence;
import com.sammysbrow.backend.exception.ResourceNotFoundException;
import com.sammysbrow.backend.mapper.appointment.AppointmentDetailsMapper;
import com.sammysbrow.backend.repository.appointment.AppointmentDetailsRepository;
import com.sammysbrow.backend.repository.appointment.ConfirmationRepository;
import com.sammysbrow.backend.service.appointment.AppointmentDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentDetailsDetailsServiceImpl implements AppointmentDetailsService {

    private AppointmentDetailsRepository appointmentDetailsRepository;
    private ConfirmationRepository confirmationRepository;

    // Add to your service implementation
    private void validateJsonFields(AppointmentDetailsDto dto) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            // Validate each JSON field can be properly serialized
            if (dto.getServiceType() != null) {
                objectMapper.writeValueAsString(dto.getServiceType());
            }
            if (dto.getBookingDeviceType() != null) {
                objectMapper.writeValueAsString(dto.getBookingDeviceType());
            }
            if (dto.getAppointmentDateTimeDetails() != null) {
                objectMapper.writeValueAsString(dto.getAppointmentDateTimeDetails());
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON data in appointment fields: " + e.getMessage(), e);
        }
    }

    @Override
    public AppointmentDetailsDto addAppointment(AppointmentDetailsDto appointmentDetailsDto) {
        validateJsonFields(appointmentDetailsDto);
        AppointmentDetails appointmentDetails = AppointmentDetailsMapper.mapToAppointmentDetails(appointmentDetailsDto);
        ConfirmationCodeSequence confirmationCodeSequence = new ConfirmationCodeSequence();
        ConfirmationCodeSequence savedConfirmCode = confirmationRepository.save(confirmationCodeSequence);
        appointmentDetails.setConfirmationCode(new ConfirmationCodeSequence(savedConfirmCode.getId()));

        AppointmentDetails savedAppointment = appointmentDetailsRepository.save(appointmentDetails);
        return AppointmentDetailsMapper.mapToAppointmentDetailsDto(savedAppointment);
    }

    @Override
    public AppointmentDetailsDto updateAppointment(Long appointmentId, AppointmentDetailsDto updatedAppointmentDetailsDto) {
        validateJsonFields(updatedAppointmentDetailsDto);
        AppointmentDetails appointmentDetails = appointmentDetailsRepository.findById(appointmentId).orElseThrow(
                ()-> new ResourceNotFoundException("Appointment with id " + appointmentId + " not found")
        );

        // Create JSON strings using ObjectMapper for complex objects
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            // Service types
            String serviceTypeJson = objectMapper.writeValueAsString(updatedAppointmentDetailsDto.getServiceType());
            appointmentDetails.setServiceTypeJson(serviceTypeJson);

            // Device type
            String deviceTypeJson = objectMapper.writeValueAsString(updatedAppointmentDetailsDto.getBookingDeviceType());
            appointmentDetails.setBookingDeviceType(deviceTypeJson);

            // Date time details
            String dateTimeJson = objectMapper.writeValueAsString(updatedAppointmentDetailsDto.getAppointmentDateTimeDetails());
            appointmentDetails.setAppointmentDateTimeDetails(dateTimeJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing to JSON: " + e.getMessage(), e);
        }

        // Update other fields
        appointmentDetails.setClientEmail(updatedAppointmentDetailsDto.getClientEmail());
        appointmentDetails.setClientName(updatedAppointmentDetailsDto.getClientName());
        appointmentDetails.setClientPhone(updatedAppointmentDetailsDto.getClientPhone());
        appointmentDetails.setAppointmentNotes(updatedAppointmentDetailsDto.getAppointmentNotes());
        appointmentDetails.setArtist(updatedAppointmentDetailsDto.getArtist());
        appointmentDetails.setAppointmentDuration(updatedAppointmentDetailsDto.getAppointmentDuration());
        appointmentDetails.setAppointmentStatus(updatedAppointmentDetailsDto.getAppointmentStatus());

        AppointmentDetails updatedAppointment = appointmentDetailsRepository.save(appointmentDetails);
        return AppointmentDetailsMapper.mapToAppointmentDetailsDto(updatedAppointment);
    }

    @Override
    public List<AppointmentDetailsDto> getAllAppointmentDetails() {
        List<AppointmentDetails> appointmentDetails = appointmentDetailsRepository.findAll();
        return appointmentDetails.stream().map(
                AppointmentDetailsMapper::mapToAppointmentDetailsDto
        ).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDetailsDto> getAllUpcomingAppointmentDetails() {
        List<AppointmentDetails> appointmentDetails = appointmentDetailsRepository.findAllUpcomingAppointment();
        return  appointmentDetails.stream().map(
                AppointmentDetailsMapper::mapToAppointmentDetailsDto
        ).collect(Collectors.toList());
    }

    @Override
    public AppointmentDetailsDto getAppointmentById(Long id) {
        AppointmentDetails appointmentDetails = appointmentDetailsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Appointment with id: " + id + " not found")
        );
        return AppointmentDetailsMapper.mapToAppointmentDetailsDto(appointmentDetails);
    }

    @Override
    public AppointmentDetailsDto getAppointmentByConfirmationCode(ConfirmationCodeSequence code) {
        Optional<AppointmentDetails> appointmentDetails = Optional.ofNullable(appointmentDetailsRepository.findByConfirmationCode(code));
        return appointmentDetails.map(AppointmentDetailsMapper::mapToAppointmentDetailsDto).
        orElseThrow(
                () -> new ResourceNotFoundException("Appointment with confirmation code: " + code + " not found")
        );
    }

    @Override
    public AppointmentDetailsDto getConfirmedAppointmentByConfirmationCode(ConfirmationCodeSequence code) {
        Optional<AppointmentDetails> appointmentDetails = Optional.ofNullable(appointmentDetailsRepository.findByAppointmentStatusAndConfirmationCode( "confirmed", code));
        return appointmentDetails.map(AppointmentDetailsMapper::mapToAppointmentDetailsDto).
                orElseThrow(
                        () -> new ResourceNotFoundException("Appointment with confirmation code: " + code + " not found")
                );
    }

    @Override
    public AppointmentDetailsDto getCancelledAppointmentByConfirmationCode(ConfirmationCodeSequence code) {
        Optional<AppointmentDetails> appointmentDetails = Optional.ofNullable(appointmentDetailsRepository.findByAppointmentStatusAndConfirmationCode( "cancelled", code));
        return appointmentDetails.map(AppointmentDetailsMapper::mapToAppointmentDetailsDto).
                orElseThrow(
                        () -> new ResourceNotFoundException("Appointment with confirmation code: " + code + " not found")
                );
    }

    @Override
    public List<AppointmentDetailsDto> getUpcomingAppointmentsByStatus(LocalDateTime now, String status) {
        List<AppointmentDetails> appointments = appointmentDetailsRepository.getUpcomingAppointmentsByStatus(now, status);
        return appointments.stream()
                .map(AppointmentDetailsMapper::mapToAppointmentDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDetailsDateTimeDto> getUpcomingConfirmedAppointmentsDateTime(LocalDateTime now) {
        List<AppointmentDetails> appointments = appointmentDetailsRepository.getUpcomingAppointmentsByStatus(now, "confirmed");
        ObjectMapper mapper = new ObjectMapper();

        return appointments.stream()
                .map(appointment -> {
                    try {
                        return mapper.readValue(
                                appointment.getAppointmentDateTimeDetails(),
                                AppointmentDetailsDateTimeDto.class
                        );
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error parsing appointment datetime details", e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDetailsArtistDateTimeDto> getUpcomingConfirmedAppointmentsArtistDateTime(LocalDateTime now) {
        List<AppointmentDetails> appointments = appointmentDetailsRepository.getUpcomingAppointmentsByStatus(now, "confirmed");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return appointments.stream()
                .map(appointment -> {
                    try {
                        AppointmentDetailsDateTimeDto dateTimeDto = mapper.readValue(
                                appointment.getAppointmentDateTimeDetails(),
                                AppointmentDetailsDateTimeDto.class
                        );
                        return new AppointmentDetailsArtistDateTimeDto(dateTimeDto, appointment.getArtist());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error parsing appointment datetime details", e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDetailsDto getFirstUpcomingConfirmedAppointmentByConfirmationCode(LocalDateTime now, ConfirmationCodeSequence code) {
        Optional<AppointmentDetails> appointmentDetails = appointmentDetailsRepository
                .findUpcomingAppointmentByStatusAndCode(
                        now,
                        "confirmed",
                        code.getId()  // Pass the ID instead of the entity
                );

        return appointmentDetails.map(AppointmentDetailsMapper::mapToAppointmentDetailsDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No upcoming confirmed appointment found with confirmation code: " + code
                ));
    }


}
