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
}
