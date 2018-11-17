/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Callbacks;

import java.util.Map;

/**
 *
 * @author adam
 */
public interface RealToRedstoneEvaluator {
    public int getRedstoneSignalPower(Map message);
}
