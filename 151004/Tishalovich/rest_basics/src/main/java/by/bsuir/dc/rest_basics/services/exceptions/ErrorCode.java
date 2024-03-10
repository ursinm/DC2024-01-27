package by.bsuir.dc.rest_basics.services.exceptions;

public class ErrorCode {

    private final int errorCode;

    public ErrorCode(int HttpResponseCode, int subCode) {
        errorCode = HttpResponseCode * 100 + subCode;
    }

    public int getHttpResponseCode() {
        return errorCode / 100;
    }

}
