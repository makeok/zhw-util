package com.zhw.core.classhotloader;

public class Hot {
    public void hot(){
        System.out.println(" version 3 : "+this.getClass().getClassLoader());
    }
}
