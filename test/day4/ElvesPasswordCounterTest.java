package day4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElvesPasswordCounterTest {

    @Test
    void validatePossiblePassCount(){
        ElvesPasswordCounter elvesPasswordCounter = new ElvesPasswordCounter();
        assertEquals(979, elvesPasswordCounter.countPossiblePass());
        System.out.println(elvesPasswordCounter.countPossiblePassAdvanced());
    }

    @Test
    void validatePossiblePassCountAdvanced(){
        ElvesPasswordCounter elvesPasswordCounter = new ElvesPasswordCounter();

        System.out.println(elvesPasswordCounter.countPossiblePassAdvanced());
    }

//    @Test
//    void validatePossiblePassAdvancedSample1(){
//        ElvesPasswordCounter elvesPasswordCounter = new ElvesPasswordCounter();
//        assertTrue(elvesPasswordCounter.validatePossiblePassAdvanced(112233));
//        assertFalse(elvesPasswordCounter.validatePossiblePassAdvanced(123444));
//        assertTrue(elvesPasswordCounter.validatePossiblePassAdvanced(111122));
//
//    }
}