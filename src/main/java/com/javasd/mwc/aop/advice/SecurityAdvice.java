/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.aop.advice;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.SERVICE.branch.BranchService;
import com.javasd.mwc.SERVICE.mwcuser.UserService;
import com.javasd.mwc.annotations.MWC_Admin;
import com.javasd.mwc.annotations.MWC_SameUser;
import com.javasd.mwc.util.beans.LoggedUser;
import com.javasd.mwc.WEB.CONTROLLER.MwcUserController;
import com.javasd.mwc.WEB.output.Company01;
import com.javasd.mwc.annotations.MWC_Realm;
import com.javasd.mwc.annotations.MWC_Role;
import com.javasd.mwc.util.misc.MiscStatic;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Almir
 */
@Component
@Aspect
public class SecurityAdvice
{

    private final Logger logger = Logger.getLogger( MwcUserController.class );
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private UserService mwcUserService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private HttpServletRequest httpServletRequest;
//    @Autowired
//    private ServletWebRequest servletWebRequest;

    
    ////////////////////////////////////////////////////////////////////////////
    //
    // ROLE ACCESS CONTROL
    //
    ////////////////////////////////////////////////////////////////////////////
    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.*) && @annotation(mWC_Role) && args( .., httpServletResponse )" )
    public Object securityRolesAccess(
            ProceedingJoinPoint pjp,
            MWC_Role mWC_Role,
            HttpServletResponse httpServletResponse ) throws Throwable
    {
        // IF NO USER IS LOGGED IN
        if ( loggedUser == null || ( loggedUser != null && loggedUser.getMwcUser() == null ) )
        {
            return "redirec:/";
        }
        
        // IF ADMIN, GO AHEAD.
        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) )
        {
            logger.info( "*** ROLE CONTROL - is admin - PROCEED\n======================\n\n" );
            Object ret = pjp.proceed();
            return ret;
        }
        
        String[] requestedAuthorities = mWC_Role.value();
        
        if ( loggedUser.getMwcUser().hasAnyRole( requestedAuthorities ) )
        {
            logger.info( "*** ROLE CONTROL - has role - PROCEED\n======================\n\n" );
            Object ret = pjp.proceed();
            return ret;
        }
        
        Cookie mWC_NotLoggedIn = WebUtils.getCookie( httpServletRequest, "MWC_NotLoggedIn" );
        if ( mWC_NotLoggedIn != null )
        {
            logger.info( "*** ROLE CONTROL - JUST LOGIN - PROCEED\n======================\n\n" );
            MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
            return "redirect:/mwcUser/show";
        }
        
        logger.info( "*** ROLE CONTROL - FORBIDDEN - DO NOT PROCEED\n======================\n\n" );
        return "redirect:/public/accessDenied?errorCode=error_code_access_denied_14";
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // REALM ACCESS CONTROL
    //
    ////////////////////////////////////////////////////////////////////////////
    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.*) && @annotation(mWC_Realm)" )
    public Object securityRealmAccess(
            ProceedingJoinPoint pjp,
            MWC_Realm mWC_Realm ) throws Throwable
    {
        // IF NO USER IS LOGGED IN
        if ( loggedUser == null || ( loggedUser != null && loggedUser.getMwcUser() == null ) )
        {
            return "redirec:/";
        }

        Object[] args = pjp.getArgs();
        String idParamName = mWC_Realm.value();
        HttpServletResponse httpServletResponse = null;
        Long idParamValue = null;
        logger.info( "\n\n========= ARGS =============" );
        // GET THE ID
        for ( Object o : args )
        {
            logger.info( "*** " + o.getClass().getSimpleName() );
            if ( o instanceof Long )
            {
                idParamValue = ( Long ) o;
            }
            if ( o instanceof HttpServletResponse )
            {
                httpServletResponse = ( HttpServletResponse ) o;
            }
        }

        // IF ADMIN, GO AHEAD.
        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) )
        {
            logger.info( "*** ADMIN - PROCEED\n======================\n\n" );
            MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
            Object ret = pjp.proceed();
            return ret;
        }

        // THE PARAMETER IS FROM COMPANY
        if ( idParamName.equals( "company" ) )
        {
            Company01 companyBasicData = loggedUser.getCompanyBasicData();
            logger.info( "\n\n** companyId: " + idParamValue + ", loggedUserCompanyId: " + ( companyBasicData == null ? null : companyBasicData.getId() ) );
            if ( companyBasicData != null )
            {
                if ( idParamValue == companyBasicData.getId() )
                {
                    logger.info( "*** COMPANY OF THE USER - PROCEED\n======================\n\n" );
                    MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                    Object ret = pjp.proceed();
                    return ret;
                }
            }
            else
            {
                MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                return "redirect:/mwcUser/show";
            }
        }

        // THE PARAMETER IS FROM BRANCH
        if ( idParamName.equals( "branch" ) )
        {
            Company01 companyBasicData = loggedUser.getCompanyBasicData();
            logger.info( "\n\n** branchId: " + idParamValue + ", loggedUserCompanyId: " + ( companyBasicData == null ? null : companyBasicData.getId() ) );
            if ( companyBasicData != null )
            {
                Branch loadedBranch = branchService.get( idParamValue );
                if ( loadedBranch != null && loadedBranch.getCompany().getId() == companyBasicData.getId() )
                {
                    logger.info( "*** REALM CONTROL (BRANCH) - COMPANY OF THE USER - PROCEED\n======================\n\n" );
                    MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                    Object ret = pjp.proceed();
                    return ret;
                }
            }
            else
            {
                MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                return "redirect:/mwcUser/show";
            }
        }

        // THE  PARAMETER IS FROM PERSON
        if ( idParamName.equals( "person" ) )
        {
            logger.info( "\n\n** idParamValue: " + idParamValue + ", loggedUser.mwcuser.person.id: " + loggedUser.getMwcUser().getPerson().getId() + "\n\n" );
            if ( idParamValue == loggedUser.getMwcUser().getPerson().getId() )
            {
                logger.info( "*** PERSON OF THE USER - PROCEED\n======================\n\n" );
                MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                Object ret = pjp.proceed();
                return ret;
            }

            Person managedPerson = loggedUser.isManagerOfPerson( idParamValue );
            if ( managedPerson != null )
            {
                logger.info( "*** MANAGED PERSON - PROCEED\n======================\n\n" );
                MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                Object ret = pjp.proceed();
                return ret;
            }
        }

        // THE PARAMETER IS FROM USER
        if ( idParamName.equals( "user" ) )
        {
            logger.info( "\n\n** idParamValue: " + idParamValue + ", loggedUser.mwcuser.id: " + loggedUser.getMwcUser().getId() + "\n\n" );
            if ( idParamValue == loggedUser.getMwcUser().getId() )
            {
                logger.info( "*** SAME USER - PROCEED\n======================\n\n" );
                MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                Object ret = pjp.proceed();
                return ret;
            }

            MwcUser managedUser = loggedUser.isManagerOfUser( idParamValue );
            if ( managedUser != null )
            {
                logger.info( "*** MANAGED USER - PROCEED\n======================\n\n" );
                MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                Object ret = pjp.proceed();
                return ret;
            }
        }

        // THE PARAMETER IS FROM SERIES
        if ( idParamName.equals( "series" ) )
        {

            if ( loggedUser.isTheUserOfTheSeries( idParamValue ) )
            {
                logger.info( "*** SAME USER - PROCEED\n======================\n\n" );
                MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                Object ret = pjp.proceed();
                return ret;
            }

            if ( loggedUser.isManagerOfUserOfSeries( idParamValue ) )
            {
                logger.info( "*** MANAGED USER - PROCEED\n======================\n\n" );
                MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
                Object ret = pjp.proceed();
                return ret;
            }

        }

        Cookie mWC_NotLoggedIn = WebUtils.getCookie( httpServletRequest, "MWC_NotLoggedIn" );
        if ( mWC_NotLoggedIn != null )
        {
            logger.info( "*** JUST LOGIN - PROCEED\n======================\n\n" );
            MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
            return "redirect:/mwcUser/show";
        }

        logger.info( "========= END OF PROCESSING =============\n\n" );

        // ACCESS DENIED
        logger.info( "*** ACCESS DENIED - DO NOT PROCEED\n======================\n\n" );
        return "redirect:/public/accessDenied?errorCode=error_code_access_denied_14";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // ADMIN ACCESS ONLY
    //
    ////////////////////////////////////////////////////////////////////////////
    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.*) && @annotation(mWC_Admin) && args( .., httpServletResponse )" )
    public Object securityAdminExecution( 
            ProceedingJoinPoint pjp,
            MWC_Admin mWC_Admin,
            HttpServletResponse httpServletResponse ) throws Throwable
    {
        if ( loggedUser == null || loggedUser.getMwcUser() == null )
        {
            return "redirect:/";
        }

        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) )
        {
            MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
            Object ret = pjp.proceed();
            return ret;
        }
        
        Cookie mWC_NotLoggedIn = WebUtils.getCookie( httpServletRequest, "MWC_NotLoggedIn" );
        if ( mWC_NotLoggedIn != null )
        {
            logger.info( "*** NOT ADMIN, BUT JUST LOGIN - PROCEED\n======================\n\n" );
            MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
            return "redirect:/mwcUser/show";
        }
        
        return "redirect:/public/accessDenied?errorCode=error_code_access_denied_14";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // OTHER (TO BE REMOVED?)
    //
    ////////////////////////////////////////////////////////////////////////////
//    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.MwcUserController) && @annotation(mWC_SameUser) && args( userId, .. )" )
//    public Object securitySameUserExecution( ProceedingJoinPoint pjp, MWC_SameUser mWC_SameUser, Long userId ) throws Throwable
//    {
//
//        if ( loggedUser == null || loggedUser.getMwcUser() == null )
//        {
//            return "redirect:/";
//        }
//
//        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) || loggedUser.getMwcUser().getId() == userId )
//        {
//            Object ret = pjp.proceed();
//            return ret;
//        }
//
//        if ( loggedUser.getMwcUser().hasRole( "ROLE_MANAGER" ) )
//        {
//            MwcUser loadedUser = mwcUserService.get( userId );
//            if ( loadedUser != null && loadedUser.getPerson().getManager() != null && loadedUser.getPerson().getManager().getId() == loggedUser.getMwcUser().getPerson().getId() )
//            {
//                Object ret = pjp.proceed();
//                return ret;
//            }
//        }
//        return "redirect:/public/accessDenied?errorCode=error_code_access_denied_11";
//    }
//    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.SeriesController) && @annotation(mWC_SameUser) && args( seriesId, .. )" )
//    public Object securitySameUserSeriesExecution( ProceedingJoinPoint pjp, MWC_SameUser mWC_SameUser, Long seriesId ) throws Throwable
//    {
//        if ( loggedUser == null || loggedUser.getMwcUser() == null )
//        {
//            return "redirect:/";
//        }
//
//        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) || loggedUser.getMwcUser().hasSeries( seriesId ) )
//        {
////            logger.info( "****************** inside SeriesController / SameUserSeries / requested seriesId: " + seriesId + " - loggedUser.id: " + loggedUser.getMwcUser().getId() );
//            Object ret = pjp.proceed();
//            return ret;
//        }
//        return "public/accessDenied/?errorCode=12";
//    }
//    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.SeriesDetailController) && @annotation(mWC_SameUser) && args( seriesDetailsId, .. )" )
//    public Object securitySameUserSeriesDetailExecution( ProceedingJoinPoint pjp, MWC_SameUser mWC_SameUser, Long seriesDetailsId ) throws Throwable
//    {
//
//        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) || loggedUser.getMwcUser().hasSeriesDetails( seriesDetailsId ) )
//        {
////            logger.info( "****************** inside SeriesDetailController / SameUserSeriesDetails / requested seriesDetailsId: " + seriesDetailsId + " - loggedUser.id: " + loggedUser.getMwcUser().getId() );
//            Object ret = pjp.proceed();
//            return ret;
//        }
//        return "public/accessDenied/?errorCode=13";
//    }
    @Around( "within(com.javasd.mwc.WEB.CONTROLLER.MwcUserController) && @annotation(mWC_SameUser) && @annotation(responseBody) && args( userId )" )
    public Object securitySameUserExecution1( ProceedingJoinPoint pjp, MWC_SameUser mWC_SameUser, ResponseBody responseBody, Long userId ) throws Throwable
    {
        if ( loggedUser == null || loggedUser.getMwcUser() == null )
        {
            return "redirect:/";
        }

        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) || loggedUser.getMwcUser().getId() == userId )
        {
            Object ret = pjp.proceed();
            return ret;
        }
        return null;
    }

}
