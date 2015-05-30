package utilities;

/**
 * Created by anantoni on 28/5/2015.
 */
public class Triple<String, Integer> {
    private String method;
    private Integer iCounter;
    private String var;

    public Triple(String method, Integer iCounter, String var) {
        this.setMethod(method);
        this.setiCounter(iCounter);
        this.setVar(var);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getiCounter() {
        return iCounter;
    }

    public void setiCounter(Integer iCounter) {
        this.iCounter = iCounter;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public java.lang.String toString() {
        return "(" + method + ", " + iCounter.toString() + ", " + var + ")";
    }

    @Override
    public boolean equals(Object otherObject) {
        Triple<String, Integer> otherTriple = (Triple) otherObject;
        return (method.equals(otherTriple.method) && (iCounter == otherTriple.iCounter) && (var.equals(otherTriple.var)));

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * method.hashCode() + iCounter.hashCode() + var.hashCode();
    }
}
