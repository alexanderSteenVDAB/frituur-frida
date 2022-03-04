package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Component
@Qualifier("CSV")
class CSVSausRepository implements SausRepository {
    private final Path path;

    public CSVSausRepository(@Value("${CSVSausenPad}") Path path) {
        this.path = path;
    }

    private Saus naarSaus(String regel) {
        var values = regel.split(",");

        if (values.length < 2) {
            throw new SausRepositoryException("Regel in CSV moet uit minimum 2 waarden bestaan");
        }

        try {
            var ingredienten = Arrays.copyOfRange(values, 2, values.length);
            return new Saus(Long.parseLong(values[0]), values[1], ingredienten);
        } catch (NumberFormatException e) {
            throw new SausRepositoryException("Verkeerde waarde als id");
        }
    }

    @Override
    public List<Saus> findAll() {
        try {
            return Files.lines(path).map(this::naarSaus).toList();
        } catch (IOException ex) {
            throw new SausRepositoryException("Kan bestand niet lezen " + path, ex);
        }
    }
}
