package enums;

/**
 * Time enum which defines Pilates Fitness Class, Spinning Fitness Class, Cardio Fitness Class, their respective
 * class indexes, their respective times, and their respective instructors.
 * @author Hasnain Ali, Carolette Saguil
 */
public enum Time {
    PILATES("Pilates", 0, "9:30", "JENNIFER"),
    SPINNING("Spinning", 1, "14:00", "DENISE"),
    CARDIO("Cardio", 2, "14:00", "KIM");

    private final String CLASS_NAME;
    /**
     * Class Index in the FitnessClass Database in {@code FitnessClass}.
     */
    private final int CLASS_INDEX;
    private final String TIME;
    private final String INSTRUCTOR;

    /**
     * @param className Fitness Class Name
     * @param classIndex Fitness Class Index in FitnessClass Database in {@code FitnessClass}.
     * @param time Fitness Class Time
     * @param instructor Fitness Class Instructor
     */
    private Time(String className, int classIndex, String time, String instructor) {
        this.CLASS_NAME = className;
        this.CLASS_INDEX = classIndex;
        this.TIME = time;
        this.INSTRUCTOR = instructor;
    }

    /**
     * @param index Fitness Class Index to get corresponding Time enum
     * @return The time corresponding Time enum based on {@code index}. May return null if invalid class is given.
     */
    public static Time returnEnumFromIndex(int index) {
        switch (index) {
            case 0:
                return PILATES;
            case 1:
                return SPINNING;
            case 2:
                return CARDIO;
            default:
                return null;
        }
    }

    /**
     * @return Returns Fitness Class time.
     */
    public final String getTime() {
        return this.TIME;
    }

    /**
     * @return Returns Fitness Class name
     */
    public final String getClassName() {
        return this.CLASS_NAME;
    }

    /**
     * @return Returns the integer fitness class index
     */
    public final int getClassIndex() {
        return this.CLASS_INDEX;
    }

    /**
     * @return Returns fitness class instructor name.
     */
    public final String getInstructor() {
        return this.INSTRUCTOR;
    }

    /**
     * @return Returns a String with the state of fitness class Times from Time enum.
     */
    @Override
    public final String toString() {
        return String.format("Class Name: %s, Class Index: %d", this.CLASS_NAME, this.CLASS_INDEX);
    }
}
