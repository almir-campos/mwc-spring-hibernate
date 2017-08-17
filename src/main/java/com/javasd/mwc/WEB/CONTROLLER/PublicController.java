/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER;

import com.javasd.mwc.DOMAIN.complements.AccessCodeType;
import com.javasd.mwc.DOMAIN.complements.Language;
import com.javasd.mwc.DOMAIN.complements.MwcUserRoleType;
import com.javasd.mwc.DOMAIN.complements.PersonStatus;
import com.javasd.mwc.DOMAIN.complements.UserStatus;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.MwcUserRole;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.SERVICE.mwcuser.UserService;
import com.javasd.mwc.SERVICE.mwcuserrrole.MwcUserRoleService;
import com.javasd.mwc.SERVICE.person.PersonService;
import com.javasd.mwc.WEB.output.PersonBasicData;
import com.javasd.mwc.WEB.output.PersonShowData;
import com.javasd.mwc.annotations.MWC_Email;
import com.javasd.mwc.util.beans.MiscDynamic;
import com.javasd.mwc.util.beans.MwcConfig;
import com.javasd.mwc.util.beans.MwcMail;
import com.javasd.mwc.util.beans.MwcPasswordEncoder;
import com.javasd.mwc.util.xml.ObjToXML;
import com.javasd.mwc.util.xml.XMLBuilder;
import com.javasd.utils.stringutils.StringUtils;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author almir
 */
@Controller
@RequestMapping( "/public" )
public class PublicController
{

    private final Logger logger = Logger.getLogger( PersonController.class );
    //
    // AUTHENTICATION
    @Autowired
    @Qualifier( "authenticationManager" )
    AuthenticationManager authenticationManager;
    @Autowired
    SecurityContextRepository repository;
    @Autowired
    RememberMeServices rememberMeServices;
    // SERVICES
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private MwcUserRoleService mwcUserRoleService;
    // MISC
    @Autowired
    private MwcMail mwcMail;
    @Autowired
    private MiscDynamic miscDynamic;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private MwcConfig mwcConfig;
    @Autowired
    private MwcPasswordEncoder mwcPasswordEncoder;
    //
    //

    //@MWC_Email
    @RequestMapping( value = "/about", method = RequestMethod.GET )
    public String about( Model uiModel, Locale locale )
    {
        return "public/about";
    }

    @RequestMapping( value = "/resetPassword/{linkCode}", method = RequestMethod.GET )
    public String resetPassword(
            @PathVariable( "linkCode" ) String linkCode )
    {
        Person loadedPerson = personService.getByLinkCode( linkCode );
        if ( loadedPerson != null )
        {
            return "public/resetPassword";
        }
        return "redirect:/public/pageNotFound";
    }

    @RequestMapping( value = "/resetPassword", method = RequestMethod.POST )
    @ResponseBody
    public String resetPassword(
            @RequestParam( "username" ) String username,
            @RequestParam( "password" ) String password,
            @RequestParam( "confirmPassword" ) String confirmPassword,
            Locale locale
    )
    {
        MwcUser loadedUser = userService.getByUsername( username );
        if ( loadedUser == null )
        {
            return messageSource.getMessage( "reset.password.invalid.username", null, locale );
        }

        if ( password.length() < 3 || password.length() > 20 )
        {
            return messageSource.getMessage( "reset.password.new.password.wrong.size", new Object[]
            {
                3, 20
            }, locale );
        }
        if ( !password.equals( confirmPassword ) )
        {
            return messageSource.getMessage( "reset.password.not.confirmed.password", null, locale );
        }

        loadedUser.setPassword( mwcPasswordEncoder.encode( password ) );
        userService.update( loadedUser );

        return "OK";
    }

    @RequestMapping( value = "/verifyUsername", method = RequestMethod.POST )
    @ResponseBody
    public String verifyUsername(
            @RequestParam( "username" ) String username,
            HttpServletRequest httpServletRequest,
            Locale locale )
    {
        MwcUser loadedUser = userService.getByUsername( username );
        if ( loadedUser == null )
        {
            return "NOK";
        }

        String linkCode = StringUtils.randomString( 64 );
        String link = ""
                + mwcConfig.getMwcInternetBaseUrl()
                + "/public/resetPassword/" + linkCode;

        String name = loadedUser.getPerson().getName();
        String msg = messageSource.getMessage( "email.user.reset.password.content", new Object[]{ name, link}, locale );
        Person person = personService.get( loadedUser.getPerson().getId() );
        person.setLinkCode( linkCode );
        personService.update( person );
        mwcMail.sendMail(
                "javasd.info@gmail.com",
                loadedUser.getPerson().getEmail(),
                messageSource.getMessage( "email.user.reset.password.subject", new Object[]{name}, locale ),
                msg );
        return "OK";
    }

    @MWC_Email
    @RequestMapping( value = "/verifyAccessCode/{accessCode}", method = RequestMethod.GET, produces = "application/xml" )
    @ResponseBody
    public String verifyAccessCode( @PathVariable( "accessCode" ) String accessCode, Locale locale )
    {
        Person person = personService.getByAccessCode( accessCode );
        if ( person == null )
        {
            logger.info( "****** person == null" );
            return null;
        }

        PersonShowData personShowData = miscDynamic.getPersonShowData( person );

        ObjToXML otx;

        StringBuilder sb = new StringBuilder();
        sb.append( XMLBuilder.HEADER );
//        sb.append( XMLBuilder.getTag( "person", true ) );
        otx = new ObjToXML( personShowData, null );
        sb.append( otx.getFromFieldsXML( "person", false ) );
//        sb.append( XMLBuilder.getTag( "person", false ) );

        return sb.toString();
    }

    // same method for change email?
    @MWC_Email
    @RequestMapping( value = "/requestActivation/{personId}", method = RequestMethod.POST )
    @ResponseBody
    public String requestActivation(
            @PathVariable( "personId" ) Long personId,
            HttpServletRequest request,
            Locale locale )
    {
        Person person = personService.get( personId );
        if ( !person.getStatus().equals( PersonStatus.INVITED ) )
        {
            logger.info( "**********  denied/wrong status /linkCode: " + person.getLinkCode() );
            return "denied";
        }

        String linkCode = StringUtils.randomString( 64 );
        String link = ""
                + mwcConfig.getMwcInternetBaseUrl()
                + "/public/activation/" + linkCode;

        try
        {
            String msg = messageSource.getMessage(
                    "email.activation.message.content",
                    new Object[]
                    {
                        person.getName(), request.getRemoteAddr(), link
                    },
                    locale );
            String subject = messageSource.getMessage(
                    "email.activation.message.subject",
                    new Object[]
                    {
                        person.getName()
                    },
                    locale );
            mwcMail.sendMail( "javasd.info@gmail.com", person.getEmail(), subject, msg );
//            mwcMail.sendMail( "javasd.info@gmail.com", person.getEmail(), "Activation Requested: " + person.getFirstName(), msg );
            person.setLinkCode( linkCode );
            person.setStatus( PersonStatus.WAITING_FOR_REGISTRATION );
            personService.update( person );
            return "ok";
        }
        catch ( Exception e )
        {
            logger.error( " &&&&&&&&&&&&&&&&&&&&&& " + e.toString() + " *** " + e.getMessage() + " *** " + e.getCause() );
            return "error";
        }
    }

    @RequestMapping( value = "/activationRequestConfirmation", method = RequestMethod.GET )
    public String activationRequestConfirmation()
    {
        return "public/activationRequestConfirmation";
    }

    @MWC_Email
    @RequestMapping( value = "/activation/{linkCode}", method = RequestMethod.GET )
    public String activation( @PathVariable( "linkCode" ) String linkCode, Model uiModel, Locale locale )
    {
        if ( linkCode == null || linkCode.length() != 64 )
        {
            logger.info( "**********  denied/null or #64/linkCode: " + linkCode );
            return "/accessDenied/?errorCode=21";
        }

        Person person = personService.getByLinkCode( linkCode );
        if ( person == null )
        {
            logger.info( "**********  denied/person == null /linkCode: " + linkCode );
            return "/accessDenied/?errorCode=22";
        }

        PersonBasicData personBasicData = miscDynamic.getPersonBasicData( person );
        uiModel.addAttribute( "linkCode", linkCode );
        uiModel.addAttribute( "person", personBasicData );

        return "public/activation";
    }

    @MWC_Email
    @RequestMapping( value = "/firstLogin", method = RequestMethod.POST )
    @ResponseBody
    // ATTENTION: MwcUser and MwcUserRole *** MUST *** be in the parameters list (do not create them with "new" statement!!!)
    public String firstLogin(
            MwcUser mwcUser,
            //            MwcUserRole mwcUserRole,
            @RequestParam( "username" ) String username,
            @RequestParam( "password" ) String password,
            @RequestParam( "confirmPassword" ) String confirmPassword,
            @RequestParam( "linkCode" ) String linkCode,
            @RequestParam( "personId" ) Long personId,
            Locale locale,
            HttpServletRequest request, HttpServletResponse response )
    {

        MwcUser otherUser = userService.getByUsername( username );
        if ( otherUser != null )
        {
            return messageSource.getMessage( "activation.username.cannot.be.used", null, locale );
        }

        if ( username.length() < 3 || username.length() > 20 )
        {
            return messageSource.getMessage( "activation.username.wrong.size", new Object[]
            {
                3, 20
            }, locale );
        }

        if ( password.length() < 3 || password.length() > 20 )
        {
            return messageSource.getMessage( "activation.password.wrong.size", new Object[]
            {
                3, 20
            }, locale );
        }
        if ( !password.equals( confirmPassword ) )
        {
            return messageSource.getMessage( "activation.password.not.confirmed", null, locale );
        }

        Person person = personService.get( personId );
        if ( !person.getLinkCode().equals( linkCode ) )
        {
            return messageSource.getMessage( "activation.invalid.link.code", null, locale );
        }

        if ( !person.getStatus().equals( PersonStatus.WAITING_FOR_REGISTRATION ) )
        {
            return messageSource.getMessage( "activation.invalid.status", null, locale );
        }

        // ADJUST THE PERSON STATUS AND RESET THE LINK CODE
        person.setStatus( PersonStatus.REGISTERED );
        person.setLinkCode( "" );
        Person updatedPerson = personService.update( person );

        // CREATE THE NEW USER AND ADJUST HIS/HER STATUS
        //MwcUser mwcUser = new MwcUser();
        mwcUser.setUsername( username );
        mwcUser.setPassword( password );
        mwcUser.setEnabled( Boolean.TRUE );
        mwcUser.setStatus( UserStatus.REGISTERED );
        mwcUser.setPreferredLanguage( Language.en_US );
        mwcUser.setDisplayName( updatedPerson.getFirstName() );
        mwcUser.setPerson( updatedPerson );
        MwcUser savedMwcUser = userService.save( mwcUser );

        Set<MwcUserRole> mwcUserRoles = savedMwcUser.getUserRoles();

        for ( MwcUser u : userService.getAll() )
        {
            logger.info( " -----------------------> " + u.getId() + ": " + u.getUsername() );
        }

        // NEEDS OPTIMIZATION!!!!!!!!!!!!!!!!!!
        MwcUserRole mwcUserRole;
        MwcUserRole savedMwcUserRole;
        if ( updatedPerson.getAccessCodeType().equals( AccessCodeType.ADMIN ) )
        {
            mwcUserRole = new MwcUserRole();
            mwcUserRole.setMwcUser( savedMwcUser );
            mwcUserRole.setAuthority( MwcUserRoleType.ROLE_ADMIN );
            savedMwcUserRole = mwcUserRoleService.save( mwcUserRole );
            mwcUserRoles.add( savedMwcUserRole );

            mwcUserRole = new MwcUserRole();
            mwcUserRole.setMwcUser( savedMwcUser );
            mwcUserRole.setAuthority( MwcUserRoleType.ROLE_MANAGER );
            savedMwcUserRole = mwcUserRoleService.save( mwcUserRole );
//            mwcUserRoles.add( savedMwcUserRole );

            mwcUserRole = new MwcUserRole();
            mwcUserRole.setMwcUser( savedMwcUser );
            mwcUserRole.setAuthority( MwcUserRoleType.ROLE_USER );
            savedMwcUserRole = mwcUserRoleService.save( mwcUserRole );
            mwcUserRoles.add( savedMwcUserRole );
        }
        else
        {
            if ( updatedPerson.getAccessCodeType().equals( AccessCodeType.MANAGER ) )
            {
                mwcUserRole = new MwcUserRole();
                mwcUserRole.setMwcUser( savedMwcUser );
                mwcUserRole.setAuthority( MwcUserRoleType.ROLE_MANAGER );
                savedMwcUserRole = mwcUserRoleService.save( mwcUserRole );
                mwcUserRoles.add( savedMwcUserRole );

                mwcUserRole = new MwcUserRole();
                mwcUserRole.setMwcUser( savedMwcUser );
                mwcUserRole.setAuthority( MwcUserRoleType.ROLE_USER );
                savedMwcUserRole = mwcUserRoleService.save( mwcUserRole );
                mwcUserRoles.add( savedMwcUserRole );
            }
            else
            {
                if ( updatedPerson.getAccessCodeType().equals( AccessCodeType.USER ) )
                {
                    mwcUserRole = new MwcUserRole();
                    mwcUserRole.setMwcUser( savedMwcUser );
                    mwcUserRole.setAuthority( MwcUserRoleType.ROLE_USER );
                    savedMwcUserRole = mwcUserRoleService.save( mwcUserRole );
                    mwcUserRoles.add( savedMwcUserRole );
                }
            }
        }

        for ( MwcUserRole r : mwcUserRoleService.getAll() )
        {
            logger.info( " -----------------------> " + r.getId() + ": " + r.getAuthority() );
        }

        // AUTHENTICATE THE USER INTO SPRING
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( username, password );
        try
        {
            Authentication auth = authenticationManager.authenticate( token );
            SecurityContextHolder.getContext().setAuthentication( auth );
            repository.saveContext( SecurityContextHolder.getContext(), request, response );
            rememberMeServices.loginSuccess( request, response, auth );
//            loggedUser.setMwcUser( savedMwcUser );
            return "OK";
        }
        catch ( BadCredentialsException ex )
        {
            updatedPerson.setStatus( PersonStatus.ERROR );
            personService.update( updatedPerson );
            userService.delete( savedMwcUser.getId() );
            return messageSource.getMessage( "activation.generic.error", null, locale );
        }
    }

    @RequestMapping( value = "/pageNotFound", method = RequestMethod.GET )
    public String pageNotFound()
    {
        return "public/pageNotFound";
    }
}
