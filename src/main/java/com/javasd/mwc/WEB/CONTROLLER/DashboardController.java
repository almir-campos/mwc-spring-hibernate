/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER;

import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.WEB.output.MwcUser01;
import com.javasd.mwc.util.beans.LoggedUser;
import com.javasd.mwc.util.beans.MiscDynamic;
import com.javasd.mwc.util.beans.MwcConfig;
import com.javasd.mwc.util.misc.MiscStatic;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;
//import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 *
 * @author Almir Campos
 */
@Controller
public class DashboardController
{

    Logger logger = Logger.getLogger( DashboardController.class );
    @Autowired
    private MwcConfig mwcConfig;
    
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private MiscDynamic miscDynamic;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

//    @Autowired
//    private MwcMail mwcMail;
//    @MWC_LastScreen
    @RequestMapping( value = "/", method = RequestMethod.GET )
    public String index(
            Model uiModel,
            Locale locale,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse )
    {
//        mwcMail.sendMail( "javasd.info@gmail.com", "almircampos@gmail.com", "Test", "Activation Code: " + MiscStatic.randomString( 30) );

//        if ( loggedUser.getMwcUser() == null || !loggedUser.isLogged() )
//        logger.info( "********** TEST PROPERTIES: " + mwcConfig.getMwcInternetBaseUrl() );
//        logger.info( "********** TEST PROPERTIES: " + mwcConfig.getMwcJdbcUrl() );
        if ( loggedUser.getMwcUser() == null )
        {
            System.out.println( " ===>>> loggedUser: " + loggedUser );
            System.out.println( " ===>>> loggedUser.getMwcUser(): " + loggedUser.getMwcUser());
            return "redirect:/public/login";
        }
        MwcUser mwcUser = loggedUser.getMwcUser();

        MwcUser01 mwcUser01 = new MwcUser01();
        mwcUser01 = miscDynamic.getMwcUser01( mwcUser );
        uiModel.addAttribute( "loggedUser", mwcUser01 );

        Cookie ck = WebUtils.getCookie( httpServletRequest, "MWC_lastScreen" );
        if ( ck != null && ck.getValue() != null && !ck.getValue().equals( "/mwc/" ) )
        {
            String lastScreen
                    = "redirect:"
                    //                    + "http://"
                    //                    + httpServletRequest.getLocalAddr()
                    //                    + ":"
                    //                    + httpServletRequest.getLocalPort()
                    + ck.getValue()
                    + "?lang="
                    + mwcUser.getPreferredLanguage().getCode();
            logger.info( "*********** REDIRECTING TO : " + lastScreen );
            return lastScreen;
        }
//        Long id = mwcUser.getId();
        return "redirect:/mwcUser/show?lang=" + mwcUser.getPreferredLanguage().getCode();
    }

    @RequestMapping( value = "/public/nojavascript", method = RequestMethod.GET )
    public String noJavaScript()
    {
        return "public/nojavascript";
    }

    //@MWC_Email
    @RequestMapping( value = "/public/accessDenied", method = RequestMethod.GET )
    public String accessDenied(
            @RequestParam( "errorCode" ) String errorCode,
            Model uiModel,
            Locale locale,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse )
//    public String accessDenied(  )
    {
        MiscStatic.setLastScreenCookie( httpServletRequest, httpServletResponse, "/mwcUser/show" );
        uiModel.addAttribute( "errorCode", messageSource.getMessage( errorCode, null, locale ) );
        return "public/accessDenied";
    }

//    @MWC_LastScreen
    @RequestMapping( value = "/public/login", method = RequestMethod.GET )
    public String login(
            ModelMap model,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse )
    {
        MiscStatic.addNotLoggedInCookie( httpServletRequest, httpServletResponse );
        return "public/login";

    }

    @RequestMapping( value = "/public/signup", method = RequestMethod.GET )
    public String signup( ModelMap model )
    {

        return "public/signup";

    }

    @RequestMapping( value = "/public/loginfailed", method = RequestMethod.GET )
    public String loginerror(
            ModelMap uiModel,
            Locale locale,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse )
    {
        MiscStatic.addNotLoggedInCookie( httpServletRequest, httpServletResponse );

        uiModel.addAttribute( "loginFailed", messageSource.getMessage( "page.login.login.failed", null, locale ) );
        return "public/login";

    }

    //@MWC_Email
    @RequestMapping( value = "/logout", method = RequestMethod.GET )
    public String logout( ModelMap model,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
    {
        MiscStatic.addNotLoggedInCookie( httpServletRequest, httpServletResponse );

//        loggedUser.setLogged( false );
        loggedUser.setMwcUser( null );
        return "public/login";

    }
}
