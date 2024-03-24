package com.nebulashrine.thparty.common.exceptions;

import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.response.StatusCode;
import jakarta.servlet.ServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    /**
     * 捕获全局bindException (参数不合法)
     * @param request 请求
     * @param bindException 绑定异常
     * @return Result
     * @see Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(ServletRequest request, BindException bindException){
        String s = bindException.toString();
        List<FieldError> fieldErrors = bindException.getFieldErrors();
        String defaultMessage = fieldErrors.get(0).getDefaultMessage();

        log.warn("=> " + s);
        log.warn("=> " + defaultMessage);

        return Result.error(StatusCode.INVALID_PARAMETERS.getStatusCode(), StatusCode.INVALID_PARAMETERS.getResultMessage());
    }

    /**
     * 处理全局空指针异常
     * @param nullPointerException
     * @return Result
     * @see Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public Result nullPointerExceptionHandler(NullPointerException nullPointerException){
        String message = nullPointerException.getMessage();
        log.error("=> " + message);
        return Result.error(StatusCode.FAILED.getStatusCode(), StatusCode.FAILED.getResultMessage());
    }

//    /**
//     * 处理运行时错误
//     * @param runtimeException
//     * @return Result
//     * @see Result
//     */
//    @ResponseBody
//    @ExceptionHandler(RuntimeException.class)
//    public Result runtimeExceptionHandler(RuntimeException runtimeException){
//        String message = runtimeException.getMessage();
//        log.error("=> " + message);
//        return Result.err(new ErrorInfo(message, StatusCode.ERROR.getResultCode()));
//    }


    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityCannotFoundException.class)
    public Result EntityCannotFoundExceptionHandler(EntityCannotFoundException entityCannotFoundException){
        String message = entityCannotFoundException.getMessage();
        log.error("=> " + message);
        return Result.error(StatusCode.FAILED.getStatusCode(), message);
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MultipleEntitiesException.class)
    public Result MultipleEntitiesExceptionHandler(MultipleEntitiesException multipleEntitiesException){
        String message = multipleEntitiesException.getMessage();
        log.error("=> " + message);
        return Result.error(StatusCode.FAILED.getStatusCode(), message);
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public Result AccessDeniedExceptionHandler(AccessDeniedException accessDeniedException){
        String message = accessDeniedException.getMessage();
        log.error("=> " + message);
        return Result.error(StatusCode.USER_AUTH_ERROR.getStatusCode(), message);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotLoginException.class)
    public RedirectView UserNotLoginExceptionHandler(UserNotLoginException userNotLoginException){
        String message = userNotLoginException.getMessage();
        log.warn("=> " + message);
        return new RedirectView("/user/loginViaTHP");
    }
}
