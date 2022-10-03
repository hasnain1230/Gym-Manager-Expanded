package enums;

public enum Time {
    PILATES("Pilates", 0, "9:30", "JENNIFER"),
    SPINNING("Spinning", 1, "14:00", "DENISE"),
    CARDIO("Cardio", 2, "14:00", "KIM");

    private final String CLASS_NAME;
    private final int CLASS_INDEX;
    private final String TIME;
    private final String INSTRUCTOR;

    private Time(String className, int classIndex, String time, String instructor) {
        this.CLASS_NAME = className;
        this.CLASS_INDEX = classIndex;
        this.TIME = time;
        this.INSTRUCTOR = instructor;
    }

    public static Time returnEnumFromString(String fitnessClass) {
        if (fitnessClass.equalsIgnoreCase("pilates")) {
            return Time.PILATES;
        } else if (fitnessClass.equalsIgnoreCase("spinning")) {
            return Time.SPINNING;
        } else if (fitnessClass.equalsIgnoreCase("cardio")) {
            return Time.CARDIO;
        } else {
            return null;
        }
    }

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

    public final String getTime() {
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
