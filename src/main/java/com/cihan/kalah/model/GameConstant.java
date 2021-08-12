package com.cihan.kalah.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameConstant {
    public static final int PIT_START_ID = 1;
    public static final int PIT_END_ID = 14;
    static final int PIT_MEDIAN_ID = PIT_END_ID / 2;

    public static final int HOUSE_A_PIT_ID = PIT_MEDIAN_ID;
    public static final int HOUSE_B_PIT_ID = PIT_END_ID;


    final static int DEFAULT_STONE_COUNT = 4;
    final static int HOUSE_STONE_COUNT = 0;
    final static int TOTAL_STONE_COUNT = DEFAULT_STONE_COUNT * (PIT_MEDIAN_ID - 1) * 2;
}
