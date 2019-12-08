package day2;

import day4.ElvesPasswordCounter;

import java.util.*;
import java.util.function.Supplier;

public class CleverIntcodeProgram {
    protected static final Integer TERMINATING_OPCODE = 99;
    private static final int POSITION_MODE = 0;
    private static final int IMMEDIATE_MODE = 1;


    protected List<Integer> inputProgram = new ArrayList<>();
    protected List<Integer> program = new ArrayList<>();
    protected List<Integer> output = new ArrayList<>();

    public void offerInputValue(Integer inputValue) {
        this.inputQueue.offer(inputValue);
    }

    protected Deque<Integer> inputQueue = new ArrayDeque<>();
    protected int marker;

    public CleverIntcodeProgram(List<Integer> program){
        this.inputProgram.addAll(program);
    }

    CleverIntcodeProgram() {
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


    protected OpcodeAndModes parseInstructionOpcode(Integer instructionOpcode) {
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

    protected void singleInstruction(Integer instructionOpcode, List<Integer> argList, Supplier<RuntimeException> onFailure) {
        // 1st output parameter is what to do with marker
        // 2nd index where to put counted value
        // counted value
        List<Integer> outputParameters = Opcodes.get(instructionOpcode).map(opcode -> opcode.run(argList)).orElseThrow(onFailure);
        if(outputParameters.size() == 1) {
            if (outputParameters.get(0) != null) {
                marker = outputParameters.get(0);
            }
        } else {
            marker += outputParameters.get(0);
        }
        if (outputParameters.size() == 3) {
            program.set(outputParameters.get(1), outputParameters.get(2));
        }
        if (outputParameters.size() == 2){
            if(instructionOpcode == 3){
                program.set(outputParameters.get(1), inputQueue.poll());
            } else if(instructionOpcode == 4){
                output.add(outputParameters.get(1));
            }
        }
    }

    List<Integer> getProgram() {
        return program;
    }

    enum Opcodes {
        SUM(1, argList -> {
            List<Integer> toReturn = new ArrayList<>();
            toReturn.add(1);
            toReturn.add(argList.get(2));
            toReturn.add(argList.get(0) + argList.get(1));
            return toReturn;
        }),
        MULTIPLY(2, argList -> {
            List<Integer> toReturn = new ArrayList<>();
            toReturn.add(1);
            toReturn.add(argList.get(2));
            toReturn.add(argList.get(0) * argList.get(1));
            return toReturn;
        }),
        STORE(3, argList -> {
            List<Integer> toReturn = new ArrayList<>();
            toReturn.add(-1);
            toReturn.add(argList.get(0));
            return toReturn;
        }),
        OUTPUTS(4, argList -> {
            List<Integer> toReturn = new ArrayList<>();
            toReturn.add(-1);
            toReturn.add(argList.get(0));
            return toReturn;
        }),
        JUMPIFTRUE(5, argList -> {
            List<Integer> toReturn = new ArrayList<>();
            if(argList.get(0)!=0) {
                toReturn.add(argList.get(1));
            } else {
                toReturn.add(null);
            }
            return toReturn;
        }),
        JUMPIFFALSE(6, argList -> {
            List<Integer> toReturn = new ArrayList<>();
            if(argList.get(0)==0) {
                toReturn.add(argList.get(1));
            } else {
                toReturn.add(null);
            }
            return toReturn;
        }),
        LESSTHAN(7, argList -> {
            List<Integer> toReturn = new ArrayList<>();
            toReturn.add(1);
            toReturn.add(argList.get(2));
            if(argList.get(0) < argList.get(1)) {
                toReturn.add(1);
            } else {
                toReturn.add(0);
            }
            return toReturn;
        }),
        EQUALS(8, argList -> {
            List<Integer> toReturn = new ArrayList<>();
            toReturn.add(1);
            toReturn.add(argList.get(2));
            if(argList.get(0).equals(argList.get(1))) {
                toReturn.add(1);
            } else {
                toReturn.add(0);
            }
            return toReturn;
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
        List<Integer> run(List<Integer> argList);
    }

    protected Supplier<RuntimeException> fail(){
        return new Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new IllegalArgumentException("Unknown opcode");
            }
        };
    }
    protected class OpcodeAndModes{
        public int opcode;
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

    protected List<Integer> getAllArguments(OpcodeAndModes opcodeAndModes){
        List<Integer> allArguments = new ArrayList<>();
        try {
            allArguments.add(getArgument(opcodeAndModes.par1mode));
        } catch (IndexOutOfBoundsException e) {
            //we are aware that we not always are able to get all arguments, but we need to remember to update marker
//            marker++;
        }
        try {
            allArguments.add(getArgument(opcodeAndModes.par2mode));
        } catch (IndexOutOfBoundsException e) {
            //we are aware that we not always are able to get all arguments, but we need to remember to update marker
//            marker++;
        }
        try {
            allArguments.add(program.get(++marker));
        } catch (IndexOutOfBoundsException e) {
            //we are aware that we not always are able to get all arguments, but we need to remember to update marker
//            marker++;
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
