package com.desperado.teamjob.thread;

import com.desperado.teamjob.helper.RepositoryHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class RepositoryOperation {
	private static Logger logger = LoggerFactory.getLogger(RepositoryOperation.class);

	/**
	 * 使用ssh方式clone一个库
	 * 
	 * @param sshUrl eg. git@git.souche-inc.com:xx/xx.git
	 * @return dir clone在那个目录下面
	 * @throws IOException
	 * @throws InvalidRemoteException
	 * @throws TransportException
	 * @throws GitAPIException
	 */
	public static String cloneWithAuthentication(String sshUrl) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		// this is necessary when the remote host does not have a valid
		// certificate, ideally we would install the certificate in the JVM
		// instead of this unsecure workaround!
		CredentialsProvider allowHosts = new CredentialsProvider() {
			@Override
			public boolean supports(CredentialItem... items) {
				for (CredentialItem item : items) {
					if ((item instanceof CredentialItem.YesNoType)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean get(URIish uri, CredentialItem... items) throws UnsupportedCredentialItem {
				for (CredentialItem item : items) {
					if (item instanceof CredentialItem.YesNoType) {
						((CredentialItem.YesNoType) item).setValue(true);
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean isInteractive() {
				return false;
			}
		};
		// prepare a new folder for the cloned repository
		//File localPath = new File(RepositoryHelper.createGitDir(sshUrl));
		File localPath = new File("c:/code/testgit/"+RepositoryHelper.getRepoDir(sshUrl));
		if (!localPath.exists()) {
			localPath.mkdirs();
		}
		// then clone
		logger.info("Cloning from " + sshUrl + " to " + localPath);
		try (Git repo = Git.cloneRepository()
				.setURI(sshUrl)
				.setDirectory(localPath)
				.setCredentialsProvider(allowHosts)
				.call();) {
			return repo.getRepository().getDirectory().getAbsolutePath();
		}catch(Exception e) {
			logger.error("clone error:{}", e);
			return null;
		}
	}

	public static String cloneWithHttps(String remoteUrl) throws IOException {
		// prepare a new folder for the cloned repository
		File localPath = new File(RepositoryHelper.createGitDir(remoteUrl));
		if (!localPath.delete()) {
			throw new IOException("Could not delete temporary file " + localPath);
		}
		try (Git repo = Git.cloneRepository().setURI(remoteUrl).setDirectory(localPath).call()) {
			// then clone
			logger.info("Cloning from " + remoteUrl + " to " + localPath);
			return repo.getRepository().getDirectory().getAbsolutePath();
		} catch (Exception e) {
			// Note: the call() returns an opened repository already which needs
			// to be closed to avoid file handle leaks!
			logger.error("{}", e);
		}
		return null;
	}
}
