package utils;


public class ExceptionUtil {
	public static String buildExceptionStackTrace(Throwable throwable)
	{
		StringBuilder error = new StringBuilder();
		StackTraceElement[] stackTraceArr = throwable.getStackTrace();
		//如果是验证码短信，发送一次失败后，就将其设置成失败数为MAX_FAIL_TIMES，不再发送
		for(int i = 0 ; i < stackTraceArr.length; i++)
		{
			error.append(stackTraceArr[i]+"\n");
		}
		return error.toString();
	}
}
