package rio.brunorodrigues.batchprogram.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bruno.rodrigues
 */
public class UserHomeUtils {
    
    public static String getPath() throws Exception{
        String OS = System.getProperty("os.name");
        
        if (OS.contains("Linux")){
            return "~";
        }else if (OS.contains("Windows")){
            return System.getProperty("user.home");
        }
        throw new Exception ("Cannot figure out the operation system");
    } 
}
