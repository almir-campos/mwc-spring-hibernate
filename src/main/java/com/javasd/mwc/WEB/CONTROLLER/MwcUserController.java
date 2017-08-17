/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER;

import com.javasd.mwc.DOMAIN.complements.Language;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.SERVICE.mwcuser.UserService;
import com.javasd.mwc.SERVICE.person.PersonService;
import com.javasd.mwc.SERVICE.series.SeriesService;
import com.javasd.mwc.WEB.output.MwcUser01;
import com.javasd.mwc.WEB.output.MwcUserRoleI18n;
import com.javasd.mwc.annotations.MWC_Admin;
import com.javasd.mwc.annotations.MWC_LastScreen;
import com.javasd.mwc.annotations.MWC_Realm;
import com.javasd.mwc.annotations.MWC_SameUser;
import com.javasd.mwc.util.beans.LoggedUser;
import com.javasd.mwc.util.beans.MiscDynamic;
import com.javasd.mwc.util.beans.ObjectFiller;
import com.javasd.mwc.util.misc.MiscStatic;
import com.javasd.mwc.util.misc.MwcUserBasicData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Almir Campos
 */
@RequestMapping( "/mwcUser" )
@Controller
public class MwcUserController
{

    private final Logger logger = Logger.getLogger( MwcUserController.class );
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private ObjectFiller objectFiller;
    @Autowired
    private MiscDynamic miscDynamic;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @MWC_Admin
    @RequestMapping( value = "/listusers", method = RequestMethod.GET )
    public String listusers( Model uiModel )
    {
        uiModel.addAttribute( "users", userService.getAllByLastName() );
        return "mwcUser/list";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // SHOW
    //
    ////////////////////////////////////////////////////////////////////////////
//    @MWC_Realm( value = "user" )
    @MWC_LastScreen
    @RequestMapping( value = "/show", method = RequestMethod.GET )
    public String show(
            Model uiModel,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse )
    {
        Cookie mWC_NotLoggedIn = WebUtils.getCookie( httpServletRequest, "MWC_NotLoggedIn" );
        if ( mWC_NotLoggedIn != null )
        {
            MiscStatic.removeNotLoggedInCookie( httpServletRequest, httpServletResponse );
        }
        MwcUser loadedUser = loggedUser.getMwcUser();
        uiModel.addAttribute( "mwcUser", miscDynamic.getMwcUser01( loadedUser ) );
        return "mwcUser/show";
    }

    @MWC_SameUser
    @RequestMapping( value = "/show/{userId}", method = RequestMethod.GET )
    public String show( @PathVariable( "userId" ) Long userId, Model uiModel )
    {
        MwcUser loadedUser = userService.get( userId );
        uiModel.addAttribute( "mwcUser", miscDynamic.getMwcUser01( loadedUser ) );
        return "mwcUser/show";
    }

    @MWC_SameUser
    @RequestMapping( value = "/avatarImg/{userId}", method = RequestMethod.GET )
    @ResponseBody
    public byte[] avatarImgDownload(
            @PathVariable( "userId" ) Long userId,
            @RequestParam( "rand" ) Double rand )
    {
        MwcUser mwcUser = userService.get( userId );
        byte[] img = mwcUser.getAvatarImg();
        return mwcUser.getAvatarImg();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // EDIT
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Realm( value = "user" )
    @MWC_LastScreen
    @RequestMapping( value = "/edit/{userId}", method = RequestMethod.GET )
    public String edit(
            @PathVariable( "userId" ) Long userId,
            Model uiModel,
            HttpServletResponse httpServletResponse,
            Locale locale )
    {
        MwcUser loadedUser = userService.get( userId );

        List<MwcUserRoleI18n> mwcUserRolesI18n = new ArrayList<MwcUserRoleI18n>();
        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) )
        {
            logger.debug( "********** hasRole ADMIN" );
            mwcUserRolesI18n.add( new MwcUserRoleI18n( "ROLE_ADMIN", messageSource.getMessage( "mwc.user.role.admin", null, locale ), loadedUser.hasRole( "ROLE_ADMIN" ) ) );
            mwcUserRolesI18n.add( new MwcUserRoleI18n( "ROLE_MANAGER", messageSource.getMessage( "mwc.user.role.manager", null, locale ), loadedUser.hasRole( "ROLE_MANAGER" ) ) );
            mwcUserRolesI18n.add( new MwcUserRoleI18n( "ROLE_USER", messageSource.getMessage( "mwc.user.role.user", null, locale ), loadedUser.hasRole( "ROLE_USER" ) ) );
        }
        else
        {
            if ( loggedUser.getMwcUser().hasRole( "ROLE_MANAGER" ) )
            {
                logger.debug( "********** hasRole MANAGER" );
                mwcUserRolesI18n.add( new MwcUserRoleI18n( "ROLE_MANAGER", messageSource.getMessage( "mwc.user.role.manager", null, locale ), loadedUser.hasRole( "ROLE_MANAGER" ) ) );
                mwcUserRolesI18n.add( new MwcUserRoleI18n( "ROLE_USER", messageSource.getMessage( "mwc.user.role.user", null, locale ), loadedUser.hasRole( "ROLE_USER" ) ) );
            }
            else
            {
                logger.debug( "********** doesn't have neither role MANAGER nor ADMIN" );
                mwcUserRolesI18n.add( new MwcUserRoleI18n( "ROLE_USER", messageSource.getMessage( "mwc.user.role.user", null, locale ), loadedUser.hasRole( "ROLE_USER" ) ) );
            }
        }

        MwcUser01 mwcUser01 = miscDynamic.getMwcUser01( loadedUser );
        mwcUser01.setRoles( mwcUserRolesI18n );

        uiModel.addAttribute( "languages", Language.values() );
        uiModel.addAttribute( "mwcUser", mwcUser01 );
        uiModel.addAttribute( "mwcUserBasicData", new MwcUserBasicData() );
        return "mwcUser/edit";
    }

    //@MWC_Email
    @MWC_SameUser
    @RequestMapping( value = "/updateMwcUserBasicData", method = RequestMethod.POST )
    @ResponseBody
    public List<String> updateMwcUserBasicData(
            @Valid MwcUserBasicData mwcUserBasicData,
            String[] rolesArr,
            BindingResult result,
            Locale locale,
            Model uiModel )
//            @RequestParam( "personId" ) Long personId,
//            @RequestParam( "firstName" ) String firstName,
//            @RequestParam( "lastName" ) String lastName,
//            @RequestParam( "displayName" ) String displayName,
//            @RequestParam( "birthDate" ) String birthDate )
    {
        List<ObjectError> errors = result.getAllErrors();
        if ( !errors.isEmpty() )
        {
            MwcUser loadedUser = userService.get( mwcUserBasicData.getMwcUserId() );
            uiModel.addAttribute( "languages", Language.values() );
            uiModel.addAttribute( "mwcUser", miscDynamic.getMwcUser01( loadedUser ) );
            uiModel.addAttribute( "errors", errors );

            return miscDynamic.getAllErrorMessages( errors, locale );

        }
        // MOVE ALL THESE LINES TO PERSONSERVICE OR PERSONREPOSITORY
        Person loadedPerson = personService.get( mwcUserBasicData.getMwcUserId() );
        MwcUser loadedUser = loadedPerson.getMwcUser();
        loadedUser.setDisplayName( mwcUserBasicData.getDisplayName() );
        loadedUser.emptyRoles();
        if ( rolesArr != null && rolesArr.length > 0 )
        {
            for ( String s : rolesArr )
            {
                if ( !loadedUser.hasRole( s ) )
                {
                    loadedUser.addRole( s );
                }
            }
        }
        MwcUser updatedUser = userService.update( loadedUser );

        loadedPerson.setMwcUser( updatedUser );

        personService.update( loadedPerson );
//        String resp = "redirect:/mwcUser/show/" + updatedUser.getId();
//        String resp = "OK";

        return null;
    }

//    @MWC_Email
//    @MWC_SameUser
//    @RequestMapping( value = "/updateIdentification", method = RequestMethod.POST )
//    @ResponseBody
//    public String updateIdentification(
//            @RequestParam( "personId" ) Long personId,
//            @RequestParam( "firstName" ) String firstName,
//            @RequestParam( "lastName" ) String lastName,
//            @RequestParam( "displayName" ) String displayName,
//            @RequestParam( "birthDate" ) String birthDate )
//    {
//        // MOVE ALL THESE LINES TO PERSONSERVICE OR PERSONREPOSITORY
//        Person loadedPerson = personService.get( personId );
//        
//        MwcUser loadedUser = loadedPerson.getMwcUser();
//        loadedUser.setDisplayName( displayName );
//        MwcUser updatedUser = userService.update( loadedUser );
//        loadedPerson.setMwcUser( updatedUser );
//        
//        loadedPerson.setFirstName( firstName );
//        loadedPerson.setLastName( lastName );
//        DateTimeFormatter formatter = DateTimeFormat.forPattern( "yyyy-MM-dd" );
//        DateTime dt = formatter.parseDateTime( birthDate );
//        loadedPerson.setBirthDate( dt );
//        personService.update( loadedPerson );
//        String resp = "redirect:/mwcUser/show/" + updatedUser.getId();
//        return resp;
//    }
    @MWC_SameUser
    @RequestMapping( value = "/updateAvatarImg", method = RequestMethod.POST )
    @ResponseBody
    public String updateAvatarImg(
            @RequestParam( "userId" ) Long userId,
            @RequestParam( value = "file" ) Part file,
            @RequestParam( "fake" ) String fake )
    {
        byte[] avatarImg = null;
        if ( file != null )
        {
            try
            {
                InputStream inputStream = file.getInputStream();
                if ( inputStream == null )
                {
                    logger.info( "File inputstream is null" );
                }
                else
                {
                    MwcUser loadedUser = userService.get( userId );
                    avatarImg = IOUtils.toByteArray( inputStream );
                    loadedUser.setAvatarImg( avatarImg );
                    userService.update( loadedUser );
                }
            }
            catch ( IOException e )
            {
                logger.error( "*************** Error saving uploaded file" );
                e.printStackTrace();
            }
        }
        else
        {
            logger.info( "***************** avatarImg ??" );
        }
        return null;
    }

    @MWC_Realm( value = "user" )
    @MWC_LastScreen
    @RequestMapping( value = "/listseries/{userId}", method = RequestMethod.GET )
    public String listseries(
            @PathVariable( "userId" ) Long userId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        MwcUser mwcUser = userService.getWithSeries( userId );
        List<Series> seriess = seriesService.getAllByStartDateDesc( mwcUser );
        uiModel.addAttribute( "mwcUser", mwcUser );
        uiModel.addAttribute( "seriess", seriess );
        return "series/list";
    }

    @MWC_Admin
    @RequestMapping( value = "/delete/{userId}", method = RequestMethod.GET )
    public String delete( @PathVariable( "userId" ) Long userId )
    {
        userService.delete( userId );
        return "redirect:/mwcUser/listusers";
    }

    @MWC_SameUser
    @RequestMapping( value = "/ajax/avatarimgload", method = RequestMethod.GET )
    public String avatarImgLoad( @RequestParam( "userId" ) Long userId, Model uiModel )
    {
        MwcUser loadedUser = userService.get( userId );
        uiModel.addAttribute( "mwcUser", miscDynamic.getMwcUser01( loadedUser ) );
        return "complements/ajax/avatarimgload";
    }

    @MWC_Admin
    @RequestMapping( value = "/add", method = RequestMethod.GET )
    public String add( Model uiModel )
    {
        uiModel.addAttribute( "languages", Language.values() );
        return "mwcUser/add";
    }

    @MWC_Admin
    @RequestMapping( value = "/addUser", method = RequestMethod.POST )
    public String addUser( MwcUser mwcUser, @RequestParam( value = "file", required = false ) Part file )
    {
        Person person = personService.get( 1L );
        mwcUser.setPerson( person );
        mwcUser.setDisplayName( person.getFirstName() );
        MwcUser savedUser = null;
        // The default language is English (en_US)
        if ( mwcUser.getPreferredLanguage() == null )
        {
            mwcUser.setPreferredLanguage( Language.en_US );
        }

        if ( file != null )
        {
            try
            {
                byte[] fileContent = null;
                InputStream inputStream = file.getInputStream();
                if ( inputStream == null )
                {
                    logger.info( "File inputstream is null" );
                }
                else
                {
                    fileContent = IOUtils.toByteArray( inputStream );
                    mwcUser.setAvatarImg( fileContent );
                    savedUser = userService.save( mwcUser );
                    //ObjToXML otx = new ObjToXML( savedUser, null );
                    //return otx.getFromFieldsXML( "savedUser", true );
                }
            }
            catch ( IOException e )
            {
                logger.error( "*************** Error saving uploaded file" );
                e.printStackTrace();
            }
        }
        else
        {
            logger.info( "***************** avatarImg ??" );
        }
        if ( savedUser == null )
        {
            return "redirect:mwcUser/listusers";
        }
        return "redirect:/mwcUser/show/" + savedUser.getId();
    }

    @MWC_SameUser
    @RequestMapping( value = "/updatePreferredLanguage", method = RequestMethod.POST )
    @ResponseBody
    public void updatePreferredLanguage(
            @RequestParam( "userId" ) Long userId,
            @RequestParam( "prefLanguage" ) String preferredLanguage )
    {
        MwcUser mwcUser = userService.get( userId );
        mwcUser.setPreferredLanguage( Language.valueOf( preferredLanguage ) );
        userService.update( mwcUser );
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // LOG
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Realm( value = "user" )
    @MWC_LastScreen
    @RequestMapping( value = "/log/{userId}", method = RequestMethod.GET )
    public String userLog( @PathVariable( "userId" ) Long userId, Model uiModel )
    {
        return "mwcUser/userLog";
    }

    @MWC_SameUser
    @RequestMapping( value = "/loadUserLog/{userId}", method = RequestMethod.GET )
    @ResponseBody
    public String loadUserLog( @PathVariable( "userId" ) Long userId, Model uiModel )
    {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File( servletContext.getRealPath( "/WEB-INF/" + loggedUser.getMwcUser().getUsername() + ".log" ) );
        long fileLength = file.length();
        long toRead = Math.min( fileLength, 1024 * 10 );
        long toSkip = fileLength - toRead;
        try
        {
            FileReader fileReader = new FileReader( file );
            BufferedReader bufferedReader = new BufferedReader( fileReader );
            bufferedReader.skip( toSkip );
            String line;
            while ( ( line = bufferedReader.readLine() ) != null )
            {
                stringBuilder.append( line );
            }
            bufferedReader.close();
            fileReader.close();

        }
        catch ( FileNotFoundException ex )
        {
            java.util.logging.Logger.getLogger( MwcUserController.class
                    .getName() ).log( Level.SEVERE, null, ex );
        }

        catch ( IOException ex )
        {
            java.util.logging.Logger.getLogger( MwcUserController.class
                    .getName() ).log( Level.SEVERE, null, ex );
        }
        return stringBuilder.toString();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // OTHER
    //
    ////////////////////////////////////////////////////////////////////////////
//    private MwcUser01 getMwcUser01( MwcUser mwcUser )
//    {
//        MwcUser01 mwcUser01 = ( MwcUser01 ) objectFiller.fillFields( mwcUser, MwcUser01.class );
//        PersonBasicData personBasicData = ( PersonBasicData ) objectFiller.fillFields( mwcUser.getPerson(), PersonBasicData.class );
//        //logger.debug( " ¨¨¨¨¨¨¨¨ mwcUser.getPerson().getId(): " + mwcUser.getPerson().getId() );
//        mwcUser01.setPerson( personBasicData );
//        return mwcUser01;
//    }
}
