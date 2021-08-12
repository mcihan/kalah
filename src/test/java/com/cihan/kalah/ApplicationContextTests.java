package com.cihan.kalah;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cihan dogan  on 12.08.2021
 */

@RunWith(SpringRunner.class)
public class ApplicationContextTests {
    @Test
    public void applicationStarts() {
        KalahApplication.main(new String[]{});
    }
}
