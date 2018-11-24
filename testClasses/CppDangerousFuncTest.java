import org.junit.jupiter.api.Test;

public class CppDangerousFuncTest {

	@Test
	void test() {
		CppAnalyzer cppana=new CppAnalyzer();
		cppana.parse("TestFiles/CppDangerousFuncTest.cpp");
		CppDangerousFuncVulnerability vuln = new CppDangerousFuncVulnerability();
		System.out.println(vuln.run(cppana));
	}
}
