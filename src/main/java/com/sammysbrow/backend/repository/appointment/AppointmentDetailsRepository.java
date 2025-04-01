package com.sammysbrow.backend.repository.appointment;
import com.sammysbrow.backend.dto.appointment.AppointmentDetailsArtistDateTimeDto;
import com.sammysbrow.backend.entity.appointment.AppointmentDetails;

import com.sammysbrow.backend.entity.appointment.ConfirmationCodeSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface AppointmentDetailsRepository extends JpaRepository <AppointmentDetails, Long> {
//    String FIND_RELEVANT_APPOINTMENTS = "SELECT appointment_date, appointment_time FROM appointment_details";
//    @Query(value = FIND_RELEVANT_APPOINTMENTS, nativeQuery = true)
    //might have to use template and return both type AppointmentDetailsDateTimeService & AppointmentDetails for function below
   // List<AppointmentDetailsDateTimeService> findByAppointmentDateTimeAfterAndAppointmentStatus(LocalDateTime now, String status)
    @Query("SELECT a FROM AppointmentDetails a " +
            "WHERE a.appointmentStatus = :status " +
            "AND function('jsonb_extract_path_text', a.appointmentDateTimeDetails, 'appointmentDateTime') > :dateTime")
    List<AppointmentDetails> getUpcomingAppointmentsByStatus(
            @Param("dateTime") LocalDateTime dateTime,
            @Param("status") String status
    );

    @Query("select a from AppointmentDetails a " +
            "where function('jsonb_extract_path_text', a.appointmentDateTimeDetails, 'appointmentDateTime') > " +
            "to_char(NOW(), 'YYYY-MM-DD\"T\"HH24:MI')")
    List<AppointmentDetails> findAllUpcomingAppointment();

    List<AppointmentDetails> findByAppointmentStatus(String status);
    AppointmentDetails findByConfirmationCode(ConfirmationCodeSequence confirmationCode);
    AppointmentDetails findByAppointmentStatusAndConfirmationCode(String appointmentStatus, ConfirmationCodeSequence confirmationCode);

    // Get upcoming appointment with confirmation code
    @Query(value = "SELECT * FROM appointment_details " +
            "WHERE appointment_status = ?2 " +
            "AND CAST(appointment_date_time_details->>'appointmentDateTime' AS timestamp) > ?1 " +
            "AND confirmation_code = ?3 " +
            "ORDER BY CAST(appointment_date_time_details->>'appointmentDateTime' AS timestamp) " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<AppointmentDetails> findUpcomingAppointmentByStatusAndCode(
            LocalDateTime dateTime,
            String status,
            Long code  // Changed from ConfirmationCodeSequence to Long
    );

    // Get upcoming appointments with artist and datetime
    @Query(value = "SELECT " +
            "appointment_date_time_details as dateTimeDto, " +
            "artist as artist " +
            "FROM appointment_details " +
            "WHERE appointment_status = :status " +
            "AND CAST(appointment_date_time_details->>'appointmentDateTime' AS timestamp) > :dateTime " +
            "ORDER BY CAST(appointment_date_time_details->>'appointmentDateTime' AS timestamp)",
            nativeQuery = true)
    List<AppointmentDetailsArtistDateTimeDto> getUpcomingConfirmedAppointmentsArtistDateTime(
            @Param("dateTime") LocalDateTime dateTime,
            @Param("status") String status
    );

    // Additional queries for future use
    @Query(value = "SELECT COUNT(*) FROM appointment_details " +
            "WHERE appointment_status = :status " +
            "AND CAST(appointment_date_time_details->>'appointmentDateTime' AS timestamp) > :dateTime",
            nativeQuery = true)
    Long countUpcomingAppointmentsByStatus(
            @Param("dateTime") LocalDateTime dateTime,
            @Param("status") String status
    );
}
