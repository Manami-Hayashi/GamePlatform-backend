package be.kdg.prog6.playerManagementContext.adapters.in;

import be.kdg.prog6.playerManagementContext.domain.Gender;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.domain.Profile;
import be.kdg.prog6.playerManagementContext.ports.in.GetProfileUseCase;
import be.kdg.prog6.playerManagementContext.ports.in.UpdateProfileCommand;
import be.kdg.prog6.playerManagementContext.ports.in.UpdateProfileUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/players/{playerId}/profile")
public class ProfileController {

    private final UpdateProfileUseCase updateProfileUseCase;
    private final GetProfileUseCase getProfileUseCase;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    public ProfileController(UpdateProfileUseCase updateProfileUseCase, GetProfileUseCase getProfileUseCase) {
        this.updateProfileUseCase = updateProfileUseCase;
        this.getProfileUseCase = getProfileUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> updateProfile(@PathVariable UUID playerId, @RequestBody ProfileDto dto) {
        logger.info("Received ProfileDto: {}", dto);

        if (dto.getGender() == null || dto.getGender().isBlank()) {
            logger.error("Gender value is null or blank for player with ID: {}", playerId);
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request
        }

        Gender gender;
        try {
            gender = Gender.valueOf(dto.getGender().toUpperCase());
        } catch (IllegalArgumentException ex) {
            logger.error("Invalid gender value '{}' for player with ID: {}", dto.getGender(), playerId);
            return ResponseEntity.badRequest().build(); // Return 400 for invalid values
        }

        UpdateProfileCommand command = new UpdateProfileCommand(
                new PlayerId(playerId),
                dto.getBio(),
                dto.getAvatar(),
                gender,
                dto.getLocation(),
                dto.getBirthDate()
        );
        logger.info("Updating profile for player with ID: {}", playerId);

        try {
            updateProfileUseCase.updateProfile(command);
        } catch (IllegalArgumentException e) {
            logger.error("Profile not found for player with ID: {}. Creating new profile.", playerId);
            UpdateProfileCommand newProfile = new UpdateProfileCommand(
                    new PlayerId(playerId),
                    dto.getBio(),
                    dto.getAvatar(),
                    gender,
                    dto.getLocation(),
                    dto.getBirthDate()
            );

            updateProfileUseCase.updateProfile(newProfile);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(@PathVariable UUID playerId) {
        try {
            // Fetch the profile
            Profile profile = getProfileUseCase.getProfile(new PlayerId(playerId));

            // Handle null gender and other potential null fields
            Gender gender = profile.getGender();

            ProfileDto dto = new ProfileDto(
                    profile.getBio() != null ? profile.getBio() : "No bio available",
                    profile.getAvatar() != null ? profile.getAvatar() : "",
                    gender.toString().toUpperCase(),
                    profile.getLocation() != null ? profile.getLocation() : "Unknown location",
                    profile.getBirthDate()
            );

            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            logger.error("Profile not found for player with ID: {}", playerId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Unexpected error while fetching profile for player with ID: {}", playerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}