package eu.razniewski.mineduino.annotations;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class TickRunnableUtil {
    public static Set<Class<?>> getTypesAnnotatedWithTickRunnable() {
        Reflections reflections = new Reflections("eu.razniewski.mineduino");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TickRunnable.class);
        return annotated;
    }

    public static Set<TickInvoker> getInvocators() {
        Set<TickInvoker> invocators = new HashSet<>();
        for(Class c: getTypesAnnotatedWithTickRunnable()) {
            TickRunnable cann = (TickRunnable)c.getAnnotation(TickRunnable.class);
            try {
                Object instance = c.getDeclaredConstructor().newInstance();
                for(Method m: c.getMethods()) {
                    MethodTickRunnable ann = m.getAnnotation(MethodTickRunnable.class);
                    if(ann != null) {
                        invocators.add(new TickInvoker(cann.perTicks(), c.getDeclaredConstructor().newInstance(), m));
                    }
                }
            } catch (InstantiationException e) {
                System.out.println("Cannot instantiate some class annotated with TickRunnable: " + c);
            } catch (IllegalAccessException e) {
                System.out.println("Cannot access some field/method in class annotated with TickRunnable" + c);
            } catch (InvocationTargetException e) {
                System.out.println("Cannot invoke some field/method in class annotated with TickRunnable" + c);
            } catch (NoSuchMethodException e) {
                System.out.println("No default constructor in class annotated with TickRunnable" + c);
            }
        }
        return invocators;
    }

}