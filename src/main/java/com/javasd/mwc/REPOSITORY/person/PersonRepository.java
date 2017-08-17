/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.person;

import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.REPOSITORY.generic.GenericRepository;
import java.util.List;

/**
 *
 * @author Almir
 */
public interface PersonRepository
    extends GenericRepository<Person>
{
    public Person getByAccessCode( String accessCode );
    public Person getByLinkCode( String linkCode );
    public List<Person> getAllByLastName();
    public List<Person> getAllManagedByLastName();
    public Company getCompany( Long personId );
    
}
