package enums;

public enum FitnessClassHelperEnum {
    PILATES("Pilates", 0, Time.MORNING, "JENNIFER"),
    SPINNING("Spinning", 1, Time.AFTERNOON, "DENISE"),
    CARDIO("Cardio", 2, Time.AFTERNOON, "KIM");

    private final String CLASS_NAME;
    private final int CLASS_INDEX;
    private final Time TIME;
    private final String INSTRUCTOR;

    private FitnessClassHelperEnum(String className, int classIndex, Time time, String instructor) {
        this.CLASS_NAME = className;
        this.CLASS_INDEX = classIndex;
        this.TIME = time;
        this.INSTRUCTOR = instructor;
    }

    public static FitnessClassHelperEnum returnEnumFromString(String fitnessClass) {
        if (fitnessClass.equalsIgnoreCase("pilates")) {
            return FitnessClassHelperEnum.PILATES;
        } else if (fitnessClass.equalsIgnoreCase("spinning")) {
            return FitnessClassHelperEnum.SPINNING;
        } else if (fitnessClass.equalsIgnoreCase("cardio")) {
            return FitnessClassHelperEnum.CARDIO;
        } else {
            return null;
        }
    }

    public final Time getTime() {
        return this.TIME;
    }

    public final String getClassName() {
        return this.CLASS_NAME;
    }

    public final int getClassIndex() {
        return this.CLASS_INDEX;
    }

    public final String getInstructor() {
        return this.INSTRUCTOR;
    }

    @Override
    public final String toString() {
        return String.format("Class Name: %s, Class Index: %d", this.CLASS_NAME, this.CLASS_INDEX);
    }
}
