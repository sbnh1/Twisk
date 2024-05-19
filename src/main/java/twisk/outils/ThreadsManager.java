package twisk.outils;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadsManager {

    private static ThreadsManager instance = new ThreadsManager();

    private List<Thread> threads;

    /**
     * Constructeur de ThreadsManager
     */
    private ThreadsManager(){
        this.threads = new CopyOnWriteArrayList<>();
    }

    /**
     * retourne l'instance de ThreadsManager
     */
    public static ThreadsManager getInstance(){
        return instance;
    }

    /**
     * Fonction qui lance un thread
     */
    public void startThread(Task<?> task){
        Thread thread = new Thread(task);
        this.threads.add(thread);
        thread.start();
    }

    /**
     * Fonction qui détruit un thread
     */
    public void destroyThread(Task<?> task){
        for(Thread thread : this.threads){
            if(thread.getState() == Thread.State.RUNNABLE && thread.getName().equals(task.getTitle())){
                thread.interrupt();
                this.threads.remove(thread);
                break;
            }
        }
    }

    /**
     * Fonction qui détruit tous les threads
     */
    public void destroyThreads(){
        for(Thread thread : this.threads){
            thread.interrupt();
        }
        this.threads.clear();
    }
}
