/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.output;

import com.javasd.mwc.annotations.MWC_FillField;

/**
 *
 * @author almir
 */
public class Branch01
{
    private Long id;
    private String acronym;
    private String name;
    private String address1;
    private String address2;
    private String zipCode;
    private Company01 company;

    public Long getId()
    {
        return id;
    }

    @MWC_FillField
    public void setId( Long id )
    {
        this.id = id;
    }

    public String getAcronym()
    {
        return acronym;
    }

    @MWC_FillField
    public void setAcronym( String acronym )
    {
        this.acronym = acronym;
    }

    public String getName()
    {
        return name;
    }

    @MWC_FillField
    public void setName( String name )
    {
        this.name = name;
    }

    public String getAddress1()
    {
        return address1;
    }

    @MWC_FillField
    public void setAddress1( String address1 )
    {
        this.address1 = address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    @MWC_FillField
    public void setAddress2( String address2 )
    {
        this.address2 = address2;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    @MWC_FillField
    public void setZipCode( String zipCode )
    {
        this.zipCode = zipCode;
    }

    public Company01 getCompany()
    {
        return company;
    }

    public void setCompany( Company01 company )
    {
        this.company = company;
    }
    
}
