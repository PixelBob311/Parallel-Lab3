public class SequentialArrayChanger extends AbstractArrayChanger {
    public SequentialArrayChanger(CommonResource res, int iters) {
        super(res, iters);
        this.changerName = "Последовательный метод";
    }

    @Override
    public void changeArray() {
        long startTime = System.currentTimeMillis();
        for (int currentIter = 0; currentIter < this.iters; currentIter++) {
            var sourceArray = this.resource.getArray();
            double[] arrayCopy = sourceArray.clone();
            for (int i = 1; i < arrayCopy.length - 1; i++) {
                arrayCopy[i] = (sourceArray[i - 1] + sourceArray[i + 1]) / 2;
            }
            this.resource.setManually(arrayCopy);
        }
        long endTime = System.currentTimeMillis();
        this.timeElapsed = endTime - startTime;
    }
}
