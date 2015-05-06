/**
 * Created by anantoni on 1/5/2015.
 */
import factgen.FactGenerator;
import org.deri.iris.Configuration;
import org.deri.iris.EvaluationException;
import org.deri.iris.KnowledgeBase;
import org.deri.iris.api.IKnowledgeBase;
import org.deri.iris.api.basics.IPredicate;
import org.deri.iris.api.basics.IQuery;
import org.deri.iris.api.basics.IRule;
import org.deri.iris.api.terms.IVariable;
import org.deri.iris.compiler.Parser;
import org.deri.iris.compiler.ParserException;
import org.deri.iris.optimisations.magicsets.MagicSets;
import org.deri.iris.storage.IRelation;
import parser.ParseException;
import parser.SpigletParser;
import syntaxtree.Goal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Driver {

    public static void main(String[] args) throws EvaluationException {
        if (args.length != 1) {
            System.err.println("Please give input file");
            System.exit(-1);
        }
        else if (!args[0].endsWith(".spg")) {
            System.err.println("Please give a .spg file as input");
            System.exit(-1);
        }

        File spigletFilePath = new File(args[0]);
        String rootFactsDir = "../generated-facts/";
        String rootAnalysisLogicDir = "../analysis-logic/";
        String rootQueriesDir = "../queries/";
        String projectFactsDir = rootFactsDir + spigletFilePath.getName().replace(".spg", "/");

        try {
            SpigletParser spigletParser = new SpigletParser(new FileReader(args[0]));
            Goal goal = spigletParser.Goal();
            FactGenerator factGenerator = new FactGenerator(projectFactsDir);
            goal.accept(factGenerator, null);
            factGenerator.closeAllFiles();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Parser parser = new Parser();
        Map<IPredicate, IRelation> factMap = new HashMap<>();

        final File factsDirectory = new File(projectFactsDir);
        factsDirectory.mkdir();
        if (factsDirectory.isDirectory()) for (final File fileEntry : factsDirectory.listFiles()) {
            if (fileEntry.isDirectory())
                System.out.println("Omitting directory " + fileEntry.getPath());

            else {
                Reader factsReader = null;
                try {
                    factsReader = new FileReader(fileEntry);
                    parser.parse(factsReader);
                } catch (ParserException e) {
                    System.err.println("Parse exception in file: " + fileEntry.getName());
                    e.printStackTrace();
                    System.exit(-1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // Retrieve the facts and put all of them in factMap
                factMap.putAll(parser.getFacts());
            }
        }
        else {
            System.err.println("Invalid facts directory path: " + projectFactsDir);
            System.exit(-1);
        }

        File copyPropagationRuleFile = new File(rootAnalysisLogicDir+ "copy-propagation.iris");
        Reader rulesReader;
        try {
            rulesReader = new FileReader(copyPropagationRuleFile);
            parser.parse(rulesReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        List<IRule> rules = parser.getRules();


        File constantPropagationRuleFile = new File(rootAnalysisLogicDir+ "constant-propagation.iris");
        try {
            rulesReader = new FileReader(constantPropagationRuleFile);
            parser.parse(rulesReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        rules.addAll(parser.getRules());

        File queriesFile = new File(rootQueriesDir + "queries.iris");
        Reader queriesReader;
        try {
            queriesReader = new FileReader(queriesFile);
            parser.parse(queriesReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        // Retrieve the queries from the parsed file.
        List<IQuery> queries = parser.getQueries();

        // Create a default configuration.
        Configuration configuration = new Configuration();

        // Enable Magic Sets together with rule filtering.
        configuration.programOptmimisers.add(new MagicSets());
        for (IRule rule : rules)
            configuration.ruleSafetyProcessor.process(rule);

        // Create the knowledge base.
        IKnowledgeBase knowledgeBase = new KnowledgeBase(factMap, rules, configuration);

        // Evaluate all queries over the knowledge base.
        for (IQuery query : queries) {
            List<IVariable> variableBindings = new ArrayList<>();
            IRelation relation = knowledgeBase.execute(query, variableBindings);

            // Output the variables.
            System.out.println("\n" + query.toString() + "\n" + variableBindings);

            // Output each tuple in the relation, where the term at position i
            // corresponds to the variable at position i in the variable
            // bindings list.
            for (int i = 0; i < relation.size(); i++) {
                System.out.println(relation.get(i));
            }
        }

    }
}
