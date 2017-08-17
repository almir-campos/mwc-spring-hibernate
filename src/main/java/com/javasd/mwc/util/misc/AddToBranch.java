/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.misc;

import javax.validation.constraints.Size;

/**
 *
 * @author almir
 */
public class AddToBranch
{
    private Long personId;
    private Long branchId;
    @Size( min = 2, max = 30 )
    private String position;

    public Long getPersonId()
    {
        return personId;
    }

    public void setPersonId( Long personId )
    {
        this.personId = personId;
    }

    public Long getBranchId()
    {
        return branchId;
    }

    public void setBranchId( Long branchId )
    {
        this.branchId = branchId;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition( String position )
    {
        this.position = position;
    }
    
}
