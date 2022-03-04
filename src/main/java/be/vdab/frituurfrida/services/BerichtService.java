package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Bericht;
import be.vdab.frituurfrida.repositories.BerichtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BerichtService {
    private final BerichtRepository berichtRepository;

    public BerichtService(BerichtRepository berichtRepository) {
        this.berichtRepository = berichtRepository;
    }

    public List<Bericht> findAll() {
        return berichtRepository.findAll();
    }

    public long create(Bericht bericht) {
        return berichtRepository.create(bericht);
    }

    public void delete(Long[] ids) {
        if (ids != null) {
            berichtRepository.delete(ids);
        }
    }
}
