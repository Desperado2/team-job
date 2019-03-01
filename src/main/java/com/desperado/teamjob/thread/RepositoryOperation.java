package com.desperado.teamjob.thread;

import com.desperado.teamjob.config.SvnContensConfig;
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
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.io.IOException;


public class RepositoryOperation {
	// 声明SVN客户端管理类
	private static SVNClientManager ourClientManager;
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
	public static String cloneWithAuthentication(String sshUrl,String targetPath) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
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
		File localPath = new File(targetPath+RepositoryHelper.getRepoDir(sshUrl));
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



	public static String cloneSVNWithAuthentication(String sshUrl, SvnContensConfig svnContensConfig) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		// 初始化库。 必须先执行此操作。具体操作封装在setupLibrary方法中。
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
		// 相关变量赋值
		SVNURL repositoryURL = null;
		try {
			repositoryURL = SVNURL.parseURIEncoded(sshUrl);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		// 实例化客户端管理类
		ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, svnContensConfig.getSvnUserName(), svnContensConfig.getSvnPassWord());
		// 要把版本库的内容check out到的目录
		File wcDir = new File(svnContensConfig.getTargetPath());

		// 通过客户端管理类获得updateClient类的实例。
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();

		updateClient.setIgnoreExternals(false);

		// 执行check out 操作，返回工作副本的版本号。
		long workingVersion = -1;
		try {
			if (!wcDir.exists()) {
				workingVersion = updateClient.doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, false);
			} else {
				ourClientManager.getWCClient().doCleanup(wcDir);
				workingVersion = updateClient.doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, false);
			}
		} catch (SVNException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("把版本：" + workingVersion + " check out 到目录：" + wcDir + "中。");
		return null;
	}
}
