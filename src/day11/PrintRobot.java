package day11;

import day2.CleverIntcodeProgram;

import java.util.*;

public class PrintRobot extends CleverIntcodeProgram {
    private static final Integer OUTPUT_OPCODE = 4;

    public void setInputValue(long inputValue) {
        this.inputValue = inputValue;
    }

    private long inputValue;

    @Override
    public Long getInputValue() {
        return inputValue;
    }


    public int getPanelPrintedCount() {
        showPosition();
        return panelGrid.size() -1;
    }

    private List<Panel> panelGrid = new ArrayList<>();

    public void setCurrentPanelWhite(){
        currentPanel.setColor((byte) 1);
    }

    private Panel currentPanel = new Panel(0, 0);
    private int currentDirection=1;

    public PrintRobot(List<Long> program) {
        super(program);

        marker = 0;
        relativeBase = 0;
    }

    private void scanCurrentPanel() {
        setInputValue((long) currentPanel.getColor());
    }

    public void paint() {
        program.addAll(inputProgram);
        instruction = parseInstructionOpcode(Math.toIntExact(program.get(marker)));
        while (!TERMINATING_OPCODE.equals(instruction.opcode)) {
            if(!panelGrid.contains(currentPanel)){
                panelGrid.add(currentPanel);
            } else {
                currentPanel=panelGrid.get(panelGrid.indexOf(currentPanel));
            }
//            showPosition();
            scanCurrentPanel();
            execute();
            long blackOrWhite = getResult();
            if (getResult() != 0 && getResult() != 1) {
                throw new IllegalArgumentException();
            }
            if (getResult() == 0) {
                currentPanel.setColor((byte) 0);
                scanCurrentPanel();
            } else {
                currentPanel.setColor((byte) 1);
                scanCurrentPanel();
            }
            execute();
            if (getResult() != 0 && getResult() != 1) {
                throw new IllegalArgumentException();
            }
            Panel nextPanel;
            long turningDirection = getResult();
                if (getResult() == 0) {
                    nextPanel = turnLeft();
                } else {
                    nextPanel = turnRight();
                }

                currentPanel = nextPanel;
        }
        showPosition();
    }

    private void showPosition() {
        int maxX = Collections.max(panelGrid, Comparator.comparing(Panel::getX)).getX();
        int minX = Collections.min(panelGrid, Comparator.comparing(Panel::getX)).getX();
        int minY = Collections.min(panelGrid, Comparator.comparing(Panel::getY)).getY();
        int maxY = Collections.max(panelGrid, Comparator.comparing(Panel::getY)).getY();

        for(int y=maxY; y>=minY;y--){
            for(int x=minX; x<=maxX; x++){
                Panel p = new Panel(x,y);
                if (panelGrid.contains(p)){
                    p = panelGrid.get(panelGrid.indexOf(p));
                    if(p == currentPanel){
                         switch (currentDirection){
                             case 1:
                                 System.out.print("^");
                                 break;
                             case 2:
                                 System.out.print(">");
                                 break;
                             case 3:
                                 System.out.print("v");
                                 break;
                             case 4:
                                 System.out.print("<");
                                 break;
                         }
                         continue;
                    }
                    if(p.getColor()==0){
                        System.out.print(".");
                    } else {
                        System.out.print("\033[0;32m" +"#" + "\033[0m");
                    }
                } else {
                    System.out.print("\033[0;30m"+"."+"\033[0m");

                }
            }
            System.out.println();
        }
        System.out.println( "-----" + panelGrid.size()+ "----");

    }

    private Panel turnRight() {
        int nextDirection = currentDirection + 1;
        if(nextDirection>4){
            nextDirection = 1;
        }
        currentDirection = nextDirection;
        return Directions.get(currentDirection).map(transfer -> transfer.turn(currentPanel.getX(), currentPanel.getY())).orElseThrow(fail());
    }

    private Panel turnLeft() {
        int nextDirection = currentDirection - 1;
        if(nextDirection<1){
            nextDirection=4;
        }
        currentDirection = nextDirection;
        return Directions.get(currentDirection).map(transfer -> transfer.turn(currentPanel.getX(), currentPanel.getY())).orElseThrow(fail());
    }

    @Override
    public void execute() {
        instruction = parseInstructionOpcode(Math.toIntExact(program.get(marker)));

        while (!TERMINATING_OPCODE.equals(instruction.opcode)) {
            List<Long> argList = getAllArguments(instruction);
            singleInstruction(instruction.opcode, argList, fail());

            if(OUTPUT_OPCODE.equals(instruction.opcode)){
                break;
            }
            instruction = parseInstructionOpcode(Math.toIntExact(program.get(marker)));
        }
    }

    enum Directions {
        UP(1, ((x, y) -> new Panel(x, y + 1))),
        RIGHT(2, (x, y) -> new Panel(x + 1, y)),
        DOWN(3, ((x, y) -> new Panel(x, y - 1))),
        LEFT(4, (x, y) -> new Panel(x - 1, y));

        Integer direction;
        Transfer transfer;

        Directions(Integer direction, Transfer transfer) {
            this.direction = direction;
            this.transfer = transfer;
        }

        public static Optional<Transfer> get(Integer nextDirection){
            return Arrays.stream(Directions.values())
                    .filter(i -> i.direction.equals(nextDirection))
                    .findFirst()
                    .map(i -> i.transfer);
        }
    }

    @FunctionalInterface
    interface Transfer {
        Panel turn(int x, int y);
    }

    private static class Panel {
        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        private int x;
        private int y;
        // 0 - black 1 - white
        private byte color = 0;

        Panel(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Panel)) return false;
            Panel that = (Panel) obj;
            return this.x == that.x && this.y == that.y;
        }

        byte getColor() {
            return color;
        }

        void setColor(byte color) {
            this.color = color;
        }
    }
}
