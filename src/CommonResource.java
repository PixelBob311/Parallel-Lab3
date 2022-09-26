import java.util.Random;

public class CommonResource implements OperationsInterface, Cloneable {
    private double[] array;
    Random rnd = new Random();

    public CommonResource(int size) {
        this.array = new double[size];
        for (int i = 0; i < size; i++) {
            this.array[i] = rnd.nextDouble(-10, 11);
        }
    }

    @Override
    public double[] getArray() {
        return this.array;
    }

    @Override
    public void printArray() {
        for (double elem : this.array) {
            System.out.printf("%.2f ", elem);
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
            CommonResource clone = (CommonResource) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
