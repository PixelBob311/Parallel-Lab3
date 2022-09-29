import java.util.concurrent.Phaser;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final Boolean IS_DEBUG_MODE = false;
        final int ITERS = 77;
        final int THREAD_NUM = 2;

//        CommonResource array = new CommonResource(12);
        CommonResource array1 = new CommonResource(new double[]{0, 1, 4, 3, 6, 5, 1, 7, 15, 9, 20, 11});
        CommonResource array2 = new CommonResource(new double[]{0, 1, 4, 3, 6, 5, 1, 7, 15, 9, 20, 11});
//        CommonResource array1 = new CommonResource(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
//        CommonResource array2 = new CommonResource(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
//        array.setManually(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

        SequentialArrayChanger seqChanger = new SequentialArrayChanger(array1, ITERS);
        seqChanger.changeArray();

        System.out.println("Последовательное изменение");
        seqChanger.getResource().printArray();

        //создаем фазер и регистрируем поток main
        Phaser phaser = new Phaser(1);

        ParallelArrayChanger parChanger = new ParallelArrayChanger(array2, ITERS, THREAD_NUM, phaser);
        parChanger.changeArray();

        System.out.println("Параллельное изменение");
        parChanger.getResource().printArray();

        System.out.println(Arrays.equals(seqChanger.getResource().getArray(), parChanger.getResource().getArray()));
        System.out.println(seqChanger.getTimeElapsed());
        System.out.println(parChanger.getTimeElapsed());

        phaser.arriveAndDeregister();

    }
}