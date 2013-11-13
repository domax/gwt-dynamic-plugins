/*
 * Copyright 2013 Maxim Dominichenko
 * 
 * Licensed under The MIT License (MIT) (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * https://github.com/domax/gwt-dynamic-plugins/blob/master/LICENSE
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwt.dynamic.common.client.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This class allows to run any amount (including 0) of asynchronous commands at once or sequentially. When last command
 * finishes, {@link #onFinish()} method is fired. So, the typical usage of this class is like that:
 * 
 * <pre>
 * <code>
 * private class MyCommand implements Command {
 *   {@literal @}Override
 *   public void onFailure(Throwable caught) {
 *     // Process command failure (just logging as a rule)
 *   }
 * 
 *   {@literal @}Override
 *   public void onSuccess(Void result) {
 *     // Process command success (just logging as a rule)
 *   }
 * 
 *   {@literal @}Override
 *   public void run(final AsyncCallback&lt;Void> callback) {
 *     // Invoke asynchronous code, that will respond about failure and success via given callback
 *   }
 * }
 * 
 * //...
 * 
 * BatchRunner batchRunner = new BatchRunner() {
 *   {@literal @}Override
 *   public void onFinish() {
 *     // Place here code that will be invoked when all commands done
 *   }
 * };
 * batchRunner.add(new MyCommand());
 * //...
 * batchRunner.run();
 * </code>
 * </pre>
 * 
 * You can add new commands into runner when it is started already - it's useful if your async commands have nested
 * async commands.<br>
 * If you define no commands for runner, then {@link #run()} method invokes {@link #onFinish()} instantly.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public abstract class BatchRunner {

	private static final Logger LOG = Logger.getLogger(BatchRunner.class.getName());

	/**
	 * Defines the command processing modes that {@link BatchRunner} supports.
	 */
	public enum Mode {

		/**
		 * Batch runner will try to launch all commands in queue at once, in parallel if possible.
		 */
		PARALLEL,

		/**
		 * Batch runner will try to launch commands in order they placed in queue. Commands are processed sequentially -
		 * next command will wait while previous one will be finished.
		 */
		SEQUENTIAL
	}
	
	/**
	 * Defines {@link BatchRunner}'s command that should be invoked in parallel.
	 */
	public static interface Command extends AsyncCallback<Void> {
		
		/**
		 * Starts any kind of asynchronous code that should be defined in this method.
		 * 
		 * @param callback
		 *          A resulting callback that custom async code should use to inform runner about success and failure.
		 */
		void run(AsyncCallback<Void> callback);
	}

	abstract public static class CommandSimple implements Command {

		@Override
		public void onSuccess(Void result) {
			LOG.info("BatchRunner.CommandSimple.onSuccess");
		}

		@Override
		public void onFailure(Throwable caught) {
			LOG.severe("BatchRunner.CommandSimple.onFailure: " + caught.getMessage());
		}
	}
	
	private class CommandAsyncCallback implements AsyncCallback<Void>, ScheduledCommand {

		private Command command;
		
		CommandAsyncCallback(Command command) {
			this.command = command;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			if (!commands.contains(command)) return; 
			commands.remove(command);
			errors.add(caught);
			command.onFailure(caught);
			onCommandFinish(command);
		}

		@Override
		public void onSuccess(Void result) {
			if (!commands.contains(command)) return;
			commands.remove(command);
			command.onSuccess(result);
			onCommandFinish(command);
		}

		@Override
		public void execute() {
			if (commands.contains(command)) command.run(this);
		}
	}
	
	private final List<Command> commands = new ArrayList<Command>();
	private final List<Throwable> errors = new ArrayList<Throwable>();
	private boolean running;
	private int startSize;
	private final Mode mode;

	/**
	 * Creates batch runner with default mode {@link Mode#PARALLEL}.
	 */
	public BatchRunner() {
		this(null);
	}

	public BatchRunner(Mode mode) {
		this.mode = mode == null ? Mode.PARALLEL : mode;
		LOG.info("BatchRunner: mode=" + mode);
	}
	
	/**
	 * Pushes new async command into runner. If runner is already started then command is run instantly, otherwise it just
	 * inserted into queue and waits for {@link BatchRunner#run()} invocation.
	 */
	public BatchRunner add(Command command) {
		LOG.info("BatchRunner.add");
		if (command == null)
			throw new IllegalArgumentException("Command cannot be a null");
		commands.add(command);
		if (running) {
			++startSize;
			if (Mode.PARALLEL.equals(mode))
				Scheduler.get().scheduleDeferred(new CommandAsyncCallback(command));
		}
		return this;
	}
	
	/**
	 * Starts commands in queue. Does nothing if process is running.
	 */
	public void run() {
		LOG.info("BatchRunner.run: running=" + running);
		if (running) return;
		errors.clear();
		if (commands.isEmpty()) {
			onFinish();
			return;
		}
		running = true;
		startSize = commands.size();
		switch (mode) {
			case PARALLEL:
				for (Command command : commands)
					Scheduler.get().scheduleDeferred(new CommandAsyncCallback(command));
				break;
			case SEQUENTIAL:
				Scheduler.get().scheduleDeferred(new CommandAsyncCallback(commands.get(0)));
				break;
		}
	}

	/**
	 * Aborts waiting for command's responses. In fact, asynchronous code, once was invoked, cannot be canceled in regular
	 * way. So, this runner simply ignores all responses from commands that are still active.
	 * <p>
	 * Does nothing if process is stopped.
	 */
	public void abort() {
		LOG.info("BatchRunner.abort: running=" + running);
		if (!running) return;
		running = false;
		commands.clear();
		onFinish();
	}
	
	/**
	 * Returns current progress as a number from 0.0 to 1.0.
	 */
	public double getProgress() {
		LOG.info("BatchRunner.getProgress: running=" + running + "; startSize=" + startSize + "; size=" + commands.size());
		if (!running || startSize == 0) return 0d;
		return ((double) startSize - commands.size()) / startSize;
	}
	
	/**
	 * Is invoked when runner completes processing all commands in queue.
	 */
	public abstract void onFinish();
	
	/**
	 * @return {@code true} in case if any of commands in queue fails. {@code false} otherwise.
	 */
	public boolean hasErrors() {
		return errors.size() > 0;
	}
	
	/**
	 * Gets list of {@link Throwable}s that occurred during commands running.
	 * 
	 * @return List of command exceptions
	 */
	public List<Throwable> getErrors() {
		return errors;
	}
	
	private void onCommandFinish(Command command) {
		LOG.info("BatchRunner.onCommandFinish");
		if (!running) return;
		if (commands.isEmpty()) {
			running = false;
			onFinish();
		} else if (Mode.SEQUENTIAL.equals(mode))
			Scheduler.get().scheduleDeferred(new CommandAsyncCallback(commands.get(0)));
	}
}
