/* Generated by JTB 1.4.4 */
package visitor;

import syntaxtree.*;

public interface IVoidVisitor {

  public void visit(final NodeList n);

  public void visit(final NodeListOptional n);

  public void visit(final NodeOptional n);

  public void visit(final NodeSequence n);

  public void visit(final NodeToken n);

  public void visit(final Goal n);

  public void visit(final StmtList n);

  public void visit(final Procedure n);

  public void visit(final Stmt n);

  public void visit(final NoOpStmt n);

  public void visit(final ErrorStmt n);

  public void visit(final CJumpStmt n);

  public void visit(final JumpStmt n);

  public void visit(final HStoreStmt n);

  public void visit(final HLoadStmt n);

  public void visit(final MoveStmt n);

  public void visit(final PrintStmt n);

  public void visit(final Exp n);

  public void visit(final StmtExp n);

  public void visit(final Call n);

  public void visit(final HAllocate n);

  public void visit(final BinOp n);

  public void visit(final Operator n);

  public void visit(final SimpleExp n);

  public void visit(final Temp n);

  public void visit(final IntegerLiteral n);

  public void visit(final Label n);

}