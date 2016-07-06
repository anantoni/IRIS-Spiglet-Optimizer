/* Generated by JTB 1.4.4 */
package main.java.visitor;

import main.java.syntaxtree.*;

public interface IVoidArguVisitor<A> {

  void visit(final NodeList n, final A argu);

  void visit(final NodeListOptional n, final A argu);

  void visit(final NodeOptional n, final A argu);


  void visit(final NodeSequence n, final A argu);

  void visit(final NodeToken n, final A argu);

  void visit(final Goal n, final A argu);

  void visit(final StmtList n, final A argu);

  void visit(final Procedure n, final A argu);

  void visit(final Stmt n, final A argu);

  void visit(final NoOpStmt n, final A argu);

  void visit(final ErrorStmt n, final A argu);

  void visit(final CJumpStmt n, final A argu);

  void visit(final JumpStmt n, final A argu);

  void visit(final HStoreStmt n, final A argu);

  void visit(final HLoadStmt n, final A argu);

  void visit(final MoveStmt n, final A argu);

  void visit(final PrintStmt n, final A argu);

  void visit(final Exp n, final A argu);

  void visit(final StmtExp n, final A argu);

  void visit(final Call n, final A argu);

  void visit(final HAllocate n, final A argu);

  void visit(final BinOp n, final A argu);

  void visit(final Operator n, final A argu);

  void visit(final SimpleExp n, final A argu);

  void visit(final Temp n, final A argu);

  void visit(final IntegerLiteral n, final A argu);

  void visit(final Label n, final A argu);

}