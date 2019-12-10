package day10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonitoringStationTest {

    @Test
    void parseMap() {
        String mapString = ".#..#\n" +
                ".....\n" +
                "#####\n" +
                "....#\n" +
                "...##";
        MonitoringStation monitoringStation = new MonitoringStation();
        monitoringStation.parseMap(mapString);
    }
}