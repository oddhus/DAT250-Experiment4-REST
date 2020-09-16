package no.hvl.dat110.rest.enums;

public enum OperationTypes {
    FIND ("find"),
    CREATE ("create"),
    UPDATE ("update"),
    DELETE ("delete");

    private String operation;

    OperationTypes(String operation) {
        this.operation = operation;
    }
}
