import java.util.concurrent.Phaser;

public class Main {
    public static void main(String[] args) {
        final int ITERS = 1;
        final int THREAD_NUM = 2;
        double[] array = {0, 1, 4, 3, 6, 5, 1, 7, 15, 9, 20, 11};
        MyThread[] threads = new MyThread[THREAD_NUM];
        //создаем фазер и регистрируем поток main
        Phaser phaser = new Phaser(1);

        for (int currentIter = 0; currentIter < ITERS; currentIter++) {
            if (currentIter > 0) {
                phaser.arriveAndAwaitAdvance();
            }
            int start = 1, end;
            for (int i = 0; i < threads.length; i++) {
                end = (array.length - 2) * (i + 1) / 2;
                threads[i] = new MyThread(start, end, phaser, array);
                threads[i].start();
                start = end + 1;
            }
            phaser.arriveAndAwaitAdvance();
        }
        for (double elem : array) {
            System.out.print(elem + " ");
        }
        phaser.arriveAndDeregister();
    }
}