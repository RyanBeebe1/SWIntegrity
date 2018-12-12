import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class JavaUntrustedInputTest {
    JavaAnalyzer jana;
    JavaUntrustedInputVulnerability vuln;
    @Before
    public void setup() {
        jana = new JavaAnalyzer();
        vuln = new JavaUntrustedInputVulnerability();
        jana.parse("./TestFiles\\JavaUntrustedInputtest.java");
    }


    @Test
    public void test() {
        List<Integer> locs = vuln.run(jana);
        assertTrue(locs.size() == 5);
        assertTrue(locs.contains(20));
        assertTrue(locs.contains(16));
        assertTrue(locs.contains(8));
    }
}
