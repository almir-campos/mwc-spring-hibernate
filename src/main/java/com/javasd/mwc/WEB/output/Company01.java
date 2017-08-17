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
public class Company01
{
    private Long id;
    private String acronym;
    private String name;

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

    
}
