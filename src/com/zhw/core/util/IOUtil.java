package com.zhw.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtil {

    /** 
     * Use InputStreamReader and System.in to read data from console 
     *  
     * @param prompt 
     *             
     * @return input string 
     */  
    public static String readDataFromConsole(String prompt) {  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
        String str = null;  
        try {  
            System.out.print(prompt);  
            str = br.readLine();  
//            br.close();
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return str;  
    }  
}
