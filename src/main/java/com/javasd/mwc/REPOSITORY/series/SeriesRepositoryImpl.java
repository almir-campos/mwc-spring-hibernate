/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.series;

import com.javasd.mwc.REPOSITORY.generic.GenericRepositoryImpl;
import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.SeriesDetail;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

/**
 *
 * @author almir
 */
@Repository("serieRepository")
public class SeriesRepositoryImpl extends GenericRepositoryImpl<Series> implements SeriesRepository
{
    public SeriesRepositoryImpl()
    {
        super( Series.class );
    }

    @Override
    public List<Series> getAllByStartDateDesc(MwcUser mwcUser)
    {
        return entityManager
                .createNamedQuery("Series.getAllByStartDateDesc")
                .setParameter("mwcUser", mwcUser)
                .getResultList();
    }

    @Override
    public Series getWithSeriesDetails( Long id )
    {
        // THIS SEQUENCE IS NECESSARY BECAUSE OF THE 'LAZY' ATTRIBUTE
        Series loadedSeries = get( id  );
        Set<SeriesDetail> seriesDetails = loadedSeries.getSeriesDetails();
        int sizeSeriesDetails = seriesDetails.size();
        
        return loadedSeries;
    }
    
}
