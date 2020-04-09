
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class TestApplication {
    private static ExecutorService service = Executors.newFixedThreadPool(5);
    private static DeckDrawAPITest deckDrawAPITest = new DeckDrawAPITest();

    public static void run(Integer multiCount, Integer runTime) {
        testNew_DeckSingleton(runTime);
        testNew_DeckParallel(multiCount);
        testDraw_cardSingleton(runTime);
        testDraw_cardParallel(multiCount);
        service.shutdown();
    }

    private static void testNew_DeckSingleton(int runTime) {
        Integer total = 0;
        Integer success = 0;
        Integer fail = 0;
        Integer exception = 0;
        for (int i = 0; i < runTime; i++) {
                Result result = null;
            result = deckDrawAPITest.testNew_Deck();
            total++;
            System.out.println("The " + total + " test: " + result);
            if (result.getResult() == 1) {
                success++;
            } else if (result.getResult() == 2) {
                fail++;
            } else {
                exception++;
            }
            if (total == runTime) {
                if(success == runTime) {
                    System.out.println(" Total : " + total + " All Passed ");
                } else {
                    System.out.println(" Total:" + total + " Success:" + success + " Fail:" + fail + " Exception:" + exception);
                }
            }
        }
    }

    private static void testNew_DeckParallel(int multiCount) {
        AtomicInteger total = new AtomicInteger();
        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        AtomicInteger exception = new AtomicInteger();
        for (int i = 0; i < multiCount; i++) {
            FutureTask<Result> testTask = new FutureTask<>(() -> {
                Result result = null;
                result = deckDrawAPITest.testNew_Deck();
                total.getAndIncrement();
                System.out.println("The " + total.get() + " test: " + result);
                if (result.getResult() == 1) {
                    success.getAndIncrement();
                } else if (result.getResult() == 2) {
                    fail.getAndIncrement();
                } else {
                    exception.getAndIncrement();
                }
                if (total.get() == multiCount) {
                    if(success.get() == multiCount) {
                        System.out.println(" Total : " + total + " All Passed ");
                    } else {
                        System.out.println(" Total:" + total + " Success:" + success + " Fail:" + fail + " Exception:" + exception);
                    }
                }
                return result;
            });
            service.submit(testTask);
        }
    }

    private static void testDraw_cardSingleton(int runTime) {
        Integer total = 0;
        Integer success = 0;
        Integer fail = 0;
        Integer exception = 0;
        for (int i = 0; i < runTime; i++) {
            Result result = null;
            result = deckDrawAPITest.testDraw_card();
            total++;
            System.out.println("The " + total + " test: " + result);
            if (result.getResult() == 1) {
                success++;
            } else if (result.getResult() == 2) {
                fail++;
            } else {
                exception++;
            }
            if (total == runTime) {
                if(success == runTime) {
                    System.out.println(" Total : " + total + " All Passed ");
                } else {
                    System.out.println(" Total:" + total + " Success:" + success + " Fail:" + fail + " Exception:" + exception);
                }
            }
        }
    }

    private static void testDraw_cardParallel(int multiCount) {
        AtomicInteger total = new AtomicInteger();
        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        AtomicInteger exception = new AtomicInteger();
        for (int i = 0; i < multiCount; i++) {
            FutureTask<Result> testTask = new FutureTask<>(() -> {
                Result result = null;
                result = deckDrawAPITest.testDraw_card();
                total.getAndIncrement();
                System.out.println("The " + total.get() + "test: " + result);
                if (result.getResult() == 1) {
                    success.getAndIncrement();
                } else if (result.getResult() == 2) {
                    fail.getAndIncrement();
                } else {
                    exception.getAndIncrement();
                }
                if (total.get() == multiCount) {
                    if(success.get() == multiCount) {
                        System.out.println(" Total : " + total + " All Passed ");
                    } else {
                        System.out.println(" Total:" + total + " Success:" + success + " Fail:" + fail + " Exception:" + exception);
                    }
                }
                return result;
            });
            service.submit(testTask);
        }
    }
}
