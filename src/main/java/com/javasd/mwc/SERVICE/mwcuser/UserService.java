/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.mwcuser;

import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.MwcUserRole;
import java.util.List;

/**
 *
 * @author almir
 */
public interface UserService
{

    public List<MwcUser> getAll();

    public List<MwcUser> getAllByLastName();

    public MwcUser get( Long id );

    public MwcUser getWithSeries( Long id );

    public MwcUser save( MwcUser mwcUser );

    public MwcUser update( MwcUser mwcUser );

    public void delete( Long id );

    public MwcUser getByUsername( String username );
}
