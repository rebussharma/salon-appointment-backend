package com.sammysbrow.backend.service.appointment;

import com.sammysbrow.backend.dto.appointment.AppointmentDetailsArtistDateTimeDto;
import com.sammysbrow.backend.dto.appointment.AppointmentDetailsDateTimeDto;
import com.sammysbrow.backend.dto.appointment.AppointmentDetailsDto;
import com.sammysbrow.backend.entity.appointment.ConfirmationCodeSequence;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentDetailsService {
    AppointmentDetailsDto addAppointment(AppointmentDetailsDto appointmentDetailsDto);
    AppointmentDetailsDto updateAppointment(Long appointmentId, AppointmentDetailsDto updatedAppointmentDetailsDto);

    //    AppointmentDetailsDto updateAppointment(AppointmentDetailsDto appointmentDetailsDto);
//    AppointmentDetailsDto deleteAppointment(AppointmentDetailsDto appointmentDetailsDto);
    List<AppointmentDetailsDto> getAllAppointmentDetails();
    List<AppointmentDetailsDto> getAllUpcomingAppointmentDetails();
    AppointmentDetailsDto getAppointmentById(Long id);
    AppointmentDetailsDto getAppointmentByConfirmationCode(ConfirmationCodeSequence code);
    AppointmentDetailsDto getConfirmedAppointmentByConfirmationCode(ConfirmationCodeSequence code);
    AppointmentDetailsDto getCancelledAppointmentByConfirmationCode(ConfirmationCodeSequence code);

    List<AppointmentDetailsDateTimeDto> getUpcomingConfirmedAppointmentsDateTime(LocalDateTime now);
    List<AppointmentDetailsArtistDateTimeDto> getUpcomingConfirmedAppointmentsArtistDateTime(LocalDateTime now);
    List<AppointmentDetailsDto> getUpcomingAppointmentsByStatus(LocalDateTime now, String status);
    AppointmentDetailsDto getFirstUpcomingConfirmedAppointmentByConfirmationCode(
            LocalDateTime now,
            ConfirmationCodeSequence code
    );
//    List<AppointmentDetailsDto> getAppointmentsByUserId(int userId);
}
