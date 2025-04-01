package com.sammysbrow.backend.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDetailsArtistDateTimeDto {
    private AppointmentDetailsDateTimeDto dateTimeDto;
    private String artist;
}
