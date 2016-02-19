package com.gocpf.web.service;

import static org.elasticsearch.index.query.QueryBuilders.queryString;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gocpf.entities.Organisme;
import com.gocpf.entities.Session;
import com.gocpf.repository.OrganismeRepository;

@RestController
@RequestMapping(value="/service")
public class OrganismeResource {
	
	
	 private final Logger log = LoggerFactory.getLogger(OrganismeResource.class);

	    @Inject
	    private OrganismeRepository  organismeRepository ;
	    
	 
	    
	    
	    @RequestMapping(value = "/organismes/init",
	            method = RequestMethod.POST,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Void> init() throws URISyntaxException {
	    	
	    	List<Session> sessions= new ArrayList<Session>();
	    	Session session = new Session();
	    	session.setDebutDate(new Date());
	    	session.setFinDate(new Date());
	    	sessions.add(session);
	    	
			Organisme organisme = new Organisme("name", null, "telephoneFixe", "telephoneMobile", "fax", "url", "raisonSocial", sessions);
			
			Organisme organisme2 =organismeRepository.save(organisme);
	    	
	        return ResponseEntity.created(new URI("/organismes/" + organisme2.getId())).build();
	    	
	    }
	    
	    
	    

	    /**
	     * POST  /organismes -> Create a new organisme.
	     */
	    @RequestMapping(value = "/organismes",
	            method = RequestMethod.POST,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Void> create(@Valid @RequestBody Organisme organisme) throws URISyntaxException {
	        log.debug("REST request to save Niveau : {}", organisme);
	        if (organisme.getId() != null) {
	            return ResponseEntity.badRequest().header("Failure", "A new organisme cannot already have an ID").build();
	        }
	        organismeRepository.save(organisme);
	        return ResponseEntity.created(new URI("/organismes/" + organisme.getId())).build();
	    }

	    /**
	     * PUT  /organismes -> Updates an existing organisme.
	     */
	    @RequestMapping(value = "/organismes",
	        method = RequestMethod.PUT,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Void> update(@Valid @RequestBody Organisme organisme) throws URISyntaxException {
	        log.debug("REST request to update Organisme : {}", organisme);
	        if (organisme.getId() == null) {
	            return create(organisme);
	        }
	        organismeRepository.save(organisme);
	        return ResponseEntity.ok().build();
	    }

	    /**
	     * GET  /organismes -> get all the organismes.
	     */
	    @RequestMapping(value = "/organismes",
	            method = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Iterable<Organisme>> getAll()
	        throws URISyntaxException {
	    
	        return new ResponseEntity<Iterable<Organisme>>(organismeRepository.findAll(), HttpStatus.OK);
	    }

	    /**
	     * GET  /organismes/:id -> get the "id" organisme.
	     */
	    @RequestMapping(value = "/organismes/{id}",
	            method = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Organisme> get(@PathVariable String id) {
	        log.debug("REST request to get Niveau : {}", id);
	        return Optional.ofNullable(organismeRepository.findOne(id))
	            .map(organisme -> new ResponseEntity<>(
	            		organisme,
	                HttpStatus.OK))
	            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    /**
	     * DELETE  /organismes/:id -> delete the "id" organisme.
	     */
	    @RequestMapping(value = "/organismes/{id}",
	            method = RequestMethod.DELETE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public void delete(@PathVariable String id) {
	        log.debug("REST request to delete organisme : {}", id);
	        organismeRepository.delete(id);
	    }
	    
	    
	    
	    
	    /**
	     * DELETE  /organismes/ -> delete all organisme.
	     */
	    @RequestMapping(value = "/organismes",
	            method = RequestMethod.DELETE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public void deleteAll() {
	        log.debug("REST request to delete all organisme : {}");
	        organismeRepository.deleteAll();
	    }

	    /**
	     * SEARCH  /_search/organismes/:query -> search for the niveau corresponding
	     * to the query.
	     */
	    @SuppressWarnings("deprecation")
		@RequestMapping(value = "/_search/organismes/{query}",
	        method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Organisme> search(@PathVariable String query) {
	        return StreamSupport
	            .stream(organismeRepository.search(queryString(query)).spliterator(), false)
	            .collect(Collectors.toList());
	    }

}
