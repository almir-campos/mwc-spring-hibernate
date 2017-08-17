/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.mwcuserrole;

import com.javasd.mwc.DOMAIN.entity.MwcUserRole;
import com.javasd.mwc.REPOSITORY.generic.GenericRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 *
 * @author almir
 */
@Repository( "mwcUserRoleRepository" )
public class MwcUserRoleRepositoryImpl
    extends GenericRepositoryImpl<MwcUserRole>
    implements MwcUserRoleRepository
{

    public MwcUserRoleRepositoryImpl()
    {
        super( MwcUserRole.class );
    }

}
