package Final.FinalBoss.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse<T> {
    private boolean succes;
    private String message;
    private T data;

    public ApiResponse(boolean succes, String message, T data) {
        this.succes = succes;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(boolean succes, String message) {
        this.succes = succes;
        this.message = message;
        this.data = null;
    }
}
