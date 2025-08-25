package desafio.application;

import desafio.domain.gateway.BrandGateway;
import desafio.domain.model.Brand;
import desafio.infrastructure.messaging.BrandProducer;
import desafio.infrastructure.mapper.BrandMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InitialLoadUseCaseTest {

    private BrandGateway brandGateway;
    private BrandProducer brandProducer;
    private InitialLoadUseCase initialLoadUseCase;

    @BeforeEach
    void setUp() {
        brandGateway = mock(BrandGateway.class);
        brandProducer = mock(BrandProducer.class);
        initialLoadUseCase = new InitialLoadUseCase(brandGateway, brandProducer);
    }

    @Test
    void execute_shouldHandleEmptyBrandList() {
        // Arrange
        when(brandGateway.fetchBrands()).thenReturn(List.of());

        // Act & Assert
        assertDoesNotThrow(() -> initialLoadUseCase.execute());
        verify(brandProducer, never()).send(any());
    }

    @Test
    void execute_shouldPropagateExceptionFromProducer() {
        // Arrange
        Brand brand = new Brand(1L, "Ford");
        when(brandGateway.fetchBrands()).thenReturn(List.of(brand));
        doThrow(new RuntimeException("Falha ao enviar")).when(brandProducer).send(any());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> initialLoadUseCase.execute());
        assertEquals("Falha ao enviar", exception.getMessage());
    }
}