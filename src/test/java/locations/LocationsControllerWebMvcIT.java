package locations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = LocationsController.class)
public class LocationsControllerWebMvcIT {

    @MockBean
    LocationsService locationsService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testListLocations() throws Exception {
        when(locationsService.listLocations(any(), any()))
                .thenReturn(List.of(
                        new LocationDto(1L, "Fót", 1.1, 2.1),
                        new LocationDto(2L, "Dunakeszi", 2.1, 3.1)
                ));
        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name", equalTo("Fót")))
        ;
    }

    @Test
    public void testFindLocationById() throws Exception {
        when(locationsService.findLocationById(1L))
                .thenReturn(new LocationDto(1L, "Fót", 1.1, 2.1)
                );
        mockMvc.perform(get("/locations/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", equalTo("Fót")))
        ;
    }

    @Test
    public void testCreateLocation() throws Exception {
        when(locationsService.createLocation(any()))
                .thenReturn(new LocationDto(0L, "Fót", 1.1, 2.1)
                );

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/locations")
                        .content(asJsonString(new LocationDto(0L, "Fót", 1.1, 2.1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status(). isCreated ())
                .andDo(print())
                .andExpect(jsonPath("$.name", equalTo("Fót")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateLocation() throws Exception {
        when(locationsService.updateLocation(anyLong(), any()))
                .thenReturn(new LocationDto(0L, "Fót", 1.1, 2.1));

        mockMvc.perform(MockMvcRequestBuilders
                                .put("/locations/0")
                                .content(asJsonString(new LocationDto(0L, "Fót", 1.1, 2.1)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status(). isAccepted ())
                .andDo(print())
                .andExpect(jsonPath("$.name", equalTo("Fót")));
    }

    @Test
    public void testDeleteLocation() throws Exception {
        mockMvc.perform(delete("/locations/1"))
                .andExpect(status().isNoContent())
                .andDo(print())
                ;
    }

}
