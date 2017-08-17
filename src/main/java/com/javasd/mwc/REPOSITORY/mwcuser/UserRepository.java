/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.mwcuser;

import com.javasd.mwc.REPOSITORY.generic.GenericRepository;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.Person;
import java.util.List;

/**
 *
 * @author Almir Campos
 */
public interface UserRepository
	extends GenericRepository<MwcUser>
{
    public List<MwcUser> getAllByLastName();
    public MwcUser getByUsername( String username );
}
