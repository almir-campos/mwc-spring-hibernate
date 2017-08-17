/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.xml;

import com.javasd.utils.stringutils.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author almir
 */
public class ObjToXML
{

    private final Object obj;
    private final Field[] fields;
    private final Method[] methods;
    private final String[] fieldsAndMethods;

    public ObjToXML( Object obj, String[] fieldsAndMethods )
    {
        this.obj = obj;
        this.fields = obj.getClass().getDeclaredFields();
        this.methods = obj.getClass().getDeclaredMethods();
        this.fieldsAndMethods = fieldsAndMethods;
    }

    public String getFromFieldsXML( String rootNode, boolean showHeader )
    {
        Method tmpMethod;
        Object tmpObj;
        XMLBuilder xb = new XMLBuilder( rootNode, showHeader );
        for ( Field f : fields )
        {
            if ( fieldHasMethod( f, "get" ) )
            {
                try
                {
                    tmpMethod = obj.getClass().getDeclaredMethod( getMethodFromField( f, "get" ) );
                    tmpObj = tmpMethod.invoke( obj );
                    addNode( tmpMethod, xb, tmpObj );
//                    xb.addNode( f.getName(), tmpObj == null ? "" : tmpObj.toString() );
                }
                catch ( NoSuchMethodException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
                catch ( SecurityException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
                catch ( IllegalAccessException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
                catch ( IllegalArgumentException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
                catch ( InvocationTargetException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
            }
        }
        return xb.getXML();
    }

    public String getFromGettersXML( String rootNode, boolean showHeader )
    {
        XMLBuilder xb = new XMLBuilder( rootNode, showHeader );
        for ( Method m : methods )
        {
            if ( isGetMethod( m ) )
            {
                try
                {
                    Object tmpObj = m.invoke( obj );
                    addNode( m, xb, tmpObj );
//                    String returnType = m.getReturnType().getName();
//                    if ( returnType.equals( "[I" ) )
//                    {
//                        xb.getXMLBlockFromIntArray( ( int[] ) tmpObj, m.getName(), "int" );
//                    }
//                    else if ( !returnType.equals( "java.util.List" ) )
//                    {
//                        xb.addNode( m.getName(), tmpObj == null ? "" : tmpObj.toString() );
//                    }
                }
                catch ( IllegalAccessException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
                catch ( IllegalArgumentException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
                catch ( InvocationTargetException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
            }
        }
        return xb.getXML();
    }

    public String getFromGettersNotFieldsXML( String rootNode, boolean showHeader )
    {
        XMLBuilder xb = new XMLBuilder( rootNode, showHeader );
        for ( Method m : methods )
        {
            if ( isGetMethod( m ) && !methodHasField( m ) )
            {
                try
                {
                    Object tmpObj = m.invoke( obj );
                    addNode( m, xb, tmpObj );
                    //xb.addNode( m.getName(), tmpObj == null ? "" : tmpObj.toString() );
                }
                catch ( IllegalAccessException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
                catch ( IllegalArgumentException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
                catch ( InvocationTargetException ex )
                {
                    Logger.getLogger( ObjToXML.class.getName() ).log( Level.SEVERE, null, ex );
                }
            }
        }
        return xb.getXML();
    }

    private boolean isGetMethod( Method m )
    {
        return m.getName().substring( 0, 3 ).equals( "get" );
    }

    private boolean methodHasField( Method m )
    {
        if ( getFieldFromMethod( m ) == null )
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private String getFieldFromMethod( Method m )
    {
        String fName = null;
        String mFirstCharLowercase = m.getName().substring( 3 ).substring( 0, 1 ).toLowerCase();
        String fNameToFind = mFirstCharLowercase + m.getName().substring( 4 );
        for ( Field f : fields )
        {
            if ( f.getName().equals( fNameToFind ) )
            {
                fName = fNameToFind;
                break;
            }
        }
        return fName;
    }

    private boolean fieldHasMethod( Field f, String mType )
    {
        if ( getMethodFromField( f, mType ) == null )
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private String getMethodFromField( Field f, String mType )
    {
        String mName = null;
        String fUppercase = f.getName().substring( 0, 1 ).toUpperCase() + f.getName().substring( 1 );
        String mNameToFind = mType + fUppercase;
        for ( Method m : methods )
        {
            if ( m.getName().equals( mNameToFind ) )
            {
                mName = mNameToFind;
                break;
            }
        }
        return mName;
    }

    private void addNode( Method m, XMLBuilder x, Object o )
    {
        if ( !isValidNode( m ) )
        {
            return;
        }
        String returnType = m.getReturnType().getName();
        if ( returnType.equals( "[I" ) )
        {
            x.getXMLBlockFromIntArray( ( int[] ) o, m.getName(), "int" );
        }
        else if ( !returnType.equals( "java.util.List" ) )
        {
            x.addNode( StringUtils.addSeparatorsCamelCase(m.getName().substring(3), "-").toLowerCase(), o == null ? "" : o.toString() );
        }
    }
    
    private boolean isValidNode( Method m )
    {
        if (fieldsAndMethods == null )
        {
            return true;
        }
        boolean isValid = false;
        for ( String s : fieldsAndMethods )
        {
            if ( m.getName().equals( s ) || ( getFieldFromMethod( m ) != null && getFieldFromMethod( m ).equals( s ) ) )
            {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
    
    
}
