package eu.razniewski.mineduino.entitybraincontroller;

import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.PathfinderGoal;

public class PathfinderGoalWalkToTile extends PathfinderGoal {
    float speed;
    private EntityInsentient entitycreature;
    private double deltaX;
    private double deltaY;
    private double deltaZ;

    public PathfinderGoalWalkToTile(EntityInsentient entitycreature, float speed, double xdelta, double ydelta, double zdelta){
        this.entitycreature = entitycreature;
        this.speed = speed;
        this.deltaX = xdelta;
        this.deltaY = ydelta;
        this.deltaZ = zdelta;
    }

    @Override
    public boolean a() {
        System.out.println("A()");
        return true;
    }

    @Override
    public void c(){
        this.entitycreature.getNavigation().a(entitycreature.getPositionVector().getX() + deltaX, entitycreature.getPositionVector().getY() + deltaY, entitycreature.getPositionVector().getZ() + deltaZ, 1);
        System.out.println("NAVIGATOR");
    }



}
