/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.mwcuserrrole;

import com.javasd.mwc.DOMAIN.entity.MwcUserRole;
import com.javasd.mwc.REPOSITORY.mwcuserrole.MwcUserRoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author almir
 */
@Service( "mwcUserRoleService" )
@Repository
@Transactional
public class MwcUserRoleServiceImpl
    implements MwcUserRoleService
{

    @Autowired
    private MwcUserRoleRepository mwcUserRoleRepository;
    
    @Override
    @Transactional( readOnly = true )
    public MwcUserRole get( Long Id )
    {
        return mwcUserRoleRepository.get( Id );
    }

    @Override
    public MwcUserRole save( MwcUserRole mwcUserRole )
    {
        return mwcUserRoleRepository.save( mwcUserRole );
    }

    @Override
    public MwcUserRole update( MwcUserRole mwcUserRole )
    {
        return mwcUserRoleRepository.update( mwcUserRole );
    }

    @Override
    public void delete( Long Id )
    {
        mwcUserRoleRepository.delete( Id );
    }

    @Override
    @Transactional( readOnly = true )
    public List<MwcUserRole> getAll()
    {
        return (List<MwcUserRole> ) mwcUserRoleRepository.getAll();
    }
    
}
