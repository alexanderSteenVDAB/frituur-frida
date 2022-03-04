package be.vdab.frituurfrida.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@PropertySource("application.properties")
@Import(CSVSausRepository.class)
class CSVSausRepositoryTest {
    private final CSVSausRepository repository;
    private final Path path;

    CSVSausRepositoryTest(CSVSausRepository repository, @Value("${CSVSausenPad}") Path path) {
        this.repository = repository;
        this.path = path;
    }


    @Test
    void erZijnEvenveelSauzenAlsErRegelsZijnInHetCSVBestand() throws IOException {
        assertThat(repository.findAll()).hasSameSizeAs(Files.readAllLines(path));
    }

    @Test
    void deEersteSausBevatDeDataVanDeEersteRegelInHetCSVBestand()
            throws IOException {
        var eersteRegel = Files.lines(path).findFirst().get();
        var eersteSaus = repository.findAll().get(0);
        assertThat(eersteSaus.getNummer() + "," + eersteSaus.getNaam() + "," +
                String.join(",", eersteSaus.getIngredienten()))
                .isEqualTo(eersteRegel);
    }

}