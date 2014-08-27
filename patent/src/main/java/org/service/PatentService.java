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
import javax.ws.rs.core.MediaType;

import org.lens.crawler.LensCujoConnection;
import org.lens.crawler.MultithreadsJSONTextConnection;
import org.lens.crawler.PatentParameter;

@Path("/search")
public class PatentService {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("stream")
	public InputStream getStream(final PatentParameter param) {

		final PipedOutputStream sink = new PipedOutputStream();
		PipedInputStream source;
		try {
			source = new PipedInputStream(sink);
			// apparently we need to write to the PipedOutputStream in a
			// separate thread
			LensCujoConnection connection = new LensCujoConnection(
					param.toString());
			List<String> keys = connection.getKeys();
			MultithreadsJSONTextConnection multiConnection = new MultithreadsJSONTextConnection(
					keys);
			final ArrayList<Future<InputStream>> futures = multiConnection
					.getFutureList();
			Runnable runnable = new Runnable() {
				public void run() {

					ZipOutputStream zip = new ZipOutputStream(sink);
					PrintStream writer = new PrintStream(
							new BufferedOutputStream(zip));

					if (futures.size() > 100) {
						int i = 0;
						int length=futures.size();
						while (i < length) {
							try {
								BufferedReader br1 = new BufferedReader(
										new InputStreamReader(futures.get(i)
												.get()));
								int mode = i % 100;
								if (mode == 0) {
									if (i != 0) {
										writer.println("]");
										writer.flush();
										zip.closeEntry();
									}
									zip.putNextEntry(new ZipEntry("patent_"
											+ (i + 1) + "_" + UUID.randomUUID()
											+ ".json"));
									writer.println("[");
									String s1 = "";
									while ((s1 = br1.readLine()) != null) {
										writer.println(s1);
									}
									if (i == length-1) {
										writer.println("]");
										writer.flush();
										zip.closeEntry();
									}
								} else {
									String s1 = "";
										writer.println(",");
									while ((s1 = br1.readLine()) != null) {
										writer.println(s1);
									}
									if (i == length-1) {
										writer.println("]");
										writer.flush();
										zip.closeEntry();
									}
								}
							} catch (InterruptedException | ExecutionException
									| IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							i++;

						}
						writer.flush();
						writer.close();
					} else {
						try {
							zip.putNextEntry(new ZipEntry("patent_"
									+ UUID.randomUUID() + ".json"));
							writer.println("[");
							for (int i = 0; i < futures.size(); i++) {
								if (i != 0)
									writer.println(",");
								try {
									// streams.add(future.get());
									BufferedReader br1 = new BufferedReader(
											new InputStreamReader(futures
													.get(i).get()));
									try {
										String s1 = "";
										br1.readLine();
										while ((s1 = br1.readLine()) != null) {
											writer.println(s1);
										}

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
							writer.println("]");
							writer.flush();
							zip.closeEntry();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						writer.flush();
						writer.close();
					}
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
