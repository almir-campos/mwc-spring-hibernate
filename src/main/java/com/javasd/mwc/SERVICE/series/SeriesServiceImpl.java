/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.series;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.REPOSITORY.series.SeriesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Almir Campos
 */
@Service ( "serieService" )
@Repository
@Transactional
public class SeriesServiceImpl
        implements SeriesService
{
    
    @Autowired
    private SeriesRepository serieRepository;

    @Override
    @Transactional ( readOnly = true )
    public Series get( Long id )
    {
        return serieRepository.get( id );
    }

    @Override
    public List<Series> getAllByStartDateDesc( MwcUser mwcUser )
    {
        return serieRepository.getAllByStartDateDesc( mwcUser );
    }

    @Override
    public Series update(Series series ) {
        return serieRepository.update( series );
    }

    @Override
    public Series getWithSeriesDetails( Long id )
    {
        return serieRepository.getWithSeriesDetails( id );
    }

    
}
