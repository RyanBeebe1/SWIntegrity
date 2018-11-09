import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/**
 * CWE: https://cwe.mitre.org/data/definitions/597.html
 * This vulnerability searches for incorrect usage when comparing strings in Java source code.
 * @author Joe Mcilvaine
 */
public class JavaWrongStringOperator implements Vulnerability {
    public List<Integer> run(Analyzer ana) {
        JavaAnalyzer jana = (JavaAnalyzer) ana;
        List<Integer> locs = new ArrayList<>(); //line number of possible vulnerability
        for (JavaAnalyzer.Variable v : jana.getVariables()) { //search through each variable
            if (v.getType().equals("String")) { //see if the variable is a string
                for (HashMap.Entry<Integer, String> k : jana.getLineToLine().entrySet()) { //go through each line of source code
                    if (k.getValue().replaceAll("\\s","").contains(v.getName()+"==") //check for improper comparison
                            || k.getValue().replaceAll("\\s", "").contains("=="+v.getName())
                            || k.getValue().replaceAll("\\s","").contains(v.getName()+"!=")
                            || k.getValue().replaceAll("\\s", "").contains("!="+v.getName())) {
                        if (!locs.contains(k.getKey()))
                        locs.add(k.getKey());
                    }
                }
            }
        }
        Collections.sort(locs);
        return locs;
    }

}
