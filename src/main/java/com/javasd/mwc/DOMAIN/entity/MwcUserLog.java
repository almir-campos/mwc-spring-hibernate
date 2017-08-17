/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Almir
 */
@Entity
@Table ( name = "mwc_user_log")
public class MwcUserLog implements DomainObject
{
    private Long id;
    private String logText;
    private MwcUser mwcUser;

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

    @Column( name = "LOG_TEXT")
    public String getLogText()
    {
        return logText;
    }

    public void setLogText( String logText )
    {
        this.logText = logText;
    }

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "MWC_USER_ID")
    public MwcUser getMwcUser()
    {
        return mwcUser;
    }

    public void setMwcUser( MwcUser mwcUser )
    {
        this.mwcUser = mwcUser;
    }
    
}
