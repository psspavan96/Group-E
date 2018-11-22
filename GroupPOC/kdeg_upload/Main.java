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
import java.util.List;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.ontology.SymmetricProperty;
import org.apache.jena.ontology.TransitiveProperty;
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
        ontology.addLabel("ontology describing different schools in Dublin", null);
        ontology.addLabel("ontology describing different schools in Dublin", "en");
        String comment = "Ontology describing different schools in Dublin";
        ontology.addComment( comment, "en");
        ontology.addComment( comment, null);
        ontology.addProperty(DCTerms.date, "23-Nov-2018");
        ontology.addProperty(DCTerms.contributor, "Aditya");
        ontology.addProperty(DCTerms.contributor, "Shubham");
        ontology.addProperty(DCTerms.contributor, "Amogh");
        ontology.addProperty(DCTerms.contributor, "Pavan");
        ontology.addProperty(DCTerms.contributor, "Paras");
        //Resource image = ontModel.createResource("https://drive.google.com/open?id=15B6U7PdfvPCvw7Kj8xIzpj4xdBl2-BL6");
        //ontology.addProperty(DCTerms.description, image);
        ontology.addProperty(DCTerms.description, comment);
        
        // GeoLocation Class
        OntClass location = model.createClass(base + "geoHive");
        location.addLabel("Locations", null);
        location.addComment("decribes counties in the country of Ireland", null);location.addComment("decribes counties in the country of Ireland", "en");
        location.addLabel("decribes counties in the country of Ireland", null);
        location.addLabel("decribes counties in the country of Ireland", "en");
        DatatypeProperty latitude = model.createDatatypeProperty(base + "latitude");
        DatatypeProperty longitude = model.createDatatypeProperty(base + "longitude");
        latitude.addLabel("latitude", null);latitude.addLabel("latitude", "en");
        longitude.addComment("longitude", null);longitude.addComment("longitude", "en");
        latitude.setDomain(location);
        latitude.setRange(XSD.xfloat);
        longitude.setDomain(location);
        longitude.setRange(XSD.xfloat);
        location.addSuperClass(model.createCardinalityRestriction(null, latitude, 1));
        location.addSuperClass(model.createCardinalityRestriction(null, longitude, 1));
        
        DatatypeProperty addressLine1 = model.createDatatypeProperty(base + "addressLine1");
        addressLine1.addLabel("address line 1", null);addressLine1.addLabel("address line 1", "en");
        addressLine1.addComment("address line 1", null);addressLine1.addComment("address line 1", "en");
        addressLine1.setDomain(location);
        addressLine1.setRange(XSD.xstring);
        location.addSuperClass(model.createCardinalityRestriction(null, addressLine1, 1));
        //school.addSuperClass(model.createCardinalityRestriction(null, addressLine1, 1));
        DatatypeProperty addressLine2 = model.createDatatypeProperty(base + "addressLine2");
        addressLine2.addLabel("address line 2", null);addressLine2.addLabel("address line 2", "en");
        addressLine2.addComment("address line 2", null);addressLine2.addComment("address line 2", "en");
        addressLine2.setDomain(location);
        addressLine2.setRange(XSD.xstring);
        location.addSuperClass(model.createCardinalityRestriction(null, addressLine2, 1));
        //school.addSuperClass(model.createCardinalityRestriction(null, addressLine2, 1));
        DatatypeProperty addressLine3 = model.createDatatypeProperty(base + "addressLine3");
        addressLine3.addLabel("address line 3", null);addressLine3.addLabel("address line 3", "en");
        addressLine3.addComment("address line 3", null);addressLine3.addComment("address line 3", "en");
        addressLine3.setDomain(location);
        addressLine3.setRange(XSD.xstring);
        location.addSuperClass(model.createCardinalityRestriction(null, addressLine3, 1));
        //school.addSuperClass(model.createCardinalityRestriction(null, addressLine3, 1));
        DatatypeProperty eirCode = model.createDatatypeProperty(base + "EIRcode");
        eirCode.addLabel("EIR code", null);eirCode.addLabel("EIR code", "en");
        eirCode.addComment("EIR code", null);eirCode.addComment("EIR code", "en");
        eirCode.setDomain(location);
        eirCode.setRange(XSD.xstring);
        location.addSuperClass(model.createCardinalityRestriction(null, eirCode, 1));
        //school.addSuperClass(model.createCardinalityRestriction(null, eirCode, 1));
        
        OntClass county = model.createClass(base + "County");
        county.addLabel("Ireland County", null);county.addLabel("Ireland County", "en");
        county.addComment("Ireland Counties", null);county.addComment("Ireland Counties", "en");
        DatatypeProperty countyArea = model.createDatatypeProperty(base + "area");
        countyArea.addLabel("area of county", null);countyArea.addLabel("area of county", "en");
        countyArea.addComment("area of a county", null);countyArea.addComment("area of a county", "en");
        countyArea.setDomain(county);
        countyArea.setRange(XSD.xfloat);
        county.addSuperClass(model.createCardinalityRestriction(null, countyArea, 1));
        SymmetricProperty sharesBoundaryWith = model.createSymmetricProperty(base + "sharesBoundaryWith");
        sharesBoundaryWith.addLabel("shares boundary with", null);sharesBoundaryWith.addLabel("shares boundary with", "en");
        sharesBoundaryWith.addComment("shares boundary with counties", null);sharesBoundaryWith.addComment("shares boundary with counties", "en");
        sharesBoundaryWith.setDomain(county);
        sharesBoundaryWith.setRange(county);
        TransitiveProperty biggerThan = model.createTransitiveProperty(base + "biggerThan");
        biggerThan.addLabel("biggerThan", null);biggerThan.addLabel("biggerThan", "en");
        biggerThan.addComment("conties having larger area than this one", null);biggerThan.addComment("conties having larger area than this one", "en");
        biggerThan.setDomain(county);
        biggerThan.setRange(county);
        TransitiveProperty smallerThan = model.createTransitiveProperty(base + "smallerThan");
        smallerThan.addLabel("smallerThan", null);smallerThan.addLabel("smallerThan", "en");
        smallerThan.addComment("conties having smallerThan area than this one", null);smallerThan.addComment("conties having smallerThan area than this one", "en");
        smallerThan.setDomain(county);
        smallerThan.setRange(county);
        biggerThan.addInverseOf(smallerThan);smallerThan.addInverseOf(biggerThan);
        /*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        /*OntClass schoolType = model.createClass(base + "schoolType");
        schoolType.addComment("school type",null);schoolType.addComment("school type","en");
        schoolType.addLabel("school type",null);schoolType.addLabel("school type","en");
        
        OntClass primarySchoolType = model.createClass(base + "primarySchoolType");
        primarySchoolType.addComment("primary school type of Ireland",null);primarySchoolType.addComment("primary school type of Ireland","en");
        primarySchoolType.addLabel("primary school type of Ireland",null);primarySchoolType.addLabel("primary school type of Ireland","en");
        OntClass secondarySchoolType = model.createClass(base + "secondarySchoolType");
        secondarySchoolType.addComment("secondary school of Ireland",null);secondarySchoolType.addComment("secondary school type of Ireland","en");
        secondarySchoolType.addLabel("secondary school of Ireland",null);secondarySchoolType.addLabel("secondary school type of Ireland","en");
        OntClass communityInstituteType = model.createClass(base + "communityInstituteType");
        communityInstituteType.addComment("community institute type of Ireland",null);communityInstituteType.addComment("community institute type of Ireland","en");
        communityInstituteType.addLabel("community institute type of Ireland",null);communityInstituteType.addLabel("community institute type of Ireland","en");
        OntClass communitySchoolType = model.createClass(base + "communitySchoolType");
        communitySchoolType.addComment("community school type of Ireland",null);communitySchoolType.addComment("community school type of Ireland","en");
        communitySchoolType.addLabel("community school type of Ireland",null);communitySchoolType.addLabel("community school type of Ireland","en");
        OntClass communityCollegeType = model.createClass(base + "communityCollegeType");
        communitySchoolType.addComment("community college type of Ireland",null);communitySchoolType.addComment("community college type of Ireland","en");
        communitySchoolType.addLabel("community college type of Ireland",null);communitySchoolType.addLabel("community college type of Ireland","en");
        communitySchoolType.addSuperClass(communityInstituteType);communityCollegeType.addSuperClass(communityInstituteType);
        OntClass othersType = model.createClass(base + "otherInstituteType");
        othersType.addComment("institute type of Ireland",null);othersType.addComment("institute type of Ireland","en");
        othersType.addLabel("institute type of Ireland",null);othersType.addLabel("institute type of Ireland","en");
        
        primarySchoolType.addDisjointWith(secondarySchoolType);  secondarySchoolType.addDisjointWith(primarySchoolType); 
        othersType.addDisjointWith(primarySchoolType);othersType.addDisjointWith(secondarySchoolType);othersType.addDisjointWith(communityCollegeType);othersType.addDisjointWith(communityInstituteType);
        
        primarySchoolType.addSuperClass(schoolType);
        secondarySchoolType.addSuperClass(schoolType);
        communityInstituteType.addSuperClass(schoolType);
        othersType.addSuperClass(schoolType);*/
        
        OntClass school = model.createClass(base + "school");
        school.addLabel("School", null);
        school.addComment("Ireland schools", null); school.addComment("Ireland schools", "en");
        
        ObjectProperty hasSchoolType = model.createObjectProperty(base + "hasSchoolType");
        hasSchoolType.addLabel("has school type", null);hasSchoolType.addLabel("has school type", "en");
        hasSchoolType.addComment("has school type", null);hasSchoolType.addComment("has school type", "en");
        hasSchoolType.setDomain(school);
        //hasSchoolType.setRange(schoolType);
        DatatypeProperty localAuthority = model.createDatatypeProperty(base + "LocalAuthority");
        localAuthority.addLabel("Local Authority", null);localAuthority.addLabel("Local Authority", "en");
        localAuthority.addComment("Local Authority", null);localAuthority.addComment("Local Authority", "en");
        localAuthority.setDomain(school);
        localAuthority.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, localAuthority, 1));
        DatatypeProperty rollNumber = model.createDatatypeProperty(base + "SchoolRollno");
        rollNumber.addLabel("School Roll no", null);rollNumber.addLabel("School Roll no", "en");
        rollNumber.addComment("School Roll no", null);rollNumber.addComment("School Roll no", "en");
        rollNumber.setDomain(school);
        rollNumber.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, rollNumber, 1));
        DatatypeProperty officialSchoolName = model.createDatatypeProperty(base + "OfficialSchoolName");
        officialSchoolName.addLabel("School Name", null);officialSchoolName.addLabel("School Name", "en");
        officialSchoolName.addComment("School Name", null);officialSchoolName.addComment("School Name", "en");
        officialSchoolName.setDomain(school);
        officialSchoolName.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, officialSchoolName, 1));
        ObjectProperty hasSchools = model.createObjectProperty(base + "hasSchools");
        hasSchools.addLabel("county has this school", null);hasSchools.addLabel("county has this school", "en");
        hasSchools.addComment("county has this school", null);hasSchools.addComment("county has this school", "en");
        hasSchools.setDomain(county);
        hasSchools.setRange(school);
        ObjectProperty inCounty = model.createObjectProperty(base + "inCounty");
        inCounty.addLabel("this school is in this particular county area", null);inCounty.addLabel("this school is in this particular county area", "en");
        inCounty.addComment("this school is in this particular county area", null);inCounty.addComment("this school is in this particular county area", "en");
        inCounty.setDomain(school);
        inCounty.setRange(county);
        hasSchools.addInverseOf(inCounty); inCounty.addInverseOf(hasSchools);
        
        OntClass primarySchool = model.createClass(base + "primarySchool");
        primarySchool.addLabel("Ireland Primary School", null);primarySchool.addLabel("Ireland Primary schools", "en");
        primarySchool.addComment("Ireland Primary schools", null); primarySchool.addComment("Ireland Primary schools", "en");
        primarySchool.addSuperClass(school);
        /*ObjectProperty hasPrimarySchoolType = model.createObjectProperty(base + "hasPrimarySchoolType");
        hasPrimarySchoolType.addLabel("has primary school type", null);hasPrimarySchoolType.addLabel("has primary school type", "en");
        hasPrimarySchoolType.addComment("has primary school type", null);hasPrimarySchoolType.addComment("has primary school type", "en");
        hasPrimarySchoolType.setDomain(primarySchool);*/
        //hasPrimarySchoolType.setRange(primarySchoolType);
        
        OntClass secondarySchool = model.createClass(base + "secondarySchool");
        secondarySchool.addLabel("Ireland Secondary School", null);secondarySchool.addLabel("Ireland Secondary schools", "en");
        secondarySchool.addComment("Ireland Secondary schools", null); secondarySchool.addComment("Ireland Secondary schools", "en");
        secondarySchool.addSuperClass(school);
        /*ObjectProperty hasSecondarySchoolType = model.createObjectProperty(base + "hasSecondarySchoolType");
        hasSecondarySchoolType.addLabel("has Secondary school type", null);hasSecondarySchoolType.addLabel("has Secondary school type", "en");
        hasSecondarySchoolType.addComment("has Secondary school type", null);hasSecondarySchoolType.addComment("has Secondary school type", "en");
        hasSecondarySchoolType.setDomain(secondarySchool);*/
        //hasSecondarySchoolType.setRange(secondarySchoolType);
        
        OntClass communitySchool = model.createClass(base + "communitySchool");
        communitySchool.addLabel("Ireland Community School", null);communitySchool.addLabel("Ireland Community schools", "en");
        communitySchool.addComment("Ireland Community schools", null); communitySchool.addComment("Ireland Community schools", "en");
        communitySchool.addSuperClass(school);
        /*ObjectProperty hasCommunitySchoolType = model.createObjectProperty(base + "hasCommunitySchoolType");
        hasCommunitySchoolType.addLabel("has community school type", null);hasCommunitySchoolType.addLabel("has community school type", "en");
        hasCommunitySchoolType.addComment("has community school type", null);hasCommunitySchoolType.addComment("has community school type", "en");
        hasCommunitySchoolType.setDomain(communitySchool);*/
        //hasCommunitySchoolType.setRange(communitySchoolType);
        
        OntClass communityCollege = model.createClass(base + "communityCollegeSchool");
        communityCollege.addLabel("community college School", null);communityCollege.addLabel("community college schools", "en");
        communityCollege.addComment("community college schools", null); communityCollege.addComment("community college schools", "en");
        communityCollege.addSuperClass(school);
        /*ObjectProperty hasCommunityCollegeType = model.createObjectProperty(base + "hasCommunityCollegechoolType");
        hasCommunityCollegeType.addLabel("has Community College school type", null);hasCommunityCollegeType.addLabel("has Community College school type", "en");
        hasCommunityCollegeType.addComment("has Community College school type", null);hasCommunityCollegeType.addComment("has Community College school type", "en");
        hasCommunityCollegeType.setDomain(communityCollege);*/
        //hasCommunityCollegeType.setRange(communityCollegeType);

        OntClass othersSchool = model.createClass(base + "othersSchool");
        othersSchool.addLabel("Ireland others School", null);othersSchool.addLabel("Ireland others schools", "en");
        othersSchool.addComment("Ireland others schools", null); othersSchool.addComment("Ireland others schools", "en");
        othersSchool.addSuperClass(school);
        /*ObjectProperty hasOthersSchoolType = model.createObjectProperty(base + "hasOthersSchoolType");
        hasOthersSchoolType.addLabel("has Others school type", null);hasOthersSchoolType.addLabel("has Others school type", "en");
        hasOthersSchoolType.addComment("has Others school type", null);hasOthersSchoolType.addComment("has Others school type", "en");
        hasOthersSchoolType.setDomain(othersSchool);*/
        //hasOthersSchoolType.setRange(othersType);
        
        //Ontology completed -- write to file
        //model.write(new FileWriter(groupE_Ontology), "TURTLE");
        //System.out.println("Turtle file for groupE_Ontology created");
        
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
        while (schoolIterator.hasNext()) {
            Resource resource = schoolIterator.next();
            String currentRollNumber=resource.getProperty(rollNumberProperty).getString();
            
            Individual currentLocation=location.asIndividual(/*base+"location_"+currentRollNumber*/);
            float curentLongitude=resource.getProperty(longitudeProperty).getFloat(); float curentLatitude=resource.getProperty(latitudeProperty).getFloat();
            currentLocation.addLiteral(latitude, curentLatitude);
            currentLocation.addLiteral(longitude, curentLongitude);
            if(resource.getProperty(addressLine1Property)!=null)
            	currentLocation.addLiteral(addressLine1, resource.getProperty(addressLine1Property).getString());
            if(resource.getProperty(addressLine2Property)!=null)
            	currentLocation.addLiteral(addressLine2, resource.getProperty(addressLine2Property).getString());
            if(resource.getProperty(addressLine3Property)!=null)
            	currentLocation.addLiteral(addressLine3, resource.getProperty(addressLine3Property).getString());
            currentLocation.addLiteral(eirCode, resource.getProperty(eirCodeProperty).getString());
            
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
                	//currentSchoolType=communityCollegeType.asIndividual();
                	//currentSchool.addProperty(hasCommunityCollegeType, currentSchoolType);
            		}
            	else {
            		currentSchool=communitySchool.createIndividual(base+"school_"+currentRollNumber);
                	//currentSchoolType=communitySchoolType.asIndividual();
                	//currentSchool.addProperty(hasCommunitySchoolType, currentSchoolType);
            	}
            }
            if(! flag) {
            	currentSchool=othersSchool.createIndividual(base+"school_"+currentRollNumber);
            	//currentSchoolType=othersType.asIndividual();
            	//currentSchool.addProperty(hasOthersSchoolType, currentSchoolType);
            }
            
            
            currentSchool.addProperty(localAuthority, resource.getProperty(localauthorityProperty).getString());
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
	}

}
