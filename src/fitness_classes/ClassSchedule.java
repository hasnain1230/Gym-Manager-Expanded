package fitness_classes;

import constants.Constants;
import enums.Time;
import member.Member;

public class ClassSchedule {
    private FitnessClass[] classes;
    private int numClasses;

    public ClassSchedule() {
        this.classes = new FitnessClass[Constants.ARRAY_DEFAULT_SIZE];
        this.numClasses = 0;
    }

    public FitnessClass[] getAllClasses() {
        return this.classes;
    }

    public FitnessClass getSpecificClass(int classIndex) {
        return this.classes[classIndex];
    }

    public int getNumClasses() {
        return this.numClasses;
    }

    public boolean addClass(FitnessClass fitnessClass) {
        for (int x = 0; x < numClasses; x++) {
            if (this.classes[x].equals(fitnessClass)) {
                return false;
            }
        }

        if (this.classes.length == numClasses) {
            this.grow();
        }


        this.classes[numClasses] = fitnessClass;
        numClasses++;
        return true;
    }

    private void grow() {
        FitnessClass[] fitnessClasses = new FitnessClass[this.numClasses + Constants.ARRAY_INCREMENT_SIZE];
        for (int x = 0; x < this.numClasses; x++) {
            fitnessClasses[x] = this.classes[x];
        }

        this.classes = fitnessClasses;
    }

}
