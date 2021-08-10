package com.cihan.kalah.util;

import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameValidationUtil {
    public static void validateParameters(String gameId, Integer pitId) {
        if (StringUtils.isBlank(gameId)) {
            throw new GameException("Parameters couldn't null");
        }
        Optional<Integer> anyInvalidInt = Stream.of(null, 14, 7).filter(i -> pitId == i).findAny();
        if (anyInvalidInt.isPresent()) {
            throw new GameException("Invalid pits");
        }
    }


}
