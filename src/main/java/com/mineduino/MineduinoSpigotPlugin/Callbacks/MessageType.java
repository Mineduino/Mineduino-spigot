/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Callbacks;

import com.mineduino.MineduinoSpigotPlugin.Callbacks.evaluators.Dht;
import com.mineduino.MineduinoSpigotPlugin.Callbacks.evaluators.Switch;
import com.mineduino.MineduinoSpigotPlugin.Callbacks.evaluators.Unrecognized;
import com.sun.corba.se.spi.orb.StringPair;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author adam
 */
public enum MessageType{
    DHT12(new Dht(), new StringTypePair("temp", Double.class), new StringTypePair("humidity", Double.class)), 
    SWITCH(new Switch(), new StringTypePair("value", Boolean.class)), 
    UNRECOGNIZED(new Unrecognized());
    
    private StringTypePair[] types;
    private RealToRedstoneEvaluator evaluator;
    
    MessageType(RealToRedstoneEvaluator evaluator, StringTypePair ... types) {
        this.evaluator = evaluator;
        this.types = types;
    }
    
    public boolean checkTypes(Map map) {
        HashSet<StringTypePair> typesCopy = new HashSet();
        for(StringTypePair type: types) {
            typesCopy.add(type);
        }
        for(Object obj: map.keySet()) {
            if(obj.getClass().equals(String.class)) {
                String key = obj.toString();
                Iterator<StringTypePair> iter = typesCopy.iterator();
                while(iter.hasNext()) {
                    StringTypePair checking = iter.next();
                    if(checking.checkFor(key, map.get(obj).getClass())) {
                        iter.remove();
                    }
                }
            } else {
                return false;
            }
        }
        if(typesCopy.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public static MessageType checkType(Map map) {
        for(MessageType type: MessageType.values()) {
            if(type.checkTypes(map)) {
                return type;
            }
        }
        return UNRECOGNIZED;
    }
    
    public int evaluate(Map map) {
        return this.evaluator.getRedstoneSignalPower(map);
    }


}
