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
}
