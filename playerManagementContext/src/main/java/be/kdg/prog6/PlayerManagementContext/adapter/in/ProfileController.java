package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.core.UpdateProfileUseCaseImpl;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.domain.Profile;
import be.kdg.prog6.PlayerManagementContext.port.in.GetProfileUseCase;
import be.kdg.prog6.PlayerManagementContext.port.in.UpdateProfileCommand;
import be.kdg.prog6.PlayerManagementContext.port.in.UpdateProfileUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @PutMapping
    public ResponseEntity<Void> updateProfile(@PathVariable UUID playerId, @RequestBody ProfileDto dto) {
        UpdateProfileCommand command = new UpdateProfileCommand(
                new PlayerId(playerId),
                dto.getBio(),
                dto.getAvatar(),
                dto.getLocation(),
                dto.getBirthDate()
        );
        logger.info("updating profile for player with id: {} ", playerId);
        updateProfileUseCase.updateProfile(command);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(@PathVariable UUID playerId) {
        Profile profile = getProfileUseCase.getProfile(new PlayerId(playerId));
        if (profile == null) {
            logger.info("profile not found for player with id: {} ", playerId);
            return ResponseEntity.notFound().build();
        }

        ProfileDto dto = new ProfileDto(
                profile.getBio(),
                profile.getAvatar(),
                profile.getLocation(),
                profile.getBirthDate()
        );
        logger.info("Getting profile for player with id: {} ", playerId);
        return ResponseEntity.ok(dto);
    }


}
