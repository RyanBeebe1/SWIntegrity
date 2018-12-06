import java.util.ArrayList;
import java.util.List;

public class CppUseOfHashWithoutSalt implements Vulnerability {

	@Override
	public List<Integer> run(Analyzer ana) {
		ArrayList<Integer> lines = new ArrayList<>();
		CppAnalyzer cppana=(CppAnalyzer)ana;
		
		for (int i = 1; i <= cppana.getNumToLine().size(); i++) {
			String line = cppana.getNumToLine().get(i);
			line = line.toUpperCase();
			
			if (line.contains("HASH") && !line.startsWith("//")) {
				lines.add(i);
			}
			

		}
		
		return lines;
	}

}
