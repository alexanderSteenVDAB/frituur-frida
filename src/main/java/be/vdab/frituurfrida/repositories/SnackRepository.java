package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.dto.SnacksMetAantalVerkocht;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SnackRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<Snack> snackMapper = (result, rowNum) ->
            new Snack(result.getLong("id"), result.getString("naam"),
                    result.getBigDecimal("prijs"));

    public SnackRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template).withTableName("snacks").usingGeneratedKeyColumns("id");
    }

    public Optional<Snack> findById(long id) {
        try {
            var sql = """
                    select id, naam, prijs
                    from snacks
                    where id = ?
                    """;
            return Optional.ofNullable(template.queryForObject(sql, snackMapper, id));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Snack snack) {
        var sql = """
                update snacks
                set naam = ?, prijs = ?
                where id = ?
                """;
        if (template.update(sql, snack.getNaam(), snack.getPrijs(), snack.getId()) == 0) {
            throw new SnackNietGevondenException();
        }
    }

    public List<Snack> findByBeginNaam(String beginNaam) {
        var sql = """
                select id, naam, prijs
                from snacks
                where naam like ?
                """;
        return template.query(sql, snackMapper, beginNaam + "%");
    }

    public List<SnacksMetAantalVerkocht> findSnacksMetAantalVerkocht() {
        var sql = """
                select s.id, naam, (select sum(aantal) from dagverkopen where snackId = s.id) as aantal
                from snacks s
                """;
        RowMapper<SnacksMetAantalVerkocht> mapper = (result, rowNum) ->
                new SnacksMetAantalVerkocht(result.getLong("id"), result.getString("naam"),
                        result.getInt("aantal"));
        return template.query(sql, mapper);
    }
}
