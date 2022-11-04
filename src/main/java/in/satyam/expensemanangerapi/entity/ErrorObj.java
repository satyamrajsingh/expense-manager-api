package in.satyam.expensemanangerapi.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorObj {

    private Integer statusCode;
    private String msg;
    private Date timestamp;

}
