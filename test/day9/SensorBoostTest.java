package day9;

import day2.CleverIntcodeProgram;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SensorBoostTest {
    @Test
    void validateSampleInput(){
        List<Long> instructionList = Arrays.stream("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99".split(",")).map(Long::parseLong).collect(Collectors.toList());
        CleverIntcodeProgram cip = new CleverIntcodeProgram(instructionList);
        cip.execute();
        assertEquals(instructionList, cip.getOutputList());
    }

    @Test
    void validateSampleInput2(){
        List<Long> instructionList = Arrays.stream("1102,34915192,34915192,7,4,7,99,0".split(",")).map(Long::parseLong).collect(Collectors.toList());
        CleverIntcodeProgram cip = new CleverIntcodeProgram(instructionList);
        cip.execute();
        assertEquals(16, String.valueOf(cip.getOutputList().get(0)).length());
    }

    @Test
    void validateSampleInput3(){
        List<Long> instructionList = Arrays.stream("104,1125899906842624,99".split(",")).map(Long::parseLong).collect(Collectors.toList());
        CleverIntcodeProgram cip = new CleverIntcodeProgram(instructionList);
        cip.execute();
        assertEquals(1125899906842624L, cip.getOutputList().get(0));
    }

    @Test
    void validateInput() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday9.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        List<Long> programInstruction = Arrays.stream(line.split(",")).map(Long::new).collect(Collectors.toList());
        CleverIntcodeProgram cleverIntcodeProgram = new CleverIntcodeProgram(programInstruction);
        cleverIntcodeProgram.offerInputValue(1L);
        cleverIntcodeProgram.execute();
        assertEquals("3235019597", cleverIntcodeProgram.getOutputList().get(0).toString());
    }

    @Test
    void validateInputPar2() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday9.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        List<Long> programInstruction = Arrays.stream(line.split(",")).map(Long::new).collect(Collectors.toList());
        CleverIntcodeProgram cleverIntcodeProgram = new CleverIntcodeProgram(programInstruction);
        cleverIntcodeProgram.offerInputValue(2L);
        cleverIntcodeProgram.execute();
        assertEquals("80274", cleverIntcodeProgram.getOutputList().get(0).toString());
    }
}
