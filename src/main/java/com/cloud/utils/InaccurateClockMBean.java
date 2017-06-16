package com.cloud.utils;

public interface InaccurateClockMBean {
    String restart();

    String turnOff();

    long[] getCurrentTimes();
}
