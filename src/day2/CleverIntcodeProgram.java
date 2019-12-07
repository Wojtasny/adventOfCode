package day2;

import day4.ElvesPasswordCounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CleverIntcodeProgram {
    private static final Integer TERMINATING_OPCODE = 99;
    private static final int POSITION_MODE = 0;
    private static final int IMMEDIATE_MODE = 1;


    List<Integer> inputProgram = new ArrayList<>();
    static List<Integer> program = new ArrayList<>();
    static List<Integer> output = new ArrayList<>();
    private static Integer inputValue;
    private static int marker;

    public CleverIntcodeProgram(List<Integer> program, Integer inputValue){
        this.program.clear();
        this.inputProgram.addAll(program);
        this.inputValue = inputValue;
    }

    public CleverIntcodeProgram() {
    }

    public void execute() {
        program.addAll(inputProgram);
        marker = 0;
        OpcodeAndModes instruction = parseInstructionOpcode(program.get(marker));

        while(!TERMINATING_OPCODE.equals(instruction.opcode)){
            List<Integer> argList = getAllArguments(instruction);
            singleInstruction(instruction.opcode, argList, fail());
            instruction = parseInstructionOpcode(program.get(marker));
        }
    }

    public int getResult() {
        return output.get(output.size()-1);
    }

//    private List<Integer> prepareArgs(OpcodeAndModes opcodeAndModes) {
//        List<Integer> integerList = new ArrayList<>();
//        switch (opcodeAndModes.opcode){
//            case 1:
//            case 2:
//            case 5:
//            case 6:
//            case 7:
//            case 8:
//                integerList = getTwoArgs(opcodeAndModes);
//                break;
//            case 3:
//            case 4:
//                integerList = getOneArg(opcodeAndModes);
//                break;
//
//        }
//        return integerList;
//    }

//    private List<Integer> getOneArg(OpcodeAndModes opcodeAndModes) {
//        List<Integer> argList = new ArrayList<>();
//        switch (opcodeAndModes.par1mode){
//            case POSITION_MODE:
//                int position = program.get(++marker);
//                argList.add(program.get(position));
//                break;
//            case IMMEDIATE_MODE:
//                argList.add(program.get(++marker));
//                break;
//            default:
//                throw new IllegalArgumentException();
//        }
//        return argList;
//    }

//    private List<Integer> getTwoArgs(OpcodeAndModes opcodeAndModes) {
//        List<Integer> argList = new ArrayList<>();
//        switch (opcodeAndModes.par1mode){
//            case POSITION_MODE:
//                int position = program.get(++marker);
//                argList.add(program.get(position));
//                break;
//            case IMMEDIATE_MODE:
//                argList.add(program.get(++marker));
//                break;
//            default:
//                throw new IllegalArgumentException();
//        }
//        switch (opcodeAndModes.par2mode) {
//            case POSITION_MODE:
//                int position = program.get(++marker);
//                argList.add(program.get(position));
//                break;
//            case IMMEDIATE_MODE:
//                argList.add(program.get(++marker));
//            default:
//                throw new IllegalArgumentException();
//        }
//        return argList;
//    }

    private OpcodeAndModes parseInstructionOpcode(Integer instructionOpcode) {
        List<Integer> instructionOpcodeList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        List<Integer> opcodeList = ElvesPasswordCounter.int2List(instructionOpcode);
        int destIndex = instructionOpcodeList.size()-1;
        for (int inputIndex = opcodeList.size()-1; inputIndex>=0; inputIndex--) {
            instructionOpcodeList.set(destIndex, opcodeList.get(inputIndex));
            destIndex--;
        }
        int opcode = 10*instructionOpcodeList.get(3) + instructionOpcodeList.get(4);
        int par1mode = instructionOpcodeList.get(2);

        // because in mode 3 we want to get position not value under that address
        if(opcode == 3) {
            par1mode = 1;
        }
        int par2mode = instructionOpcodeList.get(1);
        int par3mode = instructionOpcodeList.get(0);
        return new OpcodeAndModes(opcode, par1mode, par2mode, par3mode);
    }

    private void singleInstruction(Integer instructionMarker, List<Integer> argList, Supplier<RuntimeException> onFailure) {
        marker = Opcodes.get(instructionMarker).map(opcode -> opcode.run(argList)).orElseThrow(onFailure);
    }

    public List<Integer> getProgram() {
        return program;
    }

    enum Opcodes {
        SUM(1, argList -> {
            program.set(argList.get(2) ,argList.get(0) + argList.get(1));
            return ++marker;
        }),
        MULTIPLY(2, argList -> {
            program.set(argList.get(2) ,argList.get(0) * argList.get(1));
            return ++marker;
        }),
        STORE(3, argList -> {
            program.set(argList.get(0), inputValue);
            return marker-1;
        }),
        OUTPUTS(4, argList -> {
            output.add(argList.get(0));
            return marker-1;
        }),
        JUMPIFTRUE(5, argList -> {
            if(argList.get(0)!=0) return argList.get(1);
            return marker;
        }),
        JUMPIFFALSE(6, argList -> {
            if(argList.get(0)==0) return argList.get(1);
            return marker;
        }),
        LESSTHAN(7, argList -> {
            if(argList.get(0) < argList.get(1)) {
                program.set(argList.get(2), 1);
            } else {
                program.set(argList.get(2), 0);
            }
            return ++marker;
        }),
        EQUALS(8, argList -> {
            if(argList.get(0).equals(argList.get(1))) {
                program.set(argList.get(2), 1);
            } else {
                program.set(argList.get(2), 0);
            }
            return ++marker;
        });


        Integer opcodeIndex;
        Opcode opcode;

        Opcodes(int opcodeIndex, Opcode opcode){
            this.opcodeIndex = opcodeIndex;
            this.opcode = opcode;
        }

        public static Optional<Opcode> get(Integer marker){
            return Arrays.stream(Opcodes.values())
                    .filter(i -> i.opcodeIndex.equals(marker))
                    .findFirst()
                    .map(i -> i.opcode);
        }
    }

    @FunctionalInterface
    interface Opcode {
        Integer run(List<Integer> argList);
    }

    private Supplier<RuntimeException> fail(){
        return new Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new IllegalArgumentException("Unknown opcode");
            }
        };
    }
    private class OpcodeAndModes{
        int opcode;
        int par1mode;
        int par2mode;
        int par3mode;

        OpcodeAndModes(int opcode, int par1mode, int par2mode, int par3mode){
            this.opcode = opcode;
            this.par1mode = par1mode;
            this.par2mode = par2mode;
            this.par3mode = par3mode;
        }
    }

    private List<Integer> getAllArguments(OpcodeAndModes opcodeAndModes){
        List<Integer> allArguments = new ArrayList<>();
        try {
            allArguments.add(getArgument(opcodeAndModes.par1mode));
            allArguments.add(getArgument(opcodeAndModes.par2mode));
            allArguments.add(program.get(++marker));
        } catch (IndexOutOfBoundsException e) {
            //we are aware that we not always are able to get all arguments, but we need to remember to update marker
            marker++;
        }
        return allArguments;
    }

    private int getArgument(Integer parMode) throws IndexOutOfBoundsException{
        int arg = 0;
        try {
            switch (parMode){
                case POSITION_MODE:
                    int position = program.get(++marker);
                    arg = program.get(position);
                    return arg;
                case IMMEDIATE_MODE:
                    arg = program.get(++marker);
                    return arg;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return arg;
    }
}
