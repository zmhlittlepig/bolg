package exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-26
 * Time: 下午8:49
 */
public class InvalidReqException extends RuntimeException {


    private final String userErrMsg;
    private final int errorCode;

    public InvalidReqException(int errorCode, String sysErrMsg, String userErrMsg) {
        super(sysErrMsg);
        this.userErrMsg = userErrMsg;
        this.errorCode = errorCode;
    }

    public InvalidReqException(int errorCode, String sysErrMsg, String userErrMsg, Throwable cause) {
        super(sysErrMsg, cause);
        this.userErrMsg = userErrMsg;
        this.errorCode = errorCode;
    }

    public InvalidReqException(int errorCode, String sysErrMsg) {
        super(sysErrMsg);
        this.userErrMsg = null;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getUserErrMsg() {
        return userErrMsg;
    }

    public String getSysErrMsg() {
        return getMessage();
    }


}
