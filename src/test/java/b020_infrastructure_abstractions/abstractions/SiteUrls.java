package b020_infrastructure_abstractions.abstractions;

public class SiteUrls {

    private final Environment env;

    public SiteUrls(final Environment environment) {
        env = environment;
    }

    public String htmlForm(){
        return env.getEnvRootUrlDomain() + "/styled/basic-html-form-test.html";
    }

    public String simpleNotesApp() {
        return env.getEnvRootUrlDomain() + "/styled/apps/notes/simplenotes.html";
    }

    public String examplePage() {
        return env.getEnvRootUrlDomain() + "/styled/webdriver-example-page";
    }

    public String adminLoginExample() {
        return env.getEnvRootUrlDomain() + "/styled/cookies/adminlogin.html";
    }
}
