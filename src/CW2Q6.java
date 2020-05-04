import java.io.*;
import java.util.*;

public class CW2Q6 {

    // Initialise vars
    private List<String> mainText;
    private List<Character> senStopper;

    /**
     * Constructs an instance of CW2Q6 with two global ArrayLists
     */
    public CW2Q6() {
        mainText = new ArrayList<>();
        senStopper = new ArrayList<>();
        senStopper = Arrays.asList('.', '?', '!', '\n', ':', '\t', '*', '(', ')', (char) 8217);
    }

    /**
     * Returns the length of the specified String
     *
     * @param s - String whose length is to be tested
     * @return the int length of the specified String
     */
    private int getLength(String s) {
        int i = 0;
        for (char c : s.toCharArray()) {
            i++;
        }
        return i;
    }

    /**
     * Returns true if the two specifie Strings are equal
     *
     * @param s1 - first String
     * @param s2 - second String
     * @return true if the Strings are equal
     */
    private boolean areEqual(String s1, String s2) {
        char[] charArr1 = s1.toCharArray();
        char[] charArr2 = s2.toCharArray();
        if (getLength(s1) != getLength(s2)) {return false;}
        for (int i = 0; i < getLength(s1); i++) {
            if (charArr1[i] == charArr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the specified String in all lowercase
     *
     * @param s - String to turn to lowercase
     * @return the lowercase String
     */
    private String toLowercase(String s) {
        char[] charArr = s.toCharArray();
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < getLength(s); i++) {
            if (charArr[i] >= 65 && charArr[i] <= 90) {
                newStr.append((char)(charArr[i] + 32));
            } else {newStr.append(charArr[1]);}
        }
        return newStr.toString();
    }

    /**
     * Returns the specified String in all uppercase
     *
     * @param s - String to turn to uppercase
     * @return the uppercase String
     */
    private String toUppercase(String s) {
        char[] charArr = s.toCharArray();
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < getLength(s); i++) {
            if (charArr[i] >= 97 && charArr[i] <= 122) {
                newStr.append((char)(charArr[i] - 32));
            } else {newStr.append(charArr[i]);}
        }
        return newStr.toString();
    }

    /**
     * Returns true if the specified String starts with a capital letter
     *
     * @param s - String whose first letter wil be tested
     * @return true if a String starts with a capital letter
     */
    private boolean isCapital(String s) {
        if (s.length() > 0) {
            char[] charArr = s.toCharArray();
            return charArr[0] >= 65 && charArr[0] <= 90;
        }
        return false;
    }

    /**
     * Returns true if the specified String starts a sentence, given its preceding string
     *
     * @param previous - String that comes before the String to be tested
     * @param s        - String to be tested
     * @return true if the specified String starts a sentence
     */
    private boolean isSenStart(String previous, String s) {
        if (previous == null) {
            return true;
        }
        if (getLength(s) == 0 || areEqual(previous, "or,")) {
            return false;
        }
        char lastChar = previous.toCharArray()[getLength(previous) - 1];
        if ((int) lastChar == 8221) {
            lastChar = previous.toCharArray()[getLength(previous) - 2];
        }
        return isCapital(s) && senStopper.contains(lastChar);
    }

    /**
     * Returns true if the specified String occurs in the specified List, starting with a non-capital letter
     *
     * @param s    - String to be tested
     * @param list - List to be searched for the String
     * @return true if the String occurs in the List starting with a non-capital letter
     */
    private boolean occursLower(String s, List<String> list) {
        s = toLowercase(s);
        for (String x : list) {
            if (areEqual(s, stripPunct(x))) {
                // the string does occur in lowercase
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a String that is equivalent to the specified String with any characters that are not a letter removed
     *
     * @param s - String to be stripped of punctuation
     * @return String with only letter characters
     */
    private String stripPunct(String s) {
        StringBuilder newStr = new StringBuilder();
        // loops through each char and adds them to the new string only if they are letters and all in uppercase
        for (char c : s.toCharArray()) {
            if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)) {
                // lowercase or uppercase letters
                newStr.append(c);
            }
        }
        return newStr.toString();
    }

    /**
     * Returns a List of Strings read in from the specified file, split by lines and then spaces
     * @param filename - the name of the file to open and read
     * @return the List of Strings read in from the file
     */
    private List<String> readInFile(String filename) {
        List<String> stringList = new ArrayList<>();
        try {
            String buffer;
            Scanner s = new Scanner(new File(filename));
            while (s.hasNextLine()) {
                // reading in line by line and then splitting lines by spaces
                buffer = s.nextLine();
                Scanner subs = new Scanner(buffer);
                while (subs.hasNext()) {
                    stringList.add(subs.next());
                }
                stringList.add("\n");
                subs.close();
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found.");
        }
        return stringList;
    }

    /**
     * Returns a List of Strings read in from the specified file, split by spaces and stripped of punctuation
     * @param filename - the name of the file to open and read
     * @return the List of Strings read in from the file
     */
    private List<String> readInFileStripped(String filename) {
        List<String> stringList = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(filename));
            while (s.hasNext()) {
                stringList.add(stripPunct(s.next()));
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found.");
        }
        return stringList;
    }

    /**
     * Prints the specified List of Strings out to the specified file, adding spaces
     * @param filename - the name of the file to write to
     * @param list - the Strings to be written to the file
     */
    private void printToFile(String filename, List<String> list) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            for (String s : list) {
                // since reading in the file stripped spaces, we need to put them back
                out.print(s + " ");
            }
            out.close();
            System.out.println("File located in \"output.txt\"");
        } catch (IOException ignored) {
            System.out.println("Error outputting to file");
        }
    }

    /**
     * Returns the specified List of Strings with any words in the HashSet redacted,
     * as long as they start with a capital letter
     * @param redactSet - The HashSet of Strings to redact from the text
     * @param text - The list of Strings to redact from
     * @return the List of Strings with any words in the HashSet redacted
     */
    private List<String> redact(HashSet<String> redactSet, List<String> text) {
        List<String> newText = new ArrayList<>();
        // i represents the index of the word being checked
        int i = 0;
        for (String s : text) {
            if (redactSet.contains(stripPunct(s)) && isCapital(stripPunct(s))) {
                StringBuilder redacted = new StringBuilder(getLength(s));
                for (int j = 0; j < getLength(s); j++) {
                    redacted.append("*");
                }
                newText.add(redacted.toString());
            } else {
                // don't redact the word, add it to the new text
                newText.add(s);
            }
            i++;
        }
        return newText;
    }

    /**
     * Returns a HashSet containing proper nouns found in the specified text
     * @param redactableWords - The HashSet of Strings that are deemed proper nouns
     * @param text - The List of Strings to search for proper nouns
     * @return the HashSet containing proper nouns
     */
    private HashSet<String> addProperNouns(HashSet<String> redactableWords, List<String> text) {
        HashSet<String> commonNouns = new HashSet<String>();
        // I is a special case, it is a pronoun but always capitalised
        commonNouns.add("I");
        // previous is used to evaluate whether the current string starts a sentence
        String previous = null;
        String sStripped;
        for (String s : text) {
            // loop through every word in the text and if it satisfies certain conditions, mark as proper noun
            // keeping track of common nouns means you don't have to re-evaluate them many times over
            sStripped = stripPunct(s);
            if (redactableWords.contains(sStripped) || commonNouns.contains(sStripped)) {
                // skip this word, we don't need to re-evaluate it
                previous = s;
                continue;
            } else if (areEqual(sStripped, toUppercase(sStripped))) {
                // formatting choice: if a word is all capitals, its not necessarily a proper noun
                commonNouns.add(sStripped);
                previous = s;
                continue;
            } else if (!isCapital(sStripped)) {
                commonNouns.add(sStripped);
            } else if (isCapital(sStripped) && !isSenStart(previous, sStripped)) {
                // this is a proper noun, add to the hashset
                redactableWords.add(sStripped);
            } else if (isSenStart(previous, sStripped) && !occursLower(sStripped, text)) {
                // this is more than likely a proper noun
                redactableWords.add(sStripped);
            } else {
                commonNouns.add(sStripped);
            }
            previous = s;
        }
        return redactableWords;
    }

    /**
     * Runs the logic to redact proper nouns from a given text
     */
    public void run() {
        // read in the files to redact from and the initial set of redactable words
        mainText = readInFile("warandpeace.txt");
        HashSet<String> redactableWords = new HashSet<String>(readInFileStripped("redact2.txt"));
        // add proper nouns to the HashSet
        redactableWords = addProperNouns(redactableWords, mainText);
        // redact everything in the HashSet of redactable words
        mainText = redact(redactableWords, mainText);
        // output to a file
        printToFile("output.txt", mainText);
    }

    /**
     * Main method creates an instance of CW2Q6 and calls run
     * @param args - N/A
     */
    public static void main(String[] args) {
        CW2Q6 redactable = new CW2Q6();
        redactable.run();
    }
}
