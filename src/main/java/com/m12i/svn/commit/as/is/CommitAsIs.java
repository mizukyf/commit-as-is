package com.m12i.svn.commit.as.is;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
//import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNStatusHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class CommitAsIs {
	
	public void execute(final Parameters params) throws SVNException {
		
		final File workingCopyPath = new File(params.getWorkingCopyPath());
		if (!workingCopyPath.isDirectory()) {
			throw new IllegalArgumentException();
		}
		
//		final SVNURL repositoryURL = SVNURL.parseURIEncoded(params.getRepositoryPath());
		final DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
		options.setInteractiveConflictResolution(false);
		final SVNClientManager man = SVNClientManager.newInstance(options, params.getUserName(), params.getPassword());
		final SVNStatusClient status = man.getStatusClient();
		final List<File> missingFiles = new ArrayList<File>();
		status.doStatus(workingCopyPath,
				SVNRevision.HEAD,
				SVNDepth.INFINITY,
				/* remote */ false,
				/* reportAll */ true,
				/* includeIgnored */ false,
				/* collectParentExternals */ false,
				new ISVNStatusHandler() {
					@Override
					public void handleStatus(SVNStatus arg0) throws SVNException {
						if (arg0.getNodeStatus() == SVNStatusType.MISSING) {
							missingFiles.add(arg0.getFile());
						}
					}
				},
				/* no changelist filtering occurs */ null);
		
		final SVNWCClient wc = man.getWCClient();
		for (final File missingFile : missingFiles) {
			wc.doDelete(missingFile, /* force */ true, /* dryRun */ false);
		}
		
		wc.doAdd(workingCopyPath,
				/* force */ true,
				/* mkdir */ true,
				/* climbUnversionedParents */ false,
				/* depth */ SVNDepth.INFINITY,
				/* includeIgnored */ false,
				/* makeParent*/ true);
		
		final SVNUpdateClient update = man.getUpdateClient();
		update.doUpdate(workingCopyPath,
				SVNRevision.HEAD,
				SVNDepth.INFINITY,
				/* allowUnversionedObstructions */ true,
				/* depthIsSticky */ false);
		
		final SVNCommitClient commit = man.getCommitClient();
		commit.doCommit(new File[]{workingCopyPath},
				/* keepLocks */ false,
				/* commitMessage */ params.getCommitMessage(),
				/* revisionProperties */ null,
				/* changelists */ null,
				/* keepChangelist */ false,
				/* force */ true,
				SVNDepth.INFINITY);
	}
	
}
