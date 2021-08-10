package com.cihan.kalah.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameConstant {
    public final static int PIT_START_ID = 1;
    public final static int PIT_END_ID = 14;
    public final static int PIT_MEDIAN_ID = PIT_END_ID / 2;
    public final static int PLAYER_A_HOUSE_ID = 7;
    public final static int PLAYER_B_HOUSE_ID = 14;
    public final static int DEFAULT_STONE_COUNT = 6;
    public final static int HOUSE_STONE_COUNT = 0;
}
