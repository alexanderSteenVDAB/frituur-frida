package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
@Qualifier("properties")
class PropertiesSausRepository implements SausRepository {
    private final Path path;

    PropertiesSausRepository(@Value("${propertiesSausenPad}") Path path) {
        this.path = path;
    }

    private Saus naarSaus(String regel) {
        var idAndValues = regel.split(":");

        if (idAndValues.length < 2) {
            throw new SausRepositoryException("Regel moet beginnen met id dan een dubbel punt, naam en ingredienten");
        }

        var values = idAndValues[1].split(",");
        if (values.length < 1) {
            throw new SausRepositoryException("Na de dubbelpunt in properties file moet de naam van de saus komen");
        }

        try {
            var ingredienten = Arrays.copyOfRange(values, 1, values.length);
            return new Saus(Long.parseLong(idAndValues[0]), values[0], ingredienten);
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
