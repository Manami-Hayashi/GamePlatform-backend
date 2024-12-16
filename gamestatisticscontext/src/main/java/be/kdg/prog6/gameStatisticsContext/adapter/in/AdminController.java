package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.port.in.GetAdminStatisticsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final GetAdminStatisticsUseCase getAdminStatisticsUseCase;

    public AdminController(GetAdminStatisticsUseCase getAdminStatisticsUseCase) {
        this.getAdminStatisticsUseCase = getAdminStatisticsUseCase;
    }

    @GetMapping("/statistics")
    public List<AdminStatisticsDto> getAdminStatistics() {
        return getAdminStatisticsUseCase.getAdminStatistics();
    }
}
