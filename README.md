DataCollectionService
=============
This project contains web services, for crawling document metadata from ([lens](http://www.lens.org/lens/)), ([pubmed](http://www.ncbi.nlm.nih.gov/pubmed/)), and ([IEEE](http://ieeexplore.ieee.org/Xplore/home.jsp)) and aggregate the metadata into unified structure.  

![alt tag](https://github.com/wwhou/pubmedservice/blob/master/articleStructure.png)

Use the web services
=============
There are three clients being provided for using the services. The default URL is "http://localhost:8080/DataCollectionService"

[1. Patent crawler](https://github.com/wwhou/pubmedservice/blob/master/DataCollectionService/src/main/java/org/IEEE/client/IEEEClient.java) 

[2. IEEE crawler](https://github.com/wwhou/pubmedservice/blob/master/DataCollectionService/src/main/java/org/lens/client/PatentClient.java)

[3. Pubmed crawler](https://github.com/wwhou/pubmedservice/blob/master/DataCollectionService/src/main/java/org/pubMed/client/PubMedClient.java)


