package com.restaurant.management.web.controller;

import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.PersonnelFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/personnel")
public class PersonnelController {

    private PersonnelFacade personnelFacade;

    @Autowired
    public PersonnelController(PersonnelFacade personnelFacade) {
        this.personnelFacade = personnelFacade;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<PersonnelFormDTO> registerPerson(@CurrentUser UserPrincipal currentUser,
                                              @Valid @RequestBody PersonnelFormDTO request) {
        PersonnelFormDTO personnelFormDTO = personnelFacade.registerPerson(currentUser, request);

        Link link = linkTo(PersonnelController.class).slash(personnelFormDTO.getId()).withSelfRel();

        return new Resource<>(personnelFormDTO, link);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<PersonnelFormDTO> updatePerson(@CurrentUser UserPrincipal currentUser,
                                            @Valid @RequestBody PersonnelFormDTO request) {

        PersonnelFormDTO personnelFormDTO = personnelFacade.updatePerson(currentUser, request);

        Link link = linkTo(PersonnelController.class).slash(personnelFormDTO.getId()).withSelfRel();

        return new Resource<>(personnelFormDTO, link);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<PersonnelFormDTO> getPerson(@CurrentUser UserPrincipal currentUser, @PathVariable Long id) {
        PersonnelFormDTO personnelFormDTO = personnelFacade.getPersonById(currentUser, id);

        Link link = linkTo(PersonnelController.class).slash(personnelFormDTO.getId()).withSelfRel();

        return new Resource<>(personnelFormDTO, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<PersonnelFormDTO>> getAllPersonnelPageable(@CurrentUser UserPrincipal currentUser,
                                                                             Pageable pageable,
                                                                             PagedResourcesAssembler assembler) {
        Page<PersonnelFormDTO> personnelFormDTO = personnelFacade.getAllPersonnel(currentUser, pageable);

        if (!personnelFormDTO.hasContent()) {
            PagedResources pagedResources = assembler.toEmptyResource(personnelFormDTO, PersonnelFormDTO.class);
            return new ResponseEntity<PagedResources<PersonnelFormDTO>>(pagedResources, HttpStatus.OK);
        }

        return new ResponseEntity<>(assembler.toResource(personnelFormDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{personId}")
    public ResponseEntity<?> deletePersonById(@CurrentUser UserPrincipal currentUser,
                                              @PathVariable Long personId) {
        return ResponseEntity.ok().body(personnelFacade.deletePersonById(currentUser, personId));
    }

    @DeleteMapping(value = "/delete/{personnelIds}")
    public ResponseEntity<?> deleteAllById(@CurrentUser UserPrincipal currentUser,
                                           @PathVariable Long[] personnelIds) {
        return ResponseEntity.ok().body(personnelFacade.deleteAllByIds(currentUser, personnelIds));
    }
}
