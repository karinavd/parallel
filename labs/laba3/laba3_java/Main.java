import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

public class Main {
    private static final int storageSize = 5;
    private static final int consumers = 4;
    private static final int producers = 2;
    private static final int totalItems = 4;
    private final Semaphore access = new Semaphore(1);
    private final Semaphore full = new Semaphore(storageSize);
    private final Semaphore empty = new Semaphore(0);
    
    private final List<String> storage = new ArrayList<>();
    private int idProduct = 0;

    public static void main(String[] args) {
        Main program = new Main();
        program.starter();
    }

    private void starter() {
        createAndStartThreads(producers, totalItems, this::producer);
        createAndStartThreads(consumers, totalItems, this::consumer);
    }

    private void createAndStartThreads(int countThreads, int totalItems, BiConsumer<Integer, Integer> action) {
        int tempChunk = totalItems / countThreads;
        int rest = totalItems % countThreads;
        
        for (int i = 0; i < countThreads; i++) {
            int itemsForThisThread = tempChunk + (i < rest ? 1 : 0);
            int threadIndex = i;
            
            Thread thread = new Thread(() -> action.accept(threadIndex, itemsForThisThread));
            thread.start();
        }
    }

    private void producer(int index, int itemNumbers) {
        for (int i = 0; i < itemNumbers; i++) {
            try {
                full.acquire();
                access.acquire();

                storage.add("item " + idProduct);
                System.out.println("Producer " + index + " added item " + idProduct);
                idProduct++;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                access.release();
                empty.release();
            }
        }
    }

    private void consumer(int index, int itemNumbers) {
        for (int i = 0; i < itemNumbers; i++) {
            try {
                empty.acquire();
                Thread.sleep(1000); 
                access.acquire();

                String item = storage.get(0);
                storage.remove(0);
                
                System.out.println("Consumer " + index + " took item " + item);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                full.release();
                access.release();
            }
        }
    }
}