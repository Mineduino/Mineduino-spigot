/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Callbacks;

/**
 *
 * @author adam
 */
public class MockedRealToRedstone implements RealToRedstoneEvaluator{

    @Override
    public int getRedstoneSignalPower() {
        return 10;
    }
    
}
