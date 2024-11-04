import io.cucumber.core.cli.Main;

public class RunCucumberTest {
    public static void main(String[] args) {
        String[] cucumberOptions = new String[] {
                "-g", "stepdefs",
                "src/test/resources",
                "-p", "pretty",
                "-p", "html:target/cucumber-reports.html",
                "-p", "json:target/cucumber-reports.json"
        };


        Main.run(cucumberOptions, Thread.currentThread().getContextClassLoader());
    }
}