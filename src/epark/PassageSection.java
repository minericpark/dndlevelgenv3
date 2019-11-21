package epark;

import dnd.die.D20;
import dnd.die.Percentile;
import dnd.models.Monster;

import java.util.HashMap;

/* Represents a 10 ft section of passageway */

public class PassageSection implements java.io.Serializable {

    /**
     * Represents the description of the passage section.
     */
    private String passageDescription;
    /**
     * Represents the temporary description of the passage section.
     */
    private String tempDescription;
    /**
     * Stores and labels each passage section's potential descriptions.
     */
    private HashMap<Integer, String> passageTable;
    /**
     * Represents monster within passage section (if it exists).
     */
    private Monster passageMonster;
    /**
     * Represents the door of the passage section (if it exists).
     */
    private Door passageDoor;
    /**
     * Represents the boolean on whether monster exists within the passage section or not.
     */
    private boolean monsterExist;
    /**
     * Represents the boolean on whether door exists within the passage section or not.
     */
    private boolean doorExist;
    /**
     * Represents the boolean on whether chamber is ahead of passage section or not.
     */
    private boolean chamberAhead;
    /**
     * Represents the boolean on whether passage section is dead end or not.
     */
    private boolean deadEnd;

    /**
     * Main constructor for passage section without a string import.
     * Initializes all required instances and calls all setup methods.
     */
    public PassageSection() {
        passageTable = new HashMap<Integer, String>();
        this.passageDescription = "";
        this.setUpDescription(" ");
        this.genContents();
        this.updateDescription();
    }

    /**
     * Main constructor for passage section with a string import.
     * Initializes all required instances and calls all setup methods.
     *
     * @param description is imported string of passage section
     */
    public PassageSection(String description) {
        //sets up a specific passage based on the values sent in from
        //modified table 1
        passageTable = new HashMap<Integer, String>();
        this.passageDescription = "";
        this.setUpDescription(description);
        this.genContents();
        this.updateDescription();
    }

    /**
     * Updates description of passage section accordingly.
     */
    private void updateDescription() {

        passageDescription = tempDescription;
        if (monsterExist) {
            this.passageDescription = this.passageDescription.concat(getMonsterDescrip());
        }
        /*if (doorExist) {
            this.passageDescription = this.passageDescription.concat(indentString("Passage Door ID: " + this.passageDoor.getDescription()));
        }*/
    }

    /**
     * Generates contents of passage section dependent on string description of passage.
     */
    private void genContents() {
        /*if (passageDescription.toLowerCase().contains("door")) {
            this.genDoor();
        }*/
        if (passageDescription.toLowerCase().contains("monster")) {
            this.genMonster();
        }
        if (passageDescription.toLowerCase().contains("chamber")) {
            this.chamberAhead = true;
        }
        if (passageDescription.toLowerCase().contains("dead end")) {
            this.deadEnd = true;
        }
    }

    /**
     * Generates new door within passage section.
     */
    private void genNewDoor() {
        this.setDoorExist(true);
        Door door = new Door();
        if (passageDescription.toLowerCase().contains("archway")) {
            door.setArchway(true);
        } else {
            door.setArchway(false);
        }
        this.passageDoor = door;
    }

    /**
     * Generates given door within passage section.
     *
     * @param setDoor door used to generate new door
     */
    private void genDoor(Door setDoor) {
        this.setDoorExist(true);
        if (passageDescription.toLowerCase().contains("archway")) {
            setDoor.setArchway(true);
        } else {
            setDoor.setArchway(false);
        }
        this.passageDoor = setDoor;
    }

    /**
     * Generates monster within passage section.
     */
    private void genMonster() {
        Monster generatedMonster = new Monster();

        this.setMonsterExist(true);
        generatedMonster.setType(rollD100());
        this.passageMonster = generatedMonster;
    }

    /**
     * Sets whether monster exists within passage section or not.
     *
     * @param exist bool representation of whether monster should exists or not
     */
    public void setMonsterExist(boolean exist) {
        this.monsterExist = exist;
    }

    /**
     * Sets whether door exists within passage section or not.
     *
     * @param exist bool representation of whether door should exists or not
     */
    public void setDoorExist(boolean exist) {
        this.doorExist = exist;
    }

    /**
     * Sets whether chamber is ahead of passage section or not.
     *
     * @param ahead bool representation of whether chamber should be ahead of passage section or not
     */
    public void setChamberAhead(boolean ahead) {
        this.chamberAhead = ahead;
    }

    /**
     * Sets whether new door of chamber section.
     *
     * @param newDoor new door to replace the passage section's current door.
     */
    public void setDoor(Door newDoor) {
        this.genDoor(newDoor);
        this.updateDescription();
    }

    /**
     * Sets up passage chance table dependent on whether description is " " or not.
     *
     * @param description string description of passage section, if " " calls a randomized passage generation
     */
    private void setUpDescription(String description) {
        if (!description.equals(" ")) {
            this.setDescription(description);
        } else {
            this.setUpPassageTable();
            this.setDescription(" ");
        }
        this.genContents();

    }

    /**
     * Sets description of passage section by either rolling into table, or importing appropriate string.
     *
     * @param description string description of passage section, if " " rolls for random passage generation
     */
    private void setDescription(String description) {
        D20 die = new D20();
        int roll = 0;
        if (!description.equals(" ")) {
            this.tempDescription = description + "\n";
            this.passageDescription = description + "\n";
        } else {
            roll = die.roll();
            this.tempDescription = this.passageTable.get(roll);
            this.passageDescription = this.passageTable.get(roll);
        }
    }

    /**
     * Sets up randomely generated passage section descriptions tables dependent on roll.
     */
    private void setUpPassageTable() {
        this.passageTable.put(1, "passage goes straight for 10 ft\n");
        this.passageTable.put(2, "passage goes straight for 10 ft\n");
        this.passageTable.put(3, "passage ends in Door to a Chamber\n");
        this.passageTable.put(4, "passage ends in Door to a Chamber\n");
        this.passageTable.put(5, "passage ends in Door to a Chamber\n");
        this.passageTable.put(6, "archway (door) to right (main passage continues straight for 10 ft\n");
        this.passageTable.put(7, "archway (door) to right (main passage continues straight for 10 ft\n");
        this.passageTable.put(8, "archway (door) to left (main passage continues straight for 10 ft\n");
        this.passageTable.put(9, "archway (door) to left (main passage continues straight for 10 ft\n");
        this.passageTable.put(10, "passage turns to left and continues for 10 ft\n");
        this.passageTable.put(11, "passage turns to left and continues for 10 ft\n");
        this.passageTable.put(12, "passage turns to right and continues for 10 ft\n");
        this.passageTable.put(13, "passage turns to right and continues for 10 ft\n");
        this.passageTable.put(14, "passage ends in archway (door) to chamber\n");
        this.passageTable.put(15, "passage ends in archway (door) to chamber\n");
        this.passageTable.put(16, "passage ends in archway (door) to chamber\n");
        this.passageTable.put(17, "Stairs, (passage continues straight for 10 ft)\n");
        this.passageTable.put(18, "Dead End\n");
        this.passageTable.put(19, "Dead End\n");
        this.passageTable.put(20, "Wandering Monster (passage continues straight for 10 ft)\n");
    }

    /**
     * Updates/sets current passage section monster to newMonster.
     *
     * @param newMonster new monster that is to replace current/null monster field within passage section
     */
    public void updateMonster(Monster newMonster) {
        this.passageMonster = newMonster;
        this.monsterExist = true;
        this.updateDescription();
    }

    /**
     * Updates/sets current passage section door to newDoor.
     *
     * @param newDoor new door that is to replace current/null door field within passage section
     */
    public void updateDoor(Door newDoor) {
        this.passageDoor = newDoor;
        this.doorExist = true;
        this.updateDescription();
    }

    /**
     * Returns door of passage section.
     *
     * @return passageDoor door generated in passage section
     */
    public Door getDoor() {
        //returns the door that is in the passage section, if there is one
        return this.passageDoor;
    }

    /**
     * Returns monster of passage section.
     *
     * @return passageMonster monster generated in passage section
     */
    public Monster getMonster() {
        //returns the monster that is in the passage section, if there is one
        return this.passageMonster;
    }

    /**
     * Returns string description of monster in passage section.
     *
     * @return monsterDescrip description of monster within passage section
     */
    private String getMonsterDescrip() {
        String monsterDescrip;

        monsterDescrip = indentString("The monster is/are a " + passageMonster.getDescription() + "\n");
        monsterDescrip = monsterDescrip.concat(indentString("The amount of monsters potentially spawning is: " + passageMonster.getMinNum() + " to " + passageMonster.getMaxNum() + "\n"));

        return monsterDescrip;
    }

    /**
     * Returns string description of passage section.
     *
     * @return passageDescription description of passage section
     */
    public String getDescription() {
        return this.passageDescription;
    }

    /**
     * Returns whether monster exists within passage section or not.
     *
     * @return monsterExist bool representation of whether monster exists or not
     */
    public boolean getMonsterExist() {
        return this.monsterExist;
    }

    /**
     * Returns whether door exists within passage section or not.
     *
     * @return doorExist bool representation of whether door exists or not
     */
    public boolean getDoorExist() {
        return this.doorExist;
    }

    /**
     * Returns whether chamber is ahead of passage section or not.
     *
     * @return chamberAhead bool representation of whether chamber is ahead of passage section or not
     */
    public boolean getChamberAhead() {
        return this.chamberAhead;
    }

    /**
     * Returns whether passage section is a dead end or not.
     *
     * @return deadEnd bool representation of whether passage section is dead end or not
     */
    public boolean getDeadEnd() {
        return this.deadEnd;
    }

    /**
     * Indents selected string.
     *
     * @param string string taken in to indent
     * @return newString indented string
     */
    private String indentString(String string) {
        String newString;
        newString = "  " + string;
        return newString;
    }

    /**
     * Returns a die roll of 1-100.
     *
     * @return D100.roll() randomized number from 1-20
     */
    private int rollD100() {
        Percentile d100 = new Percentile();
        return d100.roll();
    }
}
