/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.output;

import com.javasd.mwc.annotations.MWC_FillField;
import java.util.List;

/**
 *
 * @author almir
 */
public class MwcUser01
{
    private Long id;
    private String displayName;
    private PersonBasicData person;
    private String preferredLanguageDescription;
    private List<MwcUserRoleI18n> roles;

    public Long getId()
    {
        return id;
    }

    @MWC_FillField
    public void setId( Long id )
    {
        this.id = id;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    @MWC_FillField
    public void setDisplayName( String displayName )
    {
        this.displayName = displayName;
    }

    public PersonBasicData getPerson()
    {
        return person;
    }

    public void setPerson( PersonBasicData person )
    {
        this.person = person;
    }

    public String getPreferredLanguageDescription()
    {
        return preferredLanguageDescription;
    }

    @MWC_FillField
    public void setPreferredLanguageDescription( String preferredLanguageDescription )
    {
        this.preferredLanguageDescription = preferredLanguageDescription;
    }

    public List<MwcUserRoleI18n> getRoles()
    {
        return roles;
    }

    public void setRoles( List<MwcUserRoleI18n> roles )
    {
        this.roles = roles;
    }

}
