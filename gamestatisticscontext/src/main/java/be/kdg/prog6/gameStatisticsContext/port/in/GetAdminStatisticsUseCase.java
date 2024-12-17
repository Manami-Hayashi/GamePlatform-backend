package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.adapter.in.AdminStatisticsDto;

import java.util.List;

@FunctionalInterface
public interface GetAdminStatisticsUseCase {
    List<AdminStatisticsDto> getAdminStatistics();
}
