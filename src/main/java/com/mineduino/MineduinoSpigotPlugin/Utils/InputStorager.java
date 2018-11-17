/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Utils;

import com.mineduino.MineduinoSpigotPlugin.TriggerBlocks.OutputTriggerBlock;
import java.util.List;
import java.util.Optional;
import org.bukkit.block.Block;

/**
 *
 * @author adam
 */
public interface InputStorager {
    public void replaceWith(Block topic, List<String> topics);
    public void add(Block topic, String ... topics);
    public void remove(Block topic, String ... topics);
    public Optional<List<String>> getAllFromTopic(Block block);
}
