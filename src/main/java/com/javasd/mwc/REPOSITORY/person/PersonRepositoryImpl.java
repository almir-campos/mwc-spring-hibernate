/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.person;

import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.PersonInBranch;
import com.javasd.mwc.REPOSITORY.generic.GenericRepositoryImpl;
import com.javasd.mwc.util.beans.LoggedUser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Almir
 */
@Repository( "personRepository" )
public class PersonRepositoryImpl
        extends GenericRepositoryImpl<Person>
        implements PersonRepository
{

    @Autowired
    private LoggedUser loggedUser;

    public PersonRepositoryImpl()
    {
        super( Person.class );
    }

    @Override
    public Person getByAccessCode( String accessCode )
    {
        Person person = null;
        List<Person> people;
        try
        {
            people = ( List<Person> ) entityManager.
                    createNamedQuery( "Person.getByAccessCode" ).
                    setParameter( "accessCode", accessCode ).
                    getResultList();
            if ( people.size() == 0 )
            {
                return null;
            }
            person = people.get( 0 );
        }
        catch ( Exception e )
        {
            return null;
        }
        return person;
    }

    @Override
    public Person getByLinkCode( String linkCode )
    {
        Person person = null;
        List<Person> people;
        try
        {
            people = ( List<Person> ) entityManager.
                    createNamedQuery( "Person.getByLinkCode" ).
                    setParameter( "linkCode", linkCode ).
                    getResultList();
            if ( people.size() == 0 )
            {
                return null;
            }
            person = people.get( 0 );
        }
        catch ( Exception e )
        {
            return null;
        }
        return person;
    }

    @Override
    public List<Person> getAllManagedByLastName()
    {
        List<Person> people = null;
        String query;
        try
        {
            query = "Person.getAllManagedByLastName";
            people = ( List<Person> ) entityManager.
                    createNamedQuery( query ).
                    setParameter( "manager", loggedUser.getMwcUser().getPerson() ).
                    getResultList();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return people;
        }
        return people;
    }

    @Override
    public List<Person> getAllByLastName()
    {
        List<Person> people = null;
        String query;
        try
        {
            query = "Person.getAllByLastName";
            people = ( List<Person> ) entityManager.
                    createNamedQuery( query ).
                    getResultList();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return people;
        }
        return people;
    }

//    @Override
//    public Person getPersonWithContacts( Long id )
//    {
//        Person person = get( id );
//        List<Person> contacts = person.getPeopleContact();
//        int contactsSize = contacts.size();
//        
//        return person;
//    }

    @Override
    public Company getCompany( Long personId )
    {
        Person loadedPerson = get( personId );
        PersonInBranch personInBranch = loadedPerson.getPersonInBranch();
        if ( personInBranch != null )
        {
            return personInBranch.getBranch().getCompany();
        }
        return null;
    }

}
