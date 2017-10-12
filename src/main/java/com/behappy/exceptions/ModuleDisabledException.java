package com.behappy.exceptions;

public class ModuleDisabledException extends RuntimeException {
    public ModuleDisabledException(){
        super();
    }

    public ModuleDisabledException(String s){
        super(s);
    }
}
