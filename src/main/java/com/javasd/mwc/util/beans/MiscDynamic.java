/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.beans;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.PersonInBranch;
import com.javasd.mwc.SERVICE.branch.BranchService;
import com.javasd.mwc.SERVICE.personinbranch.PersonInBranchService;
import com.javasd.mwc.WEB.output.Branch01;
import com.javasd.mwc.WEB.output.Company01;
import com.javasd.mwc.WEB.output.MwcUser01;
import com.javasd.mwc.WEB.output.PersonBasicData;
import com.javasd.mwc.WEB.output.PersonShowData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

/**
 *
 * @author almir
 */
@Component( "miscDynamic" )
public class MiscDynamic
{

    private final Logger logger = Logger.getLogger( MiscDynamic.class );
    @Autowired
    private ObjectFiller objectFiller;
    @Autowired
    private PersonInBranchService personInBranchService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private LoggedUser loggedUser;

    //
    public Locale getDefaultLocale()
    {
        if ( loggedUser != null && loggedUser.getMwcUser() != null )
        {
            return loggedUser.getLocale();
        }
        return Locale.US;
    }

    public String stringSanitizer( String str )
    {

        // THE REPLACEMENT MUST BE FOR ONE CHARACTER ONLY AND IT MUST NOT BE SPECIAL
        if ( str == null )
        {
            return null;
        }

        String validCaracters
                = "valid.caracters=01234567890"
                + "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + " '!@#$%¨&*()-_+=§?/,.;:{}[]|*\""
                + "áéíóú"
                + "ÁÉÍÓÚ"
                + "âê"
                + "ÂÊ";

        String currentChar;
        StringBuilder sb = new StringBuilder();
        int numberOfInvalidCharaters = 0;
        StringBuilder sanitized = new StringBuilder();

        for ( int i = 0; i < str.length(); i++ )
        {
            currentChar = String.valueOf( str.charAt( i ) );
            if ( validCaracters.indexOf( currentChar ) == -1 )
            {
                sb.append( "?" );
                sanitized.append( currentChar );
                numberOfInvalidCharaters++;
            }
            else
            {
                sb.append( currentChar );
            }
        }
        if ( numberOfInvalidCharaters == 0 )
        {
            logger.info( "SANITIZER =====> String is valid" );
        }
        else
        {
            logger.info( "SANITIZED =====> " + numberOfInvalidCharaters + " character(s) sanitized: " + sanitized.toString() );
        }
        return sb.toString();

    }

    public boolean hasInvalidCaracters( String str, Locale locale )
    {
        String validCaracters = messageSource.getMessage( str, null, locale );
        for ( int i = 0; i < str.length(); i++ )
        {
            if ( validCaracters.indexOf( str.substring( i, i + 1 ) ) == -1 )
            {
                return false;
            }
        }
        return true;
    }

    public PersonBasicData getPersonBasicData( Person person )
    {
        PersonBasicData personBasicData = ( PersonBasicData ) objectFiller.fillFields( person, PersonBasicData.class );
        if ( person.getMwcUser() != null )
        {
            MwcUser01 mwcUser01 = ( MwcUser01 ) objectFiller.fillFields( person.getMwcUser(), MwcUser01.class );
            personBasicData.setUser( mwcUser01 );
        }

        PersonInBranch personInBranch = personInBranchService.getByPerson( person );
        if ( personInBranch != null )
        {
            Branch branch = branchService.get( personInBranch.getBranch().getId() );
            Branch01 branch01 = ( Branch01 ) objectFiller.fillFields( branch, Branch01.class );
            Company01 company01 = ( Company01 ) objectFiller.fillFields( branch.getCompany(), Company01.class );
            branch01.setCompany( company01 );
            personBasicData.setBranch( branch01 );

        }

        return personBasicData;
    }

    public PersonShowData getPersonShowData( Person person )
    {
        PersonShowData personShowData = ( PersonShowData ) objectFiller.fillFields( person, PersonShowData.class );
        personShowData.setStatus( person.getStatus().getDescription() );
        personShowData.setAccessCodeType( person.getAccessCodeType().getDescription() );
        personShowData.setManagerName( person.getManager().getName() );
        if ( person.getMwcUser() != null )
        {
            logger.info( "**** USER: " + person.getMwcUser() + ", ID: " + person.getMwcUser().getId() );
            personShowData.setUserId( person.getMwcUser().getId() );
        }

        PersonInBranch personInBranch = personInBranchService.getByPerson( person );
        if ( personInBranch != null )
        {
            Branch branch = branchService.get( personInBranch.getBranch().getId() );
            personShowData.setCompanyName( branch.getCompany().getName() );
            personShowData.setBranchName( branch.getName() );

        }

        return personShowData;
    }

    public MwcUser01 getMwcUser01( MwcUser mwcUser )
    {
        MwcUser01 mwcUser01 = ( MwcUser01 ) objectFiller.fillFields( mwcUser, MwcUser01.class );
        PersonBasicData personBasicData = getPersonBasicData( mwcUser.getPerson() );
        mwcUser01.setPerson( personBasicData );
        return mwcUser01;
    }

    public String getFirstErrorMessage( List<ObjectError> errors, Locale locale )
    {
        ObjectError oe = null;
        String msg = "";
        Object[] obj = null;
        Iterator it = errors.iterator();
        String arg;
        while ( it.hasNext() )
        {
            oe = ( ObjectError ) it.next();
            obj = oe.getCodes();
            for ( Object o : obj )
            {
                logger.info( "\n\n****** oe.toString(): " + oe + "\n\n" );
                logger.info( "\n\n****** oe.getArguments(): " + oe.getArguments() );
                logger.info( "\n\n****** oe.getArguments(): " + oe.getArguments().length );
                logger.info( "\n\n****** oe.getArguments()[0]: " + oe.getArguments()[0] );
                logger.info( "\n\n****** oe.getArguments()[1]: " + oe.getArguments()[1] );
                logger.info( "\n\n****** oe.getArguments()[2]: " + oe.getArguments()[2] + "\n\n" );
                msg = messageSource.getMessage( o.toString(), null, locale );

                for ( int i = 1; i < oe.getArguments().length; i++ )
                {
                    arg = "{" + i + "}";
                    msg = msg.replace( arg, String.valueOf( oe.getArguments()[i] ) );
                }
                logger.info( "\n\n****** o.toString(): " + msg + "\n\n" );
                logger.info( "\n\n****** message: "
                        + msg
                        + "\n\n" );
                break;
            }
            break;
        }
        return msg;
    }

    public List<String> getAllErrorMessages( List<ObjectError> errors, Locale locale )
    {
        ObjectError oe = null;
        String msg = "";
        List<String> msgs = new ArrayList<String>();
        Object obj = null;
        Iterator it = errors.iterator();
        String arg;
        while ( it.hasNext() )
        {
            oe = ( ObjectError ) it.next();
            obj = oe.getCodes()[0];
//            for ( Object o : obj )
//            {
//                msg = messageSource.getMessage( o.toString(), null, locale );
            msg = messageSource.getMessage( obj.toString(), null, locale );

            for ( int i = 1; i < oe.getArguments().length; i++ )
            {
                arg = "{" + i + "}";
                msg = msg.replace( arg, String.valueOf( oe.getArguments()[i] ) );
            }
//            }
            msgs.add( msg );
        }
        return msgs;
    }
}
