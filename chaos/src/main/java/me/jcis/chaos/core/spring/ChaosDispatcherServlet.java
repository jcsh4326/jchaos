package me.jcis.chaos.core.spring;

import me.jcis.chaos.core.constant.EnvConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/30
 */
public class ChaosDispatcherServlet extends DispatcherServlet {
    public ChaosDispatcherServlet(){super();}

    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being placed into service.  See {@link Servlet#init}.
     * <p/>
     * <p>This implementation stores the {@link ServletConfig}
     * object it receives from the servlet container for later use.
     * When overriding this form of the method, call
     * <code>super.init(config)</code>.
     *
     * @param config the <code>ServletConfig</code> object
     *               that contains configutation
     *               information for this servlet
     * @throws ServletException if an exception occurs that
     *                          interrupts the servlet's normal
     *                          operation
     * @see UnavailableException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config);
        }catch (ServletException e){
            log(e.getLocalizedMessage());
            this.destroy();
        }
        if(StringUtils.isNotEmpty(System.getProperty(EnvConstant.ChaosHome))){

        }else{
            this.destroy();
            throw new ServletException("系统配置文件不存在！");
        }
    }
}
