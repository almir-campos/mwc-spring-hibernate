/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.DOMAIN.entity.PersonInBranch;
import com.javasd.mwc.SERVICE.branch.BranchService;
import com.javasd.mwc.SERVICE.company.CompanyService;
import com.javasd.mwc.SERVICE.personinbranch.PersonInBranchService;
import com.javasd.mwc.util.beans.ObjectFiller;
import com.javasd.mwc.WEB.output.Branch01;
import com.javasd.mwc.WEB.output.Company01;
import com.javasd.mwc.WEB.output.PersonBasicData;
import com.javasd.mwc.annotations.MWC_LastScreen;
import com.javasd.mwc.annotations.MWC_Realm;
import com.javasd.mwc.annotations.MWC_Role;
import com.javasd.mwc.util.beans.MiscDynamic;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author almir
 */
@Controller
@RequestMapping( value = "/branch" )
public class BranchController
{

    @Autowired
    private CompanyService companyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private PersonInBranchService personInBranchService;
    @Autowired
    private MiscDynamic miscDynamic;
//    SOLUTION #1
//    @Autowired
//    private PersonInBranchService personInBranchService;
    @Autowired
    private ObjectFiller objectFiller;

    ////////////////////////////////////////////////////////////////////////////
    //
    // ADD
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Role( 
    {
        "ROLE_MANAGER"
    } )
    @MWC_Realm( "branch" )
    @MWC_LastScreen
    @RequestMapping( value = "/add/{companyId}", method = RequestMethod.GET )
    public String add(
            @PathVariable( "companyId" ) Long companyId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Company company = companyService.get( companyId );
        uiModel.addAttribute( "company", ( Company01 ) objectFiller.fillFields( company, Company01.class ) );
        uiModel.addAttribute( "branch", new Branch() );
        return "branch/add";
    }

    @RequestMapping( value = "/add/{companyId}", method = RequestMethod.POST )
    public String addBranch(
            @PathVariable( "companyId" ) Long companyId,
            @Valid @ModelAttribute( "branch" ) Branch branch,
            BindingResult result,
            Model uiModel )
    {
        if ( result.hasErrors() )
        {
            Company company = companyService.get( companyId );
            uiModel.addAttribute( "company", ( Company01 ) objectFiller.fillFields( company, Company01.class ) );
            uiModel.addAttribute( "errors", result.getAllErrors() );
            return "branch/add";
        }
        Company loadedCompany = companyService.get( companyId );
        branch.setCompany( loadedCompany );
        branchService.save( branch );
        return "redirect:/company/listbranches/" + companyId;
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // SHOW
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_LastScreen
    @RequestMapping( value = "/show/{branchId}", method = RequestMethod.GET )
    public String show(
            @PathVariable( "branchId" ) Long branchId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Branch branch = branchService.get( branchId );
        uiModel.addAttribute( "company", objectFiller.fillFields( branch.getCompany(), Company01.class ) );
        uiModel.addAttribute( "branch", objectFiller.fillFields( branch, Branch01.class ) );
        return "branch/show";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // EDIT
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Role( 
    {
        "ROLE_MANAGER"
    } )
    @MWC_Realm( "branch" )
    @MWC_LastScreen
    @RequestMapping( value = "/edit/{branchId}", method = RequestMethod.GET )
    public String edit(
            @PathVariable( "branchId" ) Long branchId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        // AUTOMATIZE THIS OPERATION IN MISCDYNAMIC
        Branch loadedBranch = branchService.get( branchId );
        Branch01 branch01 = ( Branch01 ) objectFiller.fillFields( loadedBranch, Branch01.class );
        branch01.setCompany( ( Company01 ) objectFiller.fillFields( loadedBranch.getCompany(), Company01.class ) );

        uiModel.addAttribute( "branch", branch01 );
        return "branch/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST )
    public String edit(
            @Valid Branch branch,
            BindingResult result,
            Model uiModel )
    {
        if ( result.hasErrors() )
        {
            // IT WAS NECESSARY TO SEND 'COMPANY' AGAIN BECAUSE (FOR SOME REASON) THE BRANCH
            // LOSS THE COMPANY DATA DURING THE VALIDATION PROCESS
            Branch loadedBranch = branchService.get( branch.getId() );
            uiModel.addAttribute( "currentId", branch.getId() );
            uiModel.addAttribute( "company", ( Company01 ) objectFiller.fillFields( loadedBranch.getCompany(), Company01.class ) );
            uiModel.addAttribute( "errors", result.getAllErrors() );
            return "branch/edit";
        }
        branchService.update( branch );
        return "redirect:/branch/show/" + branch.getId();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // DELETE
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Role( 
    {
        "ROLE_MANAGER"
    } )
    @MWC_Realm( "branch" )
    @RequestMapping( value = "/delete/{branchId}", method = RequestMethod.GET )
    public String delete(
            @PathVariable( "branchId" ) Long branchId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Branch branch = branchService.get( branchId );
        Company company = branch.getCompany();
        branchService.delete( branchId );
        return "redirect:/company/listbranches/" + company.getId();
    }

    @RequestMapping( value = "/removePerson/{branchId}/{personId}", method = RequestMethod.GET )
    public String removePerson(
            @PathVariable( "branchId" ) Long branchId,
            @PathVariable( "personId" ) Long personId,
            Model uiModel )
    {
        personInBranchService.deleteByBranchPerson( branchId, personId );
        return "redirect:/branch/listpeople/" + branchId;
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // LIST PEOPLE
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Role( 
    {
        "ROLE_MANAGER"
    } )
    @MWC_Realm( "branch" )
    @MWC_LastScreen
    @RequestMapping( value = "/listpeople/{branchId}", method = RequestMethod.GET )
    public String listpeople(
            @PathVariable( "branchId" ) Long branchId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
//      SOLUTION #0 - Change the field "personInBranch" form LAZY to EAGER - it's not a good solution

//      SOLUTION #1 - USES personInBranchService
//      Branch loadedBranch = branchService.get( branchId );
//      SOLUTION #2 - USES A TRICK (SEE BranchRepositoryImpl/getWithPeople )
        Branch loadedBranch = branchService.getWithPeople( branchId );
        List<PersonBasicData> people = new ArrayList<PersonBasicData>();

//      SOLUTION #1
//      for ( PersonInBranch pib : personInBranchService.getPeopleInBranch( loadedBranch ) )
//      SOLUTION #2
        for ( PersonInBranch pib : loadedBranch.getPersonInBranch() )
        {
//            people.add( ( PersonBasicData ) objectFiller.fillFields( pib.getPerson(), PersonBasicData.class ) );
            people.add( ( PersonBasicData ) miscDynamic.getPersonBasicData( pib.getPerson() ) );
        }
        // AUTOMATIZE THIS OPERATION IN MISCDYNAMIC
        Branch01 branch01 = ( Branch01 ) objectFiller.fillFields( loadedBranch, Branch01.class );
        branch01.setCompany( ( Company01 ) objectFiller.fillFields( loadedBranch.getCompany(), Company01.class ) );

        uiModel.addAttribute( "branch", branch01 );
        uiModel.addAttribute( "people", people );
        return "branch/listpeople";
    }
}
