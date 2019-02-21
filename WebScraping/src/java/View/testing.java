/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author DELL
 */
public class testing {
    public static void main(String... args){
        try{
            System.out.println(Controller.TimesOfNow.fetchnews());
        }catch(Exception e){
            System.out.print(e);
        }
    }
}
