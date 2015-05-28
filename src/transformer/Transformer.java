package transformer;

import syntaxtree.*;
import utilities.Triple;
import visitor.DepthFirstRetArguVisitor;
import visitor.IRetArguVisitor;
import visitor.IVoidArguVisitor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by anantoni on 1/5/2015.
 */
public class Transformer extends DepthFirstRetArguVisitor<String, String> implements IRetArguVisitor<String, String> {

    private PrintWriter optimizedSpigletWriter;
    private int instructionCounter;
    private IRetArguVisitor<String, String> varUseTransformer;
    private IVoidArguVisitor<String> instructionLabelTransformer;
    private Map<Triple<String, Integer>, Integer> constantMap;
    private Map<Triple<String, Integer>, String> copyMap;
    Map<String, Set<Integer>> deadInstructionMap;

    public Transformer(String projectOptOutDir, String outFile, Map<Triple<String, Integer>, Integer> constantMap,  Map<Triple<String, Integer>, String> copyMap, Map<String, Set<Integer>> deadInstructionMap) {

        try {
            optimizedSpigletWriter = new PrintWriter(projectOptOutDir + "/" + outFile, "UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        this.varUseTransformer = new VarUseTransformer(this);
        this.instructionLabelTransformer = new InstructionLabelTransformer(optimizedSpigletWriter);
        this.instructionCounter = 1;
        this.constantMap = constantMap;
        this.copyMap = copyMap;
        this.deadInstructionMap = deadInstructionMap;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public void closeSpigletFile() {
        optimizedSpigletWriter.close();
    }

    public int getInstructionCounter() {
        return instructionCounter;
    }

    public String visit(final NodeChoice n, final String argu) {

        final String nRes = n.choice.accept(this, argu);
        return nRes;
    }

    public String visit(final NodeList n, final String argu) {
        String nRes = null;
        for (final Iterator<INode> e = n.elements(); e.hasNext();) {
            @SuppressWarnings("unused")
            final String sRes = e.next().accept(this, argu);
        }
        return nRes;
    }

    public String visit(final NodeListOptional n, final String argu) {
        if (n.present()) {
            String nRes = null;
            int counter = 0;

            for (final Iterator<INode> e = n.elements(); e.hasNext();) {
                String sRes = e.next().accept(this, argu);

                if (counter == 0)
                    nRes = sRes;
                else
                    nRes += " " + sRes;
                counter++;
            }
            return nRes;
        } else
            return null;
    }

    public String visit(final NodeOptional n, final String argu) {
        if (n.present()) {
            final String nRes = n.node.accept(this, argu);
            return nRes;
        } else
            return null;
    }

    public String visit(final NodeSequence n, final String argu) {
        String nRes = null;
        for (final Iterator<INode> e = n.elements(); e.hasNext();) {
            INode next = e.next();
            String subRet = next.accept(this, argu);
            next.accept(this.instructionLabelTransformer, argu);
        }
        return nRes;
    }

    public String visit(final NodeToken n, @SuppressWarnings("unused") final String argu) {
        String nRes = null;
        @SuppressWarnings("unused")
        final String tkIm = n.tokenImage;
        return nRes;
    }

    public String visit(final Goal n, String argu) {
        String nRes = null;
        argu = "MAIN";
        optimizedSpigletWriter.println("MAIN");
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        optimizedSpigletWriter.println("END");
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return nRes;
    }

    public String visit(final StmtList n, final String argu) {
        String nRes = null;
        n.f0.accept(this, argu);

        return nRes;
    }

    public String visit(final Procedure n, String argu) {
        this.instructionCounter = 1;
        argu = n.f0.accept(this, argu);

        String instructionLiteral = argu;
        instructionLiteral += " [";
        instructionLiteral += n.f2.accept(this, argu);
        instructionLiteral += "]";
        optimizedSpigletWriter.println(instructionLiteral);
        n.f4.accept(this, argu);

        return null;
    }

    public String visit(final Stmt n, final String argu) {
        String nRes = null;
        n.f0.accept(this, argu);
        return nRes;
    }

    public String visit(final NoOpStmt n, final String argu) {
        n.f0.accept(this, argu);

        optimizedSpigletWriter.println("NOOP");

        return n.f0.toString();
    }

    public String visit(final ErrorStmt n, final String argu) {
        n.f0.accept(this, argu);

        optimizedSpigletWriter.println("ERROR");
        return n.f0.toString();
    }

    public String visit(final CJumpStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();

        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this, argu);
        n.f1.accept(this.varUseTransformer, argu);
        String label = n.f2.accept(this, argu);
        instructionLiteral += " " + label;

        optimizedSpigletWriter.println(instructionLiteral);
        return instructionLiteral;
    }

    public String visit(final JumpStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();

        n.f0.accept(this, argu);
        String label = n.f1.accept(this, argu);
        instructionLiteral += " " + label;

        optimizedSpigletWriter.println(instructionLiteral);
        return instructionLiteral;
    }

    public String visit(final HStoreStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();

        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this.varUseTransformer, argu);
        instructionLiteral += " " + n.f2.accept(this, argu);
        instructionLiteral += " " + n.f3.accept(this.varUseTransformer, argu);

        optimizedSpigletWriter.println(instructionLiteral);

        return instructionLiteral;
    }

    public String visit(final HLoadStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();

        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this, argu);

        instructionLiteral += " " + n.f2.accept(this.varUseTransformer, argu);

        instructionLiteral += " " + n.f3.accept(this, argu);

        optimizedSpigletWriter.println(instructionLiteral);
        return instructionLiteral;
    }

    public String visit(final MoveStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();

        n.f0.accept(this, argu);
        String varDecl = n.f1.accept(this, argu);
        instructionLiteral += " " + varDecl;

        String simpleExp = n.f2.accept(this, argu);
        instructionLiteral += " " + simpleExp;


        return instructionLiteral;
    }

    public String visit(final PrintStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();

        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this, argu);
        n.f1.accept(this.varUseTransformer, argu);

        optimizedSpigletWriter.println();
        return instructionLiteral;
    }

    public String visit(final Exp n, final String argu) {
        return n.f0.accept(this, argu);
    }

    public String visit(final StmtExp n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        String instructionLiteral = n.f2.tokenImage;
        instructionLiteral += " " + n.f3.accept(this, argu);
        n.f3.accept(this.varUseTransformer, argu);
        n.f4.accept(this, argu);

        optimizedSpigletWriter.println(instructionLiteral);
        return instructionLiteral;
    }

    public String visit(final Call n, final String argu) {
        String instructionLiteral = n.f0.toString();

        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this.varUseTransformer, argu);
        n.f2.accept(this, argu);
        instructionLiteral += " " + n.f2.tokenImage;
        instructionLiteral += " " + n.f3.accept(this.varUseTransformer, argu);

        n.f4.accept(this, argu);
        instructionLiteral += " " + n.f4.tokenImage;

        return instructionLiteral;
    }

    public String visit(final HAllocate n, final String argu) {
        String instructionLiteral = n.f0.toString();
        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this.varUseTransformer, argu);

        return instructionLiteral;
    }

    public String visit(final BinOp n, final String argu) {
        String instructionLiteral = n.f0.f0.choice.toString();
        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this, argu);
        String firstOperand = n.f1.accept(this.varUseTransformer, argu);

        instructionLiteral += " " + n.f2.accept(this, argu);
        String secondOperand = n.f1.accept(this.varUseTransformer, argu);



        return instructionLiteral;
    }

    public String visit(final Operator n, final String argu) {
        n.f0.accept(this, argu);

        return n.f0.choice.toString();
    }

    public String visit(final SimpleExp n, final String argu) {
        String simpleExp = n.f0.accept(this, argu);

        return simpleExp;
    }

    public String visit(final Temp n, final String argu) {
        n.f0.accept(this, argu);
        String var = n.f0.toString();
        var += " " + n.f1.accept(this, argu);

        return var;
    }

    public String visit(final IntegerLiteral n, final String argu) {
        n.f0.accept(this, argu);
        return n.f0.toString();
    }

    public String visit(final Label n, final String argu) {
        n.f0.accept(this, argu);
        return n.f0.toString();
    }
}
