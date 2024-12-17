package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.domain.Winner;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestIds {
    public static final UUID PLAYER_ID = UUID.fromString("e4e685be-ed89-42fb-a681-f272149c8218");
    public static final UUID PLAYER2_ID = UUID.fromString("2aeeaba5-355f-42a7-b215-44d4d0ebfd83");
    public static final UUID GAME_ID = UUID.fromString("14910372-c39d-7de7-b05a-93f8166cf7af");
    public static final LocalDateTime START_TIME = LocalDateTime.now().minusMinutes(20);
    public static final LocalDateTime END_TIME = LocalDateTime.now();
    public static final Winner WINNER = Winner.PLAYER1;
    public static final int SCORE_P1 = 10;
    public static final int SCORE_P2 = 5;
    public static final int MOVES_MADE_P1 = 20;
    public static final int MOVES_MADE_P2 = 19;
}
