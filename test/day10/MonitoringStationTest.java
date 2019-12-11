package day10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonitoringStationTest {

    @Test
    void parseMap() {
        String mapString =
                ".#..#\n" +
                        ".....\n" +
                        "#####\n" +
                        "....#\n" +
                        "...##";
        MonitoringStation monitoringStation = new MonitoringStation();
        monitoringStation.parseMap(mapString);
        assertEquals(8,monitoringStation.getHighestCount());
    }

    @Test
    void parseMapSample1() {
        String mapString =
                "......#.#.\n" +
                "#..#.#....\n" +
                "..#######.\n" +
                ".#.#.###..\n" +
                ".#..#.....\n" +
                "..#....#.#\n" +
                "#..#....#.\n" +
                ".##.#..###\n" +
                "##...#..#.\n" +
                ".#....####";
        MonitoringStation monitoringStation = new MonitoringStation();
        monitoringStation.parseMap(mapString);
        assertEquals(33,monitoringStation.getHighestCount());
    }

    @Test
    void parseMapSample2() {
        String mapString =
                "#.#...#.#.\n" +
                ".###....#.\n" +
                ".#....#...\n" +
                "##.#.#.#.#\n" +
                "....#.#.#.\n" +
                ".##..###.#\n" +
                "..#...##..\n" +
                "..##....##\n" +
                "......#...\n" +
                ".####.###.";
        MonitoringStation monitoringStation = new MonitoringStation();
        monitoringStation.parseMap(mapString);
        assertEquals(35,monitoringStation.getHighestCount());
    }

    @Test
    void parseMapSample3() {
        String mapString =
                ".#..#..###\n" +
                "####.###.#\n" +
                "....###.#.\n" +
                "..###.##.#\n" +
                "##.##.#.#.\n" +
                "....###..#\n" +
                "..#.#..#.#\n" +
                "#..#.#.###\n" +
                ".##...##.#\n" +
                ".....#.#..";
        MonitoringStation monitoringStation = new MonitoringStation();
        monitoringStation.parseMap(mapString);
        assertEquals(41,monitoringStation.getHighestCount());
    }

    @Test
    void parseMapSample4() {
        String mapString =
                ".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##";
        MonitoringStation monitoringStation = new MonitoringStation();
        monitoringStation.parseMap(mapString);
        assertEquals(210,monitoringStation.getHighestCount());
    }
}