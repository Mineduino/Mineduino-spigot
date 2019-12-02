package eu.razniewski.mineduino.worldtominecraft;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.connector.MineduinoMessageEvent;
import eu.razniewski.mineduino.worldtominecraft.evaluators.SimpleEvaluator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class EvaluatorManager {
    public static Map<String, Consumer<MineduinoMessageEvent>> evaluators = new HashMap<>();
    static  {
        evaluators.put("simple", new SimpleEvaluator());
    }


    public static Consumer<MineduinoMessageEvent> getEvaluatorFor(String type) {
        return evaluators.getOrDefault(type, mineduinoMessageEvent -> MineduinoPlugin.getInstance().getLogger().warning("No evaluator for type: " + type));
    }




}
