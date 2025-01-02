package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.Gender;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;

import java.time.LocalDate;

public record UpdateProfileCommand (
        PlayerId playerId,
        String bio,
        String avatar,
        Gender gender,
        String location,
        LocalDate birthDate
) {

    public UpdateProfileCommand {
        if (bio == null || bio.isBlank()) {
            throw new IllegalArgumentException("Bio cannot be null or empty.");
        }
        if (avatar == null || avatar.isBlank()) {
            throw new IllegalArgumentException("Avatar cannot be null or empty.");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null.");
        }
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null.");
        }
    }
}
