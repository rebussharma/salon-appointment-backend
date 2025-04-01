package com.sammysbrow.backend.service.appointment.impl;
import com.sammysbrow.backend.entity.appointment.ConfirmationCodeSequence;
import com.sammysbrow.backend.repository.appointment.ConfirmationRepository;
import com.sammysbrow.backend.service.appointment.ConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {

    private ConfirmationRepository confirmationRepository;

    /*
        After each re-start of the server, ConfirmationCodeSequence class re-initializes.
        Which makes initialConfirmationCode to be 9999L every initialization.
        This is an issue IF there's already some data present in the confirmation code repo.
        To combat this issue, we get the latest (or lAST) confirmation code from the repo and start from there.
        If no latest data then initialConfirmationCode is 9999

     */
    public Long getInitialConfirmationCode() {
        ConfirmationCodeSequence latestEntry = confirmationRepository.findTopByOrderByIdDesc();
        if (latestEntry != null) {
            return latestEntry.getId() + 1;
        } else {
            return 9999L;
        }
    }

}
