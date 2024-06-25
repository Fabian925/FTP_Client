import org.example.FtpClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import java.io.File;

public class Test {

	private FakeFtpServer server;
	private FtpClient client;

	private final String USERNAME = "max";
	private final String PASSWORD = "max123";

	@Before
	public void setup() {
		server = new FakeFtpServer();
		server.addUserAccount(new UserAccount(USERNAME, PASSWORD, "/"));
		var fileSystem = new UnixFakeFileSystem();
		fileSystem.add(new DirectoryEntry("/"));
		server.setFileSystem(fileSystem);
		server.setServerControlPort(0);
		server.start();

		client = new FtpClient();
		Assert.assertTrue(client.connect("localhost", server.getServerControlPort()));
		Assert.assertTrue(client.login(USERNAME, PASSWORD));
	}

	@org.junit.Test
	public void connectIPAddress() {
		var localServer = new FakeFtpServer();
		localServer.setServerControlPort(0);
		localServer.start();
		var localClient = new FtpClient();
		Assert.assertTrue(localClient.connect("127.0.0.1", localServer.getServerControlPort()));
		localClient.disconnect();
		localServer.stop();
	}

	@org.junit.Test
	public void sendFile() {
		File file = new File("src/test/files/upload.txt");
		client.addFile(file, "/");
		client.loadFileSystem();
		Assert.assertTrue(true);
	}

	@After
	public void teardown() {
		client.disconnect();
		server.stop();
	}
}
