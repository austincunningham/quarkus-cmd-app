package main.java.austin;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@QuarkusMain
public class CommandRunner implements QuarkusApplication {
    @ConfigProperty(defaultValue = "Students", name = "application.greeting.recipient")
    String recipent;
    @Override
    public int run(String... args) throws Exception {
        System.out.println("Hello "+ recipent);
        return 0;
    }
}
