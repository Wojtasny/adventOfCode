package day7;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AmplificationCircuitTest {

    @Test
    void validateSample1() {
        List<Integer> programInstruction = Arrays.asList(3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0);
        AmplificationCircuit amplificationCircuit = new AmplificationCircuit(programInstruction);
        assertEquals(43210, amplificationCircuit.findGreatestOutput());
    }

    @Test
    void validateSample2() {
        List<Integer> programInstruction = Arrays.asList(3,23,3,24,1002,24,10,24,1002,23,-1,23,
                101,5,23,23,1,24,23,23,4,23,99,0,0);
        AmplificationCircuit amplificationCircuit = new AmplificationCircuit(programInstruction);
        assertEquals(54321, amplificationCircuit.findGreatestOutput());
    }

    @Test
    void validateSample3() {
        List<Integer> programInstruction = Arrays.asList(3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,
                1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0);
        AmplificationCircuit amplificationCircuit = new AmplificationCircuit(programInstruction);
        assertEquals(65210, amplificationCircuit.findGreatestOutput());
    }

    @Test
    void validateInput() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday7.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        List<Integer> programInstruction = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        AmplificationCircuit amplificationCircuit = new AmplificationCircuit(programInstruction);
        System.out.println(amplificationCircuit.findGreatestOutput());
        assertEquals(95757, amplificationCircuit.findGreatestOutput());
    }

    @Test
    void validateFeedbackSample1(){
        List<Integer> programInstruction = Arrays.asList(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
                27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5);
        AmplificationCircuit amplificationCircuit = new AmplificationCircuit(programInstruction);
        assertEquals(139629729, amplificationCircuit.findGreatestOutputWithFeedback());
    }

    @Test
    void validateFeedbackSample2(){
        List<Integer> programInstruction = Arrays.asList(3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
                -5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
                53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10);
        AmplificationCircuit amplificationCircuit = new AmplificationCircuit(programInstruction);
        assertEquals(18216, amplificationCircuit.findGreatestOutputWithFeedback());
    }

    @Test
    void validateInputWithFeedback() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday7.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        List<Integer> programInstruction = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        AmplificationCircuit amplificationCircuit = new AmplificationCircuit(programInstruction);
        System.out.println(amplificationCircuit.findGreatestOutputWithFeedback());
        assertEquals(4275738, amplificationCircuit.findGreatestOutputWithFeedback());
    }
}