import java.util.concurrent.Phaser;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

//        final int ITERS = 55;
//        final int THREAD_NUM = 4;
//
//        CommonResource array1 = new CommonResource(15555000);
//        CommonResource array2 = array1.clone();
//
//        SequentialArrayChanger seqChanger = new SequentialArrayChanger(array1, ITERS);
//        seqChanger.changeArray();
//
//        //создаем фазер и регистрируем поток main
//        Phaser phaser = new Phaser(1);
//
//        ParallelArrayChanger parChanger = new ParallelArrayChanger(array2, ITERS, THREAD_NUM, phaser);
//        parChanger.changeArray();
//
//        System.out.println(Arrays.equals(seqChanger.getResource().getArray(), parChanger.getResource().getArray()));
//        System.out.println(seqChanger.getTimeElapsed());
//        System.out.println(parChanger.getTimeElapsed());
//
//        phaser.arriveAndDeregister();

        Phaser phaser = new Phaser(3);
        for(int i = 0; i < 100; i++){
            System.out.println(phaser.getPhase());
            phaser.arriveAndAwaitAdvance();

        }
    }
}