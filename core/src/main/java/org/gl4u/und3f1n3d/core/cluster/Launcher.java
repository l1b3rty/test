package org.gl4u.und3f1n3d.core.cluster;

public class Launcher {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("SShutdown hook");
            }
        }));

        Starter starter = new Starter();
        starter.start();

        System.out.println("Started");
    }

    //test
    private static class Starter {

        void start() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(2000);
                            System.out.println(Thread.currentThread() + " | still alive");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.out.println("GOT INTERRUPTED EXC");
                            return;
                        }

                        if (Thread.interrupted()) {
                            System.out.println("we are interrupted, exit");
                            return;
                        }
                    }
                }
            });

            thread.start();
        }

    }

}



