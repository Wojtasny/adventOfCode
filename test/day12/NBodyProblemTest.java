package day12;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NBodyProblemTest {
    @Test
    void validateSample(){
        NBodyProblem nBodyProblem = new NBodyProblem();
        NBodyProblem.Moon io = nBodyProblem. new Moon(nBodyProblem. new Position(-1,0,2));
        NBodyProblem.Moon europa = nBodyProblem. new Moon(nBodyProblem. new Position(2,-10,-7));
        NBodyProblem.Moon ganymede = nBodyProblem. new Moon(nBodyProblem. new Position(4,-8,8));
        NBodyProblem.Moon callisto = nBodyProblem. new Moon(nBodyProblem. new Position(3,5,-1));

        nBodyProblem.setIo(io);
        nBodyProblem.setEuropa(europa);
        nBodyProblem.setGanymede(ganymede);
        nBodyProblem.setCallisto(callisto);
        assertEquals(179, nBodyProblem.countEnergyAfterSteps(10));
    }

    @Test
    void validateSampleCountToRepeatItself(){
        NBodyProblem nBodyProblem = new NBodyProblem();
        NBodyProblem.Moon io = nBodyProblem. new Moon(nBodyProblem. new Position(-1,0,2));
        NBodyProblem.Moon europa = nBodyProblem. new Moon(nBodyProblem. new Position(2,-10,-7));
        NBodyProblem.Moon ganymede = nBodyProblem. new Moon(nBodyProblem. new Position(4,-8,8));
        NBodyProblem.Moon callisto = nBodyProblem. new Moon(nBodyProblem. new Position(3,5,-1));

        nBodyProblem.setIo(io);
        nBodyProblem.setEuropa(europa);
        nBodyProblem.setGanymede(ganymede);
        nBodyProblem.setCallisto(callisto);
        assertEquals(2772, nBodyProblem.stepsToReturnToInitialState());
    }

    @Test
    void validateSample2(){
        NBodyProblem nBodyProblem = new NBodyProblem();
        NBodyProblem.Moon io = nBodyProblem. new Moon(nBodyProblem. new Position(-8,-10,0));
        NBodyProblem.Moon europa = nBodyProblem. new Moon(nBodyProblem. new Position(5,5,10));
        NBodyProblem.Moon ganymede = nBodyProblem. new Moon(nBodyProblem. new Position(2,-7,3));
        NBodyProblem.Moon callisto = nBodyProblem. new Moon(nBodyProblem. new Position(9,-8,-3));

        nBodyProblem.setIo(io);
        nBodyProblem.setEuropa(europa);
        nBodyProblem.setGanymede(ganymede);
        nBodyProblem.setCallisto(callisto);
        assertEquals(1940, nBodyProblem.countEnergyAfterSteps(100));
    }

    @Test
    void validateSample2CountToRepeatItself(){
        NBodyProblem nBodyProblem = new NBodyProblem();
        NBodyProblem.Moon io = nBodyProblem. new Moon(nBodyProblem. new Position(-8,-10,0));
        NBodyProblem.Moon europa = nBodyProblem. new Moon(nBodyProblem. new Position(5,5,10));
        NBodyProblem.Moon ganymede = nBodyProblem. new Moon(nBodyProblem. new Position(2,-7,3));
        NBodyProblem.Moon callisto = nBodyProblem. new Moon(nBodyProblem. new Position(9,-8,-3));

        nBodyProblem.setIo(io);
        nBodyProblem.setEuropa(europa);
        nBodyProblem.setGanymede(ganymede);
        nBodyProblem.setCallisto(callisto);
        assertEquals(1940, nBodyProblem.stepsToReturnToInitialState());
    }

    @Test
    void validateInput(){
        NBodyProblem nBodyProblem = new NBodyProblem();
        NBodyProblem.Moon io = nBodyProblem. new Moon(nBodyProblem. new Position(-7,-8,9));
        NBodyProblem.Moon europa = nBodyProblem. new Moon(nBodyProblem. new Position(-12,-3,-4));
        NBodyProblem.Moon ganymede = nBodyProblem. new Moon(nBodyProblem. new Position(6,-17,-9));
        NBodyProblem.Moon callisto = nBodyProblem. new Moon(nBodyProblem. new Position(4,-10,-6));

        nBodyProblem.setIo(io);
        nBodyProblem.setEuropa(europa);
        nBodyProblem.setGanymede(ganymede);
        nBodyProblem.setCallisto(callisto);
        assertEquals(12773, nBodyProblem.countEnergyAfterSteps(1000));
    }
}