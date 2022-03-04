package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import be.vdab.frituurfrida.repositories.SausRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SausService {
    private final SausRepository sausRepository;

    public SausService(@Qualifier("properties") SausRepository sausRepository) {
        this.sausRepository = sausRepository;
    }

    public List<Saus> findAll() {
                return sausRepository.findAll();
    }

    public List<Saus> findByBeginNaam(char letter) {
        return sausRepository.findAll().stream().filter(saus -> saus.getNaam().charAt(0) == letter).toList();
    }

    public Optional<Saus> findById(long id) {
        return sausRepository.findAll().stream().filter(saus -> saus.getNummer() == id).findFirst();

    }
}
