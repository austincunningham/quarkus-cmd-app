package main.java.austin;

import javax.inject.Inject;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.jboss.logging.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@QuarkusMain
public class CommandRunner implements QuarkusApplication {

    private static final Logger LOG = Logger.getLogger(CommandRunner.class);

    @ConfigProperty(defaultValue = "Students", name = "application.greeting.recipient")
    String recipent;

    @Inject
    DataSource dataSource;


    @Override
    public int run(String... args) throws Exception {
        LOG.debug("Starting application");
        String sql = "SELECT NAME, ROOM_NUMBER, BED_INFO FROM ROOM";
        List<Room> rooms= new ArrayList<Room>();
        try{
            Connection connection = dataSource.getConnection();
            try(Statement stmt = connection.createStatement()){
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    rooms.add(new Room(rs.getString("NAME"), rs.getString("ROOM_NUMBER"),
                            rs.getString("BED_INFO")));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        rooms.forEach(System.out::println);
        LOG.info("Hello "+ recipent);
        LOG.debug("Completing application");
        return 0;
    }
}
