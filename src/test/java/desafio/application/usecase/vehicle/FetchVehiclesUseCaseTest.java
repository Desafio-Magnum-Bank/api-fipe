package desafio.application.usecase.vehicle;

import desafio.application.exception.NoContentException;
import desafio.application.usecase.vehicle.output.VehicleOutput;
import desafio.domain.gateway.VehicleGateway;
import desafio.domain.model.Brand;
import desafio.domain.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FetchVehiclesUseCaseTest {

    private VehicleGateway vehicleGateway;
    private FetchVehiclesUseCase fetchVehiclesUseCase;

    @BeforeEach
    void setUp() {
        vehicleGateway = mock(VehicleGateway.class);
        fetchVehiclesUseCase = new FetchVehiclesUseCase(vehicleGateway);
    }

    @Test
    void execute_shouldThrowNoContentException_whenNoVehiclesFound() {
        // Arrange
        when(vehicleGateway.findByBrand("Fiat")).thenReturn(List.of());

        // Act & Assert
        assertThrows(NoContentException.class, () -> fetchVehiclesUseCase.execute("Fiat"));
        verify(vehicleGateway, times(1)).findByBrand("Fiat");
    }

    @Test
    void execute_shouldThrowIllegalArgumentException_whenBrandIsNull() {
        assertThrows(IllegalArgumentException.class, () -> fetchVehiclesUseCase.execute(null));
        verify(vehicleGateway, never()).findByBrand(any());
    }

    @Test
    void execute_shouldThrowIllegalArgumentException_whenBrandIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> fetchVehiclesUseCase.execute("  "));
        verify(vehicleGateway, never()).findByBrand(any());
    }
}