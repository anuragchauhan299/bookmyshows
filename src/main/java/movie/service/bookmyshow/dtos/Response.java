package movie.service.bookmyshow.dtos;
public class Response {
    private ResponseStatus responseStatus;
    private String message;

    public static Response getSuccessResponse(String message){
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.SUCCESS);
        response.setMessage(message);
        return response;
    }

    public static Response getFailureResponse(String message){
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.FAILURE);
        response.setMessage(message);
        return response;
    }

    public ResponseStatus getResponseStatus() { return responseStatus; }
    public void setResponseStatus(ResponseStatus responseStatus) { this.responseStatus = responseStatus; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
