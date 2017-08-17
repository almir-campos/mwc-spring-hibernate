/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.mwcuser;

import com.google.common.collect.Lists;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.MwcUserRole;
import com.javasd.mwc.REPOSITORY.mwcuser.UserRepository;
import com.javasd.mwc.util.beans.LoggedUser;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Almir Campos
 */
@Service( "userService" )
@Repository
@Transactional
public class UserServiceImpl
        implements UserService
{

    private final Logger logger = Logger.getLogger( UserServiceImpl.class );
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional( readOnly = true )
    public List<MwcUser> getAll()
    {
        return Lists.newArrayList( userRepository.getAll() );
    }

    @Override
    @Transactional( readOnly = true )
    public List<MwcUser> getAllByLastName()
    {
        return userRepository.getAllByLastName();
    }

    @Override
    @Transactional( readOnly = true )
    public MwcUser get( Long id )
    {
        MwcUser loadedUser;
        if ( loggedUser.getMwcUser().getId() == id )
        {
            loadedUser = loggedUser.getMwcUser();
        }
        else
        {
            loadedUser = userRepository.get( id );
        }
        return loadedUser;
    }

    @Override
    public MwcUser save( MwcUser mwcUser )
    {
        return userRepository.save( mwcUser );
    }

    @Override
    public MwcUser update( MwcUser mwcUser )
    {
//        if ( loggedUser.getMwcUser().getId() == mwcUser.getId() )
//        {
//            loggedUser.setToBeUpdated( true );
//        }
        return userRepository.update( mwcUser );
    }

    @Override
    public void delete( Long id )
    {
        userRepository.delete( id );
    }

    @Override
    public MwcUser getWithSeries( Long id )
    {
        MwcUser loadedUser = userRepository.get( id );
        loadedUser.getSeriess().size();
        return loadedUser;
    }

    @Override
    public MwcUser getByUsername( String username )
    {
        return userRepository.getByUsername( username );
    }
    
}
