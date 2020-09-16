package no.hvl.dat110.rest.response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Data;
import no.hvl.dat110.rest.enums.OperationTypes;
import no.hvl.dat110.rest.enums.StatusResponse;

@Data
public class TodoResponse {

    private StatusResponse status;
    private String message;
    private OperationTypes operationType;
    private JsonElement data;

    public TodoResponse(JsonElement data, OperationTypes operationType) {
        this.operationType = operationType;
        this.data = data;
    }

    public TodoResponse(StatusResponse status, String message){
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public String toJson(){
        setStatus(data.getAsJsonObject().size() == 0 ? StatusResponse.ERROR : StatusResponse.SUCCESS);
        setMessage(data.getAsJsonObject().size() == 0 ? "Could not " + operationType.name() + " the specified todo" : "");

        Gson gson = new Gson();
        return gson.toJson(this);
    }


}
