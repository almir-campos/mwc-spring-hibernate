/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.mwcuserrrole;

import com.javasd.mwc.DOMAIN.entity.MwcUserRole;
import java.util.List;

/**
 *
 * @author almir
 */
public interface MwcUserRoleService
{
    public MwcUserRole get( Long Id );
    public List<MwcUserRole> getAll();
    public MwcUserRole save( MwcUserRole mwcUserRole );
    public MwcUserRole update( MwcUserRole mwcUserRole );
    public void delete( Long Id );
}
