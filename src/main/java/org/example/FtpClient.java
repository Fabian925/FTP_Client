package org.example;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.InetAddress;

public class FtpClient {
	private String server;
	private int port;
	private FTPClient ftpClient;

	public FtpClient() {}

	/**
	 * Connects the Client to an FTP Server.
	 * @param hostname IP-Address or Domain of the FTP-Server
	 * @param port the Control-Port of the FTP-Server
	 * @return true if the connection was successful, false if the Server wasn't found.
	 */
	public boolean connect(String hostname, int port) {
		ftpClient = new FTPClient();
		try {
			if (org.roalter.InetAddresses.isValidInet4Address(hostname))
				ftpClient.connect(InetAddress.getByName(hostname), port);
			else
				ftpClient.connect(hostname, port);
		} catch (IOException e) {
			return false;
		}
		int replyCode = ftpClient.getReplyCode();
		return FTPReply.isPositiveCompletion(replyCode);
	}

	public boolean login(String username, String password) {
		try {
			return ftpClient.login(username, password);
		} catch (IOException e) {
			return false;
		}
	}

	public void addFile(String pathToFile, String ftpPath) {
		if (pathToFile != null)
			addFile(new File(pathToFile), ftpPath);
	}

	public void addFile(File file, String path) {
		addFile(file, path, file.getName());
	}

	public void addFile(File file, String path, String name) {
		try {
			InputStream fileStream = new DataInputStream(new FileInputStream(file));
			ftpClient.storeFile(path + name, fileStream);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void loadFileSystem() {
		try {
			FTPFile[] files = ftpClient.listFiles();
			for (FTPFile file : files) {
				System.out.println(file);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void disconnect() {
		try {
			ftpClient.disconnect();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
