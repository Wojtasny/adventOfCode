package day2;

import java.util.List;

public class AdvancedCleverIntcodeProgram extends CleverIntcodeProgram {

    AdvancedCleverIntcodeProgram(List<Integer> program, int noun, int verb) {
        this.inputProgram.addAll(program);
        this.inputProgram.set(1, noun);
        this.inputProgram.set(2, verb);
    }
    int getOutput(){
        return program.get(0);
    }
}
