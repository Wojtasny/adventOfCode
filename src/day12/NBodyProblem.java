package day12;

public class NBodyProblem {

    private Moon io;
    private Moon europa;
    private Moon ganymede;
    private Moon callisto;


    public void setIo(Moon io) {
        this.io = io;
    }

    public void setEuropa(Moon europa) {
        this.europa = europa;
    }

    public void setGanymede(Moon ganymede) {
        this.ganymede = ganymede;
    }

    public void setCallisto(Moon callisto) {
        this.callisto = callisto;
    }

    public NBodyProblem() {
    }

    class Position {
        int x;
        int y;
        int z;

        Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int hashCode() {
            return this.x+this.y+this.z;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Position)) return false;
            Position that = (Position) obj;
            return this.x==that.x && this.y==that.y && this.z==that.z;
        }
    }

    class Velocity {
        int velX = 0;
        int velY = 0;
        int velZ = 0;

        @Override
        public int hashCode() {
            return velY+velX+velZ;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Velocity)) return false;
            Velocity that = (Velocity) obj;
            return this.velZ==that.velZ && this.velY==that.velY && this.velX==that.velX;
        }
    }

    class Moon {
        Position position;
        Velocity velocity = new Velocity();

        Moon(Position position) {
            this.position = position;
        }

        @Override
        public int hashCode() {
            return this.position.x + this.position.y + this.position.z;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Moon)) return false;
            Moon that = (Moon) obj;
            return this.position.equals(that.position) && this.velocity.equals(that.velocity);
        }
    }

    long stepsToReturnToInitialState(){
        Moon copyOfIO = new Moon(io.position);
        Moon copyOfEurope = new Moon(europa.position);
        Moon copyOfGanymede = new Moon(ganymede.position);
        Moon copyOfCallisto = new Moon(callisto.position);

        long counter = 1;
        applyGravityOnEveryMoon();
        applyVelocityOnEveryMoon();

        while (!(copyOfIO.equals(io) && copyOfEurope.equals(europa) && copyOfCallisto.equals(callisto) && copyOfGanymede.equals(ganymede))) {
            applyGravityOnEveryMoon();
            applyVelocityOnEveryMoon();
            counter++;
            System.out.print(counter+"\r");
        }
        return counter;
    }

    int countEnergyAfterSteps(int stepCount) {
        int totalEnergy = 0;
        for (int i = 1; i <= stepCount; i++) {
            totalEnergy = energyAfterStep();
        }
        return totalEnergy;
    }

    private int energyAfterStep() {
        applyGravityOnEveryMoon();
        applyVelocityOnEveryMoon();

        int kinIo = countKinE(io);
        int kinEurope = countKinE(europa);
        int kinGanymede = countKinE(ganymede);
        int kinCallisto = countKinE(callisto);

        int potIO = countPotE(io);
        int potEurope = countPotE(europa);
        int potGanymede = countPotE(ganymede);
        int potCallisto = countPotE(callisto);

        int eIO = potIO * kinIo;
        int eEurope = potEurope * kinEurope;
        int eGanymede = potGanymede * kinGanymede;
        int eCallisto = potCallisto * kinCallisto;

        return eIO + eEurope + eGanymede + eCallisto;
    }

    private int countKinE(Moon moon) {
        return Math.abs(moon.velocity.velX) + Math.abs(moon.velocity.velY) + Math.abs(moon.velocity.velZ);
    }

    private int countPotE(Moon moon) {
        return Math.abs(moon.position.x) + Math.abs(moon.position.y) + Math.abs(moon.position.z);
    }

    private void applyVelocityOnEveryMoon() {
        applyVelocity(io);
        applyVelocity(europa);
        applyVelocity(callisto);
        applyVelocity(ganymede);
    }

    private void applyVelocity(Moon moon) {
        moon.position.x += moon.velocity.velX;
        moon.position.y += moon.velocity.velY;
        moon.position.z += moon.velocity.velZ;
    }

    private void applyGravityOnEveryMoon() {
        applyGravity(io, europa);
        applyGravity(io, ganymede);
        applyGravity(io, callisto);
        applyGravity(europa, ganymede);
        applyGravity(europa, callisto);
        applyGravity(ganymede, callisto);
    }

    private void applyGravity(Moon moon1, Moon moon2) {
        if (moon1.position.x > moon2.position.x) {
            moon1.velocity.velX--;
            moon2.velocity.velX++;
        } else if (moon1.position.x < moon2.position.x) {
            moon1.velocity.velX++;
            moon2.velocity.velX--;
        }
        if (moon1.position.y > moon2.position.y) {
            moon1.velocity.velY--;
            moon2.velocity.velY++;
        } else if (moon1.position.y < moon2.position.y) {
            moon1.velocity.velY++;
            moon2.velocity.velY--;
        }
        if (moon1.position.z > moon2.position.z) {
            moon1.velocity.velZ--;
            moon2.velocity.velZ++;
        } else if (moon1.position.z < moon2.position.z) {
            moon1.velocity.velZ++;
            moon2.velocity.velZ--;
        }
    }
}
