package com.sammysbrow.backend.entity.appointment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "confirmation_code_sequence")
public class ConfirmationCodeSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", type = com.sammysbrow.backend.entity.appointment.ConfirmationCodeGenerator.class)
    private Long id;
}
