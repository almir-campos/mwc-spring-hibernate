/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.seriesdetail;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.SeriesDetail;
import com.javasd.mwc.REPOSITORY.generic.GenericRepositoryImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author almir
 */
@Repository("seriesDetailRepository")
public class SeriesDetailRepositoryImpl extends GenericRepositoryImpl<SeriesDetail> implements SeriesDetailRepository
{
    
    public SeriesDetailRepositoryImpl()
    {
        super( SeriesDetail.class );
    }

//    @Override
//    public SeriesDetail get(Long id)
//    {
//        return get(id);
//    }

    @Override
    public List<SeriesDetail> getAllByWeightDate( Series series )
    {
        return entityManager
                .createNamedQuery("SeriesDetail.getAllByWeightDate")
                .setParameter("series", series)
                .getResultList();
    }

    @Override
    public List<SeriesDetail> getAllByWeightDateAsc( Series series )
    {
        return entityManager
                .createNamedQuery("SeriesDetail.getAllByWeightDateAsc")
                .setParameter("series", series)
                .getResultList();
    }
}
