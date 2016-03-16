package me.jcis.chaos.service.ledger.test;

import me.jcis.chaos.core.test.BaseTestCase;
import me.jcis.chaos.service.ledger.AnalysisLedger;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/10/29
 */
public class AnalysisLedgerTest extends BaseTestCase {

    private AnalysisLedger analysisLedger = new AnalysisLedger();

    @Test
    public void testInvoker() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expect = "Hello, World!";
        Object obj = analysisLedger.invoker(analysisLedger, "helloAnalysis", new Object[]{});
        Assert.assertEquals(expect, obj);
    }

    @Test
    public void testInvoker1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expect = "Hello, World!" + "ni";
        Object obj = analysisLedger.invoker(analysisLedger, "helloAnalysis", new Object[]{"ni"});
        Assert.assertEquals(expect, obj);
    }
}
