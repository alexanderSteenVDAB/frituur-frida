package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import(SnackRepository.class)
@Sql("/insertSnacks.sql")
class SnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static String SNACKS = "snacks";
    private final SnackRepository repository;

    SnackRepositoryTest(SnackRepository repository) {
        this.repository = repository;
    }

    private long idVanTestSnack() {
        return jdbcTemplate.queryForObject("select id from snacks where naam = 'test'", Long.class);
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestSnack()))
                .hasValueSatisfying(snack -> assertThat(snack.getNaam()).isEqualTo("test"));
    }

    @Test
    void findByOnbestaandeIdVindtGeenSnack() {
        assertThat(repository.findById(-1)).isEmpty();
    }

    @Test
    void update() {
        var id = idVanTestSnack();
        var snack = new Snack(id, "test", BigDecimal.TEN);
        repository.update(snack);
        assertThat(countRowsInTableWhere(SNACKS, "prijs=10 and id=" + id)).isOne();
    }

    @Test
    void updateOnbestaandeSnackGeeftEenFout() {
        assertThatExceptionOfType(SnackNietGevondenException.class).isThrownBy(
                () -> repository.update(new Snack(-1, "test", BigDecimal.TEN)));
    }

    @Test
    void findByBeginNaam() {
        assertThat(repository.findByBeginNaam("te"))
                .hasSize(countRowsInTableWhere(SNACKS, "naam like 'te%'"))
                .extracting(Snack::getNaam)
                .allSatisfy(naam -> assertThat(naam).contains("te"))
                .allSatisfy(naam -> assertThat(naam).startsWith("te"))
                .isSorted();
    }


    @Test
    void findSnacksMetAantalVerkocht() {
        var snacksMetAantalVerkocht = repository.findSnacksMetAantalVerkocht();
        assertThat(snacksMetAantalVerkocht)
                .hasSize(jdbcTemplate.queryForObject("select count(*) from snacks", Integer.class));
        var rij1 = snacksMetAantalVerkocht.get(0);
        assertThat(rij1.aantalVerkocht()).isEqualTo(jdbcTemplate.queryForObject(
                "select sum(aantal) from dagverkopen where snackId = " + rij1.id(), Integer.class));
    }
}