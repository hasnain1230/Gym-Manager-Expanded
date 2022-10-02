package enums;

public enum Time {
    MORNING("9:30"),
    AFTERNOON("14:00");

    private final String TIME;

    private Time(String time) {
        this.TIME = time;
    }

    public final String getTime() {
        return this.TIME;
    }
}
