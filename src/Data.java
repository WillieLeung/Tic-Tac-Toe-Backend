/*
 * Class Name: Data
 * Purpose: Represent a record in the HashDictionary
 * Author: Willie Leung, 251374052
 */

public class Data {
    private String configuration; // Declare configuration and score associated to that configuration
    private int score;

    public Data(String config, int score){ // Initialize new Data object with specified configuration and score
        configuration = config;
        this.score = score;
    }

    public String getConfiguration() {return configuration;} // Return configuration of this Data object

    public int getScore() {return score;} // Return score in this Data object
}
