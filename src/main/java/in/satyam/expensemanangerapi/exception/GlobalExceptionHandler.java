package in.satyam.expensemanangerapi.exception;

import in.satyam.expensemanangerapi.entity.ErrorObj;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//an annotation, to handle the exceptions globally.
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    //annotation used to handle the specific exceptions and sending the custom responses to the client.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObj> handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest request){

            ErrorObj errorObj=new ErrorObj();

            errorObj.setStatusCode(HttpStatus.NOT_FOUND.value());
            errorObj.setMsg(ex.getMessage());
            errorObj.setTimestamp(new Date());


           return new ResponseEntity<>(errorObj,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ItemAlreadyPresentException.class)
    public ResponseEntity<ErrorObj> handleItemAlreadyPresentException(ItemAlreadyPresentException ex,WebRequest request){
          ErrorObj errorObj=new ErrorObj();

          errorObj.setMsg(ex.getMessage());
          errorObj.setStatusCode(HttpStatus.CONFLICT.value());
          errorObj.setTimestamp(new Date());

          return new ResponseEntity<>(errorObj,HttpStatus.CONFLICT);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObj> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request){

        ErrorObj errorObj=new ErrorObj();

        errorObj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObj.setMsg(ex.getMessage());
        errorObj.setTimestamp(new Date());


        return new ResponseEntity<>(errorObj,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObj> handleGeneralException(Exception ex, WebRequest request){

        ErrorObj errorObj=new ErrorObj();

        errorObj.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObj.setMsg(ex.getMessage());
        errorObj.setTimestamp(new Date());


        return new ResponseEntity<>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("statusCode", HttpStatus.BAD_REQUEST);

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("message", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
