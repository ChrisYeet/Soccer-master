package cs301.Soccer;

import static java.lang.Integer.parseInt;

import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    private HashMap<String, SoccerPlayer>  database = new HashMap<>();

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

        if(database.containsKey(temp)) {
            return database.get(temp);
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
            database.get(temp).bumpGoals();
            return true;
        }
        return false;
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
            database.get(temp).bumpYellowCards();
            return true;
        }
        return false;
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
            database.get(temp).bumpRedCards();
            return true;
        }
        return false;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        int numTemp = 0;

        if(teamName == null) {
            return database.keySet().size();
        }else {
            for(SoccerPlayer obj : database.values()) {
                if(obj.getTeamName().equalsIgnoreCase(teamName)) {
                    numTemp++;
                }
            }
            return numTemp;
        }
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

        for(SoccerPlayer obj : database.values()) {
            if(teamName == null) {
                return null;
            }else if(obj.getTeamName().equalsIgnoreCase(teamName)) {
                if(numTemp == idx) {
                    return obj;
                }
                numTemp++;
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
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while(scanner.hasNext()) {
                SoccerPlayer soccerPlayer;
                String temp;
                String first = scanner.next();
                String last = scanner.next();
                int uniform = scanner.nextInt();
                String team = scanner.next();
                int int1 = scanner.nextInt();
                int int2 = scanner.nextInt();
                int int3 = scanner.nextInt();

                soccerPlayer = new SoccerPlayer(first, last, uniform, team);
                for(int i = 0; i <= int1; i++) {
                    soccerPlayer.bumpGoals();
                }
                for(int i = 0; i <= int2; i++) {
                    soccerPlayer.bumpRedCards();
                }
                for(int i = 0; i <= int3; i++) {
                    soccerPlayer.bumpYellowCards();
                }

                temp = first + " ## " + last;
                database.put(temp, soccerPlayer);
            }

            scanner.close();
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

        try {
            printWriter = new PrintWriter(file);
            for(SoccerPlayer obj : database.values()) {
                printWriter.println(logString(obj.getFirstName()));
                printWriter.println(logString(obj.getLastName()));
                printWriter.println(logString(Integer.toString(obj.getUniform())));
                printWriter.println(logString(obj.getTeamName()));
                printWriter.println(logString(Integer.toString(obj.getGoals())));
                printWriter.println(logString(Integer.toString(obj.getRedCards())));
                printWriter.println(logString(Integer.toString(obj.getYellowCards())));
            }
            printWriter.close();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
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
        HashSet<String> teams = new HashSet<>();

        for(SoccerPlayer obj : database.values()) {
            String temp = obj.getFirstName() + " ## " + obj.getLastName();
            if(database.containsKey(temp)) {
                database.put(temp, obj);
            }
            teams.add(obj.getTeamName());
        }
        
        return teams;
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
