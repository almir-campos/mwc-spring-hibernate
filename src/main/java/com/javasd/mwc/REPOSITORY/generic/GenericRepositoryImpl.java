/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.generic;

import com.javasd.mwc.DOMAIN.complements.DomainObject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author almir
 */
@SuppressWarnings( "unchecked" )
public class GenericRepositoryImpl<T extends DomainObject> implements GenericRepository<T>
{

    private final Logger logger = Logger.getLogger( GenericRepositoryImpl.class );
    private Class<T> type;
    protected EntityManager entityManager;

    public GenericRepositoryImpl( Class<T> type )
    {
        super();
        this.type = type;
    }

    @PersistenceContext
    public void setEntityManager( EntityManager entityManager )
    {
        this.entityManager = entityManager;
    }

    @Override
    public T get( Long id )
    {
        return ( T ) entityManager.find( type, id );
    }

    @Override
    public List<T> getAll()
    {
        return entityManager.createQuery( "select obj from " + type.getName() + " obj" ).getResultList();
    }

    @Override
    public T save( T object )
    {
        logger.info( "***** object: " + object );
        logger.info( "***** type: " + type );
        logger.info( "***** class: " + type.getName() );
        logger.info( "***** object (before save): " + object );
        try
        {
            entityManager.persist( object );
            entityManager.flush();
            logger.info( "***** object (after save): " + object );
        }
        catch ( Exception e )
        {
            logger.info( "***** EXCEPTION " + e.getLocalizedMessage() );
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public T update( T object )
    {
        return entityManager.merge( object );
    }

    @Override
    public void delete( Long id )
    {
        logger.info( "***** class: " + type.getName() );
        logger.info( "***** delete: " + id );
        Object obj = get( id );
        logger.info( "***** obj: " + obj );
        try
        {
            entityManager.remove( get( id ) );
            Object obj1 = get( id );
            logger.info( "***** obj1: " + obj1 );
        }
        catch ( Exception e )
        {
            logger.info( "***** EXCEPTION " + e.getLocalizedMessage() );
            e.printStackTrace();
        }
    }
}
