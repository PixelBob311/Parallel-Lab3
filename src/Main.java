import java.util.concurrent.Phaser;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int ITERS = 111;
        final int THREAD_NUM = 5;
        CommonResource array = new CommonResource(10000002);

        SequentialArrayChanger seqChanger = new SequentialArrayChanger(array.clone(), ITERS);
        seqChanger.changeArray();

        //создаем фазер и регистрируем поток main
        Phaser phaser = new Phaser(1);

        ParallelArrayChanger parChanger = new ParallelArrayChanger(array.clone(), ITERS, THREAD_NUM, phaser);
        parChanger.changeArray();
        phaser.arriveAndDeregister();

        System.out.println(Arrays.equals(seqChanger.getResource().getArray(), parChanger.getResource().getArray()));
        System.out.println(seqChanger.getTimeElapsed());
        System.out.println(parChanger.getTimeElapsed());

    }
}