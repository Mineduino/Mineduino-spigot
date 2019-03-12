/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Utils;

import java.util.List;
import java.util.Optional;
import org.bukkit.block.Block;

/**
 *
 * @author adam
 */
public interface OutputStorager {
    public void replaceWith(String topic, List<Block> topics);
    public void add(String topic, Block ... topics);
    public void remove(String topic, Block ... topics);
    public Optional<List<Block>> getAllFromTopic(String topic);
}
