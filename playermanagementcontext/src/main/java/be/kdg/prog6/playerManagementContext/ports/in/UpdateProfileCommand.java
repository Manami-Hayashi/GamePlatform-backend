package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;

import java.time.LocalDate;

public record UpdateProfileCommand (
        PlayerId playerId,
        String bio,
        String location,
        String avatar,
        LocalDate birthDate
) {
    public UpdateProfileCommand {
        if (playerId == null) {
            throw new IllegalArgumentException("PlayerId cannot be null");
        }
        if (bio == null || bio.isBlank()) {
            throw new IllegalArgumentException("Bio cannot be null or empty");
        }
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (avatar == null || avatar.isBlank()) {
            throw new IllegalArgumentException("Avatar cannot be null or empty");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birthdate cannot be null");
        }
    }


}
