package com.example.sql_lite_project.MyExceptions;

public class InsertException extends Exception{
    public InsertException(String errorMessage){
        super(errorMessage);
    }
    public InsertException(){
        super();
    }
}
