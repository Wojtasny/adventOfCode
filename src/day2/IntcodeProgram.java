package day2;

class IntcodeProgram {

    IntcodeProgram() {
    }

    int[] getIntcodeProgram() {
        return intcodeProgram;
    }

    int [] intcodeProgram;
    private static final int ADDITION = 1;
    private static final int MULTIPLY = 2;
    private static final int FINISH = 99;
    private static final int ARGUMENT = 1;
    private static final int NUM_OF_PARAMS = 3;
    private static final int NEXT_OPCODE = ARGUMENT + NUM_OF_PARAMS;

    private boolean finished = false;

    IntcodeProgram(int [] intcodeProgram){
        this.intcodeProgram = intcodeProgram;
    }

    void executeProgram(){
        int opcodeIndex = 0;

        while (!finished){
            performAction(opcodeIndex);
            opcodeIndex+=NEXT_OPCODE;
        }
    }

    private void performAction(int opcodeIndex) {
        if(getValueFromProgram(opcodeIndex) == FINISH) {
            finished = true;
            return;
        }

        int arg1Index = getValueFromProgram(opcodeIndex+1);
        int arg2Index = getValueFromProgram(opcodeIndex+2);
        int destIndex = getValueFromProgram(opcodeIndex+3);

        switch (intcodeProgram[opcodeIndex]){
            case ADDITION:
                intcodeProgram[destIndex] = getValueFromProgram(arg1Index) + getValueFromProgram(arg2Index);
                break;
            case MULTIPLY:
                intcodeProgram[destIndex] = getValueFromProgram(arg1Index) * getValueFromProgram(arg2Index);
                break;

            default:
                throw new IllegalArgumentException("Unknown action");
        }

    }
    private int getValueFromProgram(int index){
        assert index <= intcodeProgram.length-1;
        return intcodeProgram[index];
    }
}
