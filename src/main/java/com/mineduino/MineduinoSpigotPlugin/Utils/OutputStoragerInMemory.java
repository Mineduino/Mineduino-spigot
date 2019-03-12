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

public class OutputStoragerInMemory implements OutputStorager{
    private Map<String, List<Block>> memoryMap;
    
    public OutputStoragerInMemory() {
        this.memoryMap = new HashMap<>();
    }

    @Override
    public void replaceWith(String topic, List<Block> blocks) {
        this.memoryMap.put(topic, blocks);
    }

    @Override
    public void add(String topic, Block... blocks) {
        if(!memoryMap.containsKey(topic)) {
            memoryMap.put(topic, new ArrayList<>());
        }
        List<Block> associated = memoryMap.get(topic);
        for(Block block: blocks) {
            associated.add(block);
        }
    }

    @Override
    public void remove(String topic, Block... blocks) {
        if(!memoryMap.containsKey(topic)) {
            return;
        }
       
        Iterator<Block> associatedIterator = memoryMap.get(topic).iterator();
        while(associatedIterator.hasNext()) {
            Block checked = associatedIterator.next();
            for(Block block: blocks) {
                if(block.equals(checked)) {
                    associatedIterator.remove();
                    break;
                }
            }
        }
        
    }

    @Override
    public Optional<List<Block>> getAllFromTopic(String topic) {
        if(!memoryMap.containsKey(topic)) {
            return Optional.empty();
        }
        return Optional.ofNullable(memoryMap.get(topic));
    }
    

    
}
