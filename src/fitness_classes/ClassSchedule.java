package fitness_classes;

public class ClassSchedule {
    private FitnessClass[] classes;
    private int numClasses;

    public ClassSchedule(FitnessClass[] classes, int numClasses) {
        this.classes = new FitnessClass[numClasses];
        this.numClasses = numClasses;
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
}
