package org.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.entity.InputStreamEntity;
import org.crawlers.EFetch;
import org.jaxb.eSearch.Id;
import org.utils.ESearchResult;
import org.utils.PubMedMultiThreadsCall;
import org.utils.PubMedParameter;

@Path("/search")
public class PubmedService {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("stream")
	public InputStream getX(final PubMedParameter pubMedParam) {
		ESearchResult eSearchResult = new ESearchResult(pubMedParam);
		final List<Id> idList = eSearchResult.getIdList();

		final PipedOutputStream sink = new PipedOutputStream();
		PipedInputStream source;
		try {
			source = new PipedInputStream(sink);
			// apparently we need to write to the PipedOutputStream in a
			// separate thread
			Runnable runnable = new Runnable() {
				public void run() {
					// PrintStream => BufferedOutputStream =>
					// ZipOutputStream => PipedOutputStream
					ZipOutputStream zip = new ZipOutputStream(sink);
					PrintStream writer = new PrintStream(
							new BufferedOutputStream(zip));

					try {
						// break the strings up into multiple files
						if (pubMedParam.getRetMax() > 100) {
							PubMedMultiThreadsCall multiThreadCall = new PubMedMultiThreadsCall(
									idList, pubMedParam.getDatabase());
							List<Future<InputStream>> futures = multiThreadCall
									.getFutures();
							for (Future<InputStream> future : futures) {
								try {
									//streams.add(future.get());
									BufferedReader br1 = new BufferedReader(
											new InputStreamReader(future.get()));
									try {
										String s1 = "";
										zip.putNextEntry(new ZipEntry(
												"pubmed_size"
														+ pubMedParam
																.getRetMax()
														+ "_"
														+ UUID.randomUUID()
														+ ".xml"));
										while ((s1 = br1.readLine()) != null) {
											writer.println(s1);
										}

										writer.flush();
										zip.closeEntry();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} catch (InterruptedException
										| ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} else {

							String idString1 = "db="
									+ pubMedParam.getDatabase() + "&id=";
							int index1 = 0;
							for (Id id : idList) {
								if (index1 > 0)
									idString1 += ",";
								idString1 += id.getContent();
								index1++;
							}
							EFetch efetch = new EFetch(idString1, true);
							InputStream inputStream = efetch.getSearchResult();
							BufferedReader br1 = new BufferedReader(
									new InputStreamReader(inputStream));
							try {
								String s1 = "";
								zip.putNextEntry(new ZipEntry("pubmed_size"
										+ pubMedParam.getRetMax() + "_"
										+ UUID.randomUUID() + ".xml"));
								while ((s1 = br1.readLine()) != null) {
									writer.println(s1);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							writer.flush();
							zip.closeEntry();

						}

					} catch (IOException e) {
					}
					writer.flush();
					writer.close();
				}
			};
			Thread writerThread = new Thread(runnable, "FileGenerator");
			writerThread.start();

			return source;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;

	}
}
