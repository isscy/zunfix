package cn.ff.zunfix.common.core.exception;

/**
 * 基础运行时异常
 *
 * @author fengfan 2019/12/31
 */
public class BasisException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BasisException(String message) {
        super(message);
    }

    public BasisException(Throwable cause) {
        super(cause);
    }

    public BasisException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
