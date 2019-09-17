package exception;

/**
 * 异常信息组成字符串信息
 */
public class ExceptionUtil {
    public static String buildExceptionStackTrace(Throwable throwable) {
        StringBuilder error = new StringBuilder();
        StackTraceElement[] stackTraceArr = throwable.getStackTrace();
        for (int i = 0; i < stackTraceArr.length; i++) {
            error.append(stackTraceArr[i] + "\n");
        }
        return error.toString();
    }
}
