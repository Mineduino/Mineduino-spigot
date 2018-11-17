/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Callbacks.evaluators;

import com.mineduino.MineduinoSpigotPlugin.Callbacks.RealToRedstoneEvaluator;
import java.util.Map;

/**
 *
 * @author adam
 */
public class Dht implements RealToRedstoneEvaluator{

    @Override
    public int getRedstoneSignalPower(Map message) {
        double temp = (double) message.get("temp");
        double hum = (double) message.get("humidity");
        return scaleBetween(temp, 0, 16, 0, 100);
    }
    
    public int scaleBetween(double unscalledNum, int minAllowed, int maxAllowed, int min, int max) {
        return (int)((maxAllowed - minAllowed) * (unscalledNum - min) / (max - min) + minAllowed);
    }
    
}
