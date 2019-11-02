/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myagent;

import agent.Action;
import agent.Agent;
import agent.Percept;
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
    public static final int MAXTURNS = 4;
    public static final int TABLE_SIZE = 36;

    private final String ID = "1";
    // Think about locations you already visited.  Remember those.
    private boolean dirtStatus = true;
    private boolean bumpFeltInPrevMove = true;
    private boolean obstacleInFront = true;
    private int xcoor = 0;
    private int ycoor = 0;
    private int dir = NORTH;
    private ArrayList<String> path = new ArrayList<>();
    private ArrayList<Action> actions = new ArrayList<>();
    private ArrayList<String> obstaclesPos = new ArrayList<>();

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
        String pos;

        if(path.size() + obstaclesPos.size() == TABLE_SIZE){
            System.out.println("Path Size");
            System.out.println(path.size());

            System.out.println("Obstacles size");
            System.out.println(obstaclesPos.size());

            return action;
        }

        if (obstacleInFront) {
            switch (dir) {
                case NORTH:
                    setYcoor(ycoor + 1);
                    pos = "(" + xcoor + ", " + ycoor + ")";
                    if(!obstaclesPos.contains(pos)){
                        obstaclesPos.add(pos);
                    }
                    setYcoor(ycoor - 1);
                    break;
                case SOUTH:
                    setYcoor(ycoor - 1);
                    pos = "(" + xcoor + ", " + ycoor + ")";
                    if(!obstaclesPos.contains(pos)){
                        obstaclesPos.add(pos);
                    }
                    setYcoor(ycoor + 1);
                    break;
                case EAST:
                    setXcoor(xcoor + 1);
                    pos = "(" + xcoor + ", " + ycoor + ")";
                    if(!obstaclesPos.contains(pos)){
                        obstaclesPos.add(pos);
                    }
                    setXcoor(xcoor - 1);
                    break;
                case WEST:
                    setXcoor(xcoor - 1);
                    pos = "(" + xcoor + ", " + ycoor + ")";
                    if(!obstaclesPos.contains(pos)){
                        obstaclesPos.add(pos);
                    }
                    setXcoor(xcoor + 1);
                    break;
            }

            System.out.println("obstacles");
            System.out.println(obstaclesPos);

            prob = r.nextFloat();

            if (prob < 0.5) {
                actions.add(turnLeft);
                changeDirLeft();
                return turnLeft;
            } else {
                actions.add(turnRight);
                changeDirRight();
                return turnRight;
            }
        } else if (dirtStatus) {
            return suckDirt;
        }
        else if(bumpFeltInPrevMove){
            System.out.println("sintio la pared");
            prob = r.nextFloat();

            if (prob < 0.5) {
                changeDirLeft();
                actions.add(turnLeft);
                return turnLeft;
            } else {
                actions.add(turnRight);
                changeDirRight();
                return turnRight;
            }
        }else{
            if(dir == NORTH) {
                setYcoor(ycoor + 1);
                if(!path.contains("(" + xcoor + ", " + ycoor + ")")){
                    addPos();
                    actions.add(goForward);
                    return goForward;
                } else {
                    if(maxTurnsReached()) {
                      actions.add(goForward);
                      return goForward;
                    } else {
                        setYcoor(ycoor - 1);
                        prob = r.nextFloat();
                        if (prob < 0.5) {
                            actions.add(turnLeft);
                            changeDirLeft();
                            return turnLeft;
                        } else {
                            actions.add(turnRight);
                            changeDirRight();
                            return turnRight;
                        }
                    }
                }
            } else if (dir == SOUTH){
                setYcoor(ycoor - 1);
                if(!path.contains("(" + xcoor + ", " + ycoor + ")")){
                    addPos();
                    actions.add(goForward);
                    return goForward;
                } else {
                    if(maxTurnsReached()) {
                        actions.add(goForward);
                        return goForward;
                    } else {
                        setYcoor(ycoor + 1);
                        prob = r.nextFloat();
                        if (prob < 0.5) {
                            actions.add(turnLeft);
                            changeDirLeft();
                            return turnLeft;
                        } else {
                            actions.add(turnRight);
                            changeDirRight();
                            return turnRight;
                        }
                    }
                }
            } else if (dir == EAST){
                setXcoor(xcoor + 1);
                if(!path.contains("(" + xcoor + ", " + ycoor + ")")){
                    addPos();
                    actions.add(goForward);
                    return goForward;
                } else {
                    if(maxTurnsReached()) {
                        actions.add(goForward);
                        return goForward;
                    } else {
                        setXcoor(xcoor - 1);
                        prob = r.nextFloat();
                        if (prob < 0.5) {
                            actions.add(turnLeft);
                            changeDirLeft();
                            return turnLeft;
                        } else {
                            actions.add(turnRight);
                            changeDirRight();
                            return turnRight;
                        }
                    }
                }
            } else {
                setXcoor(xcoor - 1);
                if(!path.contains("(" + xcoor + ", " + ycoor + ")")){
                    addPos();
                    actions.add(goForward);
                    return goForward;
                } else {
                    if(maxTurnsReached()) {
                        actions.add(goForward);
                        return goForward;
                    } else {
                        setXcoor(xcoor + 1);
                        prob = r.nextFloat();
                        if (prob < 0.5) {
                            actions.add(turnLeft);
                            changeDirLeft();
                            return turnLeft;
                        } else {
                            actions.add(turnRight);
                            changeDirRight();
                            return turnRight;
                        }
                    }
                }
            }
        }

    }

    public boolean maxTurnsReached(){
        int n = actions.size();
        int count = 0;

        System.out.println("Actions");
        System.out.println(actions);

        if(n >= 4){
            for(int i = n-4; i < n; i++){
                Action action = actions.get(i);
                if(action instanceof TurnLeft || action instanceof TurnRight) {
                    count += 1;
                }
            }
        }

        if(count == MAXTURNS){
            return true;
        }

        return false;
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
        System.out.println("path");
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
