
package com.gocpf.web.service;

import java.net.URI;

import java.net.URISyntaxException;
import java.util.Optional;

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

import com.gocpf.entities.Auditeur;
import com.gocpf.repository.AuditeurRepository;

/*
 * https://ghiden.github.io/angucomplete-alt/#example1
 */

@RestController
@RequestMapping(value = "/service")
public class AuditeurResource {

	private final Logger log = LoggerFactory.getLogger(AuditeurResource.class);

	@Inject
	private AuditeurRepository auditeurRepository;

	/**
	 * POST /auditeurs -> Create a new auditeur.
	 */
	@RequestMapping(value = "/auditeurs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@Valid @RequestBody Auditeur auditeur) throws URISyntaxException {
		log.debug("REST request to save auditeur : {}", auditeur);
		if (auditeur.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new auditeur cannot already have an ID").build();
		}
		Auditeur auditeurS = auditeurRepository.save(auditeur);
		return ResponseEntity.created(new URI("/service/auditeurs/" + auditeurS.getId())).build();
	}

	/**
	 * GET /auditeurs/:id -> get the "id" auditeur.
	 */
	@RequestMapping(value = "/auditeurs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Auditeur> get(@PathVariable String id) {
		log.debug("REST request to get Auditeur : {}", id);

		return Optional.ofNullable(auditeurRepository.findOne(id))
				.map(auditeur -> new ResponseEntity<Auditeur>(auditeur, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@RequestMapping(value = "/auditeurs", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Auditeur>> getAll() {
		return new ResponseEntity<Iterable<Auditeur>>(auditeurRepository.findAll(), HttpStatus.OK);
	}

	/**
	 * PUT /auditeurs -> Updates an existing auditeur.
	 */
	@RequestMapping(value = "/auditeurs", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@Valid @RequestBody Auditeur auditeur) throws URISyntaxException {
		log.debug("REST request to update auditeur : {}", auditeur);
		if (auditeur.getId() == null) {
			return create(auditeur);
		}
		auditeurRepository.save(auditeur);
		return ResponseEntity.ok().build();
	}
	
	
    /**
     * DELETE  /auditeurs/:id -> delete the "id" auditeur.
     */
    @RequestMapping(value = "/auditeurs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete auditeur : {}", id);
        
        auditeurRepository.delete(id);
    }

}
