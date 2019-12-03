package day1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MassCalculatorTest {


    private static List<Long> modulesMasses;

    @Test
    void validateCalculator() {
        MassCalculator mc = new MassCalculator();
        assertEquals(mc.calculateFor(12), 2);
        assertEquals(mc.calculateFor(14), 2);
        assertEquals(mc.calculateFor(1969), 654);
        assertEquals(mc.calculateFor(100756), 33583);
    }

    @Test
    void countMassForInputFile() {
        MassCalculator mc = new MassCalculator();
        long sum = modulesMasses.stream().reduce(0L, (subtotal, element) -> subtotal + mc.calculateFor(element), Long::sum);
        assertEquals(3381405, sum);
    }

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
}