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

    public static double angleBetween(Vec3 a, Vec3 b){

        /*
        double x = a.x * b.x;
        double y = a.z * b.z;
        double z = a.z * b.z;

        double xaSqr = Math.pow(a.x,2);
        double yaSqr = Math.pow(a.y,2);
        double zaSqr = Math.pow(a.z,2);

        double AsqRoot = Math.sqrt(Math.pow(a.x,2)+Math.pow(a.y,2)+Math.pow(a.z,2));

        double xbSqr = Math.pow(b.x,2);
        double ybSqr = Math.pow(b.y,2);
        double zbSqr = Math.pow(b.z,2);

        double BsqRoot = Math.sqrt(Math.pow(b.x,2)+Math.pow(b.y,2)+Math.pow(b.z,2));

         */

        double roots = a.length()*b.length();
        double sum  = a.dot(b);

        return Math.acos(sum/roots);


    }


}
