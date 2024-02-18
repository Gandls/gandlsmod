package net.nathan.gandlsmod;

import net.minecraft.world.phys.Vec3;

public class Helper {
    public static Vec3 rotate(Vec3 toRotate, Vec3 around, double angle) {

        //When LA Y > 0, the particle come towards the player
        //When LA y < 0 the particles go away from the player (with the LA)
        Vec3 AParaB = around.normalize().scale(toRotate.dot(around) / around.dot(around));
        Vec3 APerpB = new Vec3(toRotate.x - AParaB.x,toRotate.y - AParaB.y,toRotate.z - AParaB.z);
        Vec3 w = around.cross(toRotate);
        double x1  = Math.cos(Math.toRadians(angle)) / APerpB.length();
        double x2 = Math.sin(Math.toRadians(angle)) / w.length();
        Vec3 APerpScaled = APerpB.scale(x1);
        w = w.scale(x2);
        Vec3 Add = new Vec3(APerpScaled.x + w.x,APerpScaled.y + w.y,APerpScaled.z + w.z).scale(APerpB.length());
        Vec3 total = new Vec3(Add.x + AParaB.x,Add.y + AParaB.y,Add.z + AParaB.z);

        return total;
    }


}
