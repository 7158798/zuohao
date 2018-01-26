package test.com.ruizton.base;

import com.ruizton.main.log.LOG;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by luwei on 17-2-20.
 */
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public abstract class BaseSpringTest extends AbstractJUnit4SpringContextTests {

    public MockHttpServletRequest request;
    public MockHttpServletResponse response;

    @Test
    public abstract void doTest();

    public <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    protected ApplicationContext getContext() {
        return applicationContext;
    }


    public void printAllBeans() {

        LOG.dStart(this, "printAllBeans");

        String[] allBeans = getContext().getBeanDefinitionNames();

        LOG.d(this, "allBeans count=" + allBeans.length);

        for (String beanName : allBeans) {
            System.out.println(beanName);
        }

        LOG.dEnd(this, "printAllBeans");
    }

    public void setRequest() {
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
    }

    public void setResponse() {
        response = new MockHttpServletResponse();
    }
}
