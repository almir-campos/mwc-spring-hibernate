/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.misc;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.MwcUserLog;
import com.javasd.mwc.DOMAIN.entity.MwcUserRole;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.PersonInBranch;
import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.SeriesDetail;
import com.javasd.mwc.WEB.CONTROLLER.MwcUserController;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author almir
 */
public class MiscStatic
{

    private static final Logger logger = Logger.getLogger( MwcUserController.class );

    public static String mixer( String string )
    {
//        try
//		{
//			MessageDigest hasheador = MessageDigest.getInstance("SHA-512");
//			byte[] stringBytes = hasheador.digest(string.getBytes("UTF-8"));
//			return URLEncoder.encode(new String(stringBytes, "UTF-8"), "UTF-8");
//		}
//		catch (Exception ex)
//		{
//			throw new RuntimeException(ex);
//		}
        return string + "*";
    }
    
    public static Object getOriginalEntityClass( Object obj )
    {
        if ( obj == null )
        {
            return null;
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        classes.add( Branch.class );
        classes.add( Company.class );
        classes.add( MwcUser.class );
        classes.add( MwcUserLog.class );
        classes.add( MwcUserRole.class );
        classes.add( Person.class );
        classes.add( PersonInBranch.class );
        classes.add( Series.class );
        classes.add( SeriesDetail.class );

        int indexOf = classes.indexOf( obj.getClass() );
        if ( indexOf == -1 )
        {
            return null;
        }

        return classes.get( indexOf );
    }

//    public static void xyz( Object clazz )
//    {
//        Field[] fields = clazz.getFields();
//        for ( Field f : fields )
//        {
////            if ( f.getAnnotation( MWC_StringSanitize.class ) != null )
////            {
//                logger.info( "\n\n====> FIELD NAME: " + f.getName()
//                        + ", ANNOTATIONS: " + f.getDeclaredAnnotations().length 
//                        + ", ANNOTATION SANITIZE: " + f.getAnnotation( MWC_StringSanitize.class ) );
////            }
//        }
//    }
//    public static String stringSanitizer( String str )
//    {
//
//        // THE REPLACEMENT MUST BE FOR ONE CHARACTER ONLY AND IT MUST NOT BE SPECIAL
//        if ( str == null )
//        {
//            return null;
//        }
//        return str.
//                replace( "<", "?" ).
//                replace( ">", "?" );
//    }
    public static String stringJodaDateTime( DateTime dateTime )
    {
        String r = "";
        if ( dateTime != null )
        {
            r = org.joda.time.format.DateTimeFormat.
                    forPattern( "yyyy-MM-dd" ).
                    print( dateTime );
        }
        return r;
    }

    public static DateTime toDateTimeFromStringJodaTime( String string )
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern( "yyyy-MM-dd" );
        return formatter.parseDateTime( string );
    }

//    public static String setLocaleCookie( HttpServletRequest request, HttpServletResponse response, String preferredLanguage )
//    {
//        Cookie ck = WebUtils.getCookie( request, "cocale" );
//        if ( ck != null )
//        {
//            ck.setMaxAge( 0 );
////            ck.setPath( request.getContextPath() );
//            ck.setPath( "/" );
////            ck.setPath( "/mwc/" );
//            ck.setValue( null );
//            response.addCookie( ck );
//            return request.getContextPath();
//        }
//        ck = new Cookie( "xocale", preferredLanguage );
////        ck.setPath( request.getContextPath() );
//        ck.setPath( "http://www.google.com" );
////        ck.setPath( "/mwc/" );
//        response.addCookie( ck );
//        return request.getContextPath();
//    }
    public static String setLastScreenCookie( HttpServletRequest request, HttpServletResponse response, String lastScreen )
    {
        Cookie lastScreenCookie = WebUtils.getCookie( request, "MWC_lastScreen" );
        if ( lastScreenCookie == null )
        {
            // ADD THE NEW COOKIE/VALUE
            lastScreenCookie = new Cookie( "MWC_lastScreen", "/mwcUser/show" );
            lastScreenCookie.setPath( request.getContextPath() + "/" );
            response.addCookie( lastScreenCookie );
            return null;
        }

        // REMOVE THE OLD COOKIE/VALUE
        lastScreenCookie.setMaxAge( 0 );
        lastScreenCookie.setValue( null );
        response.addCookie( lastScreenCookie );

        // ADD THE NEW COOKIE/VALUE
        lastScreenCookie = new Cookie( "MWC_lastScreen", lastScreen );
        lastScreenCookie.setValue( lastScreen );
        lastScreenCookie.setPath( request.getContextPath() + "/" );
        response.addCookie( lastScreenCookie );
        return request.getContextPath();
    }

    public static void addNotLoggedInCookie( HttpServletRequest request, HttpServletResponse response )
    {
        Cookie mWC_NotLoggedIn = WebUtils.getCookie( request, "MWC_NotLoggedIn" );
        if ( mWC_NotLoggedIn == null )
        {
            // ADD THE NEW COOKIE/VALUE
            mWC_NotLoggedIn = new Cookie( "MWC_NotLoggedIn", "true" );
            mWC_NotLoggedIn.setPath( request.getContextPath() + "/" );
            response.addCookie( mWC_NotLoggedIn );
        }
    }

    public static void removeNotLoggedInCookie( HttpServletRequest request, HttpServletResponse response )
    {
        Cookie mWC_NotLoggedIn = WebUtils.getCookie( request, "MWC_NotLoggedIn" );
        // REMOVE THE OLD COOKIE/VALUE
        if ( mWC_NotLoggedIn != null )
        {
            mWC_NotLoggedIn.setMaxAge( 0 );
            mWC_NotLoggedIn.setPath( request.getContextPath() + "/" );
            mWC_NotLoggedIn.setValue( null );
            response.addCookie( mWC_NotLoggedIn );
        }
    }

}
