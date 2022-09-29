import java.util.concurrent.Phaser;

public class MyThread extends Thread {
    Phaser phaser;
    int startIndex = 0, endIndex = 0;
    static int currentIter = 0;
    private final int totalIters;
    //сделать просто камон ресурс в качестве поля
    CommonResource resource;

    public MyThread(int startIndex, int endIndex, int totalIters, Phaser phaser, CommonResource resource) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.totalIters = totalIters;
        this.phaser = phaser;
        this.resource = resource;
        this.phaser.register();
        this.start();
    }

    @Override
    public void run() {
        System.out.println(this.getName() + " фаза регистрации закончена");
        //первый колокол - ожидание, пока main закончит создание потоков
        this.phaser.arriveAndAwaitAdvance();


        //теперь мы ждем, когда создастся цикл в мейне и проинициалазируется текущая итерация в треде
//        this.phaser.arriveAndAwaitAdvance();
        double [] targetArray = this.resource.getArray();
        double [] dummyArray;
        while (currentIter < this.totalIters) {
            //т.к. массив изменяется по ссылке - нет необходимости его переприсваивать, потому что он всегда актуален
//            targetArray = this.resource.getArray();
            dummyArray = this.resource.getCopiedArray();
            for (int i = this.startIndex; i <= this.endIndex; i++) {
                targetArray[i] = (dummyArray[i - 1] + dummyArray[i + 1]) / 2;
//                targetArray[i] += 1;
            }
            this.phaser.arriveAndAwaitAdvance();
            this.phaser.arriveAndAwaitAdvance();
            this.phaser.arriveAndAwaitAdvance();
//            System.out.println(this.getName() + " ended phase " + this.phaser.getPhase());
//            this.resource.rewriteCopiedArray();
        }
//        System.out.println(this.getName() + " работа закончена");
        this.phaser.arriveAndAwaitAdvance();
        this.phaser.arriveAndDeregister();
    }
}
