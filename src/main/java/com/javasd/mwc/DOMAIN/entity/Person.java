/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.AccessCodeType;
import com.javasd.mwc.DOMAIN.complements.DomainObject;
import com.javasd.mwc.DOMAIN.complements.PersonStatus;
import com.javasd.mwc.annotations.MWC_MergeIgnore;
import com.javasd.mwc.annotations.MWC_StringSanitize;
import com.javasd.mwc.util.misc.MiscStatic;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Almir
 */
@Entity
@Table( name = "person" )
@NamedQueries(
        {
    @NamedQuery(
            name = "Person.getByAccessCode",
            query = "SELECT p FROM Person p WHERE p.accessCode = :accessCode" ),
    @NamedQuery(
            name = "Person.getByLinkCode",
            query = "SELECT p FROM Person p WHERE p.linkCode = :linkCode" ),
    @NamedQuery(
            name = "Person.getAllByLastName",
            query = "SELECT p FROM Person p ORDER BY LOWER(p.lastName)" ),
    @NamedQuery(
            name = "Person.getAllManagedByLastName",
            query = "SELECT p FROM Person p WHERE p.manager = :manager ORDER BY LOWER(p.lastName)" ),
    @NamedQuery(
            name = "Person.getByStatus",
            query = "SELECT p FROM Person p WHERE p.status = :status ORDER BY LOWER(p.lastName)" )
} )
public class Person implements DomainObject
{

    private Long id;
    private Integer version;
    @Size( min = 2,  max = 20 )
    @MWC_StringSanitize
    private String firstName;
    @Size( min = 1, max = 50 )
    @MWC_StringSanitize
    private String lastName;
    @Past
    private DateTime birthDate;
    @Size( min = 8, max = 50)
    @MWC_StringSanitize
    private String email;
    @Size( min = 10, max = 20 )
    private String phone;
    private String accessCode;
    private AccessCodeType accessCodeType;
    private String linkCode;
    private PersonStatus status;
    private PersonInBranch personInBranch;
    private MwcUser mwcUser;
    private List<Person> peopleContact;
    private Person manager;

    @Id
    @GeneratedValue( strategy = IDENTITY )
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

//    @NotEmpty ( message = "{validation.firstname.NotEmpty.message}" )
//    @Size ( min = 3,
//            max = 30,
//            message = "{validation.firstname.Size.message}" )
    @Column( name = "FIRST_NAME" )
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

//    @NotEmpty ( message = "{validation.lastname.NotEmpty.message}" )
//    @Size ( min = 1,
//            max = 40,
//            message = "{validation.lastname.Size.message}" )
    @Column( name = "LAST_NAME" )
    public String getLastName()
    {
        return lastName;
    }

    @Column( name = "BIRTH_DATE" )
    @Type( type = "org.joda.time.contrib.hibernate.PersistentDateTime" )
    @DateTimeFormat( iso = DateTimeFormat.ISO.DATE )
    public DateTime getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate( DateTime birthDate )
    {
        this.birthDate = birthDate;
    }

    @Transient
    public String getBirthDateString()
    {
        return MiscStatic.stringJodaDateTime( birthDate );
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    @Column( name = "EMAIL" )
    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    @Column( name = "PHONE" )
    public String getPhone()
    {
        return phone;
    }

    public void setPhone( String phone )
    {
        this.phone = phone;
    }

    @Column( name = "ACCESS_CODE" )
    public String getAccessCode()
    {
        return accessCode;
    }

    public void setAccessCode( String accessCode )
    {
        this.accessCode = accessCode;
    }

    @Column( name = "ACCESS_CODE_TYPE" )
    @Enumerated( EnumType.STRING )
    public AccessCodeType getAccessCodeType()
    {
        return accessCodeType;
    }

    public void setAccessCodeType( AccessCodeType accessCodeType )
    {
        this.accessCodeType = accessCodeType;
    }

    @Column( name = "LINK_CODE" )
    public String getLinkCode()
    {
        return linkCode;
    }

    public void setLinkCode( String linkCode )
    {
        this.linkCode = linkCode;
    }

    @Column( name = "STATUS" )
    @Enumerated( EnumType.STRING )
    public PersonStatus getStatus()
    {
        return status;
    }

    public void setStatus( PersonStatus status )
    {
        this.status = status;
    }

    @OneToOne( mappedBy = "person", cascade =  {CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true, fetch = FetchType.EAGER )
    @JsonIgnore
    public PersonInBranch getPersonInBranch()
    {
        return personInBranch;
    }

    public void setPersonInBranch( PersonInBranch personInBranch )
    {
        this.personInBranch = personInBranch;
    }

    @OneToOne( mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
    @JsonIgnore
    public MwcUser getMwcUser()
    {
        return mwcUser;
    }

    public void setMwcUser( MwcUser mwcUser )
    {
        this.mwcUser = mwcUser;
    }

    @OneToMany( mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
    @JsonIgnore
    public List<Person> getPeopleContact()
    {
        return peopleContact;
    }

    public void setPeopleContact( List<Person> peopleContact )
    {
        this.peopleContact = peopleContact;
    }

    @ManyToOne
    @JoinColumn( name =  "MANAGER_ID" )
    public Person getManager()
    {
        return manager;
    }

    public void setManager( Person manager )
    {
        this.manager = manager;
    }
    
    @Transient
    @MWC_MergeIgnore
    public String getName()
    {
        return firstName + " " + lastName;
    }
    
    @Transient
    @MWC_MergeIgnore
    public String getNameLastFirst()
    {
        return lastName + " " + firstName;
    }
    
    
}
