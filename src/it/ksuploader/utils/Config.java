package it.ksuploader.utils;

import it.ksuploader.main.KSUploaderServer;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config extends Properties {

	private Logger logger = KSUploaderServer.logger;
	private String folder;
	private String web_url;
	private String pass;
	private long folderSize;
	private long maxFileSize;
	private int port;

	public Config() {
		InputStream inputStream;
		try {

			if (!new File("server.properties").exists()) {
				this.store(new FileOutputStream("server.properties"), null);
			}

			inputStream = new FileInputStream("server.properties");
			this.load(inputStream);
			inputStream.close();

			boolean correct_config = false;

			// old address
			if (this.getProperty("folder") == null || this.getProperty("folder").isEmpty()) {
				this.setProperty("folder", "files");
				correct_config = true;
				logger.log(Level.FINE, "[Config] Setting default folder");
			}
			this.folder = this.getProperty("folder");

			// Web url
			if (this.getProperty("web_url") == null || this.getProperty("web_url").isEmpty()) {
				this.setProperty("web_url", "http://domain.com/");
				correct_config = true;
				logger.log(Level.FINE, "[Config] Setting default web_url");
			}
			this.web_url = this.getProperty("web_url");

			// Password
			if (this.getProperty("password") == null || this.getProperty("password").isEmpty()) {
				this.setProperty("password", "pass");
				correct_config = true;
				logger.log(Level.FINE, "[Config] Setting default password");
			}
			this.pass = this.getProperty("password");

			// Port
			if (this.getProperty("port") == null || this.getProperty("port").isEmpty()) {
				this.setProperty("port", "4030");
				correct_config = true;
				logger.log(Level.FINE, "[Config] Setting default port");
			}
			this.port = Integer.parseInt(this.getProperty("port"));

			// Folder size
			if (this.getProperty("folder_size(MB)") == null || this.getProperty("folder_size(MB)").isEmpty()) {
				this.setProperty("folder_size(MB)", "4096");
				correct_config = true;
				logger.log(Level.FINE, "Setting default folder_size(MB)");
			}
			this.folderSize = Long.parseLong(this.getProperty("folder_size(MB)")) * 1048576;

			// File size
			if (this.getProperty("max_file_size(MB)") == null || this.getProperty("max_file_size(MB)").isEmpty()) {
				this.setProperty("max_file_size(MB)", "512");
				correct_config = true;
				logger.log(Level.FINE, "[Config] Setting default max_file_size(MB)");
			}
			this.maxFileSize = Long.parseLong(this.getProperty("max_file_size(MB)")) * 1048576;

			if (correct_config)
				this.store(new FileOutputStream("server.properties"), null);

		} catch (IOException ex) {
			this.logger.log(Level.SEVERE, "Error parsing config", ex);
		}
	}

	public String getFolder() {
		return folder;
	}

	public String getPass() {
		return pass;
	}

	public String getWebUrl() {
		return web_url;
	}

	public int getPort() {
		return port;
	}

	public long getFolderSize() {
		return folderSize;
	}

	public long getMaxFileSize() {
		return maxFileSize;
	}

	private boolean save() {
		try {
			this.store(new FileOutputStream("server.properties"), null);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Can't save config", e);
			return false;
		}
		logger.log(Level.FINE, "Config saved");
		return true;
	}

	public boolean setPort(String port) {
		this.port = Integer.parseInt(this.getProperty("port"));
		this.setProperty("port", port);
		return this.save();
	}

	public boolean setFolder(String folder) {
		this.folder = folder;
		this.setProperty("folder", folder);
		return this.save();
	}

	public boolean setWeb_url(String web_url) {
		this.web_url = web_url;
		this.setProperty("web_url", web_url);
		return this.save();
	}

	public boolean setPass(String pass) {
		this.pass = pass;
		this.setProperty("password", pass);
		return this.save();
	}

	public boolean setFolderSize(String folderSize) {
		this.folderSize = Long.parseLong(this.getProperty("folder_size(MB)")) * 1048576;
		this.setProperty("folder_size(MB)", folderSize);
		return this.save();
	}

	public boolean setMaxFileSize(String maxFileSize) {
		this.maxFileSize = Long.parseLong(this.getProperty("max_file_size(MB)")) * 1048576;
		this.setProperty("max_file_size(MB)", maxFileSize);
		return this.save();
	}


}