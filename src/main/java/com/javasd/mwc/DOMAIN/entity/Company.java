/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import com.javasd.mwc.annotations.MWC_MergeIgnore;
import com.javasd.mwc.annotations.MWC_StringSanitize;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Almir
 */
@Entity
@Table( name = "company" )
@NamedQueries( 
{
    @NamedQuery( name = "Company.getAllByName", query = "SELECT c FROM Company c ORDER BY LOWER(c.name)" ),
    @NamedQuery( name = "Company.getBasicData", query = "SELECT DISTINCT c.id, c.acronym, c.name, c.uri FROM Company c WHERE c.id = :id" )
//    @NamedQuery( name = "Company.getBasicData", query = "SELECT c FROM Company c WHERE c.id = :id" )
} )
public class Company implements DomainObject
{

    private Long id;
    private Integer version;
    @MWC_StringSanitize
    @NotNull
    @Size( min=2, max=10 )
    private String acronym;
    @MWC_StringSanitize
    @NotNull
    @Size( min=2, max=30 )
    private String name;
    private String uri;
    private Set<Branch> companyBranches;

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

    @Column( name = "URI", length = 2083 )
    public String getUri()
    {
        return uri;
    }

    public void setUri( String uri )
    {
        this.uri = uri;
    }

    
    @OneToMany( mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
    @JsonIgnore
    public Set<Branch> getCompanyBranches()
    {
        return companyBranches;
    }

    public void setCompanyBranches( Set<Branch> companyBranches )
    {
        this.companyBranches = companyBranches;
    }
    
//    @Transient
//    @MWC_MergeIgnore
//    public Branch getBranch( Long branchId )
//    {
//        
//    }
}
