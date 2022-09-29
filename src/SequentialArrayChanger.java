public class SequentialArrayChanger extends AbstractArrayChanger {
    public SequentialArrayChanger(CommonResource res, int iters) {
        super(res, iters);
        this.changerName = "Последовательный метод";
    }

    @Override
    public void changeArray() {
        long startTime = System.currentTimeMillis();
        var sourceArray = this.resource.getArray();
        var arrayCopy = this.resource.getCopiedArray();
        for (int currentIter = 0; currentIter < this.iters; currentIter++) {
            arrayCopy = this.resource.getCopiedArray();
            for (int i = 1; i < arrayCopy.length - 1; i++) {
                sourceArray[i] = (arrayCopy[i - 1] + arrayCopy[i + 1]) / 2;
            }
            this.resource.rewriteCopiedArray();
        }
        long endTime = System.currentTimeMillis();
        this.timeElapsed = endTime - startTime;
    }
}
