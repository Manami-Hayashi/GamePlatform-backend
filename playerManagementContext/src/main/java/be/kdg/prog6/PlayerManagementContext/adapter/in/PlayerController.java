package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.port.in.GetPlayersUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final GetPlayersUseCase getPlayersUseCase;

    public PlayerController(GetPlayersUseCase getPlayersUseCase) {
        this.getPlayersUseCase = getPlayersUseCase;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getPlayers() {
        List<Player> players = getPlayersUseCase.getPlayers();
        List<PlayerDto> playerDtos = players.stream()
                .map(player -> new PlayerDto(player.getPlayerId().id().toString(), player.getName()))
                .toList();
        return ResponseEntity.ok(playerDtos);
    }
}
