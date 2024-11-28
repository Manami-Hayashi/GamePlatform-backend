package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.port.in.UpdateProfileUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UpdateProfileUseCase updateProfileUseCase;

    public ProfileController(UpdateProfileUseCase updateProfileUseCase) {
        this.updateProfileUseCase = updateProfileUseCase;
    }




}
