package day2;

import day4.ElvesPasswordCounter;

import java.util.*;
import java.util.function.Supplier;

public class CleverIntcodeProgram {
    protected static final Integer TERMINATING_OPCODE = 99;
    private static final int POSITION_MODE = 0;
    private static final int IMMEDIATE_MODE = 1;
    private static final int RELATIVE_MODE = 2;


    protected List<Long> inputProgram = new ArrayList<>();
    protected List<Long> program = new ArrayList<>();
    private OpcodeAndModes instruction;

    public List<Long> getOutputList() {
        return output;
    }

    protected List<Long> output = new ArrayList<>();

    public void offerInputValue(Long inputValue) {
        this.inputQueue.offer(inputValue);
    }

    protected Deque<Long> inputQueue = new ArrayDeque<>();
    protected int marker;
    protected int relativeBase;

    public CleverIntcodeProgram(List<Long> program){
        this.inputProgram.addAll(program);
    }

    CleverIntcodeProgram() {
    }

    public void execute() {
        program.addAll(inputProgram);
        marker = 0;
        relativeBase = 0;
        instruction = parseInstructionOpcode(Math.toIntExact(program.get(marker)));

        while(!TERMINATING_OPCODE.equals(instruction.opcode)){
            List<Long> argList = getAllArguments(instruction);
            singleInstruction(instruction.opcode, argList, fail());
            instruction = parseInstructionOpcode(Math.toIntExact(program.get(marker)));
        }
    }

    public long getResult() {
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
//        if(opcode == 3) {
//            par1mode = 1;
//        }
        int par2mode = instructionOpcodeList.get(1);
        int par3mode = instructionOpcodeList.get(0);
        return new OpcodeAndModes(opcode, par1mode, par2mode, par3mode);
    }

    protected void singleInstruction(Integer instructionOpcode, List<Long> argList, Supplier<RuntimeException> onFailure) {
        // 1st output parameter is what to do with marker
        // 2nd index where to put counted value
        // counted value
        List<Long> outputParameters = Opcodes.get(instructionOpcode).map(opcode -> opcode.run(argList)).orElseThrow(onFailure);
        if(outputParameters.size() == 1) {
            if (outputParameters.get(0) != null) {
                marker = Math.toIntExact(outputParameters.get(0));
            }
        } else {
            marker += outputParameters.get(0);
        }
        if (outputParameters.size() == 3) {
            int destinationIndex = Math.toIntExact(outputParameters.get(1));
            if(destinationIndex>program.size()-1){
                for(int i=program.size(); i <= destinationIndex; i++){
                    program.add(0L);
                }
            }
            program.set(destinationIndex, outputParameters.get(2));
        }
        if (outputParameters.size() == 2){
            if(instructionOpcode == 3){
                int destinationIndex = Math.toIntExact(outputParameters.get(1));
                if(destinationIndex>program.size()-1){
                    for(int i=program.size(); i <= destinationIndex; i++){
                        program.add(0L);
                    }
                }
                program.set(destinationIndex, inputQueue.poll());
            } else if(instructionOpcode == 4){
                output.add(outputParameters.get(1));
            } else if(instructionOpcode == 9){
                relativeBase += outputParameters.get(1);
            }
        }
    }

    List<Long> getProgram() {
        return program;
    }

    enum Opcodes {
        SUM(1, argList -> {
            List<Long> toReturn = new ArrayList<>();
            toReturn.add(1L);
            toReturn.add(argList.get(2));
            toReturn.add(argList.get(0) + argList.get(1));
            return toReturn;
        }),
        MULTIPLY(2, argList -> {
            List<Long> toReturn = new ArrayList<>();
            toReturn.add(1L);
            toReturn.add(argList.get(2));
            toReturn.add(argList.get(0) * argList.get(1));
            return toReturn;
        }),
        STORE(3, argList -> {
            List<Long> toReturn = new ArrayList<>();
            toReturn.add(-1L);
            toReturn.add(argList.get(0));
            return toReturn;
        }),
        OUTPUTS(4, argList -> {
            List<Long> toReturn = new ArrayList<>();
            toReturn.add(-1L);
            toReturn.add(argList.get(0));
            return toReturn;
        }),
        JUMPIFTRUE(5, argList -> {
            List<Long> toReturn = new ArrayList<>();
            if(argList.get(0)!=0) {
                toReturn.add(argList.get(1));
            } else {
                toReturn.add(null);
            }
            return toReturn;
        }),
        JUMPIFFALSE(6, argList -> {
            List<Long> toReturn = new ArrayList<>();
            if(argList.get(0)==0) {
                toReturn.add(argList.get(1));
            } else {
                toReturn.add(null);
            }
            return toReturn;
        }),
        LESSTHAN(7, argList -> {
            List<Long> toReturn = new ArrayList<>();
            toReturn.add(1L);
            toReturn.add(argList.get(2));
            if(argList.get(0) < argList.get(1)) {
                toReturn.add(1L);
            } else {
                toReturn.add(0L);
            }
            return toReturn;
        }),
        EQUALS(8, argList -> {
            List<Long> toReturn = new ArrayList<>();
            toReturn.add(1L);
            toReturn.add(argList.get(2));
            if(argList.get(0).equals(argList.get(1))) {
                toReturn.add(1L);
            } else {
                toReturn.add(0L);
            }
            return toReturn;
        }),
        ADJUST_RELATIVE_BASE_OFFSET(9, argList -> {
            List<Long> toReturn = new ArrayList<>();
            toReturn.add(-1L);
            toReturn.add(argList.get(0));
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
        List<Long> run(List<Long> argList);
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

    protected List<Long> getAllArguments(OpcodeAndModes opcodeAndModes){
        List<Long> allArguments = new ArrayList<>();
        try {
            allArguments.add(getArgument(opcodeAndModes.par1mode));
        } catch (IndexOutOfBoundsException e) {
            allArguments.add(0L);
            //we are aware that we not always are able to get all arguments, but we need to remember to update marker
//            marker++;
        }
        try {
            allArguments.add(getArgument(opcodeAndModes.par2mode));
        } catch (IndexOutOfBoundsException e) {
            allArguments.add(0L);
            //we are aware that we not always are able to get all arguments, but we need to remember to update marker
//            marker++;
        }
        try {
            allArguments.add(program.get(++marker));
        } catch (IndexOutOfBoundsException e) {
            allArguments.add(0L);
            //we are aware that we not always are able to get all arguments, but we need to remember to update marker
//            marker++;
        }
        return allArguments;
    }

    private long getArgument(Integer parMode) throws IndexOutOfBoundsException{
        long arg = 0;
        int position;
        try {
            switch (parMode){
                case POSITION_MODE:
                    if(instruction.opcode==3){
                        return program.get(++marker);
                    }
                    position = Math.toIntExact(program.get(++marker));
                    assert position>=0;
                    arg = program.get(position);
                    return arg;
                case IMMEDIATE_MODE:
                    arg = program.get(++marker);
                    return arg;
                case RELATIVE_MODE:
//                    if(instruction.opcode==3){
//                        return (program.get(++marker) + relativeBase) ;
//                    }
                    position = Math.toIntExact(program.get(++marker)) + relativeBase;
                    assert position>=0;
//                    if(instruction.opcode ==3){
//                        return position;
//                    }
                    arg = program.get(position);
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
