package eu.razniewski.mineduino.entitybraincontroller.actions;

import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import eu.razniewski.mineduino.entitybraincontroller.EntityContext;

import java.io.Serializable;
import java.util.Objects;

public abstract class EntityRequest implements Serializable {

    public EntityRequest() {
    }

    public abstract void processBrain(BrainController controller, EntityContext context);
}
