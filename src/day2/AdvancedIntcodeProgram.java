package day2;

import java.util.Arrays;

class AdvancedIntcodeProgram extends IntcodeProgram {


    AdvancedIntcodeProgram(int[] intcodeProgram) {
        super(intcodeProgram);
    }

    public AdvancedIntcodeProgram(int[] intcodeProgram, int noun, int verb){
        this.intcodeProgram = intcodeProgram.clone();
        this.intcodeProgram[1] = noun;
        this.intcodeProgram[2] = verb;
    }

    int getOutput(){
        return intcodeProgram[0];
    }
}
