package day5;

import day4.ElvesPasswordCounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class AsteroidRainIntcodeProgram {
    private static final int POSITION_MODE = 0;
    private static final int IMMEDIATE_MODE = 1;
    private static final int FIRST_ARG = 1;
    private static final int SECOND_ARG = 2;
    private static final int THIRD_ARG = 3;
    private static final Integer TERMINATING_OPCODE = 99;

    List<Integer> inputProgram = new ArrayList<>();
    List<Integer> output = new ArrayList<>();
    private int marker;

    public AsteroidRainIntcodeProgram(List<Integer> programInstruction) {
        this.inputProgram = programInstruction;
    }

    public List<Integer> getProgram() {
        return program;
    }

    List<Integer> program = new ArrayList<>();

    public void execute(){
        program.addAll(inputProgram);
        marker = 0;
        int par1 ;
        int par2 ;
        int par3 ;
        SingleInstruction instruction = parseInstructionOpcode(program.get(marker));
        while(!TERMINATING_OPCODE.equals(instruction.opcode)){
            switch (instruction.opcode){
                case 1:
                    par1 = getArgument(instruction.par1mode, FIRST_ARG);
                    par2 = getArgument(instruction.par2mode, SECOND_ARG);
                    par3 = program.get(marker+THIRD_ARG);
                    operationSum(par1, par2, par3);
                    marker+=4;
                    break;
                case 2:
                    par1 = getArgument(instruction.par1mode, FIRST_ARG);
                    par2 = getArgument(instruction.par2mode, SECOND_ARG);
                    par3 = program.get(marker+THIRD_ARG);
                    operationMulti(par1, par2, par3);
                    marker+=4;
                    break;
                case 3:
                    par1 = getArgument(instruction.par1mode, FIRST_ARG);
                    program.set(par1,par1);
                    marker+=2;
                    break;
                case 4:
                    par1 = getArgument(instruction.par1mode, FIRST_ARG);
                    output.add(par1);
                    marker+=2;
            }
            System.out.println(output.toString());
            instruction = parseInstructionOpcode(program.get(marker));

        }
    }


    private void operationMulti(int par1, int par2, int par3) {
        program.set(par3, par1*par2);
    }

    private void operationSum(int par1, int par2, int par3) {
        program.set(par3,par1+par2);
    }

    private int getArgument(Integer par1mode, int nthArg) {
        int arg = 0;
        switch (par1mode){
            case POSITION_MODE:
                int position = program.get(marker+nthArg);
                arg = program.get(position);
                if(arg<0){
                    return 100 - arg;
                } else {
                    return arg;
                }
            case IMMEDIATE_MODE:
                arg = program.get(marker+nthArg);
                if(arg<0){
                    return 100 - arg;
                } else {
                    return arg;
                }
                default:
                    throw new IllegalArgumentException();
        }

    }

    private SingleInstruction parseInstructionOpcode(Integer instructionOpcode){
        List<Integer> instructionOpcodeList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        List<Integer> opcodeList = ElvesPasswordCounter.int2List(instructionOpcode);
        int destIndex = instructionOpcodeList.size()-1;
        for (int inputIndex = opcodeList.size()-1; inputIndex>=0; inputIndex--) {
            instructionOpcodeList.set(destIndex, opcodeList.get(inputIndex));
            destIndex--;
        }
        int opcode = 10*instructionOpcodeList.get(3) + instructionOpcodeList.get(4);
        int par1mode = instructionOpcodeList.get(2);
        int par2mode = instructionOpcodeList.get(1);
        int par3mode = instructionOpcodeList.get(0);
        return new SingleInstruction(opcode, par1mode, par2mode, par3mode);
    }

    private class SingleInstruction{
        int opcode;
        int par1mode;
        int par2mode;
        int par3mode;

        SingleInstruction(int opcode, int par1mode, int par2mode, int par3mode){
            this.opcode = opcode;
            this.par1mode = par1mode;
            this.par2mode = par2mode;
            this.par3mode = par3mode;
        }
    }

}
