/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.output;

import com.javasd.mwc.annotations.MWC_FillField;
import org.joda.time.DateTime;

/**
 *
 * @author almir
 */
public class PersonShowData
{
    private Long id;
    private String firstName;
    private String lastName;
    private DateTime birthDate;
    private String birthDateString;
    private String email;
    private String phone;
    private String status;
    private String accessCodeType;
    private String companyName;
    private String branchName;
    private String managerName;
    private Long userId;

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

    public String getBirthDateString()
    {
        return birthDateString;
    }

    @MWC_FillField
    public void setBirthDateString( String birthDateString )
    {
        this.birthDateString = birthDateString;
    }
    
    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public String getAccessCodeType()
    {
        return accessCodeType;
    }

    public void setAccessCodeType( String accessCodeType )
    {
        this.accessCodeType = accessCodeType;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName( String companyName )
    {
        this.companyName = companyName;
    }

    
    public String getBranchName()
    {
        return branchName;
    }

    public void setBranchName( String branchName )
    {
        this.branchName = branchName;
    }

    public String getManagerName()
    {
        return managerName;
    }

    public void setManagerName( String managerName )
    {
        this.managerName = managerName;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId( Long userId )
    {
        this.userId = userId;
    }

}