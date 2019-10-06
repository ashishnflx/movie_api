package com.netflix.movieapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class ResponseHandler {
    public static ResponseEntity getResponse(Object response){
        if(response == null)
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        return new ResponseEntity(response,HttpStatus.OK);
    }
    public static ResponseEntity getResponse(Collection response){
        if(response == null || response.isEmpty())
            return new ResponseEntity(response,HttpStatus.NOT_FOUND);
        return new ResponseEntity(response,HttpStatus.OK);
    }
    public static ResponseEntity getErrorResponse(String error){
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity getServerErrorResponse(String error){
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static ResponseEntity getResponse(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
