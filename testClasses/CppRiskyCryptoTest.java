import org.junit.jupiter.api.Test;

public class CppRiskyCryptoTest {

	@Test
	void test() {
		CppAnalyzer cppana=new CppAnalyzer();
		cppana.parse("TestFiles/CppRiskyCryptoTest.cpp");
		CppRiskyCryptoVulnerability vuln = new CppRiskyCryptoVulnerability();
		System.out.println(vuln.run(cppana));
	}
}
