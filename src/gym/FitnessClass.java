package gym;

import enums.Time;

public class FitnessClass {

    private String className;
    private String instructorName;
    private Time time;

    public FitnessClass(String className, String instructorName, Time time){
        this.className = className;
        this.instructorName = instructorName;
        this.time = time;
    }

    public String getClassName() {
        return className;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public Time getTime() {
        return time;
    }

}


