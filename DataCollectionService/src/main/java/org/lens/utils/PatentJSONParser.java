package org.lens.utils;

import java.io.InputStream;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.utils.jaxb.Article;
import org.utils.jaxb.ArticleId;
import org.utils.jaxb.ArticleIdList;
import org.utils.jaxb.ArticleMeta;
import org.utils.jaxb.Person;

public class PatentJSONParser {

	private InputStream inputStream;
	private String dockey;
	private Article article;
	private ArticleMeta articleMeta;
	private List<Person> inventors;
	public PatentJSONParser(InputStream inputStream) {
		this.inputStream = inputStream;
		article=new Article();
		articleMeta=new ArticleMeta();
		inventors=new ArrayList<Person>();		
	}
	public PatentJSONParser(InputStream inputStream, String dockey) {
		this.inputStream = inputStream;
		this.dockey=dockey;
	}

	
	public String parseToXML(String dockey) {

		JSONObject jsonObject1 = new JSONObject(new JSONTokener(inputStream));
		try {
			ArticleIdList articleIdList=new ArticleIdList();
			ArticleId articleId=new ArticleId();
			articleId.setContent(dockey);
			articleIdList.getArticleId().add(articleId);
			articleMeta.setArticleIdList(articleIdList);
			articleId.setContent(dockey);
			if (jsonObject1.has("title")) {
				articleMeta.setTitle(jsonObject1.getJSONObject("title")
						.getString("text"));
			}
			if (jsonObject1.has("applicants")) {
				
				JSONObject jsonApplicants = jsonObject1
						.getJSONObject("applicants");
				JSONArray jsonApplicantArray = jsonApplicants
						.getJSONArray("texts");
				List<String> applicantsList = new ArrayList<String>();
				for (int i = 0; i < jsonApplicantArray.length(); i++) {
					
					String applicantText = jsonApplicantArray.getString(i);
					applicantsList.add(applicantText);
				}
			}
			if (jsonObject1.has("inventors")) {
				JSONObject jsonInventors = jsonObject1
						.getJSONObject("inventors");
				JSONArray jsonInventorArray = jsonInventors
						.getJSONArray("texts");
				List<String> inventorsList = new ArrayList<String>();
				for (int i = 0; i < jsonInventorArray.length(); i++) {
					Person person=new Person();
					String inventorText = jsonInventorArray.getString(i);
					String[] names=inventorText.split(" ");
					if(names.length==3){
						person.setFirstName(names[1]);
						person.setMiddleName(names[2]);
						person.setLastName(names[0]);
					}else if(names.length==2){
						person.setFirstName(names[1]);
						person.setLastName(names[0]);
					}
					inventors.add(person);
					article.getPeople().add(person);
				}
			}
			
			if (jsonObject1.has("abstract")) {
				Element abstractElement = outputdocument
						.createElement("Abstract");
				String abstractText = jsonObject1.getJSONObject("abstract")
						.getString("text");
				abstractElement.setTextContent(abstractText);
				root.appendChild(abstractElement);
				patent.setAbstracts(abstractText);
			}
			if (instream2 != null) {
				Element claims = outputdocument.createElement("Claims");
				root.appendChild(claims);
				JSONObject jsonObject2 = new JSONObject(new JSONTokener(
						instream2));
				JSONArray jsonArray = jsonObject2.getJSONArray("elements");
				String ab = "";
				for (int i = 0; i < jsonArray.length(); i++) {
					Element claim = outputdocument.createElement("Claim");
					String claimText = jsonArray.getJSONObject(i).get("text")
							.toString();
					claim.setAttribute("ClaimID", "" + (i + 1));
					claim.setTextContent(claimText);
					claims.appendChild(claim);
					ab += claimText;
				}
				patent.setClaim(ab);
				if (!ab.equals("")) {
					docElement.setId(dockey);
					docElement.setContent(ab);
					docElement.setType("Patent");
				}
			}
			if (jsonObject1.has("classifications")) {
				Element classifications = outputdocument
						.createElement("Classifications");
				root.appendChild(classifications);
				JSONArray classArray = jsonObject1
						.getJSONArray("classifications");
				Map<String, String> classMap = new HashMap<String, String>();
				for (int i = 0; i < classArray.length(); i++) {
					Element classification = outputdocument
							.createElement("Classification");
					JSONObject object = classArray.getJSONObject(i);
					String classType = object.getString("classificationType");
					classification.setAttribute("Type", classType);
					JSONArray classCodes = object
							.getJSONArray("classificationCodes");
					String codes = classCodes.toString();
					classification.setTextContent(codes);
					codes = codes.substring(1, codes.length() - 1);

					classMap.put(classType, codes);
					classifications.appendChild(classification);
				}
				patent.setClassification(classMap);
			}

			if (jsonObject1.has("publicationDate")) {
				Element publicationDate = outputdocument
						.createElement("PublicationDate");
				String date = jsonObject1.getString("publicationDate");
				publicationDate.setTextContent(date);
				root.appendChild(publicationDate);
				patent.setPublicationDate(date);
			}
			if (jsonObject1.has("applicationNumber")) {
				Element applicationNumber = outputdocument
						.createElement("ApplicationNumber");
				String applicationNumberText = jsonObject1
						.getString("applicationNumber");
				applicationNumber.setTextContent(applicationNumberText);
				root.appendChild(applicationNumber);
				patent.setApplicationNumber(applicationNumberText);
			}
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans;
			try {
				trans = transfac.newTransformer();

				trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				trans.setOutputProperty(OutputKeys.INDENT, "yes");
				trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				trans.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
				// create string from xml tree
				StringWriter sw = new StringWriter();
				StreamResult result = new StreamResult(sw);
				DOMSource source = new DOMSource(outputdocument);
				try {
					trans.transform(source, result);
					return sw.toString();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
					return "error";
				}

			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error";
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}

	}

	public void parseJSON(){
		JsonParser jsonParser=Json.createParser(inputStream);
		while(jsonParser.hasNext()){

			   JsonParser.Event event = jsonParser.next();
			   switch(event) {
			      case START_ARRAY:
			      case END_ARRAY:
			      case START_OBJECT:
			      case END_OBJECT:
			      case VALUE_FALSE:
			      case VALUE_NULL:
			      case VALUE_TRUE:
			         System.out.println(event.toString());
			         break;
			      case KEY_NAME:
			         System.out.print(event.toString() + " " +
			                          jsonParser.getString() + " - ");
			         break;
			      case VALUE_STRING:
			      case VALUE_NUMBER:
			         System.out.println(event.toString() + " " +
			                            jsonParser.getString());
			         break;
			   }
			   
		}
		
	}
}
