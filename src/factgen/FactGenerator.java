package factgen;

import syntaxtree.*;
import visitor.DepthFirstRetArguVisitor;

/**
 * Created by anantoni on 1/5/2015.
 */
public class FactGenerator extends DepthFirstRetArguVisitor<String, String> {
    private int instructionCounter;

    public FactGenerator() {
        instructionCounter = 0;
    }

    @Override
    public String visit(NodeChoice n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(NodeList n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(NodeListOptional n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(NodeOptional n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(NodeSequence n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(NodeToken n, @SuppressWarnings("unused") String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(Goal n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(StmtList n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(Procedure n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(Stmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(NoOpStmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(ErrorStmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(CJumpStmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(JumpStmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(HStoreStmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(HLoadStmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(MoveStmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(PrintStmt n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(Exp n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(StmtExp n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(Call n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(HAllocate n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(BinOp n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(Operator n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(SimpleExp n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(Temp n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(IntegerLiteral n, String argu) {
        return super.visit(n, argu);
    }

    @Override
    public String visit(Label n, String argu) {
        return super.visit(n, argu);
    }
}
