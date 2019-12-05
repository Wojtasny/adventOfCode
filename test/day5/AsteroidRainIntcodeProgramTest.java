package day5;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AsteroidRainIntcodeProgramTest {

    @Test
    void validateComputerWorking() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday4.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        List<Integer> programInstruction = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        AsteroidRainIntcodeProgram arip = new AsteroidRainIntcodeProgram(programInstruction);
        arip.execute();
        System.out.println(arip.getProgram().toString());
    }

}