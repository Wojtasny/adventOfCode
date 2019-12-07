package day6;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrbitMapTest {

    @Test
    void testParsingMap(){
        String map = new String(
                "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L");
        List<String> orbitMapList = Arrays.asList(map.split("\n"));
        OrbitMap orbitMap = new OrbitMap(orbitMapList);
        orbitMap.parseMap();
        assertEquals(42, orbitMap.countOrbits());
    }

    @Test
    void testParsingMapInput() throws IOException {
        List<String> orbitMapList = new ArrayList<>();
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday6.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line=br.readLine()) != null){
            orbitMapList.add(line);
        }
        OrbitMap orbitMap = new OrbitMap(orbitMapList);
        orbitMap.parseMap();
        assertEquals(245089, orbitMap.countOrbits());
    }

    @Test
    void testDistFromYouToSanta() {
        String map = new String(
                "COM)B\n" +
                        "B)C\n" +
                        "C)D\n" +
                        "D)E\n" +
                        "E)F\n" +
                        "B)G\n" +
                        "G)H\n" +
                        "D)I\n" +
                        "E)J\n" +
                        "J)K\n" +
                        "K)L\n" +
                        "K)YOU\n" +
                        "I)SAN");
        List<String> orbitMapList = Arrays.asList(map.split("\n"));
        OrbitMap orbitMap = new OrbitMap(orbitMapList);
        orbitMap.parseMap();
        assertEquals(4, orbitMap.countFromYOUtoSAN());
    }

    @Test
    void testDistanceFromYouToSantaInput() throws IOException {
        List<String> orbitMapList = new ArrayList<>();
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday6.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line=br.readLine()) != null){
            orbitMapList.add(line);
        }
        OrbitMap orbitMap = new OrbitMap(orbitMapList);
        orbitMap.parseMap();
        assertEquals(511, orbitMap.countFromYOUtoSAN());
    }
}