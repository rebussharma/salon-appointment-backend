package com.sammysbrow.backend.dto.appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDetailsDto {
    private Long Id;
    private LocalDateTime data_insertion_date_time = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    private AppointmentDetailsDateTimeDto appointmentDateTimeDetails;
    private Long appointmentDuration;
    private Long confirmationCode;
    private List<ServiceType> serviceType;
    private String artist = "Sammy";
    private String clientName;
    private String clientEmail;
    private Long clientPhone;
    private String appointmentNotes;
    private String appointmentStatus;
    private DeviceTypeDto bookingDeviceType;
    private String captchaToken;
}
/*
    Note on different date-times:
    data_insertion_date: Date time the user/client performs booking
    appointmentDateTime: Date time user selects appointment to START
    appointmentDateTimeStart: Actual date time the appointment starts, as it is obvious sometimes appointments start
                                sooner/later than expected.
    appointmentDateTimeEnd: Actual date time the appointment ends, as it is obvious sometimes appointments end
                            sooner/later than expected.
*/
