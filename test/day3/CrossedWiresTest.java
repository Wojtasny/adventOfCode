package day3;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrossedWiresTest {

    @Test
    void validateParseWireSample1() {
        CrossedWires cw = new CrossedWires();
        List<String> instructionsLines = new ArrayList<>();
        instructionsLines.add("R75,D30,R83,U83,L12,D49,R71,U7,L72");
        instructionsLines.add("U62,R66,U55,R34,D71,R55,D58,R83");
        cw.parseWire(instructionsLines);
        assertEquals(159, cw.getMinDist());
    }

    @Test
    void validateParseWireSample2(){
        CrossedWires cw = new CrossedWires();
        List<String> instructionsLines = new ArrayList<>();
        instructionsLines.add("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51");
        instructionsLines.add("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7");
        cw.parseWire(instructionsLines);
        assertEquals(135, cw.getMinDist());
    }

    @Test
    void validateInputFile() throws IOException {
        List<String> instructionsLines = new ArrayList<>();
        instructionsLines = loadInput();
        CrossedWires cw = new CrossedWires();
        cw.parseWire(instructionsLines);
        assertEquals(489, cw.getMinDist());
    }

    private List<String> loadInput() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday3.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> inputList = new ArrayList<>();
        String line;
        while((line = br.readLine()) != null){
            inputList.add(line);
        }
        return inputList;
    }
}