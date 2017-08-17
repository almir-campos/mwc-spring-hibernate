/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.person;

import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.DOMAIN.entity.Person;
import java.util.List;

/**
 *
 * @author Almir
 */
public interface PersonService
{
    public Person get( Long id );
    public Person getByAccessCode( String accessCode );
    public Person getWithContacts( Long id );
    public Person update( Person person );
    public Person getByLinkCode( String linkCode );
    public List<Person> getAllManagedByLastName();
    public List<Person> getAllByLastName();
    public Person save( Person person );
    public void delete( Long id );
    public Company getCompany( Long personId );
}
