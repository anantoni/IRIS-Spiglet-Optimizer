package factgen;

import syntaxtree.*;
import visitor.DepthFirstRetArguVisitor;

import java.util.Iterator;

/**
 * Created by anantoni on 1/5/2015.
 */
public class FactGenerator extends DepthFirstRetArguVisitor<String, String> {
    private int instructionCounter;

    public FactGenerator() {
        instructionCounter = 0;
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
            for (final Iterator<INode> e = n.elements(); e.hasNext();) {
                @SuppressWarnings("unused")
                String sRes = e.next().accept(this, argu);
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
            @SuppressWarnings("unused")
            String subRet = e.next().accept(this, argu);
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
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
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
        String nRes = null;
        argu = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return nRes;
    }

    public String visit(final Stmt n, final String argu) {
        String nRes = null;
        n.f0.accept(this, argu);
        return nRes;
    }

    public String visit(final NoOpStmt n, final String argu) {
        n.f0.accept(this, argu);
        return n.f0.toString();
    }

    public String visit(final ErrorStmt n, final String argu) {
        n.f0.accept(this, argu);
        return n.f0.toString();
    }

    public String visit(final CJumpStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();
        int currentInstructionCounter = instructionCounter++;

        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this, argu);
        instructionLiteral += " " + n.f2.accept(this, argu);

        String instructionEDB = "instruction(" + currentInstructionCounter + ",\'" + argu + "\',\'" + instructionLiteral + "\').";
        System.out.println(instructionEDB);
        return instructionLiteral;
    }

    public String visit(final JumpStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();
        int currentInstructionCounter = instructionCounter++;

        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this, argu);

        String instructionEDB = "instruction(" + currentInstructionCounter + ",\'" + argu + "\',\'" + instructionLiteral + "\').";
        System.out.println(instructionEDB);
        return instructionLiteral;
    }

    public String visit(final HStoreStmt n, final String argu) {
        String nRes = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return nRes;
    }

    public String visit(final HLoadStmt n, final String argu) {
        String nRes = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return nRes;
    }

    public String visit(final MoveStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();
        int currentInstructionCounter = instructionCounter++;

        n.f0.accept(this, argu);
        String varDecl = n.f1.accept(this, argu);
        instructionLiteral += " " + varDecl;
        instructionLiteral += " " + n.f2.accept(this, argu);

        String instructionEDB = "instruction(" + currentInstructionCounter + ",\'" + argu + "\',\'" + instructionLiteral + "\').";
        System.out.println(instructionEDB);

        String varEDB = "var(" + "\'" + argu + "\', \'" + varDecl + "\').";
        System.out.println(varEDB);

        String varDefEDB = "varDef(" + currentInstructionCounter + ",\'" + varDecl + "\').";
        System.out.println(varDefEDB);

        return instructionLiteral;
    }

    public String visit(final PrintStmt n, final String argu) {
        String instructionLiteral = n.f0.toString();
        int currentInstructionCounter = instructionCounter++;

        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this, argu);

        String instructionEDB = "instruction(" + currentInstructionCounter + ",\'" + argu + "\',\'" + instructionLiteral + "\').";
        System.out.println(instructionEDB);
        return instructionLiteral;
    }

    public String visit(final Exp n, final String argu) {
        return n.f0.accept(this, argu);
    }

    public String visit(final StmtExp n, final String argu) {
        String nRes = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return nRes;
    }

    public String visit(final Call n, final String argu) {
        String nRes = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return nRes;
    }

    public String visit(final HAllocate n, final String argu) {
        String hAllocateLiteral = n.f0.accept(this, argu);
        String simpleExp = n.f1.accept(this, argu);
        String instructionLiteral = hAllocateLiteral + " " + simpleExp;

        return instructionLiteral;
    }

    public String visit(final BinOp n, final String argu) {
        String instructionLiteral = n.f0.f0.choice.toString();
        n.f0.accept(this, argu);
        instructionLiteral += " " + n.f1.accept(this, argu);
        instructionLiteral += " " + n.f2.accept(this, argu);
        int currentInstructionCounter = instructionCounter++;

        String instructionEDB = "instruction(" + currentInstructionCounter + ",\'" + argu + "\',\'" + instructionLiteral + "\').";
        System.out.println(instructionEDB);

        return instructionLiteral;
    }

    public String visit(final Operator n, final String argu) {
        String nRes = null;
        n.f0.accept(this, argu);

        return nRes;
    }

    public String visit(final SimpleExp n, final String argu) {
        String simpleExp = n.f0.accept(this, argu);

        return simpleExp;
    }

    public String visit(final Temp n, final String argu) {
        n.f0.accept(this, argu);
        String varDecl = n.f0.toString();
        varDecl += " " + n.f1.accept(this, argu);

        return varDecl;
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
