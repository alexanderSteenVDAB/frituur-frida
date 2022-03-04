package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Bericht;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BerichtRepository.class)
@Sql("/insertBerichten.sql")
class BerichtRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BERICHTEN = "berichten";
    private final BerichtRepository repository;

    BerichtRepositoryTest(BerichtRepository repository) {
        this.repository = repository;
    }

    @Test
    void findAll() {
        assertThat(repository.findAll()).hasSize(countRowsInTable(BERICHTEN))
                .extracting(Bericht::getDatum)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    void create() {
        var id = repository.create(new Bericht(0, LocalDate.now(), "tester2", "Testbericht"));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(BERICHTEN, "id = " + id)).isOne();
    }

    private long idVanTestBericht1() {
        return jdbcTemplate.queryForObject("select id from berichten where naam = 'tester'", Long.class);
    }

    private long idVanTestBericht2() {
        return jdbcTemplate.queryForObject("select id from berichten where naam = 'tester2'", Long.class);
    }


    @Test
    void delete() {
        Long[] ids = new Long[] {idVanTestBericht1(), idVanTestBericht2()};
        repository.delete(ids);
        assertThat(countRowsInTableWhere(BERICHTEN, "id = " + ids[0])).isZero();
        assertThat(countRowsInTableWhere(BERICHTEN, "id = " + ids[1])).isZero();
    }
}