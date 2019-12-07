package day6;

import java.util.*;

class OrbitMap {
    private List<String> orbitMap;
    private List<SpaceObject> spaceObjectsList = new ArrayList<>();
    private List<SpaceObject> orbitsToComFromYOU = new ArrayList<>();
    private List<SpaceObject> orbitsToComFromSANTA = new ArrayList<>();
    private Integer countFromYou;
    private Integer countFromSanta;

    OrbitMap(List<String> orbitMap){
        this.orbitMap = orbitMap;
    }
    
    void parseMap(){
        for (String objectOrbitObject :
                orbitMap) {
            // First object should be the one that the second orbits
            List<String> objectOrbitingObjectList = Arrays.asList(objectOrbitObject.split("\\)"));
            spaceObjectsList.add(new SpaceObject(objectOrbitingObjectList.get(0), objectOrbitingObjectList.get(1)));
        }
    }

    int countFromYOUtoSAN(){
        SpaceObject youSpaceObject = getSpaceObjectOfOrbiting("YOU");
        SpaceObject sanSpaceObject = getSpaceObjectOfOrbiting("SAN");

        if(youSpaceObject==null || sanSpaceObject==null){
            throw new IllegalArgumentException();
        }
        travelAndCount(youSpaceObject, sanSpaceObject);
        int distanceToIntersection =  findDistanceToIntersection();

        return countFromYou - distanceToIntersection + countFromSanta -distanceToIntersection -2;
    }

    private int findDistanceToIntersection() {
        List<SpaceObject> possibleSpaceObjects;
        if(orbitsToComFromYOU.size()<orbitsToComFromSANTA.size()){
            possibleSpaceObjects = new ArrayList<>(orbitsToComFromYOU);
            possibleSpaceObjects.retainAll(orbitsToComFromSANTA);
        } else {
            possibleSpaceObjects = new ArrayList<>(orbitsToComFromSANTA);
            possibleSpaceObjects.retainAll(orbitsToComFromYOU);
        }
        return possibleSpaceObjects.stream().mapToInt(this::countOrbitsOfObject).max().orElseThrow(NoSuchElementException::new);
    }

    private SpaceObject getSpaceObjectOfOrbiting(String spaceObjectOrbiting) {
        Optional<SpaceObject> spaceObj = spaceObjectsList.stream().filter(spaceObject -> spaceObjectOrbiting.equals(spaceObject.name)).findFirst();
        return spaceObj.orElse(null);
    }

    private void travelAndCount(SpaceObject youSpaceObject, SpaceObject sanSpaceObject) {
        countFromYou = countOrbitsOfObjectAndSave(youSpaceObject, orbitsToComFromYOU);
        countFromSanta = countOrbitsOfObjectAndSave(sanSpaceObject, orbitsToComFromSANTA);
    }

    int countOrbits() {
        int count = 0;
        for(SpaceObject spaceObject: spaceObjectsList){
            count = count + countOrbitsOfObject(spaceObject);
        }
        return count;
    }

    private int countOrbitsOfObjectAndSave(SpaceObject spaceObject, List<SpaceObject> journeyList){
        journeyList.add(spaceObject);
        if("COM".equals(spaceObject.nameOfOrbiting)){
            return 1;
        } else {
            SpaceObject spaceObjectThatTOrbit = findObjectThatIOrbit(spaceObject);
            return 1+countOrbitsOfObjectAndSave(spaceObjectThatTOrbit, journeyList);
        }
    }

    private int countOrbitsOfObject(SpaceObject spaceObject){
        if("COM".equals(spaceObject.nameOfOrbiting)){
            return 1;
        } else {
            SpaceObject spaceObjectThatTOrbit = findObjectThatIOrbit(spaceObject);
            return 1+countOrbitsOfObject(spaceObjectThatTOrbit);
        }
    }

    private SpaceObject findObjectThatIOrbit(SpaceObject spaceObject){
        Optional<SpaceObject> spaceObjectILookFor = spaceObjectsList.stream().filter((SpaceObject so) -> so.name.equals(spaceObject.nameOfOrbiting)).findFirst();
        if(spaceObjectILookFor.isPresent()){
            return spaceObjectILookFor.get();
        }
        throw new IllegalArgumentException();
    }

    protected static class SpaceObject{
        String nameOfOrbiting;
        String name;

        SpaceObject(String nameOfOrbiting, String name){
            this.name = name;
            this.nameOfOrbiting = nameOfOrbiting;
        }
    }
}
