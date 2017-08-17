/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.beans;

import com.javasd.mwc.annotations.MWC_FillField;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author almir
 */
@Component( "objectFiller" )
public class ObjectFillerImpl implements ObjectFiller
{

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( ObjectFillerImpl.class );
    
    @Override
    public Object fillFields( Object typeFrom, Object typeTo )
    {
        Class clazz = null;
        Object objectTo = null;
        try
        {
            clazz = Class.forName( typeTo.toString().replace( "class ", "" ) );
            objectTo = clazz.newInstance();
            Method fromMethod;
            Object ret;
            Method[] toMethods = objectTo.getClass().getDeclaredMethods();

            for ( Method m : toMethods )
            {
                if ( m.isAnnotationPresent( MWC_FillField.class ) )
                {
                    try
                    {
                        fromMethod = typeFrom.getClass().getDeclaredMethod( convertGetSet( m.getName() ), null );
                        ret = fromMethod.invoke( typeFrom, null );
                        Object o = m.invoke( objectTo, ret );
                        //logger.info( "*** fillFields: " + o );
                    }
                    catch ( NoSuchMethodException ex )
                    {
                        Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( SecurityException ex )
                    {
                        Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( IllegalAccessException ex )
                    {
                        Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( IllegalArgumentException ex )
                    {
                        Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( InvocationTargetException ex )
                    {
                        Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }

                }
            }
        }
        catch ( ClassNotFoundException ex )
        {
            Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( InstantiationException ex )
        {
            Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( IllegalAccessException ex )
        {
            Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
        }

        return objectTo;

    }

//    public Object fillFields(Object tFrom, Object tTo)
//    {
//        Method fromMethod;
//        Object ret;
//        Method[] toMethods = tTo.getClass().getDeclaredMethods();
//
//        for ( Method m : toMethods )
//        {
//            if ( m.getName().startsWith( "set" ) )
//            {
//                try
//                {
//                    fromMethod = tFrom.getClass().getDeclaredMethod( convertGetSet( m.getName() ), null );
//                    ret = fromMethod.invoke( tFrom, null );
//                    m.invoke( tTo, ret );
//                }
//                catch ( NoSuchMethodException ex )
//                {
//                    Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
//                }
//                catch ( SecurityException ex )
//                {
//                    Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
//                }
//                catch ( IllegalAccessException ex )
//                {
//                    Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
//                }
//                catch ( IllegalArgumentException ex )
//                {
//                    Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
//                }
//                catch ( InvocationTargetException ex )
//                {
//                    Logger.getLogger( ObjectFillerImpl.class.getName() ).log( Level.SEVERE, null, ex );
//                }
//
//            }
//        }
//        
//        return tTo;
//    }
//    
    private String convertGetSet( String type )
    {
        String convertedType;
        if ( type.startsWith( "get" ) )
        {
            convertedType = type.replace( "get", "set" );
        }
        else
        {
            convertedType = type.replace( "set", "get" );
        }
        return convertedType;
    }
}
