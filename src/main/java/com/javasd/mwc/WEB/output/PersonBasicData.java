/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.output;

import com.javasd.mwc.DOMAIN.complements.AccessCodeType;
import com.javasd.mwc.annotations.MWC_FillField;
import com.javasd.mwc.DOMAIN.complements.PersonStatus;
import com.javasd.mwc.DOMAIN.entity.Person;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author almir
 */
public class PersonBasicData
{
    private Long id;
    private String firstName;
    private String lastName;
    private DateTime birthDate;
    private String birthDateString;
    private String email;
    private String phone;
    private PersonStatus status;
    private AccessCodeType accessCodeType;
    private Branch01 branch;
    private Person Manager;
    private List<Person> peopleContact;
    private MwcUser01 user;

    public Long getId()
    {
        return id;
    }

    @MWC_FillField
    public void setId( Long id )
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    @MWC_FillField
    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    @MWC_FillField
    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public DateTime getBirthDate()
    {
        return birthDate;
    }

    @MWC_FillField
    public void setBirthDate( DateTime birthDate )
    {
        this.birthDate = birthDate;
    }

    public String getEmail()
    {
        return email;
    }

    @MWC_FillField
    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    @MWC_FillField
    public void setPhone( String phone )
    {
        this.phone = phone;
    }

    public PersonStatus getStatus()
    {
        return status;
    }

    @MWC_FillField
    public void setStatus( PersonStatus status )
    {
        this.status = status;
    }

    public AccessCodeType getAccessCodeType()
    {
        return accessCodeType;
    }

    @MWC_FillField
    public void setAccessCodeType( AccessCodeType accessCodeType )
    {
        this.accessCodeType = accessCodeType;
    }

    
    public Branch01 getBranch()
    {
        return branch;
    }

    public void setBranch( Branch01 branch )
    {
        this.branch = branch;
    }

    public Person getManager()
    {
        return Manager;
    }

    @MWC_FillField
    public void setManager( Person Manager )
    {
        this.Manager = Manager;
    }

    public List<Person> getPeopleContact()
    {
        return peopleContact;
    }

    @MWC_FillField
    public void setPeopleContact( List<Person> peopleContact )
    {
        this.peopleContact = peopleContact;
    }

    public MwcUser01 getUser()
    {
        return user;
    }

    public void setUser( MwcUser01 user )
    {
        this.user = user;
    }

    public String getBirthDateString()
    {
        return birthDateString;
    }

    @MWC_FillField
    public void setBirthDateString( String birthDateString )
    {
        this.birthDateString = birthDateString;
    }
 
    
    
}