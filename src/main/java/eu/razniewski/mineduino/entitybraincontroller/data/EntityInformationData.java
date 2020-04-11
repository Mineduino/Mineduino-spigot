package eu.razniewski.mineduino.entitybraincontroller.data;

import com.github.ysl3000.bukkit.pathfinding.entity.Insentient;

import java.io.Serializable;
import java.util.Objects;

public class EntityInformationData implements Serializable {
    private EntityLocation location;
    private int id;
    private String entityType;
    private boolean isMoving;

    public EntityInformationData(EntityLocation location, int id, String entityType) {
        this.location = location;
        this.id = id;
        this.entityType = entityType;
    }

    public EntityInformationData() {
    }

    public EntityLocation getLocation() {
        return location;
    }

    public void setLocation(EntityLocation location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityInformationData that = (EntityInformationData) o;
        return id == that.id &&
                isMoving == that.isMoving &&
                Objects.equals(location, that.location) &&
                Objects.equals(entityType, that.entityType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, id, entityType, isMoving);
    }

    @Override
    public String toString() {
        return "EntityInformationData{" +
                "location=" + location +
                ", id=" + id +
                ", entityType='" + entityType + '\'' +
                ", isMoving=" + isMoving +
                '}';
    }

    public static EntityInformationData from(Insentient insentient) {
        EntityInformationData data = new EntityInformationData();
        data.setEntityType(insentient.getBukkitEntity().getType().name());
        data.setId(insentient.getBukkitEntity().getEntityId());
        data.setMoving(!insentient.getNavigation().isDoneNavigating());
        data.setLocation(EntityLocation.from(insentient.getBukkitEntity().getLocation()));
        return data;
    }

}
