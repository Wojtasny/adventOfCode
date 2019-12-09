package day2;

import java.util.List;

class AdvancedCleverIntcodeProgram extends CleverIntcodeProgram {

    AdvancedCleverIntcodeProgram(List<Long> program, long noun, long verb) {
        this.program.clear();
        this.inputProgram.addAll(program);
        this.inputProgram.set(1, noun);
        this.inputProgram.set(2, verb);
    }
    long getOutput(){
        return program.get(0);
    }
}
