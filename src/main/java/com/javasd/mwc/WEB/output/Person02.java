/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.output;

import com.javasd.mwc.DOMAIN.complements.AccessCodeType;
import com.javasd.mwc.annotations.MWC_FillField;
import com.javasd.mwc.DOMAIN.complements.PersonStatus;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author almir
 */
public class Person02
{
    private Long id;
    private String firstName;
    private String lastName;
    private DateTime birthDate;
    private String email;
    private String phone;
    private PersonStatus status;
    private String accessCode;
    private AccessCodeType accessCodeType;
    private List<AccessCodeTypeI18n> accessCodeTypes;
    private Branch01 branch;
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

    public String getAccessCode()
    {
        return accessCode;
    }

    @MWC_FillField
    public void setAccessCode( String accessCode )
    {
        this.accessCode = accessCode;
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
    
    public MwcUser01 getUser()
    {
        return user;
    }

    public void setUser( MwcUser01 user )
    {
        this.user = user;
    }

    public void setAccessCodeTypes( List<AccessCodeTypeI18n> accessCodeTypes )
    {
        this.accessCodeTypes = accessCodeTypes;
    }

    public List<AccessCodeTypeI18n> getAccessCodeTypes()
    {
        return accessCodeTypes;
    }

    
}
