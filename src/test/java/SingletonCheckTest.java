import com.createspring.board.service.PostService;
import com.createspring.spring.bean.BeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class SingletonCheckTest {

    @Test
    public void singleton() throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BeanFactory.initialize("com.createspring");
        PostService service1 = BeanFactory.getBean(PostService.class);
        PostService service2 = BeanFactory.getBean(PostService.class);
        System.out.println(service1);
        System.out.println(service2);
        Assertions.assertSame(service1, service2);
    }
}
