import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavaWrongStringOperator {
    public List<Integer> run(Analyzer ana) {
        JavaAnalyzer jana = (JavaAnalyzer) ana;
        List<Integer> locs = new ArrayList<>();
        for (JavaAnalyzer.Variable v : jana.getVariables()) {
            if (v.getType().equals("String")) {
                for (HashMap.Entry<Integer, String> k : jana.getLineToLine().entrySet()) {
                    if (k.getValue().replaceAll("\\s","").contains(v.getName()+"==")
                            || k.getValue().replaceAll("\\s", "").contains("=="+v.getName())
                            || k.getValue().replaceAll("\\s","").contains(v.getName()+"!=")
                            || k.getValue().replaceAll("\\s", "").contains("!="+v.getName())) {
                        if (!locs.contains(k.getKey()))
                        locs.add(k.getKey());
                    }
                }
            }
        }
        return locs;
    }

}
