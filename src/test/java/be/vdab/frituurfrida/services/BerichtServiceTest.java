package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Bericht;
import be.vdab.frituurfrida.repositories.BerichtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BerichtServiceTest {
    @Mock
    private BerichtRepository repository;
    private BerichtService service;

    @BeforeEach
    void beforeEach() {
        service = new BerichtService(repository);
    }

    @Test
    void create() {
        var bericht = new Bericht(0, LocalDate.now(), "tester2", "Testbericht");
        when(repository.create(bericht)).thenReturn(1L);
        assertThat(service.create(bericht)).isEqualTo(1L);
        verify(repository).create(bericht);
    }
}