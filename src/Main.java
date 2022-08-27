public class Main {
    public static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        TimeCount timeCount = new TimeCount();
        Timer_7 timer_7 = new Timer_7(timeCount);
        Timer_5 timer_5 = new Timer_5();
        timer_5.start();
        timeCount.start();
        timer_7.start();

    }
}

class TimeCount extends Thread {
    @Override
    public void run() {
        synchronized (this) {
            while (true) {
                try {
                    Thread.sleep(1000);
                    Main.count++;
                    System.out.println(Main.count + " " + Thread.currentThread().getName());
                    this.notifyAll();
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

class Timer_7 extends Thread {
    TimeCount timeCount;

    Timer_7(TimeCount timeCount) {
        this.timeCount = timeCount;
    }

    @Override
    public void run() {
        synchronized (this.timeCount) {
            while (true) {
                if (Main.count != 0 && Main.count % 7 == 0) {
                    System.out.print("  прошло еще 7 секунд " + Thread.currentThread().getName());
                    System.out.println();
                }
                try {
                    this.timeCount.notify();
                    this.timeCount.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

class Timer_5 extends Thread {

    @Override
    public void run() {
        synchronized (this) {
            while (true) {
                if (Main.count % 5 == 0) {
                    if (Main.count != 0) {
                        System.out.print(" прошло еще 5 секунд " + Thread.currentThread().getName());
                        System.out.println();
                    }
                    try {
                        Thread.sleep(1100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

