/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.beans;

import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.REPOSITORY.mwcuser.UserRepository;
import com.javasd.mwc.REPOSITORY.series.SeriesRepository;
import com.javasd.mwc.SERVICE.mwcuser.UserService;
import com.javasd.mwc.SERVICE.person.PersonService;
import com.javasd.mwc.WEB.output.Branch01;
import com.javasd.mwc.WEB.output.Company01;
import com.javasd.mwc.WEB.output.MwcUser01;
import com.javasd.mwc.WEB.output.PersonBasicData;
import com.javasd.utils.stringutils.StringUtils;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Almir
 */
@Component( "loggedUser" )
public class LoggedUser
{

    @Autowired
    private SpringUser springUser;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private UserService mwcUserService;
    @Autowired
    private MiscDynamic miscDynamic;
    //
    private MwcUser mwcUser;

    public MwcUser getMwcUser()
    {
        org.springframework.security.core.userdetails.User sUser = springUser.getUser();
        if ( sUser == null )
        {
            mwcUser = null;
            return null;
        }

        String sUsername = sUser.getUsername();
        mwcUser = userRepository.getByUsername( sUsername );

        return mwcUser;
    }

    public void setMwcUser( MwcUser mwcUser )
    {
        this.mwcUser = mwcUser;
    }

    public SpringUser getSpringUser()
    {
        return springUser;
    }

    public MwcUser01 getMwcUserBasicData()
    {

        return miscDynamic.getMwcUser01( getMwcUser() );
    }

    public Person isManagerOfPerson( Long otherPersonId )
    {
        List<Person> managedPeople = personService.getAllManagedByLastName();
        int managedPeopleSize = managedPeople.size();
        for ( Person p : managedPeople )
        {
            if ( p.getId() == otherPersonId )
            {
                return p;
            }
        }
        return null;
    }

    public MwcUser isManagerOfUser( Long otherUserId )
    {
        MwcUser otherUser = mwcUserService.get( otherUserId );
//        MwcUser01 mwcUserBasicData = miscDynamic.getMwcUser01( otherUser );
        Person otherPerson = isManagerOfPerson( otherUser.getPerson().getId() );
        if ( otherPerson == null )
        {
            return null;
        }
        return otherUser;
    }

    // USE THESE METHODS IN THE CONTROLLERS
    public PersonBasicData getPersonBasicData()
    {
        PersonBasicData personBasicData = getMwcUserBasicData().getPerson();
        return personBasicData;
    }

    public Branch01 getBranchBasicData()
    {
        Branch01 branchBasicData = getPersonBasicData().getBranch();
        return branchBasicData;
    }

    public Company01 getCompanyBasicData()
    {
        Branch01 branchBasicData = getBranchBasicData();
        if ( branchBasicData == null )
        {
            return null;
        }
        Company01 company = getBranchBasicData().getCompany();
        return company;
    }

    public boolean isManagerOfUserOfSeries( Long otherUserSeriesId )
    {
        Series otherUserSeries = seriesRepository.get( otherUserSeriesId );
        MwcUser otherUser = otherUserSeries.getMwcUser();
        MwcUser otherUserManaged = isManagerOfUser( otherUser.getId() );
        return otherUserManaged != null;
    }

    public boolean isTheUserOfTheSeries( Long seriesId )
    {
        Series series = seriesRepository.get( seriesId );
        return ( series.getMwcUser().getId() == getMwcUser().getId() );
    }

    public Locale getLocale()
    {
        String loggedUserLocaleCode;
        loggedUserLocaleCode = getMwcUser().getPreferredLanguage().getCode();
        Locale locale = new Locale(
                StringUtils.first( loggedUserLocaleCode, 2 ),
                StringUtils.last( loggedUserLocaleCode, 2 ) );
        return locale;
    }
}
