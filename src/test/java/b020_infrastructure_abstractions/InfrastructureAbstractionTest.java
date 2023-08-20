package b020_infrastructure_abstractions;

import b020_infrastructure_abstractions.abstractions.Environment;
import b020_infrastructure_abstractions.abstractions.SiteUrls;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InfrastructureAbstractionTest {

    private String envProperty;

    @BeforeEach
    public void rememberBaseEnv(){
        envProperty = System.getProperty(
                Environment.ABSTRACTIONS_ENV_PROPERTY_NAME);
    }

    @Test
    public void urlControlledByEnvironmentAbstraction(){

        Environment env = new Environment();
        assertEquals("https://testpages.herokuapp.com",
                env.getEnvRootUrlDomain());
    }

    @Test
    public void systemPropertyToSetEnvironmentAbstraction(){

        System.setProperty("env", "docker");
        Environment env = new Environment();
        assertEquals("http://localhost:4567",
                env.getEnvRootUrlDomain());
    }

    @Test
    public void siteAbstractionUsesEnvironment(){

        SiteUrls site = new SiteUrls(new Environment());
        assertEquals(
                "https://testpages.herokuapp.com/styled/basic-html-form-test.html",
                site.htmlForm());
    }

    @Test
    public void siteAbstractionUsesEnvironmentConfigured(){

        System.setProperty("env", "docker");
        SiteUrls site = new SiteUrls(new Environment());
        assertEquals(
                "http://localhost:4567/styled/basic-html-form-test.html",
                site.htmlForm());
    }

    @AfterEach
    public void resetBaseEnv(){
        if(envProperty==null) {
            System.clearProperty(Environment.ABSTRACTIONS_ENV_PROPERTY_NAME);
        }else {
            System.setProperty(
                    Environment.ABSTRACTIONS_ENV_PROPERTY_NAME,
                    envProperty);
        }
    }

}
