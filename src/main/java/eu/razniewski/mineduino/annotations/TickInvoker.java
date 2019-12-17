package eu.razniewski.mineduino.annotations;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TickInvoker implements Runnable {
    private int tick;
    private Object invoker;
    private Method invocator;

    public TickInvoker(int tick, Object invoker, Method invocator) {
        this.tick = tick;
        this.invoker = invoker;
        this.invocator = invocator;
    }

    public Class getInvokerClass() {
        return invoker.getClass();
    }

    public String getMethodName() {
        return invocator.getName();
    }

    public int getTick() {
        return tick;
    }

    public Object execute() {
        System.out.println("Executing... " + this.invoker.getClass().getName());
        try {
            return invocator.invoke(invoker);
        } catch (IllegalAccessException e) {
            System.out.println("Illegal invoker" + invoker.getClass());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Illegal invoker: " + invoker.getClass());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        execute();
    }
}