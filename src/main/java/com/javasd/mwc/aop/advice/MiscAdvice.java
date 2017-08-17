/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.aop.advice;

import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.SERVICE.person.PersonService;
import com.javasd.mwc.WEB.output.MwcUser01;
import com.javasd.mwc.annotations.MWC_Email;
import com.javasd.mwc.annotations.MWC_LastScreen;
import com.javasd.mwc.annotations.MWC_StringSanitize;
import com.javasd.mwc.annotations.MWC_Test;
import com.javasd.mwc.util.beans.LoggedUser;
import com.javasd.mwc.util.beans.MiscDynamic;
import com.javasd.mwc.util.beans.MwcMail;
import com.javasd.mwc.util.beans.ObjectFiller;
import com.javasd.mwc.util.misc.MiscStatic;
import com.javasd.utils.stringutils.StringUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 *
 * @author Almir
 */
@Component
@Aspect
public class MiscAdvice
{

    private final Logger logger = Logger.getLogger( MiscAdvice.class );
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    ServletContext servletContext;
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private PersonService personService;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private MwcMail mwcMail;
    @Autowired
    private ObjectFiller objectFiller;
    @Autowired
    private MiscDynamic miscDynamic;

    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.*)" )
    public Object securityUiModelExecution( ProceedingJoinPoint pjp ) throws Throwable
    {
        String str;
        Object[] curArgs = pjp.getArgs();
        Object[] revArgs = new Object[ curArgs.length ];
        Object originalEntityClass = null;

        int i = 0;
        for ( Object obj : curArgs )
        {
//            logger.info( "\n\n******* RECEIVED ARG: " + obj + "\n\n" );
            originalEntityClass = MiscStatic.getOriginalEntityClass( obj );
            if ( originalEntityClass != null )
            {
                logger.info( "******* THIS PARAMETER IS AN INSTANCE OF: " + originalEntityClass );
//                if ( originalEntityClass.equals( SeriesDetail.class ) )
//                {
                //Field[] fields = originalEntityClass.getClass().cast( obj ).getClass().getDeclaredFields();
                Field[] fields = obj.getClass().getDeclaredFields();
                Method mSet;
                Method mGet;
                for ( Field f : fields )
                {
//                        logger.info( "******* DECLARED FIELDS: " + f);
                    if ( f.getAnnotation( MWC_StringSanitize.class ) != null )
                    {
                        mSet = obj.getClass().getDeclaredMethod( "set" + StringUtils.capitalizeString( f.getName() ), String.class );
                        mGet = obj.getClass().getDeclaredMethod( "get" + StringUtils.capitalizeString( f.getName() ) );
                        logger.info( "\n\n**==>> FIELD STRING: " + f.getName() + " <<==** SANITIZE(1)\n\n" );
                        mSet.invoke( obj, miscDynamic.stringSanitizer( ( String ) mGet.invoke( obj, null ) ) );
                    }
                }
//                }
            }
            // I'M NOT SURE IF I HAVE TO KEEP THIS AUTOMATIC ADDING ATTRIBUTE FOR 'LOGGEDUSER'
            if ( obj instanceof Model )
            {
                Model uiModel = ( Model ) obj;
                MwcUser01 mwcUser01 = new MwcUser01();
                MwcUser mwcUser = loggedUser.getMwcUser();
                if ( mwcUser != null )
                {
                    mwcUser01 = miscDynamic.getMwcUser01( loggedUser.getMwcUser() );
                }
                uiModel.addAttribute( "loggedUser", mwcUser01 );
                revArgs[ i] = obj;
                i++;
                //break;
            }
            else
            {
                if ( obj instanceof String )
                {
                    str = ( String ) obj;
                    logger.info( "\n\n**==>> GENERIC STRING:" + str + " <<==** SANITIZE(2)\n\n" );
                    revArgs[ i] = miscDynamic.stringSanitizer( str );
                    i++;
                }
                else
                {
                    revArgs[ i] = obj;
                    i++;
                }
            }
        }

        if ( loggedUser != null && loggedUser.getMwcUser() != null )
        {
            StringBuilder args = new StringBuilder();
            for ( Object obj : pjp.getArgs() )
            {
                if ( ( obj instanceof Long ) || ( obj instanceof Double ) || ( obj instanceof String ) )
                {
                    args.append( obj ).append( ", " );
                }
            }
//            String log = ""
//                    + new DateTime( System.currentTimeMillis() )
//                    + " >> " + pjp.getSignature().getDeclaringTypeName()
//                    + " >> " + pjp.getSignature().
//                    + " >> " + pjp.getSignature().getName()
//                    + " >> " + args.toString();

//            UserLog userLog = new UserLog();
//            userLog.setLogText( log );
//            userLog.setUser( loggedUser.getMwcUser() );
//            loggedUser.getMwcUser().getMwcUserLogs().add( userLog );
//            userService.update( loggedUser.getMwcUser() );
//            applicationContext.getParent().getDisplayName();
//            logger.info( "****************** servletContext.getRealPath(): " + servletContext.getRealPath( "/") );
//            logger.info( "****************** httpServletRequest.getLocalName().....: " + httpServletRequest.getLocalName() );
//            logger.info( "****************** httpServletRequest.getContextPath()...: " + httpServletRequest.getContextPath() );
//            logger.info( "****************** httpServletRequest.getPathInfo()......: " + httpServletRequest.getPathInfo() );
//            logger.info( "****************** httpServletRequest.getPathTranslated(): " + httpServletRequest.getPathTranslated() );
//            logger.info( "****************** httpServletRequest.getRemoteAddr()....: " + httpServletRequest.getRemoteAddr() );
//            logger.info( "****************** httpServletRequest.getRemoteHost()....: " + httpServletRequest.getRemoteHost() );
//            logger.info( "****************** httpServletRequest.getRequestURI()....: " + httpServletRequest.getRequestURI() );
//            logger.info( "****************** httpServletRequest.getRequestURL()....: " + httpServletRequest.getRequestURL() );
//            logger.info( "****************** httpServletRequest.getRemoteUser()....: " + httpServletRequest.getRemoteUser() );
            File file = new File( servletContext.getRealPath( "/WEB-INF/" + loggedUser.getMwcUser().getUsername() + ".log" ) );
//            logger.info( "************** file.getAbsolutePath(): " + file.getAbsolutePath() );
//            logger.info( "************** file.toURI(): " + file.toURI() );
            FileWriter fw = new FileWriter( file, true );
            BufferedWriter bw = new BufferedWriter( fw );
            bw.append( new DateTime( System.currentTimeMillis() ).toString() );
            bw.append( "<br />" );
            bw.append( ">" + httpServletRequest.getRequestURI() );
            bw.append( "<br />" );
            bw.append( "&nbsp;&nbsp;&nbsp;>" + pjp.getSignature().getDeclaringTypeName() );
            bw.append( "<br />" );
            bw.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>" + pjp.getSignature().getName() );
            bw.append( "<br />" );
            bw.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>" + args.toString() );
            bw.append( "<br />" );
            bw.close();
            fw.close();

        }

        Object ret = pjp.proceed( revArgs );
        return ret;
    }

    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.*) && @annotation(mWC_Email)" )
    public Object emailAnnotation(
            ProceedingJoinPoint pjp,
            MWC_Email mWC_Email ) throws Throwable
    {
        StringBuilder args = new StringBuilder();
        for ( Object obj : pjp.getArgs() )
        {
            if ( ( obj instanceof Long ) || ( obj instanceof Double ) || ( obj instanceof String ) || ( obj instanceof DateTime ) )
            {
                args.append( obj ).append( ", " );
            }
        }

        String msg = new DateTime( System.currentTimeMillis() ).toString()
                + " -> " + httpServletRequest.getRequestURI()
                + " -> " + pjp.getSignature().getDeclaringTypeName()
                + " -> " + pjp.getSignature().getName()
                + System.getProperty( "line.separator" )
                + "ARGS: " + args;

        Locale locale = miscDynamic.getDefaultLocale();

        Person loadedPerson;
        if ( loggedUser != null && loggedUser.getMwcUser() != null )
        {
            logger.info( "******* EMAIL -->> javasd.info@gmail.com/javasd.info@gmail.com/Activity of User: " + loggedUser.getMwcUser().getPerson().getFirstName() + "/msg: " + msg );
            loadedPerson = loggedUser.getMwcUser().getPerson();
        }
        else
        {
            loadedPerson = new Person();
            if ( pjp.getSignature().getName().equals( "verifyAccessCode" ) )
            {
                loadedPerson = personService.getByAccessCode( String.valueOf( pjp.getArgs()[ 0] ) );
                logger.info( "\n\n\n******* EMAIL -->> accessCode: *" + String.valueOf( pjp.getArgs()[ 0] ) + "*   LOADED PERSON (inside the if): " + loadedPerson );
            }
            logger.info( "\n\n\n******* EMAIL -->> NOT LOGGED USER/IP: " + httpServletRequest.getRemoteAddr() + "\n\nARGS: " + args );
        }
        if ( loadedPerson != null )
        {
            String msg1 = messageSource.getMessage(
                    "email.user.action.message.content",
                    new Object[]
                    {
                        new DateTime( System.currentTimeMillis() ).toString(),
                        httpServletRequest.getRequestURI(),
                        pjp.getSignature().getDeclaringTypeName(),
                        pjp.getSignature().getName(),
                        args,
                        null
                    },
                    locale );
            String subject = messageSource.getMessage(
                    "email.user.action.message.subject",
                    new Object[]
                    {
                        loadedPerson.getName()
                    },
                    locale );
            mwcMail.sendMail( "javasd.info@gmail.com", "javasd.info@gmail.com", subject, msg1 );
        }
        //mwcMail.sendMail( "javasd.info@gmail.com", "javasd.info@gmail.com", "Activity of User: " + loggedUser.getMwcUser().getPerson().getFirstName(), msg );
        return pjp.proceed();
    }

    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.*) && @annotation(mWC_LastScreen)" )
    public Object lastScreenAnnotation( ProceedingJoinPoint pjp, MWC_LastScreen mWC_LastScreen ) throws Throwable
    {
//        logger.info( " ********************* Last Screen: " + httpServletRequest.getRequestURI() );
        // INTERESTING: ServletWebRequest servletWebRequest = new ServletWebRequest( httpServletRequest );
        for ( Object obj : pjp.getArgs() )
        {
            if ( obj instanceof HttpServletResponse )
            {
                HttpServletResponse httpServletResponse = ( HttpServletResponse ) obj;
                String requestURI = httpServletRequest.getRequestURI();
                String reducedURI = requestURI.substring( httpServletRequest.getContextPath().length() );
//                logger.info( "**** reducedURI: " + reducedURI );
                MiscStatic.setLastScreenCookie( httpServletRequest, httpServletResponse, reducedURI );
                break;
            }
        }
        return pjp.proceed();
    }

    @Around( "within(com.javasd.mwc.DOMAIN.entity.* ) && @annotation(mWC_Test)" )
    public Object testAnnotation(
            ProceedingJoinPoint pjp,
            MWC_Test mWC_Test ) throws Throwable
    {
        Object[] args = pjp.getArgs();
        logger.info( "*** @MWC_Test/signature: " + pjp.getSignature() );
        if ( args[0] == null )
        {
            logger.info( "\n\n*** @MWC_Test/arg null: " + args[0] + ", " + args[ 1].getClass() );
//            return false;
            return pjp.proceed( new Object[]
            {
                -1.0, args[1]
            } );
        }
        else
        {
            logger.info( "\n\n*** @MWC_Test/arg class: " + args[0].getClass() + ", " + args[ 1].getClass() );
            return pjp.proceed();
//            return true;
        }
    }

}
