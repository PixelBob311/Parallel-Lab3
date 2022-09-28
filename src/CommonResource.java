import java.util.Random;

public class CommonResource implements OperationsInterface, Cloneable {
    private double[] array;

    private double[] copiedArray;

    Random rnd = new Random();

    public CommonResource(int size) {
        this.array = new double[size];
        for (int i = 0; i < size; i++) {
            this.array[i] = rnd.nextDouble(-10, 11);
        }
        this.copiedArray = this.array.clone();
    }

    public CommonResource(double[] array) {
        this.array = array;
        this.copiedArray = array.clone();
    }

    @Override
    public double[] getArray() {
        return this.array;
    }

    public void rewriteCopiedArray() {
        this.copiedArray = this.array.clone();
    }

    public double[] getCopiedArray() {
        return this.copiedArray;
    }

    @Override
    public void printArray() {
        for (double elem : this.array) {
//            System.out.printf("%.2f ", elem);
            System.out.print("    " + elem);
        }
        System.out.println();
    }

    @Override
    public void setManually(double[] array) {
        this.array = array;
    }

    @Override
    public CommonResource clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (CommonResource) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
