package day3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrossedWiresTest {

    @Test
    void validateParseWire() {
        CrossedWires cw = new CrossedWires();
        List<String> instructionsLines = new ArrayList<>();
        instructionsLines.add("U7");
//        instructionsLines.add("R75,D30,R83,U83,L12,D49,R71,U7,L72");
//        instructionsLines.add("U62,R66,U55,R34,D71,R55,D58,R83");
        cw.parseWire(instructionsLines);
        assertEquals(159, cw.getMinDist());
    }
}