import com.sun.tools.javac.Main;

import java.util.concurrent.Phaser;

public class ParallelArrayChanger extends AbstractArrayChanger {
    Phaser phaser;
    MyThread[] threads;

    public ParallelArrayChanger(CommonResource res, int iters, int threadsCount, Phaser phaser) {
        super(res, iters);
        this.changerName = "Параллельный метод";
        this.phaser = phaser;
        this.threads = new MyThread[threadsCount];
    }

    @Override
    public void changeArray() {
        this.initThreads();
        long startTime = System.currentTimeMillis();
        //в этот момент созданные потоки ожидают поток main, чтобы он закончил создание потоков.
        System.out.println("main заканчивает регистрацию потоков");
        this.phaser.arriveAndAwaitAdvance();
        for(int currentIter = 0; currentIter < this.iters; currentIter++){
            //подождали, пока потоки завершат обработку
            phaser.arriveAndAwaitAdvance();
            //крутанули счетчик MyTrhead
            MyThread.currentIter++;
            //подождали все потоки (они тоже должны встать на ожидание)
            phaser.arriveAndAwaitAdvance();
            //переписали копию массива и снова ждем
            this.resource.rewriteCopiedArray();
            phaser.arriveAndAwaitAdvance();
        }
        long endTime = System.currentTimeMillis();
        this.timeElapsed = endTime - startTime;
    }

    private void initThreads() {
        int startIndex = 1, endIndex = 1;
        int elementsPerThread = (int) (this.getResource().getArray().length - 2) / this.threads.length;
        for (int i = 0; i < this.threads.length; i++) {
            endIndex = elementsPerThread * (i + 1);
            if (i == this.threads.length - 1) {
                endIndex = this.getResource().getArray().length - 2;
            }
            this.threads[i] = new MyThread(startIndex, endIndex, this.iters, this.phaser, this.getResource());
            startIndex = endIndex + 1;
        }
    }
}
