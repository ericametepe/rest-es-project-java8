package com.gocpf.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.gocpf.entities.Formation;
import com.gocpf.repository.AuditeurRepository;
import com.gocpf.repository.CouvertureGeoRepository;
import com.gocpf.repository.FormationRepository;
import com.gocpf.web.service.CouvertureGeoServiceImpl;

@Component
public class FormationConverter {
	@Inject
	private FormationRepository formationRepository;
	@Inject
	private CouvertureGeoServiceImpl couvertureGeoServiceImpl ;
	@Inject
	private CouvertureGeoRepository couvertureGeoRepository;
	@Inject
	private AuditeurRepository auditeurRepository;
	
	public FormationDto toDto(Formation formation) {
		
		if(formation==null){
			return null;
		}
		FormationDto dto = new FormationDto();
		dto.setId(formation.getId());
		dto.setDebutdevalidite(formation.getDebutdevalidite());
		dto.setFindevalidite(formation.getFindevalidite());
		dto.setOrganismeEditeur(formation.getOrganismeEditeur());
		dto.setCodeAPEs(formation.getCodeAPEs());
		dto.setOrganismes(formation.getOrganismes());
		dto.setCodeCertifInfo(formation.getCodeCertifInfo());
		dto.setCodeCPF(formation.getCodeCPF());
		dto.setCodeRNCP(formation.getCodeRNCP());
		Optional<String> couvertureGeo=couvertureGeoServiceImpl.getNom(formation.getCouvertureGeo());
		dto.setCouvertureGeo(couvertureGeo.orElse(null));
		String aud = auditeurRepository.findOne(formation.getPublicAuditeur()).getNom();
		if(aud!=null){
			dto.setPublicAuditeur(aud);
		}
//		dto.setCouvertureGeo(;
		dto.setIntitule(formation.getIntitule());
		dto.setNiveau(formation.getNiveau());
		dto.setRatings(formation.getRatings());
		
		return dto;
		
	}

	public Set<FormationDto> toDtos(Set<Formation> inputs) {
		Set<FormationDto> dtos = new HashSet<FormationDto>();
		inputs.forEach(in -> {dtos.add(toDto(in));});
		return dtos;
	}

}
