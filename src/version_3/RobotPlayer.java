package version_3;

import battlecode.common.*;

import java.util.Random;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {

    /**
     * We will use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want. Keep in mind that even though
     * these variables are static, in Battlecode they aren't actually shared between your robots.
     */
    static int turnCount = 0;
    static int botType = 0;
    static int Disdirection = 0;



    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */
    static final Random rng = new Random(6147);

    /** Array containing all the possible movement directions. */
    static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc  The RobotController object. You use it to perform actions from this robot, and to get
     *            information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you run a match!
        System.out.println("I'm alive");

        // You can also use indicators to save debug notes in replays.
        rc.setIndicatorString("Hello world!");

        while (true) {
            // This code runs during the entire lifespan of the robot, which is why it is in an infinite
            // loop. If we ever leave this loop and return from run(), the robot dies! At the end of the
            // loop, we call Clock.yield(), signifying that we've done everything we want to do.

            // First check for type
            // if no BigThinker, this is BigThinker
            if (turnCount == 0 ) {

                int dir_ind_0 = rng.nextInt(directions.length);
                Disdirection = dir_ind_0;
                int control_0_state = rc.readSharedArray(0);
                if (control_0_state == 0) {
                    botType = 0; // Big Thinker
                    rc.writeSharedArray(0,1);
                }
                else if (control_0_state < 3) {
                    botType = 1; // BUILDER
                    rc.writeSharedArray(0,control_0_state + 1);
                }
                else{
                    botType = 2; // DRONE
                    rc.writeSharedArray(0,control_0_state+1);
                }
            }



            turnCount += 1;  // We have now been alive for one more turn!

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {
                // Make sure you spawn your robot in before you attempt to take any actions!
                // Robots not spawned in do not have vision of any tiles and cannot perform any actions.
                // Ensure the robot is spawned before taking any actions
                if (!rc.isSpawned()) {
                    MapLocation[] spawnLocs = rc.getAllySpawnLocations();
                    // Read the shared array to get the starting index for spawn locations
                    int spawnLocIndex = rc.readSharedArray(1) % spawnLocs.length;

                    // Iterate over all spawn locations starting from the index
                    for (int i = 0; i < spawnLocs.length; i++) {
                        int currentIndex = (spawnLocIndex + i) % spawnLocs.length;
                        if (rc.canSpawn(spawnLocs[currentIndex])) {
                            rc.spawn(spawnLocs[currentIndex]);
                            rc.writeSharedArray(1, currentIndex + 1);
                            break; // Exit once a valid location is found
                        }
                    }

                    // Optionally add logic here if no valid spawn location is found
                }

                else{
                    if (rc.canPickupFlag(rc.getLocation())){
                        rc.pickupFlag(rc.getLocation());
                        rc.setIndicatorString("Holding a flag!");
                    }
                    // If we are holding an enemy flag, singularly focus on moving towards
                    // an ally spawn zone to capture it! We use the check roundNum >= SETUP_ROUNDS
                    // to make sure setup phase has ended.
                    if (rc.hasFlag() && rc.getRoundNum() >= GameConstants.SETUP_ROUNDS){
                        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
                        MapLocation firstLoc = spawnLocs[0];
                        Direction dir = rc.getLocation().directionTo(firstLoc);
                        if (rc.canMove(dir)) rc.move(dir);
                    }

                    // Move and attack randomly if no objective.

                    Direction dir = directions[Disdirection];
                    Direction dir2 = directions[Disdirection];
                    int direction_ind = 0;
                    // 
                    MapLocation nextLoc = rc.getLocation().add(dir2);
                    MapLocation og_next_loc = nextLoc;

                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }
                    // now do front to left
                    direction_ind = (direction_ind + 2) % 8;
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);
                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }

                    // now do front to right front
                    direction_ind = (direction_ind - 2+8) % 8;
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);
                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }
                    
                    // Now front front
                    direction_ind = (direction_ind - 2+8) % 8;
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);

                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }

                    // Now front left front
//                    direction_ind = direction_ind; no change here
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);
                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }

                    // Now front left
                    direction_ind = (direction_ind - 2+8) % 8;
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);
                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }

                    // Now left
//                    direction_ind = direction_ind; no change here
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);
                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }

                    // Now left back
//                    direction_ind = direction_ind; no change here
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);
                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }

                    // Now back
                    direction_ind = (direction_ind - 2+8) % 8;
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);
                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }
                    // Now right back
//                    direction_ind = direction_ind; no change here
                    dir2 = directions[direction_ind];
                    nextLoc = nextLoc.add(dir2);
                    if (rc.canAttack(nextLoc)){
                        rc.attack(nextLoc);
                        System.out.println("Take that! Damaged an enemy that was in our way!");
                    }

                    // Sense for enemies
                    RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
                    // if you find one enemy robot, change direction to point towards it
                    if (enemyRobots.length > 0) {
                        MapLocation enem_loc = enemyRobots[0].getLocation();
                        // get current location
                        MapLocation cur_loc = rc.getLocation();
                        // get direction to enemy
                        Direction dir_to_enem = cur_loc.directionTo(enem_loc);
                        // Update the direct
                        Disdirection = dir_to_enem.ordinal();
                    }
                    // Check that this location exists on the map
                    if (rc.onTheMap(og_next_loc)) {
                        MapInfo nextLocMapInfo = rc.senseMapInfo(og_next_loc);
                        if (nextLocMapInfo.isWater()) {
                            if (rc.canFill(og_next_loc)) {
                                rc.fill(og_next_loc);
                            }
                        }
                    }

                    
                    if (rc.canMove(dir)){
                        rc.move(dir);
                    } else {
                        Disdirection = (Disdirection + 3 + rng.nextInt(2)) % 8;
                        dir = directions[Disdirection];
                        if (rc.canMove(dir)){
                            rc.move(dir);
                        }
                    }

                    // Rarely attempt placing traps behind the robot.
                    MapLocation prevLoc = rc.getLocation().subtract(dir);
                    if (rc.canBuild(TrapType.EXPLOSIVE, prevLoc) && rng.nextInt() % 37 == 1)
                        rc.build(TrapType.EXPLOSIVE, prevLoc);
                    // We can also move our code into different methods or classes to better organize it!
                    updateEnemyRobots(rc);
                }

            } catch (GameActionException e) {
                // Oh no! It looks like we did something illegal in the Battlecode world. You should
                // handle GameActionExceptions judiciously, in case unexpected events occur in the game
                // world. Remember, uncaught exceptions cause your robot to explode!
                System.out.println("GameActionException");
                e.printStackTrace();

            } catch (Exception e) {
                // Oh no! It looks like our code tried to do something bad. This isn't a
                // GameActionException, so it's more likely to be a bug in our code.
                System.out.println("Exception");
                e.printStackTrace();

            } finally {
                // Signify we've done everything we want to do, thereby ending our turn.
                // This will make our code wait until the next turn, and then perform this loop again.
                Clock.yield();
            }
            // End of loop: go back to the top. Clock.yield() has ended, so it's time for another turn!
        }

        // Your code should never reach here (unless it's intentional)! Self-destruction imminent...
    }
    public static void updateEnemyRobots(RobotController rc) throws GameActionException{
        // Sensing methods can be passed in a radius of -1 to automatically
        // use the largest possible value.
        RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        if (enemyRobots.length != 0){
            rc.setIndicatorString("There are nearby enemy robots! Scary!");
            // Save an array of locations with enemy robots in them for future use.
            MapLocation[] enemyLocations = new MapLocation[enemyRobots.length];
            for (int i = 0; i < enemyRobots.length; i++){
                enemyLocations[i] = enemyRobots[i].getLocation();
            }
            // Let the rest of our team know how many enemy robots we see!
            if (rc.canWriteSharedArray(0, enemyRobots.length)){
                rc.writeSharedArray(0, enemyRobots.length);
                int numEnemies = rc.readSharedArray(0);
            }
        }
    }
}
