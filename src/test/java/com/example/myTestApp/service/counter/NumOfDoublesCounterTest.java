package com.example.myTestApp.service.counter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumOfDoublesCounterTest {
    final NumOfDoublesCounter counter = new NumOfDoublesCounterImpl();

    @Test
    void count() {
        var str = "";
        var res = counter.count(str);
        assertEquals(res, 0);

        str = "fvfvf3vffvf1.4ddf0";
        res = counter.count(str);
        assertEquals(res, 3);

        str = "2+ .2";
        res = counter.count(str);
        assertEquals(res, 2);

    }
}