package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.dto.SnacksMetAantalVerkocht;
import be.vdab.frituurfrida.repositories.SnackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnackService {
    private final SnackRepository snackRepository;

    public SnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    public Optional<Snack> read(long id) {
        return snackRepository.findById(id);
    }

    public void update(Snack snack) {
        snackRepository.update(snack);
    }

    public List<Snack> findByBeginNaam(String beginNaam) {
        return snackRepository.findByBeginNaam(beginNaam);
    }

    public List<SnacksMetAantalVerkocht> findSnacksMetAantalVerkocht() {
        return snackRepository.findSnacksMetAantalVerkocht();
    }
}
