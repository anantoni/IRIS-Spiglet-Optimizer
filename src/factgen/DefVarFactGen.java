package factgen;

import syntaxtree.*;
import visitor.DepthFirstVoidArguVisitor;

import java.util.Iterator;

/**
 * Created by anantoni on 5/5/2015.
 */
public class DefVarFactGen extends DepthFirstVoidArguVisitor {

    private FactGenerator primaryVisitor;

    public DefVarFactGen(FactGenerator primaryVisitor) {
        this.primaryVisitor = primaryVisitor;
    }

    public void visit(final NodeChoice n, final String argu) {
        n.choice.accept(this, argu);
        return;
    }

    public void visit(final NodeList n, final String argu) {
        for (final Iterator<INode> e = n.elements(); e.hasNext();) {
            e.next().accept(this, argu);
        }
        return;
    }

    public void visit(final NodeListOptional n, final String argu) {
        if (n.present()) {
            for (final Iterator<INode> e = n.elements(); e.hasNext();) {
                e.next().accept(this, argu);
            }
            return;
        } else
            return;
    }

    public void visit(final NodeOptional n, final String argu) {
        if (n.present()) {
            n.node.accept(this, argu);
            return;
        } else
            return;
    }

    public void visit(final NodeSequence n, final String argu) {
        for (final Iterator<INode> e = n.elements(); e.hasNext();) {
            e.next().accept(this, argu);
        }
        return;
    }

    public void visit(final NodeToken n, @SuppressWarnings("unused") final String argu) {
        @SuppressWarnings("unused")
        final String tkIm = n.tokenImage;
        return;
    }

    public void visit(final Goal n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
    }

    public void visit(final StmtList n, final String argu) {
        n.f0.accept(this, argu);
    }

    public void visit(final Procedure n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
    }

    public void visit(final Stmt n, final String argu) {
        n.f0.accept(this, argu);
    }

    public void visit(final NoOpStmt n, final String argu) {
        n.f0.accept(this, argu);
    }

    public void visit(final ErrorStmt n, final String argu) {
        n.f0.accept(this, argu);
    }

    public void visit(final CJumpStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
    }

    public void visit(final JumpStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
    }

    public void visit(final HStoreStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
    }

    public void visit(final HLoadStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
    }

    public void visit(final MoveStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
    }

    public void visit(final PrintStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
    }

    public void visit(final Exp n, final String argu) {
        n.f0.accept(this, argu);
    }

    public void visit(final StmtExp n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
    }

    public void visit(final Call n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
    }

    public void visit(final HAllocate n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
    }

    public void visit(final BinOp n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
    }

    public void visit(final Operator n, final String argu) {
        n.f0.accept(this, argu);
    }

    public void visit(final SimpleExp n, final String argu) {
        n.f0.accept(this, argu);
    }

    public void visit(final Temp n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        String var = n.f0.tokenImage + " " + n.f1.f0.tokenImage;
        String varDefEDB = "varDef(" + argu + ",\'" + primaryVisitor.getInstructionCounter() + ",\'" + var + "\').";
        System.out.println(varDefEDB);
    }

    public void visit(final IntegerLiteral n, final String argu) {
        n.f0.accept(this, argu);
    }

    public void visit(final Label n, final String argu) {
        n.f0.accept(this, argu);
    }

}
