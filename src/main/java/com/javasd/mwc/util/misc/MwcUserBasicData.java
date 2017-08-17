/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.javasd.mwc.util.misc;

import com.javasd.mwc.WEB.output.MwcUserRoleI18n;
import java.util.List;
import javax.validation.constraints.Size;

/**
 *
 * @author almir
 */
public class MwcUserBasicData
{
    private Long mwcUserId;
    //@Pattern(regexp = "[0-9]+")
    @Size( min = 3, max = 20 )
    private String displayName;
    private List<String> roles;

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName( String displayName )
    {
        this.displayName = displayName;
    }

    public Long getMwcUserId()
    {
        return mwcUserId;
    }

    public void setMwcUserId( Long mwcUserId )
    {
        this.mwcUserId = mwcUserId;
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public void setRoles( List<String> roles )
    {
        this.roles = roles;
    }
    
}
