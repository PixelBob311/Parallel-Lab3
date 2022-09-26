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
        double[] dummyArray = this.array.clone();
        this.phaser.arriveAndAwaitAdvance();
        for (int i = this.start; i <= this.end; i++) {
            this.array[i] = (dummyArray[i - 1] + dummyArray[i + 1]) / 2;
        }
        this.phaser.arriveAndDeregister();
    }
}
