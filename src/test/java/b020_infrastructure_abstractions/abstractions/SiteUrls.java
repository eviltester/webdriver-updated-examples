package b020_infrastructure_abstractions.abstractions;

public class SiteUrls {

    private final Environment env;

    public SiteUrls(final Environment environment) {
        env = environment;
    }

    public String htmlForm(){
        return env.getEnvRootUrlDomain() + "/pages/forms/html-form/";
    }

    public String simpleNotesApp() {
        return env.getEnvRootUrlDomain() + "/apps/note-taker/";
    }

    public String examplePage() {
        return env.getEnvRootUrlDomain() + "/pages/basics/basic-web-page/";
    }

    public String adminLoginExample() {
        return env.getEnvRootUrlDomain() + "/apps/simulated-login/";
    }
}
