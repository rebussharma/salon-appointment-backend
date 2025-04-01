package com.sammysbrow.backend.entity.appointment;

import com.sammysbrow.backend.service.appointment.ConfirmationService;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ConfirmationCodeGenerator implements IdentifierGenerator {

    private Long initialConfirmationCode;

    @Autowired
    @Lazy // Spring will delay initialization of dependency until it's actually needed
    private ConfirmationService confirmationService;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Lazy load the initial value only when it is first accessed
        if (initialConfirmationCode == null) {
            this.initialConfirmationCode = confirmationService.getInitialConfirmationCode();        }
        return initialConfirmationCode++;
    }
}
