package desafio.application.usecase.vehicle;

import desafio.application.usecase.vehicle.input.UpdateVehicleInput;
import desafio.domain.gateway.VehicleGateway;
import desafio.domain.model.Brand;
import desafio.domain.model.Vehicle;
import io.quarkus.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateVehicleUseCaseTest {

    private VehicleGateway vehicleGateway;
    private Cache cache;
    private UpdateVehicleUseCase updateVehicleUseCase;

    @BeforeEach
    void setUp() {
        vehicleGateway = mock(VehicleGateway.class);
        cache = mock(Cache.class);
        when(cache.invalidate(anyString())).thenReturn(io.smallrye.mutiny.Uni.createFrom().voidItem());

        updateVehicleUseCase = new UpdateVehicleUseCase(vehicleGateway, cache);
    }

    @Test
    void execute_shouldUpdateVehicleAndInvalidateCache() {
        // Arrange
        Brand brand = new Brand(1L, "Ford");
        Vehicle vehicle = new Vehicle(1L, "Fusca", "note", brand);

        when(vehicleGateway.findByCode(1L)).thenReturn(vehicle);

        UpdateVehicleInput input = new UpdateVehicleInput(1L, "Ka", "Nova nota");

        // Act
        Optional<Void> result = updateVehicleUseCase.execute(input);

        // Assert
        assertTrue(result.isEmpty());
        assertEquals("Ka", vehicle.getName());
        assertEquals("Nova nota", vehicle.getNote());
        verify(vehicleGateway, times(1)).updateVehicle(vehicle);
        verify(cache, times(1)).invalidate("Ford");
    }

    @Test
    void execute_shouldThrowException_whenVehicleNotFound() {
        // Arrange
        when(vehicleGateway.findByCode(1L)).thenReturn(null);
        UpdateVehicleInput input = new UpdateVehicleInput(1L, "Ka", "Nota");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateVehicleUseCase.execute(input));
        assertEquals("Veiculo nao encontrado", exception.getMessage());
        verify(vehicleGateway, never()).updateVehicle(any());
        verify(cache, never()).invalidate(anyString());
    }

    @Test
    void execute_shouldThrowException_whenCodeInvalid() {
        UpdateVehicleInput input = new UpdateVehicleInput(0L, "Ka", "Nota");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateVehicleUseCase.execute(input));
        assertEquals("Codigo invalido", exception.getMessage());
        verify(vehicleGateway, never()).findByCode(any());
        verify(cache, never()).invalidate(anyString());
    }
}
