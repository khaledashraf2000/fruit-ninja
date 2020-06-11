package controller.entities;

import javafx.scene.shape.QuadCurveTo;

import java.util.Random;

public class Projectile {
    private final double xInitial;
    private final double yInitial;
    private final double yFinal;
    private final double maxHeight;
    private final double thetaInitial;
    private double controlX;
    private double controlY;
    private double xFinal;
    private double velocityInitial;
    private double time;
    private double deltaX;

    //Constructing projectile using projectile dynamics laws
    public Projectile() {
        Random rnd = new Random();
        thetaInitial = Math.toRadians(getRandomWithExclusion(rnd, 88, 92, 90)); //theta randomly generated
        maxHeight = (Math.random() * 100) + 450; // randomly generated
        xInitial = (Math.random() * (800) + 100); // randomly generated
        yInitial = 650; //constant
        yFinal = yInitial;
    }

    public double getXInitial() {
        return xInitial;
    }

    public double getYInitial() {
        return yInitial;
    }

    public double getTime() {
        return time;
    }

    public QuadCurveTo constructPath() {
        doMaths();
        //System.out.printf("MAX H : %.2f\tCTRLY %.2f\tTHETA:%.2f\tVELOCITY:%.2f\tPROJECTILE TIME :%.2f\tDELTA X:%.2f\n",maxHeight,controlY,Math.toDegrees(thetaInitial),velocityInitial,time,deltaX);
        return new QuadCurveTo(controlX, controlY, xFinal, yFinal);
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    public void doMaths() {
        double slope;
        velocityInitial = Math.sqrt(maxHeight * 2 * 9.81 / Math.pow(Math.sin(thetaInitial), 2));
        xFinal = xInitial + Math.pow(velocityInitial, 2) * Math.sin(2 * thetaInitial) / 9.81;
        deltaX = xFinal - xInitial;
        time = 2 * Math.abs(velocityInitial * Math.sin(thetaInitial) / 9.81);
        controlX = (xFinal + xInitial) / 2;
        slope = -1 * (Math.tan(thetaInitial));
        /*-((9.81*xInitial)/(Math.pow(Math.cos(thetaInitial),2)*Math.pow(velocityInitial, 2))));*/
        controlY = slope * (controlX - xInitial) + 600;
    }


}