package com.gocpf.web.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocpf.entities.CouvertureGeo;
import com.gocpf.repository.CouvertureGeoRepository;
import com.google.common.collect.Lists;

@RestController
@RequestMapping(value = "/service")
public class CouvertureGeoResource {
	@Inject
	private CouvertureGeoRepository couvertureGeoRepository;
	
	private static final Logger log=LoggerFactory.getLogger(CouvertureGeoRepository.class) ;
	
	
	@RequestMapping(value="/couvgeos/search",method=RequestMethod.GET)
	public ResponseEntity<CouvertureGeo> findByName(@RequestParam  String name){
		
		return new ResponseEntity<CouvertureGeo>(couvertureGeoRepository.findByNom(name),HttpStatus.OK);
		
	}
	
	
	
	 /**
     * PUT  /couvertureGeo -> Updates an existing CouvertureGeo.
     */
    @RequestMapping(value = "/couvgeos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Void> update(@Valid @RequestBody CouvertureGeo couvertureGeo) throws URISyntaxException {
        log.debug("REST request to update couvertureGeo : {}", couvertureGeo);
        if (couvertureGeo.getId() == null) {
            return create(couvertureGeo);
        }
        couvertureGeoRepository.save(couvertureGeo);
        return ResponseEntity.ok().build();
    }

	
	
	@RequestMapping(value="/couvgeos/{id}",method=RequestMethod.GET)
	public ResponseEntity<CouvertureGeo> findById(@PathVariable  int id){
		return new ResponseEntity<CouvertureGeo>(couvertureGeoRepository.findOne(String.valueOf(id)),HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/couvgeos",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<CouvertureGeo>> getAll(){
		return new ResponseEntity<Iterable<CouvertureGeo>>(couvertureGeoRepository.findAll(),HttpStatus.OK);
		
	}

	@RequestMapping(value = "/couvgeos", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody CouvertureGeo couvertureGeo) throws URISyntaxException {
		CouvertureGeo geo = couvertureGeoRepository.save(couvertureGeo);
		return ResponseEntity.created(new URI("/couvgeos/" + geo.getId())).build();
	}

	@RequestMapping(value = "/couvgeos/bulk", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> initCouvertureGeo() throws URISyntaxException {

		List<CouvertureGeo> couvertureGeos = new ArrayList<CouvertureGeo>();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			couvertureGeos = objectMapper.readValue(
					new File(
							"/Users/kodjovi1/Documents/workspace/gocpf-backend/src/main/resources/regions.json"),
					new TypeReference<List<CouvertureGeo>>() {
					});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    Iterable<CouvertureGeo> geos= couvertureGeoRepository.save(couvertureGeos);
	    
		return ResponseEntity.created(new URI("/couvgeo/" + Lists.newArrayList(geos).size())).build();
	}

}
