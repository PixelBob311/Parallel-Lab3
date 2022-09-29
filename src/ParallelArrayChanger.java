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
//    int start = 1, end;
//    end = (this.resource.getArray().length - 2) * (i + 1) / this.threadsCount;
//    threads[i] = new MyThread(start, end, this.iters, phaser, this.resource);
//    start = end + 1;

    @Override
    public void changeArray() {
        this.initThreads();
        //в этот момент созданные потоки ожидают поток main, чтобы он закончил создание потоков.
        System.out.println("main заканчивает регистрацию потоков");
        this.phaser.arriveAndAwaitAdvance();
        for(int currentIter = 0; currentIter < this.iters; currentIter++){
//            System.out.println("Отработала фаза № " + this.phaser.getPhase() + " ");

            //подождали, пока потоки завершат обработку
            phaser.arriveAndAwaitAdvance();
            //крутанули счетчик MyTrhead
            MyThread.currentIter++;
//            System.out.println("Iter № " + MyThread.currentIter++);

            //подождали все потоки (они тоже должны встать на ожидание)
            phaser.arriveAndAwaitAdvance();
            //переписали копию массива и снова ждем
            this.resource.rewriteCopiedArray();

            phaser.arriveAndAwaitAdvance();
        }
        //здесь не надо делать дерегистер. Это поток main. Он дерегистрится в фале main.
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
            /*DEBUG INFO*/
//            System.out.println("start = " + startIndex + " end = "
//                    + endIndex + "; Обработает " + (endIndex - startIndex + 1) + " потоков");
            /*DEBUG INFO*/
            startIndex = endIndex + 1;
        }
    }
}
