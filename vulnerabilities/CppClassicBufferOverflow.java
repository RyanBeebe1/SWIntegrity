import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CppClassicBufferOverflow implements Vulnerability {
    @Override
    public List<Integer> run(Analyzer ana) {
        CppAnalyzer cppana = (CppAnalyzer) ana;
        List<Integer> lines = new ArrayList<>();
        String args = "";

        for (CppAnalyzer.Variable v : cppana.getVariables()) {
            for (HashMap.Entry<Integer, String> e : cppana.getNumToLine().entrySet()) {
                String line = e.getValue();

                if ((line.contains("strcpy(") || line.contains("scanf(") || line.contains("gets(") && line.contains(v.getName()))) {
                    for (int i = 0; i < line.length(); i++) {
                        char a = line.charAt(i);
                        if (a == '(') {
                            while (a != ')') {
                                a = line.charAt(++i);
                                args += a;
                            }
                        }
                    }
                    String[] argys = args.split(",");
                    for (String s : argys) {
                        if (s.contains(v.getName())) {
                            if (!lines.contains(e.getKey()))
                                lines.add(e.getKey());
                        }
                    }
                }
            }

        }
        Collections.sort(lines);
        return lines;
    }
}
