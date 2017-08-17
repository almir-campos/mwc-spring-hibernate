/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.mwcuser;

import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.REPOSITORY.generic.GenericRepositoryImpl;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author almir
 */
@Repository( "userRepository" )
public class UserRepositoryImpl 
    extends GenericRepositoryImpl<MwcUser>
    implements UserRepository
{

    public UserRepositoryImpl()
    {
        super( MwcUser.class );
    }

    @Override
    public List<MwcUser> getAllByLastName()
    {
        return entityManager.createNamedQuery( "MwcUser.getAllByLastName" ).getResultList();

    }

    @Override
    public MwcUser getByUsername( String username )
    {
//        Query query = entityManager.createQuery( "SELECT u FROM MwcUser u WHERE u.username = :username" ).setParameter( "username", username );
        Query query = entityManager.createNamedQuery( "MwcUser.getByUsername" ).setParameter( "username", username );
        try
        {
            List<Object> objects = query.getResultList();
            MwcUser mwcUser = (MwcUser) objects.get( 0 );
            return mwcUser;
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return null;
        }
        
    }

}
