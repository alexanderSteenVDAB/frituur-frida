package be.vdab.frituurfrida.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class Bericht {
    private final long id;
    @PastOrPresent
    private final LocalDate datum;
    @NotBlank
    private final String naam;
    @NotBlank
    private final String bericht;

    public Bericht(long id, LocalDate datum, String naam, String bericht) {
        this.id = id;
        this.datum = datum;
        this.naam = naam;
        this.bericht = bericht;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getNaam() {
        return naam;
    }

    public String getBericht() {
        return bericht;
    }
}
