package day1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecurentFuelCalculatorTest {

    private static List<Long> modulesMasses;

    @BeforeAll
    public static void init() throws IOException {
        modulesMasses = new ArrayList<>();
        File file = new File("D:\\git\\web-kmf\\adventOfCode\\resources\\input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String string;
        while ((string = br.readLine()) != null){
            modulesMasses.add(Long.valueOf(string));
        }
    }

    @Test
    void validateInitValues() {
        RecurentFuelCalculator rfc = new RecurentFuelCalculator();
        assertEquals(2, rfc.recurrentCalculateFor(14));
        assertEquals(966, rfc.recurrentCalculateFor(1969));
        assertEquals(50346, rfc.recurrentCalculateFor(100756));
    }

    @Test
    void validateForInput() {
        RecurentFuelCalculator rfc = new RecurentFuelCalculator();
        long result = modulesMasses.stream().reduce(0L, (sum, element) -> sum + rfc.recurrentCalculateFor(element));


        assertEquals(5069241, result);
    }
}