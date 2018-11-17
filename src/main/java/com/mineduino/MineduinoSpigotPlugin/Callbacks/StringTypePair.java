/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Callbacks;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 *
 * @author adam
 */
public class StringTypePair {
    private String keyName;
    private Type valueType;

    public StringTypePair(String keyName, Type valueType) {
        this.keyName = keyName;
        this.valueType = valueType;
    }
    
    public boolean checkFor(String key, Type type) {
        return keyName.equals(key) && valueType.equals(type);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.keyName);
        hash = 41 * hash + Objects.hashCode(this.valueType);
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
        final StringTypePair other = (StringTypePair) obj;
        if (!Objects.equals(this.keyName, other.keyName)) {
            return false;
        }
        if (!Objects.equals(this.valueType, other.valueType)) {
            return false;
        }
        return true;
    }
    
    
}
