/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.beans;

import com.javasd.mwc.annotations.MWC_MergeIgnore;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.springframework.stereotype.Component;

/**
 *
 * @author almir
 */
@Component( "objectMerger" )
public class ObjectMergerImpl implements ObjectMerger
{

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger( ObjectMergerImpl.class );

    @Override
    public Object mergeFields( Object typeNew, Object typeCurrent )
    {

        Object retCurrent;
        Object retNew;
        Method[] newMethods = typeNew.getClass().getDeclaredMethods();
        Method currentMethodGet;
        Method currentMethodSet;

        Class clazz;

        for ( Method newMethod : newMethods )
        {
            if ( !newMethod.isAnnotationPresent( MWC_MergeIgnore.class ) )
            {
                if ( newMethod.getName().startsWith( "get" ) )
                {
                    try
                    {
                        clazz = Class.forName( newMethod.getReturnType().toString().replace( "class ", "" ).replace( "interface ", "" ) );
                        retNew = newMethod.invoke( typeNew, null );

                        currentMethodGet = typeCurrent.getClass().getDeclaredMethod( newMethod.getName(), null );
                        retCurrent = currentMethodGet.invoke( typeCurrent, null );

                        if ( retNew != null && !retNew.equals( retCurrent ) )
                        {
                            String newMethodName = newMethod.getName();
                            String convertedGetSet = convertGetSet( newMethodName );
                            currentMethodSet = typeCurrent.getClass().getDeclaredMethod( convertedGetSet, clazz );
                            currentMethodSet.invoke( typeCurrent, retNew );
                            logger.info( " ===========>>> " + newMethod.getName() + " changed from '" + retCurrent + "' to '" + retNew + "'" );
                        }
                    }
                    catch ( IllegalAccessException ex )
                    {
                        Logger.getLogger( ObjectMergerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( IllegalArgumentException ex )
                    {
                        Logger.getLogger( ObjectMergerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( InvocationTargetException ex )
                    {
                        Logger.getLogger( ObjectMergerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( NoSuchMethodException ex )
                    {
                        Logger.getLogger( ObjectMergerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( SecurityException ex )
                    {
                        Logger.getLogger( ObjectMergerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    catch ( ClassNotFoundException ex )
                    {
                        Logger.getLogger( ObjectMergerImpl.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                }
            }
            else
            {
                logger.info( "\n\n=====> IGNORE: " + newMethod.getName() + "\n\n");
            }
        }
        return typeCurrent;
    }

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
