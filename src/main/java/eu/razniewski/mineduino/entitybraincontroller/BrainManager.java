package eu.razniewski.mineduino.entitybraincontroller;

import java.util.Set;

public interface BrainManager {
    public Set<BrainController> getBrainsFor(String topic);
    public void addBrain(String topic, BrainController controller);
}
