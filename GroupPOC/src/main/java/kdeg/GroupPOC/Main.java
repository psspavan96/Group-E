package kdeg.GroupPOC;


import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.ontology.SymmetricProperty;
import org.apache.jena.ontology.TransitiveProperty;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shared.AddDeniedException;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.OperatorImportFromWkt;
import com.esri.core.geometry.OperatorIntersects;
import com.esri.core.geometry.OperatorWithin;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.WktImportFlags;


public class Main {
	public static String firstDatasetOnline="http://data.geohive.ie/dumps/county/default.ttl";
	public static String firstDatasetLocal="county-default.ttl";
	public static String secondDatasetLocal="resource\\EducationUT-output-minimal.ttl";
	public static String groupE_Ontology="resource\\groupE_Ontology.ttl";
	
	public static String link="http://group-e/ontology";
	public static String base="http://group-e/ontology#";
	public static void main(String[] args) throws Exception {
		Files.copy( URI.create(firstDatasetOnline).toURL().openStream(), Paths.get(firstDatasetLocal),StandardCopyOption.REPLACE_EXISTING);
		createOntology();
		QueryGUI.initializeAppGUI();
		/*---------Amogh's UI will go down here-----------*/
	}
	private static void createOntology() throws Exception {
		OntModel model = ModelFactory.createOntologyModel();
		model.setNsPrefix("base", base);
		Ontology ontology = model.createOntology(link);
        ontology.addLabel("Ontology for schools in Dublin", null);
        ontology.addLabel("Ontology for schools in Dublin", "en");
        ontology.addComment( "Ontology for schools in Dublin. This ontology is built by combining 2 different datasets", "en");
        ontology.addComment( "Ontology for schools in Dublin. This ontology is built by combining 2 different datasets", null);
        ontology.addProperty(DCTerms.date, "23-Nov-2018");
        ontology.addProperty(DCTerms.contributor, "Aditya");
        ontology.addProperty(DCTerms.contributor, "Shubham");
        ontology.addProperty(DCTerms.contributor, "Amogh");
        ontology.addProperty(DCTerms.contributor, "Pavan");
        ontology.addProperty(DCTerms.contributor, "Paras");
        
        ontology.addProperty(DCTerms.description, "Ontology for schools in Dublin.  This ontology is built by combining 2 different datasets");
        
        OntClass school = model.createClass(base + "school");
        school.addLabel("Ireland schools", null); school.addLabel("Ireland schools", "en");
        school.addComment("Schools in Ireland separated by counties", null); school.addComment("Schools in Ireland separated by counties", "en");
        
        
        DatatypeProperty latitude = model.createDatatypeProperty(base + "latitude");
        DatatypeProperty longitude = model.createDatatypeProperty(base + "longitude");
        latitude.addLabel("latitude", null);latitude.addLabel("latitude", "en");
        latitude.addComment("Latitude parameter of location", null);latitude.addComment("Latitude parameter of location", "en");
        longitude.addLabel("longitude", null);longitude.addLabel("longitude", "en");
        longitude.addComment("Longitude parameter of location", null);longitude.addComment("Longitude parameter of location", "en");
        latitude.setDomain(school);
        latitude.setRange(XSD.xfloat);
        longitude.setDomain(school);
        longitude.setRange(XSD.xfloat);
        school.addSuperClass(model.createCardinalityRestriction(null, latitude, 1));
        school.addSuperClass(model.createCardinalityRestriction(null, longitude, 1));
        
        DatatypeProperty addressLine1 = model.createDatatypeProperty(base + "addressLine1");
        addressLine1.addLabel("address line 1", null);addressLine1.addLabel("address line 1", "en");
        addressLine1.addComment("address line 1 of the address", null);addressLine1.addComment("address line 1 of the address", "en");
        addressLine1.setDomain(school);
        addressLine1.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, addressLine1, 1));
        //school.addSuperClass(model.createCardinalityRestriction(null, addressLine1, 1));
        DatatypeProperty addressLine2 = model.createDatatypeProperty(base + "addressLine2");
        addressLine2.addLabel("address line 2", null);addressLine2.addLabel("address line 2", "en");
        addressLine2.addComment("address line 2 of the address", null);addressLine2.addComment("address line 2 of the address", "en");
        addressLine2.setDomain(school);
        addressLine2.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, addressLine2, 1));
        //school.addSuperClass(model.createCardinalityRestriction(null, addressLine2, 1));
        DatatypeProperty addressLine3 = model.createDatatypeProperty(base + "addressLine3");
        addressLine3.addLabel("address line 3", null);addressLine3.addLabel("address line 3", "en");
        addressLine3.addComment("address line 3 of the address", null);addressLine3.addComment("address line 3 of the address", "en");
        addressLine3.setDomain(school);
        addressLine3.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, addressLine3, 1));
        //school.addSuperClass(model.createCardinalityRestriction(null, addressLine3, 1));
        DatatypeProperty eirCode = model.createDatatypeProperty(base + "eirCode");
        eirCode.addLabel("EIR code", null);eirCode.addLabel("EIR code", "en");
        eirCode.addComment("EIR code of area", null);eirCode.addComment("EIR code of area", "en");
        eirCode.setDomain(school);
        eirCode.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, eirCode, 1));
        //school.addSuperClass(model.createCardinalityRestriction(null, eirCode, 1));
        
        OntClass county = model.createClass(base + "county");
        county.addLabel("Ireland County", null);county.addLabel("Ireland County", "en");
        county.addComment("Ireland Counties", null);county.addComment("Ireland Counties", "en");
        DatatypeProperty countyArea = model.createDatatypeProperty(base + "area");
        countyArea.addLabel("area of county", null);countyArea.addLabel("area of county", "en");
        countyArea.addComment("The area of a county of the schools", null);countyArea.addComment("The area of a county of the schools", "en");
        countyArea.setDomain(county);
        countyArea.setRange(XSD.xfloat);
        county.addSuperClass(model.createCardinalityRestriction(null, countyArea, 1));
        DatatypeProperty countyName = model.createDatatypeProperty(base + "countyName");
        countyName.addLabel("name of county", null);countyName.addLabel("name of county", "en");
        countyName.addComment("The name of a county of the schools", null);countyName.addComment("The name of a county of the schools", "en");
        countyName.setDomain(county);
        countyName.setRange(XSD.xstring);
        county.addSuperClass(model.createCardinalityRestriction(null, countyName, 1));
        SymmetricProperty sharesBoundaryWith = model.createSymmetricProperty(base + "sharesBoundaryWith");
        sharesBoundaryWith.addLabel("shares boundary with", null);sharesBoundaryWith.addLabel("shares boundary with", "en");
        sharesBoundaryWith.addComment("These conties shares the boundaries", null);sharesBoundaryWith.addComment("These conties shares the boundaries", "en");
        sharesBoundaryWith.setDomain(county);
        sharesBoundaryWith.setRange(county);
        TransitiveProperty biggerThan = model.createTransitiveProperty(base + "biggerThan");
        biggerThan.addLabel("biggerThan", null);biggerThan.addLabel("biggerThan", "en");
        biggerThan.addComment("Counties having larger area than the current one", null);biggerThan.addComment("Counties having larger area than the current one", "en");
        biggerThan.setDomain(county);
        biggerThan.setRange(county);
        TransitiveProperty smallerThan = model.createTransitiveProperty(base + "smallerThan");
        smallerThan.addLabel("smallerThan", null);smallerThan.addLabel("smallerThan", "en");
        smallerThan.addComment("Counties having smallerThan area than this one", null);smallerThan.addComment("Counties having smallerThan area than the current one", "en");
        smallerThan.setDomain(county);
        smallerThan.setRange(county);
        biggerThan.addInverseOf(smallerThan);smallerThan.addInverseOf(biggerThan);
        
        OntClass localAuthority = model.createClass(base + "localAuthority");
        localAuthority.addLabel("Local Authority", null);localAuthority.addLabel("Local Authority", "en");
        localAuthority.addComment("Local Authority of the area", null);localAuthority.addComment("Local Authority the area", "en");
        DatatypeProperty localAuthorityName = model.createDatatypeProperty(base + "localAuthorityName");
        localAuthorityName.addLabel("name of local authority", null);localAuthorityName.addLabel("name of local authority", "en");
        localAuthorityName.addComment("the name of local authority of school", null);localAuthorityName.addComment("the name of local authority of school", "en");
        localAuthorityName.setDomain(localAuthority);
        localAuthorityName.setRange(XSD.xstring);
        localAuthority.addSuperClass(model.createCardinalityRestriction(null, localAuthorityName, 1));
        /*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        
        

        DatatypeProperty rollNumber = model.createDatatypeProperty(base + "schoolRollno");
        rollNumber.addLabel("School Roll no", null);rollNumber.addLabel("School Roll no", "en");
        rollNumber.addComment("School Roll number", null);rollNumber.addComment("School Roll number", "en");
        rollNumber.setDomain(school);
        rollNumber.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, rollNumber, 1));
        DatatypeProperty officialSchoolName = model.createDatatypeProperty(base + "officialSchoolName");
        officialSchoolName.addLabel("School Name", null);officialSchoolName.addLabel("School Name", "en");
        officialSchoolName.addComment("Official School Name", null);officialSchoolName.addComment("Official School Name", "en");
        officialSchoolName.setDomain(school);
        officialSchoolName.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, officialSchoolName, 1));
        ObjectProperty hasSchools = model.createObjectProperty(base + "hasSchools");
        hasSchools.addLabel("County has this school", null);hasSchools.addLabel("County has this school", "en");
        hasSchools.addComment("Counties contains the schools", null);hasSchools.addComment("Counties contains the schools", "en");
        hasSchools.setDomain(county);
        hasSchools.setRange(school);
        ObjectProperty inCounty = model.createObjectProperty(base + "inCounty");
        inCounty.addLabel("Inside county", null);inCounty.addLabel("Inside county", "en");
        inCounty.addComment("The school is in particular county area", null);inCounty.addComment("The school is in this particular county area", "en");
        inCounty.setDomain(school);
        inCounty.setRange(county);
        hasSchools.addInverseOf(inCounty); inCounty.addInverseOf(hasSchools);
        ObjectProperty hasLocalAuthority = model.createObjectProperty(base + "hasLocalAuthority");
        hasLocalAuthority.addLabel("has local authority ", null);hasLocalAuthority.addLabel("has local authority ", "en");
        hasLocalAuthority.addComment("has local authority  of", null);hasLocalAuthority.addComment("has local authority of ", "en");
        hasLocalAuthority.setDomain(school);
        hasLocalAuthority.setRange(localAuthority);
        
        
        OntClass primarySchool = model.createClass(base + "primarySchool");
        primarySchool.addLabel("Primary School", null);primarySchool.addLabel("Primary schools", "en");
        primarySchool.addComment("Ireland Primary schools", null); primarySchool.addComment("Ireland Primary schools", "en");
        primarySchool.addSuperClass(school);
        
        OntClass secondarySchool = model.createClass(base + "secondarySchool");
        secondarySchool.addLabel("Secondary School", null);secondarySchool.addLabel("Secondary schools", "en");
        secondarySchool.addComment("Ireland Secondary schools", null); secondarySchool.addComment("Ireland Secondary schools", "en");
        secondarySchool.addSuperClass(school);
        
        OntClass communityInstitute = model.createClass(base + "communityInstitute");
        communityInstitute.addLabel("community institute School", null);communityInstitute.addLabel("community intitute schools", "en");
        communityInstitute.addComment("Ireland community institute schools", null); communityInstitute.addComment("Ireland community intitute schools", "en");
        communityInstitute.addSuperClass(school);
        
        OntClass communitySchool = model.createClass(base + "communitySchool");
        communitySchool.addLabel("Community School", null);communitySchool.addLabel("Community schools", "en");
        communitySchool.addComment("Ireland Community schools", null); communitySchool.addComment("Ireland Community schools", "en");
        communitySchool.addSuperClass(communityInstitute);
        OntClass communityCollege = model.createClass(base + "communityCollegeSchool");
        communityCollege.addLabel("community college School", null);communityCollege.addLabel("community college schools", "en");
        communityCollege.addComment("Ireland community college schools", null); communityCollege.addComment("Ireland community college schools", "en");
        communityCollege.addSuperClass(communityInstitute);
        communityCollege.addDisjointWith(communitySchool);
        
        //disjoints
        primarySchool.addDisjointWith(secondarySchool);primarySchool.addDisjointWith(communityInstitute);
        secondarySchool.addDisjointWith(communityInstitute);
        county.addDisjointWith(school);
        
        /*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        Model firstDataset = RDFDataMgr.loadModel(firstDatasetLocal);
        
        ArrayList<Individual> countyList = new ArrayList<Individual>();
        ArrayList<ArrayList<Object>> countyInfoList = new ArrayList<ArrayList<Object>>();
        ResIterator countyResIter = firstDataset.listResourcesWithProperty(RDFS.label);
        Property hasGeometry = firstDataset.getProperty("http://www.opengis.net/ont/geosparql#hasGeometry");
        Property asWKT = firstDataset.getProperty("http://www.opengis.net/ont/geosparql#asWKT");
        List<Geometry> geometryList=new ArrayList<Geometry>();
        List<Float> areaList=new ArrayList<Float>();
        
        while (countyResIter.hasNext()) {
            Resource resources = countyResIter.next();

            NodeIterator labelsIter = firstDataset.listObjectsOfProperty(resources, RDFS.label);
            List<RDFNode> labels = labelsIter.toList();
            String enLabel = "";

            for (RDFNode label : labels)
                if (label.asLiteral().getLanguage().equals("en"))
                    enLabel = label.asLiteral().getString();

            Resource geometryResource = firstDataset.listObjectsOfProperty(resources, hasGeometry).next().asResource();
            String wkt = firstDataset.listObjectsOfProperty(geometryResource, asWKT).next().toString();
            wkt = wkt.substring(0, wkt.indexOf("^^"));
            OperatorImportFromWkt importer = OperatorImportFromWkt.local();
            Geometry geometry = importer.execute(WktImportFlags.wktImportDefaults, Geometry.Type.Unknown, wkt, null);

            Individual aCounty = county.createIndividual(base + enLabel);
            aCounty.addLabel(enLabel, null);
            aCounty.addLabel(enLabel, "en");
            aCounty.addLiteral(countyName, enLabel);
            float area=(float)geometry.calculateArea2D() * 7365.0f;
            aCounty.addLiteral(countyArea, area);
            
            geometryList.add(geometry);
            areaList.add(area);
            
            countyList.add(aCounty);
        }
        
        for (int i=0;i<countyList.size();i++) {
            Individual currentCounty = countyList.get(i);
            Geometry currentCountyGeometry = geometryList.get(i);
            float currentCountyArea = areaList.get(i);
            
            for (int j=0;j<countyList.size();j++) {
                if (i == j) continue;
                
                Individual iterativeCounty =  countyList.get(j);
                Geometry iterativeCountyGeometry = geometryList.get(j);
                float iterativeCountyArea = areaList.get(j);
                
                if (currentCountyArea > iterativeCountyArea)  currentCounty.addProperty(biggerThan, iterativeCounty);
                else 										  iterativeCounty.addProperty(biggerThan, currentCounty);
                
                OperatorIntersects intersects = OperatorIntersects.local();
                if (intersects.execute(currentCountyGeometry, iterativeCountyGeometry, SpatialReference.create("WGS84"), null)) 
                	currentCounty.addProperty(sharesBoundaryWith, iterativeCounty);
                
            }
        }
        /*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        Model secondDataset = RDFDataMgr.loadModel(secondDatasetLocal);
        Property rollNumberProperty = secondDataset.getProperty("http://example.org/data/EducationUT.csv#RollNumber");
        Property addressLine1Property = secondDataset.getProperty("http://example.org/data/EducationUT.csv#Address1");
        Property addressLine2Property = secondDataset.getProperty("http://example.org/data/EducationUT.csv#Address2");
        Property addressLine3Property = secondDataset.getProperty("http://example.org/data/EducationUT.csv#Address3");
        Property eirCodeProperty = secondDataset.getProperty("http://example.org/data/EducationUT.csv#Eircode");
        Property latitudeProperty = secondDataset.getProperty("http://example.org/data/EducationUT.csv#Latitude");
        Property longitudeProperty = secondDataset.getProperty("http://example.org/data/EducationUT.csv#Longitude");
        Property officialschoolnameProperty = secondDataset.getProperty("http://example.org/data/EducationUT.csv#OfficialSchoolName");
        Property localauthorityProperty = secondDataset.getProperty("http://example.org/data/EducationUT.csv#LocalAuthority");
        /*Individual primarySchoolType=primarySchool.createIndividual();
        Individual secondarySchoolType=secondarySchool.createIndividual();
        Individual communityCollegeType=communityCollege.createIndividual();
        Individual communityInstituteType=communityInstitute.createIndividual();
        Individual othersType=others.createIndividual();*/
        ResIterator schoolIterator = secondDataset.listResourcesWithProperty(rollNumberProperty);
        Map<String, Individual> localAuthorityMap=new HashMap<String, Individual>();
        while (schoolIterator.hasNext()) {
        	Resource resource = schoolIterator.next();
        	String localAuthorityStringOriginal=resource.getProperty(localauthorityProperty).getString();
        	String localAuthorityString=localAuthorityStringOriginal.toLowerCase();
        	if(localAuthorityMap.get(localAuthorityString)==null) {
        		localAuthorityString=localAuthorityString.replaceAll(" ", "_");
        		Individual ind=localAuthority.createIndividual(base+"school_"+localAuthorityString);
        		ind.addLiteral(localAuthorityName, localAuthorityStringOriginal);
        		localAuthorityMap.put(localAuthorityString, ind);
        	}
        }
        	
        schoolIterator = secondDataset.listResourcesWithProperty(rollNumberProperty);
        while (schoolIterator.hasNext()) {
            Resource resource = schoolIterator.next();
            String currentRollNumber=resource.getProperty(rollNumberProperty).getString();
            
            
            
            //Individual currentSchool=school.createIndividual(base+"school_"+currentRollNumber);
            Individual currentSchool=null;
            Individual currentSchoolType=null;
            String currentSchoolName=resource.getProperty(officialschoolnameProperty).getString();
            boolean flag=false;
            if(currentSchoolName.toLowerCase().contains("primary")) {
            	flag=true;
            	currentSchool=primarySchool.createIndividual(base+"school_"+currentRollNumber);
            	//currentSchoolType=primarySchoolType.asIndividual();
            	//currentSchool.addProperty(hasPrimarySchoolType, currentSchoolType);
            	
            }else if(currentSchoolName.toLowerCase().contains("secondary")) {
            	flag=true;
            	currentSchool=secondarySchool.createIndividual(base+"school_"+currentRollNumber);
            	//currentSchoolType=secondarySchoolType.asIndividual();
            	//currentSchool.addProperty(hasSecondarySchoolType, currentSchoolType);
            }
            if(currentSchoolName.toLowerCase().contains("community")) {
            	flag=true;
            	if(currentSchoolName.toLowerCase().contains("college")) 
            		{
            		currentSchool=communityCollege.createIndividual(base+"school_"+currentRollNumber);
            		}
            	else if(currentSchoolName.toLowerCase().contains("school")){
            		currentSchool=communitySchool.createIndividual(base+"school_"+currentRollNumber);
            	}else {
            		currentSchool=communityInstitute.createIndividual(base+"school_"+currentRollNumber);            		
            	}
            }
            if(! flag) {
            	currentSchool=school.createIndividual(base+"school_"+currentRollNumber);
            	//currentSchoolType=othersType.asIndividual();
            	//currentSchool.addProperty(hasOthersSchoolType, currentSchoolType);
            }
            
            //Individual currentLocation=location.asIndividual(/*base+"location_"+currentRollNumber*/);
            float curentLongitude=resource.getProperty(longitudeProperty).getFloat(); float curentLatitude=resource.getProperty(latitudeProperty).getFloat();
            currentSchool.addLiteral(latitude, curentLatitude);
            currentSchool.addLiteral(longitude, curentLongitude);
            if(resource.getProperty(addressLine1Property)!=null)
            	currentSchool.addLiteral(addressLine1, resource.getProperty(addressLine1Property).getString());
            if(resource.getProperty(addressLine2Property)!=null)
            	currentSchool.addLiteral(addressLine2, resource.getProperty(addressLine2Property).getString());
            if(resource.getProperty(addressLine3Property)!=null)
            	currentSchool.addLiteral(addressLine3, resource.getProperty(addressLine3Property).getString());
            currentSchool.addLiteral(eirCode, resource.getProperty(eirCodeProperty).getString());
            
            String localAuthoritySring=resource.getProperty(localauthorityProperty).getString().toLowerCase();
            localAuthoritySring=localAuthoritySring.replaceAll(" ", "_");
            Individual localAuthorityIndividual=localAuthorityMap.get(localAuthoritySring);
            currentSchool.addProperty(hasLocalAuthority, localAuthorityIndividual);
            currentSchool.addProperty(rollNumber, resource.getProperty(rollNumberProperty).getString());
            currentSchool.addProperty(officialSchoolName, resource.getProperty(officialschoolnameProperty).getString());
            
            //Individual currentCounty=county.createIndividual();
            for (int i = 0; i < geometryList.size(); i++) {
                Geometry geometry = (Geometry)geometryList.get(i);
                Point point = new Point(curentLongitude, curentLatitude);
                
                OperatorWithin within = OperatorWithin.local();
                if (within.execute(point, geometry, SpatialReference.create("WGS84"), null)) {
                	currentSchool.addProperty(inCounty, countyList.get(i));
                	//countyList.get(i).addProperty(hasSchools, currentSchool);
                    break;
                }
            }
            
            
        }
        
        //Ontology completed -- write to file
        model.write(new FileWriter(groupE_Ontology,true), "TURTLE");
        System.out.println("GroupE_Ontology instances created");
        runQuery(model);
	}

public static void	runQuery( OntModel ontModelObj){
	String output = "";
	String queryString="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
			"PREFIX base: <http://group-e/ontology#>\r\n" + 
			"select ?county ((COUNT(?schoolS)) AS ?noOfSecondarySchool) ((COUNT(?schoolP)) AS ?noOfPrimarySchool) ((COUNT(?schoolCS)) AS ?noOfCommunitySchool) ((COUNT(?schoolCCS)) AS ?noOfCommunityCollegeSchool) ((COUNT(?schoolOS) -  ?noOfSecondarySchool - ?noOfCommunityCollegeSchool - ?noOfCommunitySchool - ?noOfPrimarySchool) AS ?noOfOthersSchool) \r\n" + 
			"WHERE {\r\n" + 
			"{?schoolS a base:secondarySchool.\r\n" + 
			"?schoolS base:inCounty ?county.\r\n" + 
			"\r\n" + 
			"}  \r\n" + 
			"UNION\r\n" + 
			"{?schoolP a base:primarySchool.\r\n" + 
			"?schoolP base:inCounty ?county.} \r\n" + 
			"UNION\r\n" + 
			"{?schoolCS a base:communitySchool.\r\n" + 
			"?schoolCS base:inCounty ?county.} \r\n" + 
			"UNION\r\n" + 
			"{?schoolCCS a base:communityCollegeSchool.\r\n" + 
			"?schoolCCS base:inCounty ?county.} \r\n" + 
			"UNION\r\n" + 
			"{?schoolOS a base:school.\r\n" + 
			"?schoolOS base:inCounty ?county.} \r\n" + 
			"} group by ?county" ;
	Query query = QueryFactory.create(queryString);
	QueryExecution queryExecutor = QueryExecutionFactory.create(query, ontModelObj);
	ResultSet results = queryExecutor.execSelect();
	output = ResultSetFormatter.asText(results);
	System.out.println(output);
	queryExecutor.close();
	}
}
