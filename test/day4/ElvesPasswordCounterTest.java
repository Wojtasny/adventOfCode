package day4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElvesPasswordCounterTest {

    @Test
    void validatePossiblePassCount(){
        ElvesPasswordCounter elvesPasswordCounter = new ElvesPasswordCounter();
        assertEquals(979, elvesPasswordCounter.countPossiblePass());
    }

    @Test
    void validatePossiblePassCountAdvanced(){
        ElvesPasswordCounter elvesPasswordCounter = new ElvesPasswordCounter();
        elvesPasswordCounter.countPossiblePass();
        assertEquals(635, elvesPasswordCounter.countPossiblePassAdvanced());
    }
}