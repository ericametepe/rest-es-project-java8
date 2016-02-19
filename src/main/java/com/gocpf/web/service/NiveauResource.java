package com.gocpf.web.service;

import static org.elasticsearch.index.query.QueryBuilders.queryString;

import java.net.URI;
import java.net.URISyntaxException;
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

import com.gocpf.entities.Niveau;
import com.gocpf.repository.NiveauRepository;

@RestController
@RequestMapping(value="/service")
public class NiveauResource {
	    private final Logger log = LoggerFactory.getLogger(NiveauResource.class);

	    @Inject
	    private NiveauRepository  niveauRepository ;

	  

	    /**
	     * POST  /Niveaus -> Create a new Niveau.
	     */
	    @RequestMapping(value = "/niveaux",
	            method = RequestMethod.POST,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Void> create(@Valid @RequestBody Niveau niveau) throws URISyntaxException {
	        log.debug("REST request to save Niveau : {}", niveau);
	        if (niveau.getId() != null) {
	            return ResponseEntity.badRequest().header("Failure", "A new niveau cannot already have an ID").build();
	        }
	        niveauRepository.save(niveau);
	        return ResponseEntity.created(new URI("/api/niveaux/" + niveau.getId())).build();
	    }

	    /**
	     * PUT  /Niveaus -> Updates an existing Niveau.
	     */
	    @RequestMapping(value = "/niveaux",
	        method = RequestMethod.PUT,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Void> update(@Valid @RequestBody Niveau niveau) throws URISyntaxException {
	        log.debug("REST request to update Niveau : {}", niveau);
	        if (niveau.getId() == null) {
	            return create(niveau);
	        }
	        niveauRepository.save(niveau);
	        return ResponseEntity.ok().build();
	    }

	    /**
	     * GET  /Niveaus -> get all the Niveaus.
	     */
	    @RequestMapping(value = "/niveaux",
	            method = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Iterable<Niveau>> getAll()
	        throws URISyntaxException {
	    
	        return new ResponseEntity<Iterable<Niveau>>(niveauRepository.findAll(), HttpStatus.OK);
	    }

	    /**
	     * GET  /niveaux/:id -> get the "id" Niveau.
	     */
	    @RequestMapping(value = "/niveaux/{id}",
	            method = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Niveau> get(@PathVariable String id) {
	        log.debug("REST request to get Niveau : {}", id);
	        return Optional.ofNullable(niveauRepository.findOne(id))
	            .map(niveau -> new ResponseEntity<>(
	                niveau,
	                HttpStatus.OK))
	            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    /**
	     * DELETE  /niveaux/:id -> delete the "id" Niveau.
	     */
	    @RequestMapping(value = "/niveaux/{id}",
	            method = RequestMethod.DELETE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public void delete(@PathVariable String id) {
	        log.debug("REST request to delete Niveau : {}", id);
	        niveauRepository.delete(id);
	    }

	    /**
	     * SEARCH  /_search/niveaux/:query -> search for the niveau corresponding
	     * to the query.
	     */
	    @SuppressWarnings("deprecation")
		@RequestMapping(value = "/_search/niveaux/{query}",
	        method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Niveau> search(@PathVariable String query) {
	        return StreamSupport
	            .stream(niveauRepository.search(queryString(query)).spliterator(), false)
	            .collect(Collectors.toList());
	    }

}
