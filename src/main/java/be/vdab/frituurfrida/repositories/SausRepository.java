package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;

import java.util.List;

public interface SausRepository {
    List<Saus> findAll();
}
