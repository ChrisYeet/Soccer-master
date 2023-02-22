package cs301.Soccer;

import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** Christopher Yee ***
 * @version *** 2/21/23 ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private Hashtable database = new Hashtable();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {
        String temp = firstName + " ## " + lastName;

        if(database.containsKey(temp)) {
            return false;
        }else {
            SoccerPlayer player = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);
            database.put(temp, player);
            return true;
        }
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        String temp = firstName + " ## " + lastName;

        if(database.containsKey(temp)) {
            database.remove(temp);
            return true;
        }
        return false;
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        String temp = firstName + " ## " + lastName;

            if (database.containsKey(temp)) {
                Object player = database.get(temp);
                return (SoccerPlayer) player;
            }

        return null;
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        String temp = firstName + " ## " + lastName;

        if(database.containsKey(temp)) {
            SoccerPlayer goals = (SoccerPlayer) database.get(temp);
            goals.bumpGoals();
            return true;
        }else {
            return false;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        String temp = firstName + " ## " + lastName;

        if(database.containsKey(temp)) {
            SoccerPlayer goals = (SoccerPlayer) database.get(temp);
            goals.bumpYellowCards();
            return true;
        }else {
            return false;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        String temp = firstName + " ## " + lastName;

        if(database.containsKey(temp)) {
            SoccerPlayer goals = (SoccerPlayer) database.get(temp);
            goals.bumpRedCards();
            return true;
        }else {
            return false;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        String temp = teamName;
        int numTemp = 0;

        if(temp == "### ALL ###") {
            return database.keySet().size();
        }
        Iterator iterator = database.keySet().iterator();
        while(iterator.hasNext()) {
            numTemp++;
            if(iterator.equals(temp)) {
                return numTemp;
            }
        }

        return numTemp;
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        int numTemp = 0;
        if(idx > database.size()) {
            return null;
        }

        Iterator iterator = database.entrySet().iterator();
        while(iterator.hasNext()) {
            numTemp++;

            if(teamName == null) {
                if(iterator.equals(database.size())) {
                    return (SoccerPlayer) database.elements();
                }
            }
            else {
                if (iterator.equals(teamName)) {
                    return (SoccerPlayer) database.elements();
                }
            }
        }
        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        return file.exists();
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        PrintWriter printWriter = null;
        Enumeration<Integer> temp = database.keys();

        try {
            printWriter = new PrintWriter(file);
            while(temp.hasMoreElements()) {
                int keys = temp.nextElement();
                printWriter.println(logString(database.get(keys).getFirstName()));
                printWriter.println(logString(database.get(keys).getLastName()));
                printWriter.println(logString(database.get(keys).uniformNumber()));
                printWriter.println(logString(database.get(keys).teamNumber()));
            }
            printWriter.close();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            return false;
        }
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
//        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        Enumeration<Integer> temp = database.keys();
        Integer[] array = new Integer[database.size()];
        while(temp.hasMoreElements()) {
            int keys = temp.nextElement();
            for (int i = 0; i < database.keySet().size(); i++) {
                array[i] = Integer.parseInt((String) database.get(keys));
            }
        }
        return getTeams();
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if(database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
