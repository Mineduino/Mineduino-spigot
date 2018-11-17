/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Events;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author adam
 */
public class SendObject implements Serializable{
    private boolean value;

    public SendObject(boolean value) {
        this.value = value;
    }


    public boolean isValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.value ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SendObject other = (SendObject) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
    
    
    
}
