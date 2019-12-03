package day2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntcodeProgramTest {

    private static int[] programCodeArray;

    @Test
    public void validateSampleInput(){
        int[] intcodeInput = {1,0,0,0,99};
        int[] intcodeOuput = {2,0,0,0,99};
        IntcodeProgram intcodeProgram = new IntcodeProgram(intcodeInput);
        intcodeProgram.executeProgram();
        int[] calculatedOutput = intcodeProgram.getIntcodeProgram();
        assertArrayEquals(intcodeOuput, calculatedOutput);
    }

    @Test
    public void validateSampleInput2(){
        int[] intcodeInput = {2,3,0,3,99};
        int[] intcodeOuput = {2,3,0,6,99};
        IntcodeProgram intcodeProgram = new IntcodeProgram(intcodeInput);
        intcodeProgram.executeProgram();
        int[] calculatedOutput = intcodeProgram.getIntcodeProgram();
        assertArrayEquals(intcodeOuput, calculatedOutput);
    }

    @Test
    public void validateSampleInput3(){
        int[] intcodeInput = {2,4,4,5,99,0};
        int[] intcodeOuput = {2,4,4,5,99,9801};
        IntcodeProgram intcodeProgram = new IntcodeProgram(intcodeInput);
        intcodeProgram.executeProgram();
        int[] calculatedOutput = intcodeProgram.getIntcodeProgram();
        assertArrayEquals(intcodeOuput, calculatedOutput);
    }

    @Test
    public void validateSampleInput4(){
        int[] intcodeInput = {1,1,1,4,99,5,6,0,99};
        int[] intcodeOuput = {30,1,1,4,2,5,6,0,99};
        IntcodeProgram intcodeProgram = new IntcodeProgram(intcodeInput);
        intcodeProgram.executeProgram();
        int[] calculatedOutput = intcodeProgram.getIntcodeProgram();
        assertArrayEquals(intcodeOuput, calculatedOutput);
    }

    @Test
    public void processExcerciseInput() throws IOException {
        int [] programCode = programCodeArray.clone();
        programCode[1] = 12;
        programCode[2] = 2;
        System.out.println("Before: " +Arrays.toString(programCode));
        IntcodeProgram intcodeProgram = new IntcodeProgram(programCode);
        intcodeProgram.executeProgram();
        System.out.println("Result: " + intcodeProgram.getIntcodeProgram()[0]);
    }

    @Test
    public void processExcerciseInputWithAdvencedIntcodeProgram(){
        int [] programCode = programCodeArray.clone();
        AdvancedIntcodeProgram advancedIntcodeProgram = new AdvancedIntcodeProgram(programCode, 12, 2);
        advancedIntcodeProgram.executeProgram();
        assertEquals(2692315, advancedIntcodeProgram.getOutput());
    }

    @Test
    public void findVerbAndNoun(){
        final int expectedOutput = 19690720;
        int result=0;
        for(int noun=0; noun<=99; noun++){
            for(int verb=0; verb<=99; verb++){
                int[] programCode = programCodeArray.clone();
                AdvancedIntcodeProgram advancedIntcodeProgram = new AdvancedIntcodeProgram(programCode, noun, verb);
                try {
                    advancedIntcodeProgram.executeProgram();
                } catch (IllegalArgumentException e) {
                    continue;
                }
                if(advancedIntcodeProgram.getOutput() == expectedOutput){
                    System.out.println("Noun= "+ noun + " verb= " + verb);
                    result = 100*noun + verb;
                    System.out.println("Result is: " + result);
                    return;
                }
            }
        }
        assertEquals(9507, result);
    }

    @BeforeAll
    public static void setup() throws IOException {
        File file = new File("D:\\git\\web-kmf\\adventOfCode\\resources\\inputday2.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        List<String> programCodeString = Arrays.asList(line.split(","));
        programCodeArray = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
        System.out.println(Arrays.toString(programCodeArray));
    }
}