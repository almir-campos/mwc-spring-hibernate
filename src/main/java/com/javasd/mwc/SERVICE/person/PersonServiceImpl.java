/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.person;

import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.REPOSITORY.person.PersonRepository;
import com.javasd.mwc.util.beans.LoggedUser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Almir
 */
@Service( "personService" )
@Repository
@Transactional
public class PersonServiceImpl
        implements PersonService
{

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LoggedUser loggedUser;

    @Override
    @Transactional( readOnly = true )
    public Person get( Long id )
    {
        return personRepository.get( id );
    }

    @Override
    @Transactional( readOnly = true )
    public Person getByAccessCode( String accessCode )
    {
        return personRepository.getByAccessCode( accessCode );
    }

    @Override
    public Person update( Person person )
    {
        // Add verification if the updated person is the logged user.
        // If 'yes', then the logged user must be updated

        return personRepository.update( person );
    }

    @Override
    @Transactional( readOnly = true )
    public Person getByLinkCode( String linkCode )
    {
        return personRepository.getByLinkCode( linkCode );
    }

    @Override
    @Transactional( readOnly = true )
    public List<Person> getAllByLastName()
    {
        return personRepository.getAllByLastName();
    }

    @Override
    @Transactional( readOnly = true )
    public List<Person> getAllManagedByLastName()
    {
        return personRepository.getAllManagedByLastName();
    }

    @Override
    public Person save( Person person )
    {
        Person manager = loggedUser.getMwcUser().getPerson();
        person.setManager( manager );
        Person savedPerson = personRepository.save( person );

//        manager.getPeopleContact().add( savedPerson );
//        Person updatedPerson = personRepository.update( manager );
        return savedPerson;
    }

    @Override
    public Person getWithContacts( Long id )
    {
        Person loadedPerson = personRepository.get( id );
        if ( loadedPerson != null )
        {
            loadedPerson.getPeopleContact().size();
        }
        return loadedPerson;
    }

    @Override
    public void delete( Long id )
    {
        personRepository.delete( id );
    }

    @Override
    public Company getCompany( Long personId )
    {
        return personRepository.getCompany( personId );
    }

}
