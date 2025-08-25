package desafio.application.usecase.brand;

import desafio.application.exception.NoContentException;
import desafio.application.usecase.brand.output.BrandOutput;
import desafio.domain.gateway.BrandGateway;
import desafio.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindBrandsUseCaseTest {

    private BrandGateway brandGateway;
    private FindBrandsUseCase findBrandsUseCase;

    @BeforeEach
    void setUp() {
        brandGateway = mock(BrandGateway.class);
        findBrandsUseCase = new FindBrandsUseCase(brandGateway);
    }

    @Test
    void execute_shouldReturnBrandOutput_whenGatewayReturnsBrands() {
        // Arrange
        Brand brand1 = new Brand(1L, "Ford");
        Brand brand2 = new Brand(2L, "Chevrolet");
        when(brandGateway.findAll()).thenReturn(List.of(brand1, brand2));

        // Act
        BrandOutput output = findBrandsUseCase.execute();

        // Assert
        assertNotNull(output);
        assertEquals(2, output.brands().size());
        assertEquals("Ford", output.brands().get(0).getName());
        verify(brandGateway, times(1)).findAll();
    }

    @Test
    void execute_shouldThrowNoContentException_whenGatewayReturnsEmptyList() {
        // Arrange
        when(brandGateway.findAll()).thenReturn(List.of());

        // Act & Assert
        assertThrows(NoContentException.class, () -> findBrandsUseCase.execute());
        verify(brandGateway, times(1)).findAll();
    }

    @Test
    void execute_shouldHandleNullReturnedFromGateway() {
        // Arrange
        when(brandGateway.findAll()).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> findBrandsUseCase.execute());
        verify(brandGateway, times(1)).findAll();
    }
}