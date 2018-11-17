/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Utils;

import com.mineduino.MineduinoSpigotPlugin.TriggerBlocks.OutputTriggerBlock;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author adam
 */
public interface Storager {
    public void replaceWith(String topic, List<OutputTriggerBlock> blocks);
    public void add(String topic, OutputTriggerBlock ... blocks);
    public void remove(String topic, OutputTriggerBlock ... blocks);
    public Optional<List<OutputTriggerBlock>> getAllFromTopic(String topic);
}
