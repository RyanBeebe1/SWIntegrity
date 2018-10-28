import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class JavaIncorrectStrOperatorTest {
    JavaAnalyzer jana;
    JavaWrongStringOperator vuln;
    @Before
    public void setup() {
        jana = new JavaAnalyzer();
        vuln = new JavaWrongStringOperator();
        jana.parse("./TestFiles\\JavaStrCompareTest.java");
    }


    @Test
    public void test() {
        List<Integer> locs = vuln.run(jana);
        assertTrue(locs.size() == 4);
        assertTrue(locs.contains(25));
        assertTrue(locs.contains(7));
        assertTrue(locs.contains(14));
        assertTrue(!locs.contains(21));
        assertTrue(locs.contains(28));
        assertTrue(!locs.contains(35));
    }
}
