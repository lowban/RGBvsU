package rgbvsu.engine.ai;

import java.awt.Point;
import rgbvsu.engine.objects.BigTurret;
import rgbvsu.engine.objects.CrushProjectile;
import rgbvsu.engine.objects.GameObject;
import rgbvsu.engine.objects.GameObject.Facing;

/**
 *
 * @author Robin Horneman
 */
public class BigTurretAI extends AI {

    int AItime = 100;
    static int shotCoolDown = 500;
    int elapsed = 0;
    int stunTime = 0;
    int projectileSpeed = 1;
    boolean seeing = false;
    boolean hasShot = false;
    BigTurret turret;
    Facing facing;
    int state = 0;
    boolean stunned = false;

    public BigTurretAI(BigTurret turret, Facing facing) {
        super(turret);
        this.turret = turret;
        this.facing = facing;
        turret.setFacing(facing);
        this.turret.setAI(this);
    }

    @Override
    public void collisionResponse(GameObject otherobj) {

    }

    @Override
    public void update(long time) {
        //Add AI-logic here
        if (time < AItime) {
            if (hasShot) {
                elapsed += time;
            }

            //Choose state
            if (turret.inFacingView(turret.getWorld().getPlayer())) {
                state = 1; //Seen                
            } else {
                state = 0;
            }
            
            GameObject owner = getGameObject();
            GameObject player = owner.getWorld().getPlayer();
            int playerx = (int) player.getX();
            int playery = (int) player.getY();
            int ownerx = (int) owner.getX();
            int ownery = (int) owner.getY();
/*
            //SHOOT?
            if ((playerx + player.getWidth() > ownerx && playerx < ownerx + owner.getWidth())
                    || (playery + player.getHeight() > ownery && playery < ownery + owner.getHeight())) {

                if (facing == Facing.LEFT) {
                    if (playerx < ownerx) {
                        seeing = true;
                    }
                }
                if (facing == Facing.RIGHT) {
                    if (playerx < ownerx) {
                        seeing = true;
                    }
                }
                if (facing == Facing.UP) {
                    if (playery < ownery) {
                        seeing = true;
                    }
                }
                if (facing == Facing.DOWN) {
                    if (playery > ownery) {
                        seeing = true;
                    }
                }
*/

                if (state == 1 && !hasShot) {
                    Point facingOffset = owner.getFacingOffset();
                    turret.shoot((int) (owner.getCenterX() + facingOffset.x * 10),
                            (int) (owner.getCenterY() + facingOffset.y * 10),
                            facingOffset.x * projectileSpeed,
                            facingOffset.y * projectileSpeed);
                    hasShot = true;
                }
                if (hasShot && elapsed > shotCoolDown) 
                {
                    elapsed = 0;
                    hasShot = false;
                }                           
        }
    }
}
