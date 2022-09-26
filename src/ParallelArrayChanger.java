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
        long startTime = System.currentTimeMillis();
        for (int currentIter = 0; currentIter < this.iters; currentIter++) {
            if (currentIter > 0) {
                phaser.arriveAndAwaitAdvance();
            }
            int start = 1, end;
            for (int i = 0; i < threads.length; i++) {
                end = (this.resource.getArray().length - 2) * (i + 1) / this.threadsCount;
                threads[i] = new MyThread(start, end, phaser, this.resource.getArray());
                threads[i].start();
                start = end + 1;
            }
            phaser.arriveAndAwaitAdvance();
        }
        long endTime = System.currentTimeMillis();
        this.timeElapsed = endTime - startTime;
    }
}
