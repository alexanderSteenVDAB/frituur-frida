package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Bericht;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class BerichtRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<Bericht> berichtMapper = (result, rowNum) ->
            new Bericht(result.getLong("id"), result.getObject("datum", LocalDate.class),
                    result.getString("naam"), result.getString("bericht"));

    public BerichtRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template)
                .withTableName("berichten")
                .usingGeneratedKeyColumns("id");
    }

    public List<Bericht> findAll() {
        var sql = """
                select id, datum, naam, bericht
                from berichten
                order by datum desc
                """;
        return template.query(sql,berichtMapper);
    }

    public long create(Bericht bericht) {
        return insert.executeAndReturnKey(
                Map.of("datum", bericht.getDatum(),
                        "naam", bericht.getNaam(),
                        "bericht", bericht.getBericht()))
                .longValue();
    }

    public void delete(Long[] ids) {
        if (ids.length != 0) {
            var sql = """
                    delete from berichten
                    where id in (
                    """ + "?,".repeat(ids.length - 1) + "?)";
            template.update(sql, ids);
        }
    }
}
