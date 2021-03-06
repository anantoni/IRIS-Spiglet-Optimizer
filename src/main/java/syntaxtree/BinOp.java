/* Generated by JTB 1.4.4 */
package main.java.syntaxtree;

import main.java.visitor.IRetArguVisitor;
import main.java.visitor.IRetVisitor;
import main.java.visitor.IVoidArguVisitor;
import main.java.visitor.IVoidVisitor;

public class BinOp implements INode {

    private static final long serialVersionUID = 144L;
    public Operator f0;
    public Temp f1;
    public SimpleExp f2;

    public BinOp(final Operator n0, final Temp n1, final SimpleExp n2) {
        f0 = n0;
        f1 = n1;
        f2 = n2;
    }

    public <R, A> R accept(final IRetArguVisitor<R, A> vis, final A argu) {
        return vis.visit(this, argu);
    }

    public <R> R accept(final IRetVisitor<R> vis) {
        return vis.visit(this);
    }

    public <A> void accept(final IVoidArguVisitor<A> vis, final A argu) {
        vis.visit(this, argu);
    }

    public void accept(final IVoidVisitor vis) {
        vis.visit(this);
    }

}
