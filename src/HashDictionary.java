/*
 * Class Name: HashDictionary
 * Purpose: Implements DictionaryADT with a hash table with separate chaining
 * Author: Willie Leung
 */

import java.util.LinkedList; // Import built in Java LinkedList class

public class HashDictionary implements DictionaryADT {
    private LinkedList<Data>[] T; // Declare hash table and its size
    private int size;

    /*
     * Constructor HashDictionary(size)
     * Input: Size of the hash table
     * Output: None
     */

    public HashDictionary(int size){
        this.size = size; // Initialize size
        T = new LinkedList[size]; // Initialize hash table
        for (int i = 0; i < size; i++){
            T[i] = new LinkedList<Data>();
        }
    }

    /*
     * Algorithm put(pair)
     * Input: A pair object to insert into the dictionary
     * Output: Integer representing if there was a collision or not, or dictionary exception if the record is already in the dictionary
     */
    public int put (Data pair) throws DictionaryException{
        if (get(pair.getConfiguration()) != -1) throw new DictionaryException(); // Throw exception if record is in dictionary
        else{
            int collision; // Declare variable to indicate collision
            LinkedList<Data> list = T[h(pair.getConfiguration(), size)];
            if (list.isEmpty()){collision = 0;} // Determine if there is a collision
            else {collision = 1;}
            list.add(pair); // Add record to dictionary
            return collision;
        }
    }

    /*
     * Algorithm remove(config)
     * Input: Configuration to remove the record of
     * Output: None or dictionary exception if the record is not in the dictionary
     */
    public void remove (String config) throws DictionaryException {
        if (get(config) == -1) throw new DictionaryException(); // Throw exception if record with the given configuration is not in the dictionary
        else{
            LinkedList<Data> list = T[h(config, size)]; // Look for record with given configuration
            for (int i = 0; i < list.size(); i++){
                if (list.get(i).getConfiguration().equals(config)){
                    list.remove(i); // Remove record from dictionary
                }
            }

        }
    }

    /*
     * Algorithm get(config)
     * Input: Configuration to get the score of
     * Output: Integer representing the score of the given configuration, -1 if record with given configuration is not in the dictionary
     */
    public int get (String config) {
       LinkedList<Data> list = T[h(config, size)];
       for (int i = 0; i < list.size(); i++){ // Look for record with given configuration
           if (list.get(i).getConfiguration().equals(config)) return i;
       }
       return -1; // If its not in the dictionary return -1
    }

    /*
     * Algorithm numRecords()
     * Input: None
     * Output: Integer representing the number of records in the dictionary
     */
    public int numRecords() {
        int numRec = 0; // Initialize sum variable
        for (int i = 0; i < size; i++) { // Calculate sum
            numRec = numRec + T[i].size();
        }
        return numRec; // Return sum
    }

    /*
     * Algorithm h(key, tableSize)
     * Input: A key to map onto the hash table that has a size of tableSize
     * Output: Integer representing the hash code of the given key
     */
    public int h(String key, int tableSize){
        int val = key.charAt(key.length()-1); // Initalize value

        for (int i = key.length() - 2; i >= 0; i--) { // Calculate hash code
            val = (val * 39 + (int) key.charAt(i)) % tableSize;
        }
        return val; // Return hash code
    }
}
