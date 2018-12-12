import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class CppUntrustedInputTest {

    CppAnalyzer jana;
    CppUntrustedInputVulnerability vuln;
    @Before
    public void setup() {
        jana = new CppAnalyzer();
        vuln = new CppUntrustedInputVulnerability();
        jana.parse("./TestFiles\\CppUntrustedInputtest.cpp");
    }


    @Test
    public void test() {
        List<Integer> locs = vuln.run(jana);
        assertTrue(locs.size() == 1);
        assertTrue(locs.contains(8));
    }
}
