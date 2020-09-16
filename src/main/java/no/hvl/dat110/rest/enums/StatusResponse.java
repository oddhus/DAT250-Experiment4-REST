package no.hvl.dat110.rest.enums;

public enum StatusResponse {
    SUCCESS ("Success"),
    ERROR ("Error");

    private String status;

    private StatusResponse(String status) {
        this.status = status;
    }
}
