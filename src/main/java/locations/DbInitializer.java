package locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        //language=SQL
        jdbcTemplate.execute("create table locations (id bigint auto_increment, name varchar(255),  lat double, lon double, primary key (id) )");
        //language=SQL
        jdbcTemplate.execute("insert into locations (name, lat, lon) values ('Fót', 1.1, 2.1)");
        //language=SQL
        jdbcTemplate.execute("insert into locations (name, lat, lon) values ('Dunakeszi', 2.1, 3.1)");
    }


}
