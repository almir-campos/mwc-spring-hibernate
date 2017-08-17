/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.entity;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import com.javasd.mwc.annotations.MWC_MergeIgnore;
import com.javasd.mwc.annotations.MWC_PositiveDouble;
import com.javasd.mwc.annotations.MWC_StringSanitize;
import com.javasd.mwc.util.misc.MiscStatic;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
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
    @NamedQuery(name = "SeriesDetail.getAllByWeightDate",
                query = "SELECT sd FROM SeriesDetail sd  WHERE sd.series = :series ORDER BY sd.weightDate DESC"),
    @NamedQuery(name = "SeriesDetail.getAllByWeightDateAsc",
                query = "SELECT sd FROM SeriesDetail sd  WHERE sd.series = :series ORDER BY sd.weightDate")
})
@Table(name = "series_detail")
@Entity
public class SeriesDetail implements DomainObject
{

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private int version;
    private DateTime weightDate;
//    private String weightDateString;
    private Double weight;
    private Byte[] photo;
    @MWC_StringSanitize
    private String comment;
    private Series series;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Version @MWC_MergeIgnore
    @Column(name = "VERSION")
    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    @Column(name = "WEIGHT_DATE")
//    @NotEmpty
    public DateTime getWeightDate()
    {
        return weightDate;
    }

    public void setWeightDate(DateTime weightDate)
    {
        this.weightDate = weightDate;
    }

    @Transient
    public String getWeightDateString()
    {
        return MiscStatic.stringJodaDateTime(weightDate);
    }
    
    @Column(name = "WEIGHT")
//    @NotEmpty
    @MWC_PositiveDouble
    public Double getWeight()
    {
        return weight;
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "PHOTO")
    public Byte[] getPhoto()
    {
        return photo;
    }

    public void setPhoto(Byte[] photo)
    {
        this.photo = photo;
    }

    @Column(name = "COMMENT")
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @ManyToOne ( fetch = FetchType.EAGER )
    @JoinColumn( name = "SERIES_ID")
    public Series getSeries()
    {
        return series;
    }

    public void setSeries(Series series )
    {
        this.series = series;
    }
    
}
