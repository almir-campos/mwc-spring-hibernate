/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import com.javasd.mwc.DOMAIN.complements.Language;
import com.javasd.mwc.DOMAIN.complements.MwcUserRoleType;
import com.javasd.mwc.DOMAIN.complements.UserStatus;
import com.javasd.mwc.annotations.MWC_MergeIgnore;
import com.javasd.mwc.annotations.MWC_StringSanitize;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Almir Campos
 */
@NamedQueries(
        {
    @NamedQuery( name = "MwcUser.getAllByLastName", query = "SELECT u FROM MwcUser u ORDER BY LOWER(u.person.lastName)" ),
    @NamedQuery( name = "MwcUser.getByUsername", query = "SELECT u FROM MwcUser u WHERE u.username = :username" )
} )
@Entity
@Table( name = "mwc_user" )
public class MwcUser
        implements DomainObject
{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( MwcUser.class );
    private long id;
    private int version;
    private Language preferredLanguage;
    @MWC_StringSanitize
    private String username;
    //@MWC_StringSanitize
    private String password;
    private byte[] avatarImg;
    @MWC_StringSanitize
    private String displayName;
    private UserStatus status;
    private Boolean enabled;
    private Person person;
    private Set<Series> seriess = new HashSet<Series>();
    private Set<MwcUserRole> userRoles = new HashSet<MwcUserRole>();
    private Set<MwcUserLog> userLogs = new HashSet<MwcUserLog>();

    @Id
    @GeneratedValue( strategy = IDENTITY )
    @Column( name = "ID" )
    public long getId()
    {
        return id;
    }

    @Version @MWC_MergeIgnore
    @Column( name = "VERSION" )
    public int getVersion()
    {
        return version;
    }

    @Column( name = "PREFERRED_LANGUAGE" )
    @Enumerated( EnumType.STRING )
    public Language getPreferredLanguage()
    {
        return preferredLanguage;
    }

    @Transient
    public String getPreferredLanguageDescription()
    {
        return Language.valueOf( preferredLanguage.getCode() ).getDescription();
    }

//    @NotEmpty(message = "{validation.username.NotEmpty.message}")
//    @Size(min = 6,
//            max = 20,
//            message = "{validation.username.Size.message}")
    @Column( name = "USERNAME" )
    public String getUsername()
    {
        return username;
    }

//    @NotEmpty(message = "{validation.password.NotEmpty.message}")
//    @Size(min = 6,
//            max = 20,
//            message = "{validation.password.Size.message}")
    @Column( name = "PASSWORD", length = 1024 )
    public String getPassword()
    {
//        logger.info( "**** getPassword: " + password );
        String ret = hashPwd(password);
//        logger.info( "**** getPassword (ret): " + ret );
        return ret;
    }

    @Basic( fetch = FetchType.LAZY )
    @Lob
    @Column( name = "AVATAR_IMG" )
    @JsonIgnore
    public byte[] getAvatarImg()
    {
        return avatarImg;
    }

//    @NotEmpty(message = "{validation.displayName.NotEmpty.message}")
//    @Size(min = 6,
//            max = 20,
//            message = "{validation.displayName.Size.message}")
    @Column( name = "DISPLAY_NAME" )
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public String toString()
    {
        //DateTime dt = new DateTime();
        org.joda.time.format.DateTimeFormatter fmt;
//        fmt = org.joda.time.format.DateTimeFormat.forPattern( "dd-MMMM-yyyy" );
//        String str = fmt.withLocale( Locale.US ).print( person.getBirthDate() );
        return "Users{" + "Id=" + id + ", version=" + version + ", firstName=" + person.getFirstName() + ", lastName=" + person.getLastName() + ", username=" + username + ", password: " + password + '}';
//        return "Users{" + "Id=" + id + ", version=" + version + ", username=" + username + ", password: " + password + '}';
//          return this.toString();
    }

    public void setId( long Id )
    {
        this.id = Id;
    }

    public void setVersion( int version )
    {
        this.version = version;
    }

    public void setPreferredLanguage( Language preferredLanguage )
    {
        this.preferredLanguage = preferredLanguage;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public void setPassword( String password )
    {
//        logger.info( "**** setPassword: " + password );
        this.password = password;
    }

    public void setAvatarImg( byte[] avatarImg )
    {
        this.avatarImg = avatarImg;
    }

    public void setDisplayName( String displayName )
    {
        this.displayName = displayName;
    }

    @Enumerated( EnumType.STRING )
    @Column( name = "STATUS" )
    public UserStatus getStatus()
    {
        return status;
    }

    public void setStatus( UserStatus status )
    {
        this.status = status;
    }

    @OneToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "PERSON_ID")
    public Person getPerson()
    {
        return person;
    }

    public void setPerson( Person person )
    {
        this.person = person;
    }

    @OneToMany( mappedBy = "mwcUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
    @JsonIgnore
    public Set<Series> getSeriess()
    {
        return seriess;
    }

    public void setSeriess( Set<Series> seriess )
    {
        this.seriess = seriess;
    }

    public void addSeries( Series series )
    {
        series.setMwcUser( this );
        getSeriess().add( series );
    }

    @Column( name = "ENABLED" )
    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled( Boolean enabled )
    {
        this.enabled = enabled;
    }

    @OneToMany( mappedBy = "mwcUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
    @JsonIgnore
    public Set<MwcUserRole> getUserRoles()
    {
        return userRoles;
    }

    public void setUserRoles( Set<MwcUserRole> userRoles )
    {
        this.userRoles = userRoles;
    }

    public boolean hasRole( String role )
    {
        boolean r = false;
        for ( MwcUserRole ur : userRoles )
        {
            if ( ur.getAuthority().getCode().equals( role ) )
            {
                r = true;
                break;
            }
        }
        return r;
    }
    
    public boolean hasAnyRole( String[] roles )
    {
        for (String s : roles )
        {
            if ( hasRole( s ) )
            {
                return true;
            }
        }
        return false;
    }

    @OneToMany( mappedBy = "mwcUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
    @JsonIgnore
    public Set<MwcUserLog> getUserLogs()
    {
        return userLogs;
    }

    public void setUserLogs( Set<MwcUserLog> userLogs )
    {
        this.userLogs = userLogs;
    }

    // Can be optmized
    public boolean hasSeries( Long seriesId )
    {
        boolean r = false;
        for ( Series s : seriess )
        {
            if ( s.getId() == seriesId )
            {
                r = true;
                break;
            }
        }
        return r;
    }

    // Can be optmized
    public boolean hasSeriesDetails( Long seriesDetailsId )
    {
        boolean r = false;
        for ( Series s : seriess )
        {
            for ( SeriesDetail sd : s.getSeriesDetails() )
            {
                if ( sd.getId() == seriesDetailsId )
                {
                    return true;
                }
            }
        }
        return r;
    }
    
    public void addRole( String role )
    {
        MwcUserRole mwcUserRole = new MwcUserRole();
        mwcUserRole.setAuthority( MwcUserRoleType.valueOf( role ) );
        mwcUserRole.setMwcUser( this );
        userRoles.add( mwcUserRole );
    }
    
    public void emptyRoles()
    {
        userRoles.clear();
        // THE PROCESS TO REMOVE OR DEACTIVATE/REACTIVATE AN USER IS TO BE DEFINED
        addRole( "ROLE_USER" );
    }
    
    private String hashPwd( String pwd )
    {
//        logger.info( "******* HASHPWD: " + pwd  );
        return pwd;
    }
}
