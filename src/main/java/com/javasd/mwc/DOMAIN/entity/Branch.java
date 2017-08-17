/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import com.javasd.mwc.annotations.MWC_MergeIgnore;
import com.javasd.mwc.annotations.MWC_StringSanitize;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Almir
 */
@Entity
@Table( name = "branch" )
@NamedQueries( 
{
    @NamedQuery(
            name = "Branch.getAllByAcronymForCompany",
            query = "SELECT b FROM Branch b WHERE b.company = :company ORDER BY LOWER(b.acronym)"),
    @NamedQuery( 
            name = "Branch.getAllByCompanyAndAcronym",
            query = "SELECT b FROM Branch b ORDER BY LOWER(b.company.acronym), LOWER(b.acronym)"),
} )
public class Branch implements DomainObject
{

    private Long id;
    private Integer version;
    @MWC_StringSanitize
    @Size( min=2, max=10 )
    private String acronym;
    @Size( min=2, max=30 )
    @MWC_StringSanitize
    private String name;
    private String address1;
    private String address2;
    //@Digits( integer = Integer.MAX_VALUE, fraction = 0 )
    @Pattern(regexp = "[0-9]+")
    @Size( min=5 )
    private String zipCode;
    private Company company;
    private List<PersonInBranch> personInBranch;

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

    @Column( name = "ACRONYM" )
    public String getAcronym()
    {
        return acronym;
    }

    public void setAcronym( String acronym )
    {
        this.acronym = acronym;
    }

    @Column( name = "NAME" )
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Column( name = "ADDRESS1" )
    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1( String address1 )
    {
        this.address1 = address1;
    }

    @Column( name = "ADDRESS2" )
    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2( String address2 )
    {
        this.address2 = address2;
    }

    @Column( name = "ZIP_CODE" )
    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode( String zipCode )
    {
        this.zipCode = zipCode;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "COMPANY_ID" )
    public Company getCompany()
    {
        return company;
    }

    public void setCompany( Company company )
    {
        this.company = company;
    }

    @OneToMany( mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
    @JsonIgnore
    public List<PersonInBranch> getPersonInBranch()
    {
        return personInBranch;
    }

    public void setPersonInBranch( List<PersonInBranch> personInBranch )
    {
        this.personInBranch = personInBranch;
    }
}
