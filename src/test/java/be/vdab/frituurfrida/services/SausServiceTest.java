package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.repositories.SausRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SausServiceTest {
    private SausService sausService;
    private Saus saus1;
    private Saus saus2;
    private final List<Saus> sauzen = new ArrayList<>();

    @Mock
    private SausRepository sausRepository;

    @BeforeEach
    void beforeEach() {
        sausService = new SausService(sausRepository);
        saus1 = new Saus(1, "cocktail", new String[]{"mayonaise", "ketchup", "cognac"});
        saus2 = new Saus(2, "mayonaise", new String[]{"ei", "mosterd"});
        sauzen.add(saus1);
        sauzen.add(saus2);
    }


    @Test
    void findAll() {
        when(sausRepository.findAll()).thenReturn(sauzen);
        assertThat(sausService.findAll()).containsExactly(saus1, saus2);
        verify(sausRepository).findAll();
    }

    @Test
    void findByBeginNaam() {
        when(sausRepository.findAll()).thenReturn(sauzen);
        assertThat(sausService.findByBeginNaam('c')).containsOnly(saus1);
        verify(sausRepository).findAll();
    }

    @Test
    void findById() {
        when(sausRepository.findAll()).thenReturn(sauzen);
        var gevondenSaus = sausService.findById(2);
        assertThat(gevondenSaus.isPresent()).isTrue();
        gevondenSaus.ifPresent(saus -> assertThat(saus).isEqualTo(saus2));
        verify(sausRepository).findAll();
    }
}