

package com.gocpf.web.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.collect.Sets;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocpf.entities.Addresse;
import com.gocpf.entities.Auditeur;
import com.gocpf.entities.CouvertureGeo;
import com.gocpf.entities.Formation;
import com.gocpf.entities.Intitule;
import com.gocpf.entities.Niveau;
import com.gocpf.entities.Organisme;
import com.gocpf.entities.Session;
import com.gocpf.repository.AuditeurRepository;
import com.gocpf.repository.CouvertureGeoRepository;
import com.gocpf.repository.FormationRepository;
import com.gocpf.repository.NiveauRepository;
import com.gocpf.repository.OrganismeRepository;
import com.gocpf.service.BrancheAPE;
import com.gocpf.service.DataConversionService;
import com.gocpf.service.FormationConverter;
import com.gocpf.service.FormationDto;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/service")
public class FormationResource {

	public static final String FILE_AUDITEURS_JSON = "auditeurs.json";

	public static final String CODE_APE_SEPARATOR = ".";

	private static final String SPECIAL_CHARACTER = "[^\\p{L}\\p{Nd}]+";

	private static final String FILE_REGION_JSON = "regions.json";

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Inject
	private FormationRepository formationRepository;

	@Inject
	private CouvertureGeoRepository couvertureGeoRepository;

	@Inject
	private AuditeurRepository auditeurRepository;

	@Inject
	private NiveauRepository niveauRepository;

	@Inject
	private OrganismeRepository organismeRepository;

	@Inject
	private DataConversionService dataConversionService;

	@Inject
	private ResourceLoader resourceLoader;

	private Character delim = ';';

	private static final String[] CODEAPE_BRANCHE_HEADER = { "codeAPE", "intitule", "branche" };

	private static final String FILE_CODEAPE_CSV = "CPNE-liste-codeAPE.csv";

	private static final String FILE_FORMATION_CSV = "/Users/kodjovi1/Desktop/CPF/all/csv/liste-cpf-2016.csv";

	// private static final String FILE_FORMATION_CSV =
	// "classpath:liste-cpf-2016.csv";
	private static final String DIR_CSV = "/Users/kodjovi1/Desktop/CPF/all";

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	private static final String[] FILE_HEADER_APE = { "organismeEditeur", "niveau", "intitule", "certificateur",
			"codeRNCP" };

	// private static final String[] FILE_FORMATION_HEADER = {
	// "organismeEditeur", "niveau", "intitule", "certificateur",
	// "codeRNCP", "codeInventaire", "codeCertifInfo", "codeCPF", "public" };

	private static final String[] FILE_FORMATION_HEADER = { "organismeediteur", "public", "intitule", "certificateur",
			"niveau", "codeRNCP", "codeInventaire", "codeCertifInfo", "codeOffre", "codeCPF", "debutValidite",
			"finValidite", "codeNSF", "codeROME", "formacode" };

	private static final String[] FILE_HEADER2 = { "niveau", "intitule", "codeRNCP", "codeInventaire", "codeCertifInfo",
			"codeOffre", "codeCPF", "debutValidite", "finValidite", "certificateur", "public", "organismeEditeur" };

	private static final String COPAREF = "coparef";

	private static final String COPANEF = "copanef";

	private static final String CPNEF = "cpne";
	private static final String CPNEFP = "cpnefp";
	private static final String CPNE = "cpne";
	private static final String NATIONAL = "national";
	private static final String TOUT_PUBLIC = "tout public";
	private static final String DEMANEUR = "demandeur d'emploi";

	private static final Logger LOG = LoggerFactory.getLogger(FormationResource.class);

	private static final String CLEA_SPEC = "1";

	private static final String CODE_APE_ALL = "12345";

	private static final String SALARIE = "salarie";

	@Inject
	private FormationConverter formationConverter;

	private static final String FILE_NIVEAU_JSON = "niveaux.json";

	@RequestMapping(value = "/formations/certifinfo/{codeCertifInfo}", method = RequestMethod.GET)
	public ResponseEntity<String> getFromHtl(@PathVariable String codeCertifInfo) {
		// String url =
		// "http://www.intercariforef.org/formations/certification-"+codeCertifInfo+".html";

		String url = "http://www.intercariforef.org/formations/certification-83578.html";
		String res = null;
		try {
			Document doc = Jsoup.connect(url).get();

			Elements elements = doc.getElementsByClass("detaildip");
			Element elements2 = doc.getElementById("lstBrancheEligibilite1");

			Elements element = doc.getElementsContainingText("Code CPF : 157068");

			res = elements2.text();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(res, HttpStatus.OK);

	}

	public Set<FormationDto> getByPublicAuditeurAndCouvertureGeoAndIntitule(
			 String pub,  String region, String intitule,String clea) {

		Set<FormationDto> formations = formationConverter.toDtos(Sets.newHashSet(
				formationRepository.findByPublicAuditeurAndCouvertureGeoAndIntituleOriginalTextSansAccent(pub, region,
						org.apache.commons.lang3.StringUtils
								.stripAccents(intitule.replaceAll(SPECIAL_CHARACTER, " ")))));
		
		Iterables.concat(formationRepository.findByPublicAuditeurAndCouvertureGeoAndIntituleOriginalTextSansAccent(pub, region, stripeSpecialCharacter(intitule)));

		return  formations;
	}

	@RequestMapping(value = "/formations/niveaux", method = RequestMethod.GET)
	public ResponseEntity<Set<String>> getNiveaux() {

		Set<String> niveaux = Lists.newArrayList(formationRepository.findAll()).stream().map(f -> f.getNiveau())
				.collect(Collectors.toSet());

		return new ResponseEntity<Set<String>>(niveaux, HttpStatus.OK);
	}

	@RequestMapping(value = "/formations/regions", method = RequestMethod.GET)
	public ResponseEntity<Set<String>> getRegion() {

		Set<String> regions = Lists.newArrayList(formationRepository.findAll()).stream().map(f -> f.getCouvertureGeo())
				.sorted().collect(Collectors.toSet());

		return new ResponseEntity<Set<String>>(regions, HttpStatus.OK);
	}

	@RequestMapping(value = "/formations/editeurs", method = RequestMethod.GET)
	public ResponseEntity<Set<String>> getEditeur() {

		Set<String> editeurs = Lists.newArrayList(formationRepository.findAll()).stream()
				.map(f -> f.getOrganismeEditeur())
				.filter(o -> org.apache.commons.lang3.StringUtils.contains(o, COPAREF)).collect(Collectors.toSet());

		return new ResponseEntity<Set<String>>(editeurs, HttpStatus.OK);
	}

	@RequestMapping(value = "/formations/auditeurs", method = RequestMethod.GET)
	public ResponseEntity<Set<String>> getAuditeur() {
		Set<String> auditeurs = Lists.newArrayList(formationRepository.findAll()).stream()
				.map(f -> f.getPublicAuditeur()).collect(Collectors.toSet());
		return new ResponseEntity<Set<String>>(auditeurs, HttpStatus.OK);
	}

	@RequestMapping(value = "/formations/activites/{codeAPE}", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Formation>> getByCodeAPE(@PathVariable String codeAPE) {
		return new ResponseEntity<Iterable<Formation>>(formationRepository.findByCodeAPEsLike(codeAPE), HttpStatus.OK);
	}

	@RequestMapping(value = "/formations/codeAPE", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateWithCodeAPE() throws IOException {

		List<BrancheAPE> brancheAPEs = new ArrayList<BrancheAPE>();
		File in = getFileFromClasspath(FILE_CODEAPE_CSV);

		List<CSVRecord> csvRecords = dataConversionService.convertCSVFileToRecod(in, CODEAPE_BRANCHE_HEADER);

		csvRecords.forEach(c -> {
			BrancheAPE brancheAPE = new BrancheAPE();

			brancheAPE.setBranche(org.apache.commons.lang3.StringUtils.lowerCase(c.get("branche")));
			brancheAPE.setCodeAPE((c.get("codeAPE")));
			brancheAPE.setIntitule(org.apache.commons.lang3.StringUtils.lowerCase(c.get("intitule")));

			brancheAPEs.add(brancheAPE);
		});

		LOG.info("============Branche initialised " + brancheAPEs.size());

		List<Formation> formationToSave = new ArrayList<Formation>();

		Lists.newArrayList(formationRepository.findAll()).forEach(f -> {
			f.setCodeAPEs(brancheAPEs.stream()
					.filter(b -> StringUtils.equalsIgnoreCase(b.getBranche(), f.getOrganismeEditeur()))
					.map(b -> StringUtils.remove(b.getCodeAPE(), CODE_APE_SEPARATOR)).collect(Collectors.toList()));
			formationToSave.add(f);
		});

		// update with code for editeur COPANEF or COPAREF
		Lists.newArrayList(formationRepository.findAll()).forEach(f -> {
			if (StringUtils.contains(f.getOrganismeEditeur(), COPAREF)
					|| StringUtils.contains(f.getOrganismeEditeur(), COPANEF)
					|| StringUtils.contains(f.getOrganismeEditeur(), CPNEF)) {
				f.setCodeAPEs(Arrays.asList(CODE_APE_ALL));
				formationToSave.add(f);
			}
		});

		if (!formationToSave.isEmpty()) {
			formationRepository.save(formationToSave);
		}
		
		LOG.info("========== Code APE SET done =====");
		return ResponseEntity.ok().build();
	}

	/**
	 * init with ref datas : niveau, region,
	 * 
	 * @return
	 */
	@RequestMapping(value = "/formations/bulk", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateWithRefDatas() {

		LOG.info("============ Niveau set ===========");

		List<Formation> formationsWithNiveauToSave = new ArrayList<Formation>();

		Lists.newArrayList(niveauRepository.findAll()).forEach(n -> {
			formationRepository.findByNiveau(n.getNom()).forEach(f -> {
				f.setNiveau(n.getId());
				formationsWithNiveauToSave.add(f);
			});
		});

		if (!formationsWithNiveauToSave.isEmpty()) {
			formationRepository.save(formationsWithNiveauToSave);
		}
		LOG.info("=============Niveau setting ok =================");

		List<Formation> formationsWithCouv = new ArrayList<Formation>();

		Lists.newArrayList(couvertureGeoRepository.findAll()).stream().forEach(c -> c.getComite().forEach(co -> {
			formationRepository.findByOrganismeEditeur(co).stream().forEach(f -> {
				f.setCouvertureGeo(c.getId());
				formationsWithCouv.add(f);
			});
			;
		}));

		formationRepository.save(formationsWithCouv);

		List<Formation> formationsWithCPNEFP = new ArrayList<Formation>();

		Lists.newArrayList(formationRepository.findAll()).stream()
				.filter(f -> org.apache.commons.lang3.StringUtils.contains(f.getOrganismeEditeur(), CPNE)
						|| org.apache.commons.lang3.StringUtils.contains(f.getOrganismeEditeur(), COPANEF))
				.forEach(f ->

		{
					f.setCouvertureGeo(couvertureGeoRepository.findByNom(NATIONAL).getId());
					formationsWithCPNEFP.add(f);
				});

		if (!formationsWithCPNEFP.isEmpty())

		{
			formationRepository.save(formationsWithCPNEFP);
		}

		LOG.info("============Update with couverture geo done==========");

		populateWithAuditeur();

		//
		List<Formation> formationsWithPub = new ArrayList<Formation>();
		auditeurRepository.findAll().forEach(a ->

		{
			Lists.newArrayList(formationRepository.findByPublicAuditeur(a.getNom())).forEach(f -> {
				f.setPublicAuditeur(a.getId());
				formationsWithPub.add(f);
			});
		});

		if (!formationsWithPub.isEmpty())

		{
			formationRepository.save(formationsWithPub);
		}
		
		
		try {
			updateWithCodeAPE();
		} catch (IOException e) {
		LOG.error(e.getMessage(), e);
		}

		return ResponseEntity.ok().build();

	}

	@RequestMapping(value = "/formations/bulk/productionData", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateWithProductionData() {
		List<Formation> formationsWithOrg = new ArrayList<Formation>();

		Addresse a = new Addresse("adresseLine1", "adresseLine2", "adresseLine3", "postalcode", "city", "country");
		List<Session> sessions = new ArrayList<>();
		sessions.add(new Session("code", "description", new DateTime().toDate(), new DateTime().plus(12).toDate()));
		Organisme organisme = new Organisme("ORG", a, "telephoneFixe", "telephoneMobile", "fax", "url", "raisonSocial",
				sessions);

		organismeRepository.deleteAll();
		organismeRepository.save(organisme);

		formationRepository.findAll().forEach(f -> {
			organismeRepository.findAll().forEach(o -> {
				f.getOrganismes().add(o.getId());
				formationsWithOrg.add(f);
			});
		});

		Iterable<Formation> foIterableSave = formationRepository.save(formationsWithOrg);

		LOG.info(Lists.newArrayList(foIterableSave).size() + "=====Set with organismes done ");

		List<Formation> formationsWithRating = new ArrayList<Formation>();

		List<Rating> ratings = new ArrayList<>();

		ratings.add(new Rating("Big Brother", "For training by CPF, you know for search", 3));

		ratings.add(new Rating("Small Brother", "For learning you know the keystone", 4));

		formationRepository.findAll().forEach(f -> {
			f.setRatings(ratings);
			formationsWithRating.add(f);
		});

		Iterable<Formation> foterableRating = formationRepository.save(formationsWithRating);

		LOG.info(Lists.newArrayList(foterableRating).size() + "=====Set with rating done ");

		return ResponseEntity.ok().build();

	}

	@RequestMapping(value = "/formations/ratings", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateWithRating() {
		List<Formation> formations = new ArrayList<Formation>();

		List<Rating> ratings = new ArrayList<>();

		ratings.add(new Rating("Big Brother", "For training by CPF, you know for search", 3));

		ratings.add(new Rating("Small Brother", "For learning you know the keystone", 4));

		formationRepository.findAll().forEach(f -> {
			f.setRatings(ratings);
			formations.add(f);
		});

		formationRepository.save(formations);

		return ResponseEntity.ok().build();

	}

	// @RequestMapping(value = "/formations/pub", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateWithPubAuditeur() {

		Set<String> pubs = new HashSet<String>();
		for (Formation formation : formationRepository.findAll()) {
			pubs.add(formation.getPublicAuditeur());

		}
		int i = 0;
		for (String pub : pubs) {
			Auditeur auditeur = new Auditeur();

			auditeur.setId(String.valueOf(++i));
			auditeur.setDescription("Situation professionnelle" + org.apache.commons.lang3.StringUtils.SPACE + pub);
			auditeur.setNom(pub);
			auditeurRepository.save(auditeur);
		}

		List<Formation> formations = new ArrayList<Formation>();

		for (Auditeur auditeur : auditeurRepository.findAll()) {
			for (Formation formation : formationRepository.findByPublicAuditeur(auditeur.getNom())) {
				formation.setPublicAuditeur(auditeur.getId());
				formations.add(formation);
			}

		}

		formationRepository.save(formations);

		return ResponseEntity.ok().build();

	}

	@RequestMapping(value = "/formations/search", method = RequestMethod.GET)
	ResponseEntity<Iterable<Formation>> getByCouvertureGeo(@RequestParam String couvGeo) {

		return new ResponseEntity<Iterable<Formation>>(formationRepository.findByCouvertureGeo(couvGeo), HttpStatus.OK);

	}

	// @RequestMapping(value = "/formations", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateWithCouvGeo() {
		// List<Formation>
		// formations=Lists.newArrayList(formationRepository.findAll());

		List<CouvertureGeo> couvertureGeos = Lists.newArrayList(couvertureGeoRepository.findAll());

		List<Formation> formationToSave = new ArrayList<Formation>();

		for (CouvertureGeo couvertureGeo : couvertureGeos) {
			List<Formation> formations = formationRepository.findByOrganismeEditeur(couvertureGeo.getNom());

			for (Formation formation : formations) {
				formation.setCouvertureGeo((couvertureGeo.getId()));
				formationToSave.add(formation);
			}
		}

		formationRepository.save(formationToSave);
		return ResponseEntity.ok().build();

	}

	/**
	 * Think to format the input XLS file for cell values as niveau
	 * 
	 * iconv -c -t UTF-8 liste-cpf.csv > liste-utf8-cpf.csv
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	@RequestMapping(value = "/formations/populate", method = RequestMethod.POST)
	@ApiOperation(value = "Init the ES index ", notes = "This can only be done by the admin user.")
	public ResponseEntity<Void> populate(
			@ApiParam(value = "CSV file to convert to documents with column header : [ organismeediteur	public	intitule	certificateur	niveau	codeRNCP	codeInventaire	codeCertifInfo	codeOffre	codeCPF	debutValidite	finValidite	codeNSF	codeROME	formacode]  ", required = true) @RequestParam String filepath)
					throws URISyntaxException, IOException {

		// File csvFile = getFileFromClasspath(FILE_FORMATION_CSV);

		// ClassPathResource csvFile = new
		// ClassPathResource(FILE_FORMATION_CSV);
		// Resources.getResource(FILE_FORMATION_CSV);

		List<CSVRecord> csvRecords = dataConversionService.convertCSVFileToRecod(new File(filepath),
				FILE_FORMATION_HEADER);
		List<Formation> formations = new ArrayList<Formation>();

		for (CSVRecord csvRecord : csvRecords) {
			if (csvRecord.getRecordNumber() > 1) {
				Formation formation = new Formation();
				formation.setId(String.valueOf(csvRecord.getRecordNumber()));
				formation.setIntitule(
						new Intitule(org.apache.commons.lang3.StringUtils.lowerCase((csvRecord.get("intitule"))),
								org.apache.commons.lang3.StringUtils.stripAccents(
										org.apache.commons.lang3.StringUtils.lowerCase((csvRecord.get("intitule"))))));

				formation.setNiveau((csvRecord.get("niveau")));
				formation.setOrganismecertificateur(
						(org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("certificateur"))));
				formation.setCodeRNCP((org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("codeRNCP"))));
				formation.setCodeInventaire(
						(org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("codeInventaire"))));
				formation.setCodeCertifInfo(
						(org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("codeCertifInfo"))));
				formation.setCodeCPF((org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("codeCPF"))));

				formation.setPublicAuditeur(org.apache.commons.lang3.StringUtils
						.stripAccents(org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("public"))));
				formation.setOrganismeEditeur(
						org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("organismeediteur")));

				formation.setCodeRome(org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("codeROME")));

				formation.setCodeNSF((org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("codeNSF"))));

				formation.setFormacode(org.apache.commons.lang3.StringUtils
						.stripAccents(org.apache.commons.lang3.StringUtils.lowerCase(csvRecord.get("formacode"))));

				try {
					formation.setDebutdevalidite(dateFormat.parse(csvRecord.get("debutValidite")
							.concat(org.apache.commons.lang3.StringUtils.SPACE).concat("00:00")));
				} catch (ParseException e) {
					LOG.error(e.getMessage(), e);
				}

				try {
					formation.setFindevalidite(dateFormat.parse(csvRecord.get("finValidite")
							.concat(org.apache.commons.lang3.StringUtils.SPACE).concat("00:00")));
				} catch (ParseException e) {
					LOG.error(e.getMessage(), e);
				}

				formations.add(formation);

			}
		}
		Iterable<Formation> formationsS = null;

		if (!formations.isEmpty()) {
			formationRepository.deleteAll();
			formationsS = formationRepository.save(formations);
		}
		// init couv geos

		LOG.info("============= Start couvertureGeosInit   ==== ");

		List<CouvertureGeo> couvertureGeosInit = new ArrayList<CouvertureGeo>();

		couvertureGeosInit = (List<CouvertureGeo>) fromJsonFileToData(FILE_REGION_JSON);

		couvertureGeoRepository.deleteAll();
		couvertureGeoRepository.save(couvertureGeosInit);

		LOG.info("================ Couverture geo init done =======");

		LOG.info("============= Start niveaux init    ==== ");

		niveauRepository.deleteAll();
		List<Niveau> niveauxInit = new ArrayList<Niveau>();
		niveauxInit = fromNiveauJsonFileToData(FILE_NIVEAU_JSON);
		niveauRepository.deleteAll();
		niveauRepository.save(niveauxInit);

		LOG.info("============= End niveaux init   ==== ");

		return ResponseEntity.created(new URI("/formations/" + Lists.newArrayList(formationsS).size())).build();

	}

	public static File getFileFromClasspath(String filepath) throws IOException {
	
		ClassPathResource classPathResource = new ClassPathResource(filepath);

        InputStream inputStream = classPathResource.getInputStream();
        File somethingFile = File.createTempFile(filepath.split("\\.")[0], "."+filepath.split("\\.")[1]);
        try {
            FileUtils.copyInputStreamToFile(inputStream, somethingFile);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
		return somethingFile;
	}

	private List<Niveau> fromNiveauJsonFileToData(String niveauFile) {

		ObjectMapper mapper = new ObjectMapper();
		List<Niveau> niveaus = new ArrayList<>();

		try {
			niveaus = mapper.readValue(getFileFromClasspath(niveauFile), new TypeReference<List<Niveau>>() {
			});
		} catch (JsonParseException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return niveaus;
	}

	static List<CouvertureGeo> fromJsonFileToData(String file) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<CouvertureGeo> couvertureGeos = new ArrayList<>();

		try {
			couvertureGeos = objectMapper.readValue(getFileFromClasspath(file), new TypeReference<List<CouvertureGeo>>() {
			});
		} catch (JsonParseException e) {
			LOG.error(e.getMessage(), e);

		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);

		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return couvertureGeos;

	}

	public List<CouvertureGeo> jsonToData(List<CouvertureGeo> couvertureGeosInit, String filePath) {

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			couvertureGeosInit = objectMapper.readValue(new File(filePath), new TypeReference<List<CouvertureGeo>>() {
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
		return couvertureGeosInit;
	}

	@RequestMapping(value = "/formations/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Formation>> getByMotCle(@RequestParam String mot) {
		return new ResponseEntity<List<Formation>>(formationRepository.findByIntituleContains(StringUtils.split(mot)),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/formations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Formation> getAll(@RequestParam(required = false) int page, @RequestParam(required = false) int size) {
		return (formationRepository.findAll(new PageRequest(page, size)));

	}

	@RequestMapping(value = "/formations/{codeCPF}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Formation> getByCodeCPF(@PathVariable String codeCPF) {

		Formation formation = formationRepository.findByCodeCPF(codeCPF);

		return new ResponseEntity<Formation>(formation, HttpStatus.OK);

	}

	@RequestMapping(value = "/formations/public/{pub}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Formation>> getByPublic(@PathVariable(value = "pub") String pub) {
		return new ResponseEntity<Iterable<Formation>>(formationRepository.findByPublicAuditeur(pub), HttpStatus.OK);

	}

	@RequestMapping(value = "/formations/search/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FormationProfileResponse>> getByProfile(@RequestParam(required = true) String pub,
			@RequestParam(required = true) String region, @RequestParam String codeAPE,
			@RequestParam(required = true) String intitule, @RequestParam(required = true) String clea) {

		// statut ~ tout public : statut ~ demandeur

		if (isDemandeur(pub) || isToutPublic(pub)) {
			LOG.info("params :" + "pub: " + pub + "clea: " + clea + "region: " + region + "intitule: " + intitule);
//			   getByPublicAuditeurAndCouvertureGeoAndIntitule(pub, region, stripeSpecialCharacter(intitule));

		}

		Set<FormationProfileResponse> profilSal = new HashSet<FormationProfileResponse>();

		if (isSalarie(pub)) {

			List<Formation> specificSearch = Lists.newArrayList(
					formationRepository.findByPublicAuditeurAndCouvertureGeoAndIntituleAndCodeAPEsLike(pub, region,
							stripeSpecialCharacter(intitule), codeAPE));

			if (isClea(clea)) {

				profilSal = convertToDTO(Iterables.concat(specificSearch,
						getAllFormationSalarieNational(stripeSpecialCharacter(intitule)),
						getByToutPublicAndNational(stripeSpecialCharacter(intitule))));

			} else {
				profilSal = convertToDTO(specificSearch);
			}
		}
		return new ResponseEntity<Set<FormationProfileResponse>>(profilSal, HttpStatus.OK);

	}

	private boolean isSalarie(String pub) {
		return org.apache.commons.lang3.StringUtils.equals(pub, AuditeurType.SALARIE.getId());
	}

	public Iterable<Formation> getAllFormationDemandeurNational(String intitule) {

		String allRegionId = couvertureGeoRepository.findByNom(NATIONAL).getId();
		String allPubId = auditeurRepository.findByNom(DEMANEUR).get().getId();

		LOG.info("allRegionId: " + allRegionId + "allPubId: " + allPubId);

		List<Formation> formations = Lists.newArrayList(
				formationRepository.findByPublicAuditeurAndCouvertureGeoAndIntituleOriginalTextSansAccent(allPubId,
						allRegionId, intitule));

		LOG.info("All size :" + formations.size());
		return formations;
	}

	public Iterable<Formation> getAllFormationSalarieNational(String intitule) {

		String allPubId = auditeurRepository.findByNom(SALARIE).get().getId();
		String allRegionId = couvertureGeoRepository.findByNom(NATIONAL).getId();

		LOG.info("allRegionId: " + allRegionId + "allPubId: " + allPubId);

		List<Formation> formations = Lists.newArrayList(
				formationRepository.findByPublicAuditeurAndCouvertureGeoAndIntituleOriginalTextSansAccent(allPubId,
						allRegionId, intitule));

		LOG.info("getAllFormationSalarieNational :" + formations.size());
		return formations;
	}

	public Iterable<Formation> getByToutPublicAndNational(String intitule) {

		List<Formation> formations = Lists.newArrayList(formationRepository
				.findByPublicAuditeurAndCouvertureGeoAndIntituleOriginalTextSansAccent(AuditeurType.ALL.getId(),
						CouvertureGeoType.NATIONAL.getId(), stripeSpecialCharacter(intitule)));

		LOG.info("All size :" + formations.size());
		return formations;
	}

	private boolean isClea(String clea) {
		return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(clea, AuditeurType.ALL.getId());
	}

	private boolean isToutPublic(String pub) {
		return org.apache.commons.lang3.StringUtils.equals(pub, AuditeurType.ALL.getId());
	}

	private boolean isDemandeur(String pub) {
		return org.apache.commons.lang3.StringUtils.equals(pub, AuditeurType.DE.getId());
	}
	//
	// if
	// (org.apache.commons.lang3.StringUtils.equals(auditeurRepository.findOne(pub).getNom(),
	// SALARIE)) {
	// Iterable<Formation> pubSalFormations = null;
	//
	// pubSalFormations =
	// formationRepository.findByPublicAuditeurAndCouvertureGeoAndIntituleAndCodeAPEsLike(pub,
	// region, intitule, codeAPE);
	//
	// if (pubSalFormations != null &&
	// !Lists.newArrayList(pubSalFormations).isEmpty()) {
	// resultFormations = pubSalFormations;
	// }
	//
	// }
	//
	// List<Formation> noSpecForm = null;
	//
	// if
	// (!org.apache.commons.lang3.StringUtils.equals(auditeurRepository.findOne(pub).getNom(),
	// TOUT_PUBLIC)
	// && StringUtils.equals(clea, CLEA_SPEC)) {
	//
	// noSpecForm = formationRepository.findByPublicAuditeurAndIntituleLike(pub,
	// intitule);
	//
	// if (noSpecForm != null && !noSpecForm.isEmpty() && resultFormations !=
	// null
	// && !Lists.newArrayList(resultFormations).isEmpty()) {
	// Iterables.concat(resultFormations, noSpecForm);
	// }
	//
	// }
	//
	// Iterable<Formation> all = null;
	//
	// // convertToDTO(profileResponses, comFormation, allPubFormations,
	// // resultFormations);
	//
	// return new
	// ResponseEntity<List<FormationProfileResponse>>(profileResponses,
	// HttpStatus.OK);

	private Set<FormationProfileResponse> convertToDTO(Iterable<Formation> pFormations) {

		Set<FormationProfileResponse> profileResponses = new HashSet<FormationProfileResponse>();

		if (pFormations != null) {
			Sets.newHashSet(pFormations).forEach(f -> {
				profileResponses.add(new FormationProfileResponse(f, niveauRepository.findOne(f.getNiveau()),
						couvertureGeoRepository.findOne(f.getCouvertureGeo()),
						f.getOrganismes().isEmpty() ? Collections.emptyList()
								: Lists.newArrayList(organismeRepository.findAll(f.getOrganismes()))));
			});
		}
		return profileResponses;
	}

	@RequestMapping(value = "/formations/search/{intitule}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Formation>> getByIntitule(@PathVariable(value = "intitule") String intitule,
			@RequestParam int page, @RequestParam int size) {
		return new ResponseEntity<Iterable<Formation>>(
				formationRepository.findByIntitule(stripeSpecialCharacter(intitule), new PageRequest(page, size)),
				HttpStatus.OK);

	}

	public static String stripeSpecialCharacter(String intitule) {
		return org.apache.commons.lang3.StringUtils
				.stripAccents(org.apache.commons.lang3.StringUtils.strip(intitule.replaceAll(SPECIAL_CHARACTER, " ")));
	}

	public void populateWithAuditeur() {
		List<Auditeur> auditeurs = new ArrayList<Auditeur>();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			auditeurs = objectMapper.readValue(
					getFileFromClasspath(FILE_AUDITEURS_JSON),
					new TypeReference<List<Auditeur>>() {
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

		auditeurRepository.deleteAll();

		auditeurRepository.save(auditeurs);

		LOG.info("================ Auditeurs  init done =======");

	}

}
