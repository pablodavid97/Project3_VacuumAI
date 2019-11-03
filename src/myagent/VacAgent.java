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
import java.util.Collections;
import java.util.Comparator;
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
    private Coordinate coor = new Coordinate(0, 0);
    private int dir = NORTH;
    private ArrayList<Coordinate> path = new ArrayList<>();
    private ArrayList<Action> actions = new ArrayList<>();
    private ArrayList<Coordinate> obstaclesPos = new ArrayList<>();

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
        String coor;

        if(path.size() + obstaclesPos.size() == TABLE_SIZE){
            System.out.println("Path Size");
            System.out.println(path.size());

            System.out.println("Path");
            System.out.println(path);

            System.out.println("Obstacles size");
            System.out.println(obstaclesPos.size());
            System.out.println("obstacles");
            System.out.println(obstaclesPos);

            return action;
        }

        Coordinate c;
        if (obstacleInFront) {
            switch (dir) {
                case NORTH:
                    c = new Coordinate(this.coor.getXcoor(), this.coor.getYcoor() + 1);
                    if(!obstaclesPos.contains(c)){
                        obstaclesPos.add(c);
                    }
                    break;
                case SOUTH:
                    c = new Coordinate(this.coor.getXcoor(), this.coor.getYcoor() - 1);
                    if(!obstaclesPos.contains(c)){
                        obstaclesPos.add(c);
                    }
                    break;
                case EAST:
                    c = new Coordinate(this.coor.getXcoor() + 1, this.coor.getYcoor());
                    if(!obstaclesPos.contains(c)){
                        obstaclesPos.add(c);
                    }
                    break;
                case WEST:
                    c = new Coordinate(this.coor.getXcoor() - 1, this.coor.getYcoor());
                    if(!obstaclesPos.contains(c)){
                        obstaclesPos.add(c);
                    }
                    break;
            }

            System.out.println("obstacles");
            Collections.sort(obstaclesPos, new Comparator<Coordinate>() {
                @Override
                public int compare(Coordinate o1, Coordinate o2) {
                    return Integer.compare(o1.getYcoor(), o2.getYcoor());
                }
            });
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
                this.coor.setYcoor(this.coor.getYcoor() + 1);
                if(!path.contains(this.coor)){
                    addPos();
                    actions.add(goForward);
                    return goForward;
                } else {
                    if(maxTurnsReached()) {
                      actions.add(goForward);
                      return goForward;
                    } else {
                        this.coor.setYcoor(this.coor.getYcoor() - 1);
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
                this.coor.setYcoor(this.coor.getYcoor() - 1);
                if(!path.contains(this.coor)){
                    addPos();
                    actions.add(goForward);
                    return goForward;
                } else {
                    if(maxTurnsReached()) {
                        actions.add(goForward);
                        return goForward;
                    } else {
                        this.coor.setYcoor(this.coor.getYcoor() + 1);
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
                this.coor.setXcoor(this.coor.getXcoor() + 1);
                if(!path.contains(this.coor)){
                    addPos();
                    actions.add(goForward);
                    return goForward;
                } else {
                    if(maxTurnsReached()) {
                        actions.add(goForward);
                        return goForward;
                    } else {
                        this.coor.setXcoor(this.coor.getXcoor() - 1);
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
                this.coor.setXcoor(this.coor.getXcoor() - 1);
                if(!path.contains(this.coor)){
                    addPos();
                    actions.add(goForward);
                    return goForward;
                } else {
                    if(maxTurnsReached()) {
                        actions.add(goForward);
                        return goForward;
                    } else {
                        this.coor.setXcoor(this.coor.getXcoor() + 1);
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
        Coordinate c = new Coordinate(coor.getXcoor(), coor.getYcoor());
        path.add(c);
        System.out.println("path");
        System.out.println(path);
    }

    public String getId() {
        return ID;
    }

    public void setDir(int dir){
        this.dir = dir;
    }
}
