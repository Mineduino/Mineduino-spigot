/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bukkit.block.Block;

/**
 *
 * @author adam
 */

public class InputStoragerInMemory implements InputStorager{
    private Map<Block, List<String>> memoryMap;
    
    public InputStoragerInMemory() {
        this.memoryMap = new HashMap<>();
    }

    @Override
    public void replaceWith(Block topic, List<String> blocks) {
        this.memoryMap.put(topic, blocks);
    }

    @Override
    public void add(Block topic, String... blocks) {
        if(!memoryMap.containsKey(topic)) {
            memoryMap.put(topic, new ArrayList<>());
        }
        List<String> associated = memoryMap.get(topic);
        for(String block: blocks) {
            associated.add(block);
        }
    }

    @Override
    public void remove(Block topic, String... blocks) {
        if(!memoryMap.containsKey(topic)) {
            return;
        }
       
        Iterator<String> associatedIterator = memoryMap.get(topic).iterator();
        while(associatedIterator.hasNext()) {
            String checked = associatedIterator.next();
            for(String block: blocks) {
                if(block.equals(checked)) {
                    associatedIterator.remove();
                    break;
                }
            }
        }
        
    }

    @Override
    public Optional<List<String>> getAllFromTopic(Block topic) {
        if(!memoryMap.containsKey(topic)) {
            return Optional.empty();
        }
        return Optional.ofNullable(memoryMap.get(topic));
    }
    

    
}
