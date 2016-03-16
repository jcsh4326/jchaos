package me.jcis.chaos.core.spring;

import me.jcis.chaos.core.config.ChaosConfig;
import me.jcis.chaos.core.constant.EnvConstant;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2015/12/29
 */
public class ChaosContextLoaderListener extends ContextLoaderListener {
    private boolean isConfiged = false;

    private ContextLoader contextLoader;

    public ChaosContextLoaderListener(){super();}

    public ChaosContextLoaderListener(WebApplicationContext context) {
        super(context);
    }

    /**
     * 在Servlet之前读取系统配置文件
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
//        this.contextLoader = this.createContextLoader();

        if(this.contextLoader == null) {
            this.contextLoader = this;
        }
        String prePath = event.getServletContext().getInitParameter(EnvConstant.ChaosConfLocation);
        isConfiged = null==prePath ? ChaosConfig.load() : ChaosConfig.load(prePath);
        if(isConfiged){
            this.contextLoader.initWebApplicationContext(event.getServletContext());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        return super.initWebApplicationContext(servletContext);
    }

    @Override
    protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac, ServletContext sc) {
        String initParameter;
        if(ObjectUtils.identityToString(wac).equals(wac.getId())) {
            initParameter = sc.getInitParameter("contextId");
            if(initParameter != null) {
                wac.setId(initParameter);
            } else if(sc.getMajorVersion() == 2 && sc.getMinorVersion() < 5) {
                wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX + ObjectUtils.getDisplayString(sc.getServletContextName()));
            } else {
                wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX + ObjectUtils.getDisplayString(sc.getContextPath()));
            }
        }

        wac.setServletContext(sc);
        initParameter = sc.getInitParameter("contextConfigLocation");
        if(initParameter != null) {
            if(initParameter.startsWith(EnvConstant.Conf_Prex)) {
                String confPath = sc.getInitParameter(EnvConstant.ChaosConfLocation);
                initParameter = initParameter.replace(EnvConstant.Conf_Prex, confPath);
            }
            wac.setConfigLocation(initParameter);
        }

        this.customizeContext(sc, wac);
        wac.refresh();
    }
}
