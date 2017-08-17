/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.seriesdetail;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.SeriesDetail;
import com.javasd.mwc.REPOSITORY.seriesdetail.SeriesDetailRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Almir Campos
 */
@Service ( "seriesDetailService" )
@Repository
@Transactional
public class SeriesDetailServiceImpl
        implements SeriesDetailService
{
    @Autowired
    private SeriesDetailRepository seriesDetailRepository;

    @Override
    @Transactional ( readOnly = true )
    public SeriesDetail get( Long id )
    {
        return seriesDetailRepository.get( id );
    }

    @Override
    @Transactional ( readOnly = true )
    public List<SeriesDetail> getAllByWeightDate( Series series )
    {
        return seriesDetailRepository.getAllByWeightDate( series );
    }    

    @Override
    @Transactional ( readOnly = true )
    public List<SeriesDetail> getAllByWeightDateAsc( Series series )
    {
        return seriesDetailRepository.getAllByWeightDateAsc( series );
    }    

    @Override
    public SeriesDetail update(SeriesDetail seriesDetail) {
        return seriesDetailRepository.update(seriesDetail);
    }

    @Override
    public void delete( Long id )
    {
        seriesDetailRepository.delete( id );
    }
    
}
