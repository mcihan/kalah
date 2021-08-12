package com.cihan.kalah.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameConstant {
    private static final int DEFAULT_PIT_COUNT = 6;

    public static final int PIT_START_ID = 1;
    public static final int PIT_END_ID = DEFAULT_PIT_COUNT * 2 + 2;
    static final int PIT_MEDIAN_ID = PIT_END_ID / 2;

    public static final int HOUSE_A_PIT_ID = PIT_MEDIAN_ID;
    public static final int HOUSE_B_PIT_ID = PIT_END_ID;

    static final int DEFAULT_STONE_COUNT = 4;
    static final int HOUSE_STONE_COUNT = 0;
    static final int TOTAL_STONE_COUNT = DEFAULT_STONE_COUNT * (PIT_MEDIAN_ID - 1) * 2;
}
