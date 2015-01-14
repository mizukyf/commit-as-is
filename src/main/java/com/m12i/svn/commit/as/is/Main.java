package com.m12i.svn.commit.as.is;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public final class Main {
	
	private static final char ARG_WORKING_COPY = 'w';
	private static final char ARG_REPOSITORY_PATH = 'r';
	private static final char ARG_USER_NAME = 'u';
	private static final char ARG_PASSWORD = 'p';
	private static final char ARG_MESSAGE = 'm';

	public static void main(String[] args) throws Exception {
		final Options options = defineOptions();
		final Parameters params = parseArgs(args, options);
		new CommitAsIs().execute(params);
	}
	
	private static final Parameters parseArgs(final String[] args, final Options options) throws ParseException {
		final CommandLine cmd = new PosixParser().parse(options, args);
		final Parameters params = new Parameters();
		
		final String workingCopyPath = cmd.hasOption(ARG_WORKING_COPY)
				? cmd.getOptionValue(ARG_WORKING_COPY) : ".";
		final String repositoryPath = cmd.getOptionValue(ARG_REPOSITORY_PATH);
		final String userName = cmd.getOptionValue(ARG_USER_NAME);
		final String password = cmd.getOptionValue(ARG_PASSWORD);
		final String commitMessage = cmd.getOptionValue(ARG_MESSAGE);
		
		params.setCommitMessage(commitMessage);
		params.setPassword(password);
		params.setRepositoryPath(repositoryPath);
		params.setUserName(userName);
		params.setWorkingCopyPath(workingCopyPath);
		
		return params;
	}
	
	@SuppressWarnings("static-access")
	private static final Options defineOptions() {
		final Options options = new Options();
		
		options.addOption(OptionBuilder
				.isRequired(true)
				.hasArg(true)
				.withArgName("repository-url")
				.create('r'));
		
		options.addOption(OptionBuilder
				.isRequired(false)
				.hasArg(true)
				.withArgName("working-copy-path")
				.withDescription("default is '.'")
				.create('w'));

		options.addOption(OptionBuilder
				.isRequired(true)
				.hasArg(true)
				.withArgName("username")
				.create('u'));
		
		options.addOption(OptionBuilder
				.isRequired(true)
				.hasArg(true)
				.withArgName("password")
				.create('p'));
		
		options.addOption(OptionBuilder
				.isRequired(true)
				.hasArg(true)
				.withArgName("commit-message")
				.create('m'));
		
		return options;
	}

}
