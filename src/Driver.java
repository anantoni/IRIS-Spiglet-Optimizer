/**
 * Created by anantoni on 1/5/2015.
 */
import factgen.FactGenerator;
import parser.ParseException;
import parser.SpigletParser;
import syntaxtree.Goal;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Driver {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please give input file");
            System.exit(-1);
        }

        try {
            SpigletParser parser = new SpigletParser(new FileReader(args[0]));
            Goal goal = parser.Goal();
            goal.accept(new FactGenerator(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
