package day2;

class AdvancedIntcodeProgram extends IntcodeProgram {


    AdvancedIntcodeProgram(int[] intcodeProgram, int noun, int verb){
        this.intcodeProgram = intcodeProgram.clone();
        this.intcodeProgram[1] = noun;
        this.intcodeProgram[2] = verb;
    }

    int getOutput(){
        return intcodeProgram[0];
    }
}
