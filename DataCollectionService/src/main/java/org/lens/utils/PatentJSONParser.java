package org.lens.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.utils.UnifiedID;
import org.utils.jaxb.Article;
import org.utils.jaxb.ArticleId;
import org.utils.jaxb.ArticleIdList;
import org.utils.jaxb.ArticleMeta;
import org.utils.jaxb.Date;
import org.utils.jaxb.Keyword;
import org.utils.jaxb.KeywordList;
import org.utils.jaxb.Patent;
import org.utils.jaxb.Person;
import org.utils.jaxb.Priority;

public class PatentJSONParser {

	private InputStream inputStream;
	private String dockey;
	private Article article;
	private ArticleMeta articleMeta;
	private List<Person> inventors;

	public PatentJSONParser(InputStream inputStream, String dockey) {
		this.inputStream = inputStream;
		this.dockey = dockey;
		article = new Article();
		articleMeta = new ArticleMeta();
		inventors = new ArrayList<Person>();
	}

	public ArticleMeta getArticleMeta() {
		if (articleMeta != null)
			return articleMeta;
		return null;
	}

	public List<Person> getInventors() {
		if (inventors.size() > 0) {
			return inventors;
		}
		return null;
	}

	public Article getArticle() {
		if (article != null)
			return article;
		return null;
	}

	public void parse() {

		JSONObject jsonObject1 = new JSONObject(new JSONTokener(inputStream));

		ArticleIdList articleIdList = new ArticleIdList();
		ArticleId articleId = new ArticleId();
		articleId.setIdType("patent");
		articleId.setContent(dockey);
		Patent patent = new Patent();
		articleId.setContent(dockey);
		articleIdList.getArticleId().add(articleId);	
		if (jsonObject1.has("title")) {
			articleMeta.setTitle(jsonObject1.getJSONObject("title").getString(
					"text"));
		}
		if (jsonObject1.has("applicants")) {

			JSONObject jsonApplicants = jsonObject1.getJSONObject("applicants");
			JSONArray jsonApplicantArray = jsonApplicants.getJSONArray("texts");
			List<String> applicantsList = new ArrayList<String>();
			for (int i = 0; i < jsonApplicantArray.length(); i++) {

				String applicantText = jsonApplicantArray.getString(i);
				applicantsList.add(applicantText);
			}
		}
		if (jsonObject1.has("inventors")) {
			JSONObject jsonInventors = jsonObject1.getJSONObject("inventors");
			JSONArray jsonInventorArray = jsonInventors.getJSONArray("texts");
			for (int i = 0; i < jsonInventorArray.length(); i++) {
				Person person = new Person();
				String inventorText = jsonInventorArray.getString(i);
				String[] names = inventorText.split(" ");
				if (names.length == 3) {
					person.setFirstName(names[1]);
					person.setMiddleName(names[2]);
					person.setLastName(names[0]);
				} else if (names.length == 2) {
					person.setFirstName(names[1]);
					person.setLastName(names[0]);
				}
				person.setFullName(inventorText);
				person.setType("inventor");
				person.setId(UnifiedID.generateID("IN"));
				inventors.add(person);
				article.getPeople().add(person);
			}
		}

		if (jsonObject1.has("abstract")) {

			String abstractText = jsonObject1.getJSONObject("abstract")
					.getString("text");

			articleMeta.setArticleAbstract(abstractText);
		}
		if (jsonObject1.has("publicationNumber")) {
			articleId = new ArticleId();
			articleId.setContent(jsonObject1.get("publicationNumber")
					.toString());
			articleId.setIdType("publicationNumber");
			articleIdList.getArticleId().add(articleId);
		}
		//
		if (jsonObject1.has("classifications")) {
			KeywordList keywordList = new KeywordList();
			JSONArray classArray = jsonObject1.getJSONArray("classifications");
			for (int i = 0; i < classArray.length(); i++) {
				JSONObject object = classArray.getJSONObject(i);
				String classType = object.getString("classificationType").toString();

				if(!classType.isEmpty()){
				JSONArray classCodes = object
						.getJSONArray("classificationCodes");

				for (int index = 0; index < classCodes.length(); index++) {
					String code = classCodes.get(index).toString();
					PatentCodeFinder patentCodeFinder=new PatentCodeFinder(classType,code);
					patentCodeFinder.check();
					classType=patentCodeFinder.getType();
					code=patentCodeFinder.getCode();
					if (classType!=null) {
						Classification classi = new Classification(classType,
								code);
						Keyword keyword = new Keyword();
						if (!classi.translateCodeToText().equals("")) {
							keyword.setContent(classi.translateCodeToText());
							keywordList.getKeyword().add(keyword);
						}
					}

					articleMeta.setKeywords(keywordList);
				}
				}
			}

		}

		if (jsonObject1.has("publicationDate")) {
			// 2010-10-14
			String date = jsonObject1.getString("publicationDate");
			String[] dates = date.split("-");
			Date da = new Date();
			if (dates.length == 3) {
				da.setDay(Integer.parseInt(dates[2]));
				da.setMonth(Integer.parseInt(dates[1]));
				da.setYear(Integer.parseInt(dates[0]));
			} else if (dates.length == 2) {
				da.setMonth(Integer.parseInt(dates[1]));
				da.setYear(Integer.parseInt(dates[0]));
			} else if (dates.length == 1)
				da.setYear(Integer.parseInt(dates[0]));
			articleMeta.setPubDate(da);
		}

		if (jsonObject1.has("jurisdiction"))
			patent.setJuristiction(jsonObject1.get("jurisdiction").toString());
		if (jsonObject1.has("priorities")) {
			JSONArray priorities = jsonObject1.getJSONArray("priorities");
			for (int index = 0; index < priorities.length(); index++) {
				JSONObject ob = priorities.getJSONObject(index);
				Priority priority = new Priority();
				priority.setCountryCode(ob.get("countryCode").toString());
				priority.setDocumentDate(ob.get("documentDate").toString());
				priority.setDocumentNumber(ob.get("documentNumber").toString());
				priority.setDocumentKind(ob.get("documentKind").toString());
				patent.getPriority().add(priority);
			}

		}
		if (articleIdList.getArticleId().size() > 0)
			articleMeta.setArticleIdList(articleIdList);
		articleMeta.setId(UnifiedID.generateID("PA"));
		articleMeta.setArticleType(patent);
		article.setArticleMeta(articleMeta);
	}

	public void parseJSON() {
		JsonParser jsonParser = Json.createParser(inputStream);
		while (jsonParser.hasNext()) {

			JsonParser.Event event = jsonParser.next();
			switch (event) {
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
				System.out.print(event.toString() + " "
						+ jsonParser.getString() + " - ");
				break;
			case VALUE_STRING:
			case VALUE_NUMBER:
				System.out.println(event.toString() + " "
						+ jsonParser.getString());
				break;
			}

		}

	}
	
	public static void main(String[] args) throws FileNotFoundException{
		InputStream in=new  FileInputStream("src/test/files/frontpage.json");
		PatentJSONParser pa=new PatentJSONParser(in, "US_A1_2010258342");
		System.out.print(pa.getArticle().getArticleMeta().getKeywords().getKeyword().get(0));
		
	}
}
