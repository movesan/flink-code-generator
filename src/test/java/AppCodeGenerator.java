import org.generator.flink.generator.base.GeneratorFactoryImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppCodeGenerator {

    private static ApplicationContext context;

    private static GeneratorFactoryImpl generatorFactory;

    @BeforeClass
    public static void beforeClass() {
        try {
            context = new ClassPathXmlApplicationContext("classpath:spring-generator.xml");
            generatorFactory = (GeneratorFactoryImpl) context.getBean("generatorFactory");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void doAfter() {
        if (context != null && context instanceof ClassPathXmlApplicationContext) {
            ((ClassPathXmlApplicationContext) context).close();
        }
    }

    @Test
    public void flinkCodeGeneratorTest() {
        generatorFactory.flinkGeneratorStarter();
    }
}
