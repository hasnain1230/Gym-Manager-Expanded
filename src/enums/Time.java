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
     * "Spinning", 1, "14:00", "DENISE"
     */
    AFTERNOON("14:00"),
    /**
     * "Cardio", 2, "14:00", "KIM"
     */
    EVENING( "18:30");


    private final String TIME;

    /**
     * @param time Fitness Class Time
     */
    private Time(String time) {
        this.TIME = time;
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
