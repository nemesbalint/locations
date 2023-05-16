package locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

public class LocationNotFoundException extends ErrorResponseException {
    public LocationNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND,
                asProblemDetail(String.format("Location id %d not found!", id)),
                null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setType(URI.create("location/not-found"));
        problemDetail.setTitle("Not found");
        return problemDetail;
    }
}
