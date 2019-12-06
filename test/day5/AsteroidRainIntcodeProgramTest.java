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
        AsteroidRainIntcodeProgram arip = new AsteroidRainIntcodeProgram();
        arip.setInputProgram(programInstruction);
        arip.setInputValue(1);
        assertEquals(15426686, arip.execute());

        AsteroidRainIntcodeProgram arip2 = new AsteroidRainIntcodeProgram();
        arip2.setInputProgram(programInstruction);
        arip2.setInputValue(5);
        System.out.println(arip2.execute());
    }

    @Test
    void validatePart2sample1() {
        List<Integer> programInstruction = Arrays.asList(3,9,8,9,10,9,4,9,99,-1,8);
        int inputValue=8;
        AsteroidRainIntcodeProgram arip = setupProgram(programInstruction, inputValue);
        assertEquals(1, arip.execute());

        AsteroidRainIntcodeProgram arip2 = setupProgram(programInstruction, 11);
        assertEquals(0, arip2.execute());
    }

    @Test
    void validatePart2sample2(){
        List<Integer> programInstruction = Arrays.asList(3,9,7,9,10,9,4,9,99,-1,8);
        AsteroidRainIntcodeProgram arip = setupProgram(programInstruction, 7);
        assertEquals(1, arip.execute());

        AsteroidRainIntcodeProgram arip2 = setupProgram(programInstruction, 11);
        assertEquals(0, arip2.execute());
    }

    @Test
    void validatePart2sample3(){
        List<Integer> programInstruction = Arrays.asList(3,3,1108,-1,8,3,4,3,99);
        AsteroidRainIntcodeProgram arip = setupProgram(programInstruction, 8);
        assertEquals(1, arip.execute());

        AsteroidRainIntcodeProgram arip2 = setupProgram(programInstruction, 11);
        assertEquals(0, arip2.execute());
    }

    @Test
    void validatePart2sample4(){
        List<Integer> programInstruction = Arrays.asList(3,3,1107,-1,8,3,4,3,99);
        AsteroidRainIntcodeProgram arip = setupProgram(programInstruction, 7);
        assertEquals(1, arip.execute());

        AsteroidRainIntcodeProgram arip2 = setupProgram(programInstruction, 11);
        assertEquals(0, arip2.execute());

        AsteroidRainIntcodeProgram arip3 = setupProgram(programInstruction, 8);
        assertEquals(0, arip3.execute());
    }

    @Test
    void validateLargerSample1(){
        List<Integer> programInstruction = Arrays.asList(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9);
        AsteroidRainIntcodeProgram arip = setupProgram(programInstruction, 7);
        assertEquals(1, arip.execute());

        AsteroidRainIntcodeProgram arip2 = setupProgram(programInstruction, 0);
        assertEquals(0, arip2.execute());
    }

    @Test
    void validateLargerSample2(){
        List<Integer> programInstruction = Arrays.asList(3,3,1105,-1,9,1101,0,0,12,4,12,99,1);
        AsteroidRainIntcodeProgram arip = setupProgram(programInstruction, 7);
        assertEquals(1, arip.execute());

        AsteroidRainIntcodeProgram arip2 = setupProgram(programInstruction, 0);
        assertEquals(0, arip2.execute());
    }

    @Test
    void validateLargestSample(){
        List<Integer> programInstruction = Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99);
        AsteroidRainIntcodeProgram arip = setupProgram(programInstruction, 7);
        assertEquals(999, arip.execute());

        AsteroidRainIntcodeProgram arip2 = setupProgram(programInstruction, 8);
        assertEquals(1000, arip2.execute());

        AsteroidRainIntcodeProgram arip3 = setupProgram(programInstruction, 12);
        assertEquals(1001, arip3.execute());
    }

    private AsteroidRainIntcodeProgram setupProgram(List<Integer> programInstruction, int inputValue) {
        AsteroidRainIntcodeProgram arip = new AsteroidRainIntcodeProgram();
        arip.setInputProgram(programInstruction);
        arip.setInputValue(inputValue);
        return arip;
    }

}