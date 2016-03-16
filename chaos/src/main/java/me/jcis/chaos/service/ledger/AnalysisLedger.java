package me.jcis.chaos.service.ledger;

import me.jcis.chaos.core.reflect.BaseInvoker;
import me.jcis.chaos.core.reflect.MethodInvoker;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/10/29
 */
public class AnalysisLedger extends BaseInvoker implements MethodInvoker {

    /**
     *
     * @return
     */
    public Object helloAnalysis(String a){
        return "Hello, World!" + a;
    }

    public Object helloAnalysis(){
        return "Hello, World!";
    }

}
