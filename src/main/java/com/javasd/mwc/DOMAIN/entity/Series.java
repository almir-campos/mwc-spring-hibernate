/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import com.javasd.mwc.annotations.MWC_MergeIgnore;
import com.javasd.mwc.annotations.MWC_PositiveDouble;
import com.javasd.mwc.annotations.MWC_StringSanitize;
import com.javasd.mwc.util.misc.MiscStatic;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 *
 * @author almir
 */
@NamedQueries(
         {
            @NamedQuery( name = "Series.getAllByStartDateDesc", query = "SELECT s FROM Series s WHERE mwcUser = :mwcUser ORDER BY s.startDate DESC" )
        } )
@Table( name = "series" )
@Entity
public class Series implements DomainObject
{

    private static final long serialVersionUID = 1L;
    private Long id;
    private int version;
    @Size( min = 3 )
    @MWC_StringSanitize
    private String description;
    @NotNull
    private DateTime startDate;
    @NotNull
    private DateTime estimatedEndDate;
    private DateTime realEndDate;
    //@NotEmpty
    //@Min( 1 )
//    @MWC_PositiveDouble
//    @Pattern(regexp = "\\d+")
    private Double initialWeight;
//    @NotNull
//    @Min( 1 )
//    @Pattern(regexp = "/-?[0-9]+\\.[0-9]{1}$/;")
    @MWC_PositiveDouble
    private Double goalWeight;
    private byte[] initialPhoto;
    private MwcUser mwcUser;
    private Set<SeriesDetail> seriesDetails;

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
    public int getVersion()
    {
        return version;
    }

    public void setVersion( int version )
    {
        this.version = version;
    }

    @Column( name = "START_DATE" )
    @Type( type = "org.joda.time.contrib.hibernate.PersistentDateTime" )
    @DateTimeFormat( iso = ISO.DATE )
    public DateTime getStartDate()
    {
        return startDate;
    }

    @Transient
    @MWC_MergeIgnore
    public String getStartDateString()
    {
        return MiscStatic.stringJodaDateTime( startDate );
    }

    public void setStartDate( DateTime startDate )
    {
        this.startDate = startDate;
    }

    @Column( name = "ESTIMATED_END_DATE" )
    @Type( type = "org.joda.time.contrib.hibernate.PersistentDateTime" )
    @DateTimeFormat( iso = ISO.DATE )
    //@NotEmpty
    public DateTime getEstimatedEndDate()
    {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate( DateTime estimatedEndDate )
    {
        this.estimatedEndDate = estimatedEndDate;
    }

    @Transient
    @MWC_MergeIgnore
    public String getEstimatedEndDateString()
    {
        return MiscStatic.stringJodaDateTime( estimatedEndDate );
    }

    @Column( name = "REAL_END_DATE" )
    @Type( type = "org.joda.time.contrib.hibernate.PersistentDateTime" )
    @DateTimeFormat( iso = ISO.DATE )
    //@NotEmpty
    public DateTime getRealEndDate()
    {
        return realEndDate;
    }

    public void setRealEndDate( DateTime realEndDate )
    {
        this.realEndDate = realEndDate;
    }

    @Transient
    @MWC_MergeIgnore
    public String getRealEndDateString()
    {
        return MiscStatic.stringJodaDateTime( realEndDate );
    }

    @Column( name = "INITIAL_WEIGHT" )
    //@NotEmpty
    public Double getInitialWeight()
    {
        return initialWeight;
    }

//    @MWC_Test
    public void setInitialWeight( Double initialWeight )
    {
        this.initialWeight = initialWeight;
    }

    @Column( name = "GOAL_WEIGHT" )
    //@NotEmpty
    public Double getGoalWeight()
    {
        return goalWeight;
    }

    public void setGoalWeight( Double goalWeight )
    {
        this.goalWeight = goalWeight;
    }

    @Basic( fetch = FetchType.LAZY )
    @Lob
    @Column( name = "INITIAL_PHOTO" )
    public byte[] getInitialPhoto()
    {
        return initialPhoto;
    }

    public void setInitialPhoto( byte[] initialPhoto )
    {
        this.initialPhoto = initialPhoto;
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

    @Column( name = "DESCRIPTION" )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @OneToMany( mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
    @JsonIgnore
    public Set<SeriesDetail> getSeriesDetails()
    {
        return seriesDetails;
    }

    public void setSeriesDetails( Set<SeriesDetail> seriesDetails )
    {
        this.seriesDetails = seriesDetails;
    }

    public void addSeriesDetail( SeriesDetail seriesDetail )
    {
        seriesDetail.setSeries( this );
        getSeriesDetails().add( seriesDetail );
    }

    @Override
    public String toString()
    {
        String r
                = "Description: " + getDescription()
                + ", Initial Weight: " + getInitialWeight()
                + ", Goal Weight: " + getGoalWeight()
                + ", Start Date: " + getStartDateString()
                + ", Estimated End Date: " + getEstimatedEndDateString();
        return r;
    }
}
