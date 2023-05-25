package locations;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@AllArgsConstructor
public class LocationsDao {

    private JdbcTemplate jdbcTemplate;

    public List<Location> findAll () {
        //language=SQL
        return jdbcTemplate.query("select id, name, lat, lon from locations",
                LocationsDao::mapRow);
    }

    public Location findById(long id) {
        //language=SQL
        return jdbcTemplate.queryForObject("select id, name, lat, lon from locations where id = ?",
                LocationsDao::mapRow,
                id);
    }

    public void createLocation(Location location) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //language=SQL
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "insert into locations (name, lat, lon) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, location.getName());
            ps.setDouble(2, location.getLat());
            ps.setDouble(3, location.getLon());
            return ps;
        }, keyHolder);
        location.setId(keyHolder.getKey().longValue());
    }

    public void updateLocation(Location location) {
        //language=SQL
        jdbcTemplate.update("update locations set name = ?, lat = ?, lon = ? where id = ?",
                location.getName(),
                location.getLat(),
                location.getLon(),
                location.getId());
    }

    public void deleteLocation(long id) {
        //language=SQL
        jdbcTemplate.update("delete from locations where id = ?", id);
    }

    public void deleteAllLocation() {
        //language=SQL
        jdbcTemplate.update("delete from locations");
    }


    private static Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        double lat = rs.getDouble("lat");
        double lon = rs.getDouble("lon");
        return new Location(id, name, lat, lon);
    }

}
