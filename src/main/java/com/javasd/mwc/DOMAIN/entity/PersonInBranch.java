/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import com.javasd.mwc.annotations.MWC_MergeIgnore;
import com.javasd.mwc.annotations.MWC_StringSanitize;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author almir
 */
@Entity
@Table( name = "person_in_branch" )
@NamedQueries( 
{
    @NamedQuery( name = "PersonInBranch.getByPerson", query = "SELECT pib FROM PersonInBranch pib WHERE pib.person = :person" ),
    @NamedQuery( name = "PersonInBranch.getByBranch", query = "SELECT pib FROM PersonInBranch pib WHERE pib.branch = :branch" ),
    @NamedQuery( name = "PersonInBranch.getByBranchPerson", query = "SELECT pib FROM PersonInBranch pib WHERE pib.branch = :branch and pib.person = :person" )
} )
public class PersonInBranch implements DomainObject
{

    private Long id;
    private Integer version;
    @MWC_StringSanitize
    private String position;
    private Branch branch;
    private Person person;

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

    @Version @MWC_MergeIgnore
    @Column( name = "VERSION" )
    public Integer getVersion()
    {
        return version;
    }

    public void setVersion( Integer version )
    {
        this.version = version;
    }

    @Column( name = "POSITION" )
    public String getPosition()
    {
        return position;
    }

    public void setPosition( String position )
    {
        this.position = position;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "BRANCH_ID" )
    public Branch getBranch()
    {
        return branch;
    }

    public void setBranch( Branch branch )
    {
        this.branch = branch;
    }

    @OneToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "PERSON_ID" )
    public Person getPerson()
    {
        return person;
    }

    public void setPerson( Person person )
    {
        this.person = person;
    }
}
