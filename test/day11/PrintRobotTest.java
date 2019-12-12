package day11;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PrintRobotTest {

    private static List<Long> programInput;

    @BeforeAll
    public static void setup() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday11.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        line = br.readLine();

        programInput = Arrays.stream(line.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

    @Test
    void validatePainter(){
        PrintRobot pr = new PrintRobot(programInput);
        pr.paint();
        assertEquals(1732, pr.getPanelPrintedCount());
    }

    @Test
    void validatePainterWithWhiteStart(){
        PrintRobot pr = new PrintRobot(programInput);
        pr.setCurrentPanelWhite();
        pr.paint();
    }

}