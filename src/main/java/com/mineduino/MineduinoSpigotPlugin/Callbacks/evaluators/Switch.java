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
public class Switch implements RealToRedstoneEvaluator{

    @Override
    public int getRedstoneSignalPower(Map message) {
        return ((Boolean) message.get("value")) ? 16 : 0;
    }
    
}
