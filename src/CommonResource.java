public class CommonResource implements OperationsInterface {
    private double[] array;

    public CommonResource(int size) {
        this.array = new double[size];
    }

    @Override
    public double[] getArray() {
        return this.array;
    }

    @Override
    public void printArray() {
        for (double elem : this.array) {
            System.out.print(elem + " ");
        }
    }
}
