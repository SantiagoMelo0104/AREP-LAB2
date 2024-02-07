

import org.arep.ApiConnection;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ApiConnectionTest {

    @Test
    public void testHttpClientAPI_cachedResponse() throws IOException {
        String searchFilm = "The Matrix";
        String response1 = ApiConnection.httpClientAPI(searchFilm);
        String response2 = ApiConnection.httpClientAPI(searchFilm);

        assertTrue(response1.contains("The Matrix"));

        assertEquals(response1, response2);
    }

    @Test
    public void testHttpClientAPI_differentMovie() throws IOException {
        String searchFilm1 = "The Matrix";
        String searchFilm2 = "Inception";
        String response1 = ApiConnection.httpClientAPI(searchFilm1);
        String response2 = ApiConnection.httpClientAPI(searchFilm2);


        assertTrue(response1.contains("The Matrix"));

        assertTrue(response2.contains("Inception"));
    }

    @Test
    public void testHttpClientAPI_emptyRequest() throws IOException {
        String response = ApiConnection.httpClientAPI("");

        assertEquals("", response);
    }
}