package no.hvl.dat110.rest.response;

import com.google.gson.JsonElement;
import lombok.Data;
import no.hvl.dat110.rest.enums.StatusResponse;

@Data
public class StandardResponse {

    private StatusResponse status;
    private String message;
    private JsonElement data;

    public StandardResponse(StatusResponse status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
    public StandardResponse(StatusResponse status, JsonElement data) {
        this.status = status;
        this.message = "";
        this.data = data;
    }
}
