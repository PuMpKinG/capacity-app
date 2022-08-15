package de.chu.capacityapp.server.error;

public class CapacityAppException extends IllegalStateException {

    public CapacityAppException(CapacityAppError error){
        super(error.getMessage());
    }

    public CapacityAppException(CapacityAppError error, Object ...params){
        super(String.format(error.getMessage(), params));
    }

    public CapacityAppException(String errorMsg){
        super(errorMsg);
    }
}
