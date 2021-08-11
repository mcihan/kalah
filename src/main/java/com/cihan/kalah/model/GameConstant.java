package com.cihan.kalah.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameConstant {
    public final static int PIT_START_ID = 1;
    public final static int PIT_END_ID = 14;
    public final static int PIT_MEDIAN_ID = PIT_END_ID / 2;
    final static int DEFAULT_STONE_COUNT = 4;
    final static int HOUSE_STONE_COUNT = 0;
}
