/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import com.javasd.mwc.DOMAIN.complements.MwcUserRoleType;
import com.javasd.mwc.annotations.MWC_MergeIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author Almir
 */
@Entity
@Table( name = "mwc_user_role" )
public class MwcUserRole implements DomainObject
{

    private Long id;
    private MwcUserRoleType authority;
    private MwcUser mwcUser;
    private int Version;

    @Id
    @GeneratedValue
    @Column( name = "ID" )
    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    @Column( name = "AUTHORITY" )
    @Enumerated( EnumType.STRING )
    public MwcUserRoleType getAuthority()
    {
        return authority;
    }

    public void setAuthority( MwcUserRoleType authority )
    {
        this.authority = authority;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "MWC_USER_ID" )
    public MwcUser getMwcUser()
    {
        return mwcUser;
    }

    public void setMwcUser( MwcUser mwcUser )
    {
        this.mwcUser = mwcUser;
    }

    @Version @MWC_MergeIgnore
    @Column( name = "VERSION" )
    public int getVersion()
    {
        return Version;
    }

    public void setVersion( int Version )
    {
        this.Version = Version;
    }
}
