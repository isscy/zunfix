package cn.ff.zunfix.common.core.exception;

/**
 * 内部异常 ， 由于开发造成的或者数据导致的异常
 *
 * @author fengfan 2020/9/14
 */
public class InnerException extends BasisException {

    public InnerException(String message) {
        super(message);
    }

    public InnerException(Throwable cause) {
        super(cause);
    }

    public InnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InnerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
