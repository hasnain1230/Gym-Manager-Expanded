package enums;

/**
 * Time enum which defines Pilates Fitness Class, Spinning Fitness Class, Cardio Fitness Class, their respective
 * class indexes, their respective times, and their respective instructors.
 * @author Hasnain Ali, Carolette Saguil
 */
public enum Time {
    /**
     * "9:30"
     */
    MORNING("9:30"),
    /**
     * "14:00"
     */
    AFTERNOON("14:00"),
    /**
     * "18:30"
     */
    EVENING("18:30");


    private final String TIME;

    /**
     * @param time Fitness Class Time
     */
    private Time(String time) {
        this.TIME = time;
    }

    public static Time returnTimeEnumFromString(String time) {
        if (time.equalsIgnoreCase("morning")) {
            return Time.MORNING;
        } else if (time.equalsIgnoreCase("afternoon")) {
            return Time.AFTERNOON;
        } else if (time.equalsIgnoreCase("evening")) {
            return Time.EVENING;
        } else {
            throw new IllegalArgumentException("Time: " + time + "not valid.");
        }
    }

    /**
     * @return Returns Fitness Class time.
     */
    public final String getTime() {
        return this.TIME;
    }

    @Override
    public final String toString() {
        return String.format("Time: %s", this.TIME);
    }
}
