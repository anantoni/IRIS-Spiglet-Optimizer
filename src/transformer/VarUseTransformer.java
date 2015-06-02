package transformer;

import syntaxtree.*;
import utilities.Triple;
import visitor.DepthFirstRetArguVisitor;
import visitor.IRetArguVisitor;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by anantoni on 28/5/2015.
 */
public class VarUseTransformer extends DepthFirstRetArguVisitor<String, String> implements IRetArguVisitor<String, String> {

    private Transformer primaryVisitor;
    private Map<Triple<String, Integer>, Integer> constantMap;
    private Map<Triple<String, Integer>, String> copyMap;

    public VarUseTransformer(Transformer primaryVisitor) {
        this.primaryVisitor = primaryVisitor;
        this.constantMap = primaryVisitor.constantMap;
        this.copyMap = primaryVisitor.copyMap;
    }

    public String visit(final NodeChoice n, final String argu) {
        n.choice.accept(this, argu);
        return null;
    }

    public String visit(final NodeList n, final String argu) {
        for (final Iterator<INode> e = n.elements(); e.hasNext();)
            e.next().accept(this, argu);
        return null;
    }

    public String visit(final NodeListOptional n, final String argu) {
        if (n.present()) {
            for (final Iterator<INode> e = n.elements(); e.hasNext();)
                e.next().accept(this, argu);
            return null;
        } else
            return null;
    }

    public String visit(final NodeOptional n, final String argu) {
        if (n.present()) {
            n.node.accept(this, argu);
            return null;
        } else
            return null;
    }

    public String visit(final NodeSequence n, final String argu) {
        for (final Iterator<INode> e = n.elements(); e.hasNext();)
            e.next().accept(this, argu);
        return null;
    }

    public String visit(final NodeToken n, @SuppressWarnings("unused") final String argu) {
        @SuppressWarnings("unused")
        final String tkIm = n.tokenImage;
        return null;
    }

    public String visit(final Goal n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        return null;
    }

    public String visit(final StmtList n, final String argu) {
        n.f0.accept(this, argu);

        return null;
    }

    public String visit(final Procedure n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        return null;
    }

    public String visit(final Stmt n, final String argu) {
        n.f0.accept(this, argu);

        return null;
    }

    public String visit(final NoOpStmt n, final String argu) {
        n.f0.accept(this, argu);

        return null;
    }

    public String visit(final ErrorStmt n, final String argu) {
        n.f0.accept(this, argu);

        return null;
    }

    public String visit(final CJumpStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        return null;
    }

    public String visit(final JumpStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        return null;
    }

    public String visit(final HStoreStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);

        return null;
    }

    public String visit(final HLoadStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);

        return null;
    }

    public String visit(final MoveStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        return null;
    }

    public String visit(final PrintStmt n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        return null;
    }

    public String visit(final Exp n, final String argu) {
        n.f0.accept(this, argu);

        return null;
    }

    public String visit(final StmtExp n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        return null;
    }

    public String visit(final Call n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        return null;
    }

    public String visit(final HAllocate n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        return null;
    }

    public String visit(final BinOp n, final String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        return null;
    }

    public String visit(final Operator n, final String argu) {
        n.f0.accept(this, argu);

        return null;
    }

    public String visit(final SimpleExp n, final String argu) {
        return n.f0.accept(this, argu);
    }

    public String visit(final Temp n, final String argu) {
        int iCounter = this.primaryVisitor.getInstructionCounter();
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        String var = n.f0.tokenImage + " " + n.f1.f0.tokenImage;
        Triple key = new Triple(argu, iCounter, var);
        if (this.constantMap.containsKey(key))
            return this.constantMap.get(key).toString();
        else if (this.copyMap.containsKey(key))
            return this.copyMap.get(key);
        else
            return var;
    }

    public String visit(final IntegerLiteral n, final String argu) {
        n.f0.accept(this, argu);

        return n.f0.tokenImage;
    }

    public String visit(final Label n, final String argu) {
        n.f0.accept(this, argu);

        return n.f0.tokenImage;
    }
}

