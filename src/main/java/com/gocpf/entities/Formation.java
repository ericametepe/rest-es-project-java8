package com.gocpf.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import com.gocpf.web.service.Rating;

/**
 * Created by kodjovi1 on 22/11/2015.
 */
@Document(indexName = "gocpf",type="formation",shards = 1, replicas = 0, refreshInterval = "-1")
@Mapping(mappingPath="classpath:formation-mapping.json")
public class Formation {
    @Id
	private String id;
    private String niveau;
    private Intitule intitule;
/**
 * http://www.rncp.cncp.gouv.fr/grand-public/visualisationFiche?format=fr&fiche=20601
 * codeRNCP : 20601
 */
    private String codeRNCP;
    private String codeInventaire;
    /**
     * http://www.intercariforef.org/formations/certification-69891.html
     *  codeCertifInfo : 69891
     */
    private String codeCertifInfo;
    private String codeOffreInfo;
    private String codeCPF;
    private Date debutdevalidite;
    private Date findevalidite;
    private String organismecertificateur;
    private String organismeEditeur;
    private String publicAuditeur;
    private String couvertureGeo;
    //Code NSF (Nomenclature des Spécialités de Formation)
    private String codeNSF;
    //Code ROME (Répertoire Opérationnel des Métiers et Emplois)
    private String codeRome;
    //Formacode (Domaine de formation)
    private String formacode;
    
    private List<String> codeAPEs= new ArrayList<String>();
    private List<String> organismes = new ArrayList<String>();
    private List<Rating> ratings = new ArrayList<Rating>();
    
    public Formation() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}



	public String getCodeRNCP() {
		return codeRNCP;
	}

	public void setCodeRNCP(String codeRNCP) {
		this.codeRNCP = codeRNCP;
	}

	public String getCodeInventaire() {
		return codeInventaire;
	}

	public void setCodeInventaire(String codeInventaire) {
		this.codeInventaire = codeInventaire;
	}

	public String getCodeCertifInfo() {
		return codeCertifInfo;
	}

	public void setCodeCertifInfo(String codeCertifInfo) {
		this.codeCertifInfo = codeCertifInfo;
	}

	public String getCodeOffreInfo() {
		return codeOffreInfo;
	}

	public void setCodeOffreInfo(String codeOffreInfo) {
		this.codeOffreInfo = codeOffreInfo;
	}

	public String getCodeCPF() {
		return codeCPF;
	}

	public void setCodeCPF(String codeCPF) {
		this.codeCPF = codeCPF;
	}

	public Date getDebutdevalidite() {
		return debutdevalidite;
	}

	public void setDebutdevalidite(Date debutdevalidite) {
		this.debutdevalidite = debutdevalidite;
	}

	public Date getFindevalidite() {
		return findevalidite;
	}

	public void setFindevalidite(Date findevalidite) {
		this.findevalidite = findevalidite;
	}

	public String getOrganismecertificateur() {
		return organismecertificateur;
	}

	public void setOrganismecertificateur(String organismecertificateur) {
		this.organismecertificateur = organismecertificateur;
	}

	public String getOrganismeEditeur() {
		return organismeEditeur;
	}

	public void setOrganismeEditeur(String organismeEditeur) {
		this.organismeEditeur = organismeEditeur;
	}

	public String getPublicAuditeur() {
		return publicAuditeur;
	}

	public void setPublicAuditeur(String publicAuditeur) {
		this.publicAuditeur = publicAuditeur;
	}

	public String getCouvertureGeo() {
		return couvertureGeo;
	}

	public void setCouvertureGeo(String couvertureGeo) {
		this.couvertureGeo = couvertureGeo;
	}

	public List<String> getOrganismes() {
		return organismes;
	}

	public void setOrganismes(List<String> organismes) {
		this.organismes = organismes;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}




	
	
	public Formation(String id, String niveau, Intitule intitule, String intituleSansAccent, String codeRNCP,
			String codeInventaire, String codeCertifInfo, String codeOffreInfo, String codeCPF, Date debutdevalidite,
			Date findevalidite, String organismecertificateur, String organismeEditeur, String publicAuditeur,
			String couvertureGeo, String codeNSF, String codeRome, String formacode, List<String> codeAPEs,
			List<String> organismes, List<Rating> ratings) {
		super();
		this.id = id;
		this.niveau = niveau;
		this.intitule = intitule;
		this.codeRNCP = codeRNCP;
		this.codeInventaire = codeInventaire;
		this.codeCertifInfo = codeCertifInfo;
		this.codeOffreInfo = codeOffreInfo;
		this.codeCPF = codeCPF;
		this.debutdevalidite = debutdevalidite;
		this.findevalidite = findevalidite;
		this.organismecertificateur = organismecertificateur;
		this.organismeEditeur = organismeEditeur;
		this.publicAuditeur = publicAuditeur;
		this.couvertureGeo = couvertureGeo;
		this.codeNSF = codeNSF;
		this.codeRome = codeRome;
		this.formacode = formacode;
		this.codeAPEs = codeAPEs;
		this.organismes = organismes;
		this.ratings = ratings;
	}

	public void addCodeAPE(String codeAPE) {
		this.codeAPEs.add(codeAPE);
	}



	public List<String> getCodeAPEs() {
		return codeAPEs;
	}

	public void setCodeAPEs(List<String> codeAPEs) {
		this.codeAPEs = codeAPEs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeCPF == null) ? 0 : codeCPF.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Formation other = (Formation) obj;
		if (codeCPF == null) {
			if (other.codeCPF != null)
				return false;
		} else if (!codeCPF.equals(other.codeCPF))
			return false;
		return true;
	}

	public String getCodeNSF() {
		return codeNSF;
	}

	public void setCodeNSF(String codeNSF) {
		this.codeNSF = codeNSF;
	}

	public String getCodeRome() {
		return codeRome;
	}

	public void setCodeRome(String codeRome) {
		this.codeRome = codeRome;
	}

	public String getFormacode() {
		return formacode;
	}

	public void setFormacode(String formacode) {
		this.formacode = formacode;
	}



	@Override
	public String toString() {
		return "Formation [id=" + id + ", niveau=" + niveau + ", intitule=" + intitule  + ", codeRNCP=" + codeRNCP + ", codeInventaire=" + codeInventaire
				+ ", codeCertifInfo=" + codeCertifInfo + ", codeOffreInfo=" + codeOffreInfo + ", codeCPF=" + codeCPF
				+ ", debutdevalidite=" + debutdevalidite + ", findevalidite=" + findevalidite
				+ ", organismecertificateur=" + organismecertificateur + ", organismeEditeur=" + organismeEditeur
				+ ", publicAuditeur=" + publicAuditeur + ", couvertureGeo=" + couvertureGeo + ", codeNSF=" + codeNSF
				+ ", codeRome=" + codeRome + ", formacode=" + formacode + ", codeAPEs=" + codeAPEs + ", organismes="
				+ organismes + ", ratings=" + ratings + "]";
	}

	public Intitule getIntitule() {
		return intitule;
	}

	public void setIntitule(Intitule intitule) {
		this.intitule = intitule;
	}
    
    
	
	



   
    
    
	}
