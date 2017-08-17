/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.misc;

/**
 *
 * @author Almir Campos
 */
public class Msg
{

    private static int counter = 0;

    public static void msg()
    {
        msg( "" );
    }

    public static void msg( int number )
    {
        msg( String.valueOf( number ) );
    }
    
    public static void msg( String msg )
    {
        System.out.println( ">>> " + ( counter < 10 ? " " : "" ) + counter + " <<< " + msg );
        counter++;
    }
}
