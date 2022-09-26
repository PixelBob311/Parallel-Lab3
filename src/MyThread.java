import java.util.concurrent.Phaser;

public class MyThread extends Thread {
    Phaser phaser;
    int start = 0, end = 0;
    double[] array;

    public MyThread(int start, int end, Phaser phaser, double [] array) {
        this.start = start;
        this.end = end;
        this.phaser = phaser;
        this.array = array;
        this.phaser.register();
    }

    @Override
    public void run() {
        //потоки обязательно должны ждать перед началом работы, пока запустятся все остальные потоки
        double[] dummyArray = this.array.clone();
        this.phaser.arriveAndAwaitAdvance();
        for (int i = this.start; i <= this.end; i++) {
//            this.array[i] = (this.array[i - 1] * this.array[i + 1]) / 2;
            this.array[i] = (dummyArray[i - 1] + dummyArray[i + 1]) / 2;
//            this.array[i] = this.array[i] + 1;
        }
//        System.out.println("Thread " + this.getName() + " " + phaser.getPhase());
//        this.phaser.arriveAndAwaitAdvance();
        this.phaser.arriveAndDeregister();
    }
}
