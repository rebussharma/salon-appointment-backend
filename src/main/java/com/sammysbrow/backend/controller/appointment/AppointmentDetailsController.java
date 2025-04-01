package com.sammysbrow.backend.controller.appointment;

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
import com.sammysbrow.backend.service.appointment.projection.AppointmentDetailsDateTimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentDetailsController {

    private AppointmentDetailsService appointmentDetailsService;
    private AppointmentDetailsRepository appointmentDetailsRepository;

    @PostMapping
    public ResponseEntity<AppointmentDetailsDto> addAppointment(@RequestBody AppointmentDetailsDto appointmentDetailsDto) {
        AppointmentDetailsDto savedAppointment = appointmentDetailsService.addAppointment(appointmentDetailsDto);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<AppointmentDetailsDto> updateAppointment(@PathVariable("id") Long appointmentId, @RequestBody AppointmentDetailsDto updatedAppointmentDto) {
        AppointmentDetailsDto updatedAppointment = appointmentDetailsService.updateAppointment(appointmentId, updatedAppointmentDto);
        return ResponseEntity.ok(updatedAppointment);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDetailsDto>> getAllAppointmentDetails() {
        List<AppointmentDetailsDto> appointmentDetailsDto = appointmentDetailsService.getAllAppointmentDetails();
        return ResponseEntity.ok(appointmentDetailsDto);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentDetailsDto>> getAllUpcomingAppointmentDetails() {
        List<AppointmentDetailsDto> appointmentDetailsDto = appointmentDetailsService.getAllUpcomingAppointmentDetails();
        return ResponseEntity.ok(appointmentDetailsDto);
    }

    @GetMapping("/confirmed")
    public ResponseEntity<List<AppointmentDetailsDto>> getAllConfirmedAppointmentDetails() {

        List<AppointmentDetails> appointmentDetailsList = appointmentDetailsRepository.findByAppointmentStatus("confirmed");
        List<AppointmentDetailsDto> details =  appointmentDetailsList.stream().map(
                AppointmentDetailsMapper::mapToAppointmentDetailsDto
        ).toList();

        return ResponseEntity.ok(details);
    }

    @GetMapping("/confirmed/upcoming")
    public ResponseEntity<List<AppointmentDetailsDto>> getUpcomingCancelledAppointments() {
        List<AppointmentDetailsDto> appointments =
                appointmentDetailsService.getUpcomingAppointmentsByStatus(LocalDateTime.now(), "confirmed");
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/confirmed/upcoming/{code}")
    public ResponseEntity<AppointmentDetailsDto> getFirstUpcomingConfirmedAppointmentByConfirmationCode(
            @PathVariable("code") Long code) {
        AppointmentDetailsDto appointment = appointmentDetailsService.getFirstUpcomingConfirmedAppointmentByConfirmationCode(
                LocalDateTime.now(),
                new ConfirmationCodeSequence(code)
        );
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/confirmed/upcoming/date-time")
    public ResponseEntity<List<AppointmentDetailsDateTimeDto>> getUpcomingConfirmedAppointmentsDateTime() {
        List<AppointmentDetailsDateTimeDto> appointments =
                appointmentDetailsService.getUpcomingConfirmedAppointmentsDateTime(LocalDateTime.now());
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/confirmed/upcoming/artist-date-time")
    public ResponseEntity<List<AppointmentDetailsArtistDateTimeDto>> getUpcomingConfirmedAppointmentsArtistDateTime() {
        List<AppointmentDetailsArtistDateTimeDto> appointments =
                appointmentDetailsService.getUpcomingConfirmedAppointmentsArtistDateTime(LocalDateTime.now());
        return ResponseEntity.ok(appointments);
    }


    @GetMapping("/cancelled")
    public ResponseEntity<List<AppointmentDetailsDto>> getAllCancelledAppointmentDetails() {

        List<AppointmentDetails> appointmentDetailsList = appointmentDetailsRepository.findByAppointmentStatus("cancelled");
        List<AppointmentDetailsDto> details =  appointmentDetailsList.stream().map(
                AppointmentDetailsMapper::mapToAppointmentDetailsDto
        ).toList();

        return ResponseEntity.ok(details);
    }

    @GetMapping("/cancelled/upcoming")
    public ResponseEntity<List<AppointmentDetailsDto>> getUpcomingConfirmedAppointments() {
        List<AppointmentDetailsDto> appointments =
                appointmentDetailsService.getUpcomingAppointmentsByStatus(LocalDateTime.now(), "cancelled");
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/cancelled/upcoming/date-time")
    public ResponseEntity<List<AppointmentDetailsDateTimeDto>> getUpcomingCancelledAppointmentsDateTime() {
        List<AppointmentDetailsDateTimeDto> appointments =
                appointmentDetailsService.getUpcomingConfirmedAppointmentsDateTime(LocalDateTime.now());
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<AppointmentDetailsDto> getAppointmentDetailsById(@PathVariable("id") Long id) {
        AppointmentDetailsDto appointmentDetailsDto = appointmentDetailsService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentDetailsDto);
    }

    @GetMapping("code/{confirmation_code}")
    public ResponseEntity<AppointmentDetailsDto> getAppointmentDetailsByConfirmationCode(@PathVariable("confirmation_code") ConfirmationCodeSequence confirmation_code) {
        AppointmentDetailsDto appointmentDetailsDto = appointmentDetailsService.getAppointmentByConfirmationCode(confirmation_code);
        return ResponseEntity.ok(appointmentDetailsDto);
    }

    @GetMapping("confirmed/code/{confirmation_code}")
    public ResponseEntity<AppointmentDetailsDto> getConfirmedAppointmentDetailsByConfirmationCode(@PathVariable("confirmation_code") ConfirmationCodeSequence confirmation_code) {
        AppointmentDetailsDto appointmentDetailsDto = appointmentDetailsService.getConfirmedAppointmentByConfirmationCode(confirmation_code);
        return ResponseEntity.ok(appointmentDetailsDto);
    }

    @GetMapping("cancelled/code/{confirmation_code}")
    public ResponseEntity<AppointmentDetailsDto> getCancelledAppointmentDetailsByConfirmationCode(@PathVariable("confirmation_code") ConfirmationCodeSequence confirmation_code) {
        AppointmentDetailsDto appointmentDetailsDto = appointmentDetailsService.getCancelledAppointmentByConfirmationCode(confirmation_code);
        return ResponseEntity.ok(appointmentDetailsDto);
    }


}
