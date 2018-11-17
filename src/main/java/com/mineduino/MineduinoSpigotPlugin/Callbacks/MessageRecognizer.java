/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Callbacks;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
/**
 *
 * @author adam
 */
public class MessageRecognizer implements Recognizer{
    private Gson gson = new Gson();
    
    @Override
    public MessageType recognize(String message) {
        Map map = gson.fromJson(message, Map.class);
        return MessageType.checkType(map);
    }

    @Override
    public int recognizeAndEvaluate(String message) {
        
        Map map = gson.fromJson(message, Map.class);
        MessageType type = MessageType.checkType(map);
        return type.evaluate(map);
    }
    

    
}
