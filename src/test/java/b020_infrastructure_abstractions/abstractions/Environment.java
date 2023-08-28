package b020_infrastructure_abstractions.abstractions;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    public static final String ABSTRACTIONS_ENV_VARIABLE_NAME = "AbstractionsEnv";
    public static final String ABSTRACTIONS_ENV_PROPERTY_NAME = "env";

    public String getEnvRootUrlDomain(){

        // by default use the cloud heroku hosted
        String env = "official";

        // support changing using an environment variable
        if(System.getenv().containsKey(ABSTRACTIONS_ENV_VARIABLE_NAME)){
            env = System.getenv(ABSTRACTIONS_ENV_VARIABLE_NAME);
        }

        // support changing using a System Property
        if(System.getProperties().containsKey(ABSTRACTIONS_ENV_PROPERTY_NAME)){
            env=System.getProperty(ABSTRACTIONS_ENV_PROPERTY_NAME);
        }

        Map<String, String> urls = new HashMap<>();
        urls.put("official", "https://testpages.eviltester.com");
        urls.put("heroku", "https://testpages.herokuapp.com");
        urls.put("docker", "http://localhost:4567");

        return urls.get(env);
    }
}
