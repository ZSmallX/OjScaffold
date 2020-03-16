package dio.oj.scaffolding;

import org.junit.Assert;
import org.junit.Test;

public class OjUtilsTest {
    @Test
    public void testToIntMatrix() {
        Assert.assertEquals(new int[][]{{1}}, OjUtils.toIntMatrix("[[1]]"));
    }
}
