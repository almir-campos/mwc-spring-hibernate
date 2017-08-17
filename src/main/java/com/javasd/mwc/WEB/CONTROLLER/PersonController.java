/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER;

import com.javasd.mwc.DOMAIN.complements.AccessCodeType;
import com.javasd.mwc.DOMAIN.complements.PersonStatus;
import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.SERVICE.branch.BranchService;
import com.javasd.mwc.SERVICE.person.PersonService;
import com.javasd.mwc.SERVICE.personinbranch.PersonInBranchService;
import com.javasd.mwc.WEB.output.AccessCodeTypeI18n;
import com.javasd.mwc.WEB.output.Branch01;
import com.javasd.mwc.WEB.output.Company01;
import com.javasd.mwc.WEB.output.PersonBasicData;
import com.javasd.mwc.annotations.MWC_Admin;
import com.javasd.mwc.annotations.MWC_Email;
import com.javasd.mwc.annotations.MWC_LastScreen;
import com.javasd.mwc.annotations.MWC_Realm;
import com.javasd.mwc.util.beans.LoggedUser;
import com.javasd.mwc.util.beans.MiscDynamic;
import com.javasd.mwc.util.beans.MwcConfig;
import com.javasd.mwc.util.beans.MwcMail;
import com.javasd.mwc.util.beans.ObjectFiller;
import com.javasd.mwc.util.beans.ObjectMerger;
import com.javasd.mwc.util.misc.AddToBranch;
import com.javasd.utils.stringutils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Almir
 */
@Controller
@RequestMapping( "/person" )
public class PersonController
{

    private final Logger logger = Logger.getLogger( PersonController.class );
    // AUTHENTICATION
    @Autowired
    @Qualifier( "authenticationManager" )
    AuthenticationManager authenticationManager;
    @Autowired
    SecurityContextRepository repository;
    @Autowired
    RememberMeServices rememberMeServices;
    @Autowired
    private ObjectFiller objectFiller;
    @Autowired
    private ObjectMerger objectMerger;
    // SERVICES
    @Autowired
    private PersonService personService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private PersonInBranchService personInBranchService;
    // MISC
    @Autowired
    private MwcMail mwcMail;
    @Autowired
    private MiscDynamic miscDynamic;
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private MwcConfig mwcConfig;

    ////////////////////////////////////////////////////////////////////////////
    //
    // LIST
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Admin
    @MWC_LastScreen
    @RequestMapping( value = "/listpeople", method = RequestMethod.GET )
    public String listPeople(
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        List<PersonBasicData> people = new ArrayList<PersonBasicData>();
        for ( Person p : personService.getAllByLastName() )
        {
            people.add( miscDynamic.getPersonBasicData( p ) );
        }
        uiModel.addAttribute( "people", people );
        return "person/list";
    }

    @MWC_LastScreen
    @RequestMapping( value = "/listmanagedpeople", method = RequestMethod.GET )
    public String listManagedPeople(
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        List<PersonBasicData> people = new ArrayList<PersonBasicData>();
        for ( Person p : personService.getAllManagedByLastName() )
        {
            people.add( miscDynamic.getPersonBasicData( p ) );
        }
        uiModel.addAttribute( "people", people );
        return "person/list";
    }

    @RequestMapping( value = "/removeFromBranch/{branchId}/{personId}", method = RequestMethod.GET )
    public String removeFromBranch(
            @PathVariable( "branchId" ) Long branchId,
            @PathVariable( "personId" ) Long personId
    )
    {
        personInBranchService.deleteByBranchPerson( branchId, personId );
        return "redirect:/person/listmanagedpeople";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // ADD
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_LastScreen
    @RequestMapping( value = "/add", method = RequestMethod.GET )
    public String add(
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {

        uiModel.addAttribute( "accessCodeTypes", AccessCodeType.values() );
        uiModel.addAttribute( "personStatus", PersonStatus.values() );
        uiModel.addAttribute( "person", new Person() );
        return "person/add";
    }

    @MWC_Email
    @RequestMapping( value = "/add", method = RequestMethod.POST )
    public String add(
            @Valid Person person,
            BindingResult result,
            Locale locale,
            Model uiModel )
    {
        if ( result.hasErrors() )
        {
            uiModel.addAttribute( "currentId", person.getId() );
            List<ObjectError> allErrors = result.getAllErrors();
            uiModel.addAttribute( "errors", allErrors );
            return "person/add";
        }
        personService.save( person );
        return "redirect:/person/listmanagedpeople";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // ADD TO A BRANCH
    //
    ////////////////////////////////////////////////////////////////////////////
//    @MWC_LastScreen
    @RequestMapping( value = "/addToBranch/{personId}", method = RequestMethod.GET )
    public String addToBranch(
            @PathVariable( "personId" ) Long personId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Company personCompany = personService.getCompany( loggedUser.getMwcUser().getPerson().getId() );
        if ( personCompany == null )
        {
            return "redirect:/person/listmanagedpeople";
        }
        addToBranchData( uiModel, personId );
        uiModel.addAttribute( "addToBranch", new AddToBranch() );
        return "person/addToBranch";
    }

    @RequestMapping( value = "/addToBranch", method = RequestMethod.POST )
    public String addToBranch(
            @Valid AddToBranch addToBranch,
            BindingResult result,
            Model uiModel )
    {
        List<ObjectError> errors = result.getAllErrors();

        if ( !errors.isEmpty() )
        {
            addToBranchData( uiModel, addToBranch.getPersonId() );
            uiModel.addAttribute( "errors", errors );
            return "person/addToBranch";
        }

        personInBranchService.update( addToBranch.getPersonId(), addToBranch.getBranchId(), addToBranch.getPosition() );
        return "redirect:/branch/listpeople/" + addToBranch.getBranchId();
    }

    private void addToBranchData( Model uiModel, Long personId )
    {
        List<Branch01> branches = new ArrayList<Branch01>();

        Person loadedPerson = personService.get( personId );
        Company personCompany = personService.getCompany( loggedUser.getPersonBasicData().getId() );
        if ( personCompany != null )
        {
            Branch01 branch01;
            List<Branch> loadedBranches;
            if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) )
            {
                loadedBranches = branchService.getAllByCompanyAndAcronym();
            }
            else
            {
                loadedBranches = branchService.getAllByAcronym( personCompany );
            }
            for ( Branch b : loadedBranches )
            {
                branch01 = ( Branch01 ) objectFiller.fillFields( b, Branch01.class );
                branch01.setCompany( ( Company01 ) objectFiller.fillFields( b.getCompany(), Company01.class ) );
                branches.add( branch01 );
            }
        }
        uiModel.addAttribute( "person", objectFiller.fillFields( loadedPerson, PersonBasicData.class ) );
        uiModel.addAttribute( "branches", branches );
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // DELETE
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Realm( value = "person" )
    @MWC_Email
    @RequestMapping( value = "/delete/{personId}", method = RequestMethod.GET )
//    @ResponseBody
    public String delete(
            @PathVariable( "personId" ) Long id,
            HttpServletResponse httpServletResponse )
    {
        //Person loadedPerson = personService.get( id );
        String r;
        if ( loggedUser.getMwcUser().getId() == id )
        {
            r = "redirect:/logout";
        }
        else
        {
            r = "redirect:/person/listpeople";
        }
        personService.delete( id );

        return r;
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // EDIT
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_LastScreen
    @MWC_Realm( value = "person" )
    @RequestMapping( value = "/edit/{personId}", method = RequestMethod.GET )
    public String edit(
            @PathVariable( "personId" ) Long personId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        uiModel.addAttribute( "person", miscDynamic.getPersonBasicData( personService.get( personId ) ) );
        uiModel.addAttribute( "personStatus", PersonStatus.values() );
        return "person/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST )
    public String edit(
            @Valid Person person,
            BindingResult result,
            Model uiModel )
    {
        if ( result.hasErrors() )
        {
            uiModel.addAttribute( "currentId", person.getId() );
            List<ObjectError> allErrors = result.getAllErrors();
            uiModel.addAttribute( "errors", allErrors );
            return "person/edit";
        }
        Person loadedPerson = personService.get( person.getId() );
        Person mergedPerson = ( Person ) objectMerger.mergeFields( person, loadedPerson );
        Person updatedPerson = personService.update( mergedPerson );
        return "redirect:/person/show/" + updatedPerson.getId();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // SHOW
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Realm( value = "person" )
    @MWC_LastScreen
    @RequestMapping( value = "/show/{personId}", method = RequestMethod.GET )
    public String show(
            @PathVariable( "personId" ) Long personId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Person loadedPerson = personService.getWithContacts( personId );
        if ( loadedPerson != null )
        {
            uiModel.addAttribute( "person", miscDynamic.getPersonBasicData( loadedPerson ) );
            return "person/show";
        }
        else
        {
            return "redirect:/person/show/" + loggedUser.getMwcUser().getPerson().getId();
        }
//        uiModel.addAttribute( "personStatus", PersonStatus.values() );
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // ACCESS CODE
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_LastScreen
    @RequestMapping( value = "/manageAccessCode/{personId}", method = RequestMethod.GET )
    public String invite(
            @PathVariable( "personId" ) Long personId,
            Model uiModel,
            Locale locale,
            HttpServletResponse httpServletResponse )
    {
        Person person = personService.get( personId );
        PersonBasicData personBasicData = ( PersonBasicData ) objectFiller.fillFields( person, PersonBasicData.class );
        List<AccessCodeTypeI18n> accessCodeTypes = new ArrayList<AccessCodeTypeI18n>();
        if ( loggedUser.getMwcUser().hasRole( "ROLE_ADMIN" ) )
        {
            logger.debug( "********** hasRole ADMIN" );
            accessCodeTypes.add( new AccessCodeTypeI18n( "ADMIN", messageSource.getMessage( "access_code_type_admin", null, locale ) ) );
            accessCodeTypes.add( new AccessCodeTypeI18n( "MANAGER", messageSource.getMessage( "access_code_type_manager", null, locale ) ) );
            accessCodeTypes.add( new AccessCodeTypeI18n( "USER", messageSource.getMessage( "access_code_type_user", null, locale ) ) );
        }
        else
        {
            if ( loggedUser.getMwcUser().hasRole( "ROLE_MANAGER" ) )
            {
                logger.debug( "********** hasRole MANAGER" );
                accessCodeTypes.add( new AccessCodeTypeI18n( "MANAGER", messageSource.getMessage( "access_code_type_manager", null, locale ) ) );
                accessCodeTypes.add( new AccessCodeTypeI18n( "USER", messageSource.getMessage( "access_code_type_user", null, locale ) ) );
            }
            else
            {
                logger.debug( "********** doesn't have neither role MANAGER nor ADMIN" );
                accessCodeTypes.add( new AccessCodeTypeI18n( "USER", messageSource.getMessage( "access_code_type_user", null, locale ) ) );
            }
        }
//        personBasicData.setAccessCodeTypes( accessCodeTypes );
        uiModel.addAttribute( "newAccessCode", StringUtils.randomString( 6, true, false, false, true ) );
        uiModel.addAttribute( "accessCodeTypes", accessCodeTypes );
        uiModel.addAttribute( "currentAccessCode", person.getAccessCode() );
        uiModel.addAttribute( "currentAccessCodeType", person.getAccessCodeType() );
        uiModel.addAttribute( "person", personBasicData );
        return "person/manageAccessCode";
    }

    @MWC_Email
    @RequestMapping( value = "/accessCode", method = RequestMethod.POST )
    public String accessCode(
            @RequestParam( "personId" ) Long personId,
            @RequestParam( "email" ) String email,
            @RequestParam( "accessCode" ) String accessCode,
            @RequestParam( "accessCodeType" ) String accessCodeType,
            @RequestParam( "invitationLanguage" ) String invitationLanguage,
            HttpServletRequest httpServletRequest,
            Locale locale)
//            String newAccessCodeType )
//  Spring has problems with @RequestParam from checkboxes, when the value is 'false' (because the form don't send the param)
//  @RequestParam( "sendEmail" ) boolean sendEmail )
    {
        boolean sendInvitation;

        if ( httpServletRequest.getParameter( "sendInvitation" ) == null )
        {
            sendInvitation = false;
            logger.info( " -----------> sendInvitation (null): " + sendInvitation );
        }
        else
        {
            logger.info( " -----------> sendInvitation (param): " + httpServletRequest.getParameter( "sendInvitation" ) );
            sendInvitation = httpServletRequest.getParameter( "sendInvitation" ).equals( "on" ) ? true : false;
            logger.info( " -----------> sendInvitation: " + sendInvitation );
        }
        Person loadedPerson = personService.get( personId );
        loadedPerson.setEmail( email );
        loadedPerson.setAccessCode( accessCode );
        loadedPerson.setAccessCodeType( AccessCodeType.valueOf( accessCodeType ) );
        loadedPerson.setStatus( PersonStatus.INVITED );
        personService.update( loadedPerson );
        if ( sendInvitation )
        {
            logger.info( " ***** sendInvitation to: " + loadedPerson.getFirstName() + " " + loadedPerson.getLastName() + " - id: " + loadedPerson.getId() );
            String link = ""
                    + mwcConfig.getMwcInternetBaseUrl()
                    + "/public/signup?lang="
                    + invitationLanguage;
            
//            Locale originalLocale = locale;
            Locale invitationLocale = new Locale(
                    StringUtils.first( invitationLanguage, 2 ),
                    StringUtils.last( invitationLanguage, 2 ));
            String loggedUserName = loggedUser.getMwcUser().getPerson().getName();
            String msg = messageSource.getMessage(
                    "email.signup.message.content",
                    new Object[]{ loadedPerson.getFirstName(), loggedUserName, loadedPerson.getAccessCode(), link  }, 
                    invitationLocale );
            String subject = messageSource.getMessage(
                    "email.signup.message.subject",
                    new Object[] { loadedPerson.getName() },
                    invitationLocale );
            mwcMail.sendMail( "javasd.info@gmail.com", loadedPerson.getEmail(), subject, msg );
        }
        return "redirect:/person/show/" + loadedPerson.getId();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // OTHER
    //
    ////////////////////////////////////////////////////////////////////////////
    @RequestMapping( value = "/almir", method = RequestMethod.POST )
    @ResponseBody
    public String performLogin(
            //            @RequestParam( "j_username" ) String username,
            //            @RequestParam( "j_password" ) String password,
            HttpServletRequest request, HttpServletResponse response )
    {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( "almir", "" );
        try
        {
            Authentication auth = authenticationManager.authenticate( token );
            SecurityContextHolder.getContext().setAuthentication( auth );
            repository.saveContext( SecurityContextHolder.getContext(), request, response );
            rememberMeServices.loginSuccess( request, response, auth );
            MwcUser mwcUser = loggedUser.getMwcUser();
            return "" + mwcUser.getId();
        }
        catch ( BadCredentialsException ex )
        {
            return "error";
        }
    }

//    private Person02 getPerson02( Person person )
//    {
//        logger.debug( "$$$$$$$$ 2" );
//        Person02 person02 = ( Person02 ) objectFiller.fillFields( person, Person02.class );
//        logger.debug( "$$$$$$$$ 3" );
//        PersonInBranch personInBranch = personInBranchService.getByPerson( person );
//        logger.debug( "$$$$$$$$ 4" );
//        if ( personInBranch != null )
//        {
//            logger.debug( "$$$$$$$$ 5" );
//            Branch branch = branchService.get( personInBranch.getBranch().getId() );
//            logger.debug( "$$$$$$$$ 6" );
//            Branch01 branch01 = ( Branch01 ) objectFiller.fillFields( branch, Branch01.class );
//            logger.debug( "$$$$$$$$ 7" );
//            Company01 company01 = ( Company01 ) objectFiller.fillFields( branch.getCompany(), Company01.class );
//            logger.debug( "$$$$$$$$ 8" );
//            if ( person.getMwcUser() != null )
//            {
//                logger.debug( "$$$$$$$$ 9" );
//                MwcUser01 mwcUser01 = ( MwcUser01 ) objectFiller.fillFields( person.getMwcUser(), MwcUser01.class );
//                logger.debug( "$$$$$$$$ 10" );
//                person02.setUser( mwcUser01 );
//                logger.debug( "$$$$$$$$ 11" );
//            }
//            logger.debug( "$$$$$$$$ 12" );
//            branch01.setCompany( company01 );
//            logger.debug( "$$$$$$$$ 13" );
//            person02.setBranch( branch01 );
//        }
//
//        return person02;
//    }
}
