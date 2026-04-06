import com.createspring.board.service.PostService;
import com.createspring.spring.bean.ApplicationContext;
import com.createspring.spring.bean.BeanFactory;
import com.createspring.spring.bean.DefaultSingletonBeanRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class SingletonCheckTest {

    @Test
    public void singleton() throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BeanFactory beanFactory = BeanFactory.getBeanFactory();
        beanFactory.initialize("com.createspring");
        
        PostService service1 = (PostService) beanFactory.getBean("postService");
        PostService service2 = (PostService) beanFactory.getBean("postService");
        System.out.println(service1);
        System.out.println(service2);
        Assertions.assertSame(service1, service2);
    }
}
