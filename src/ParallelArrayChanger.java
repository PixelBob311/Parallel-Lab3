import java.util.concurrent.Phaser;

public class ParallelArrayChanger extends AbstractArrayChanger {
    Phaser phaser;
    int threadsCount;

    public ParallelArrayChanger(CommonResource res, int iters, int threadsCount, Phaser phaser) {
        super(res, iters);
        this.changerName = "Параллельный метод";
        this.phaser = phaser;
        this.threadsCount = threadsCount;
    }

    @Override
    public void changeArray() {
        MyThread[] threads = new MyThread[this.threadsCount];
        //создаем потоки
        int start = 1, end;
        for (int i = 0; i < threads.length; i++) {
            end = (this.resource.getArray().length - 2) * (i + 1) / this.threadsCount;
            threads[i] = new MyThread(start, end, this.iters, phaser, this.resource);
            start = end + 1;
        }
        phaser.arriveAndAwaitAdvance();


        long startTime = System.currentTimeMillis();
        for (int currentIter = 0; currentIter < this.iters; currentIter++) {
//            System.out.println("currentIter = " + currentIter);
            ++MyThread.currentIter;
            //сделать копию результирующего массива и записать на место исходного
//            double [] clonedArray = this.resource.getArrayDeepClone();
            phaser.arriveAndAwaitAdvance();
            this.resource.rewriteCopiedArray();
            phaser.arriveAndAwaitAdvance();
        }
        long endTime = System.currentTimeMillis();
        this.timeElapsed = endTime - startTime;
    }
}
