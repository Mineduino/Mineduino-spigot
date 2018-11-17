/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Utils;

import com.mineduino.MineduinoSpigotPlugin.TriggerBlocks.OutputTriggerBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author adam
 */

public class OutputStoragerInMemory implements OutputStorager{
    private Map<String, List<OutputTriggerBlock>> memoryMap;
    
    public OutputStoragerInMemory() {
        this.memoryMap = new HashMap<>();
    }

    @Override
    public void replaceWith(String topic, List<OutputTriggerBlock> blocks) {
        this.memoryMap.put(topic, blocks);
    }

    @Override
    public void add(String topic, OutputTriggerBlock... blocks) {
        if(!memoryMap.containsKey(topic)) {
            memoryMap.put(topic, new ArrayList<>());
        }
        List<OutputTriggerBlock> associated = memoryMap.get(topic);
        for(OutputTriggerBlock block: blocks) {
            associated.add(block);
        }
    }

    @Override
    public void remove(String topic, OutputTriggerBlock... blocks) {
        if(!memoryMap.containsKey(topic)) {
            return;
        }
       
        Iterator<OutputTriggerBlock> associatedIterator = memoryMap.get(topic).iterator();
        while(associatedIterator.hasNext()) {
            OutputTriggerBlock checked = associatedIterator.next();
            for(OutputTriggerBlock block: blocks) {
                if(block.equals(checked)) {
                    associatedIterator.remove();
                    break;
                }
            }
        }
        
    }

    @Override
    public Optional<List<OutputTriggerBlock>> getAllFromTopic(String topic) {
        if(!memoryMap.containsKey(topic)) {
            return Optional.empty();
        }
        return Optional.ofNullable(memoryMap.get(topic));
    }
    

    
}
