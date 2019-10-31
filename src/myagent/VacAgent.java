/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myagent;

import agent.Action;
import agent.Agent;
import agent.Percept;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import vacworld.*;

import java.util.ArrayList;
import java.util.Random;

/* Change the code as appropriate.  This code
   is here to help you understand the mechanism
   of the simulator.
*/

public class VacAgent extends Agent {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    private final String ID = "1";
    // Think about locations you already visited.  Remember those.
    private boolean dirtStatus = true;
    private boolean bumpFeltInPrevMove = true;
    private boolean obstacleInFront = true;
    private int xcoor = 0;
    private int ycoor = 0;
    private int dir = NORTH;
    private ArrayList<String> path = new ArrayList<>();

    public VacAgent(){
        addPos();
    }

    public void see(Percept p) {
        VacPercept vp = (VacPercept) p;
        dirtStatus = vp.seeDirt();
        bumpFeltInPrevMove = vp.feelBump();
        obstacleInFront = vp.seeObstacle();
    }

    public Action selectAction() {

        Action action = new ShutOff();
        SuckDirt suckDirt = new SuckDirt();
        TurnLeft turnLeft = new TurnLeft();
        TurnRight turnRight = new TurnRight();
        GoForward goForward = new GoForward();

        Random r = new Random(System.currentTimeMillis());

        r.setSeed(System.currentTimeMillis());
        float prob;

        if (obstacleInFront) {
            prob = r.nextFloat();
            if (prob < 0.5) {
                changeDirLeft();
                return turnLeft;
            } else {
                changeDirRight();
                return turnRight;
            }
        } else if (dirtStatus) {
            return suckDirt;
        } else if(bumpFeltInPrevMove){
            System.out.println("sintio la pared");
            prob = r.nextFloat();
            if (prob < 0.5) {
                changeDirLeft();
                return turnLeft;
            } else {
                changeDirRight();
                return turnRight;
            }
        }else{
            prob = r.nextFloat();
            System.out.println("otra");
            System.err.println(prob);
            if (prob < 0.15) {
                changeDirLeft();
                return turnLeft;
            } else if (prob < 0.30) {
                changeDirRight();
                return turnRight;
            } else if (prob < 0.95) {
                switch (dir){
                    case NORTH:
                        setYcoor(ycoor + 1);
                        break;
                    case SOUTH:
                        setYcoor(ycoor - 1);
                        break;
                    case EAST:
                        setXcoor(xcoor + 1);
                        break;
                    case WEST:
                        setXcoor(xcoor - 1);
                        break;
                }
                addPos();
                return goForward;
            } else {
                return action;
            }
        }
    }

    public void changeDirLeft(){
        switch (dir) {
            case NORTH:
                setDir(WEST);
                break;
            case SOUTH:
                setDir(EAST);
                break;
            case EAST:
                setDir(NORTH);
                break;
            case WEST:
                setDir(SOUTH);
                break;
        }
    }

    public void changeDirRight(){
        switch (dir) {
            case NORTH:
                setDir(EAST);
                break;
            case SOUTH:
                setDir(WEST);
                break;
            case EAST:
                setDir(SOUTH);
                break;
            case WEST:
                setDir(NORTH);
                break;
        }
    }

    public void addPos(){
        path.add("(" + xcoor + ", " + ycoor + ")");
        System.out.println("initial path");
        System.out.println(path);
    }

    public String getId() {
        return ID;
    }

    public void setXcoor(int x){
        xcoor = x;
    }

    public int getXcoor(){
        return xcoor;
    }

    public void setYcoor(int y){
        ycoor = y;
    }

    public int getYcoor(){
        return ycoor;
    }

    public int getDir(){
        return dir;
    }

    public void setDir(int dir){
        this.dir = dir;
    }

}
