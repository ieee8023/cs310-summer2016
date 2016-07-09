package com.sshtools.daemon.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.sshtools.j2ssh.io.DynamicBuffer;

/**
 * This is a shell provider that prints a message saying that
 * we don't support shell access, and then closes the connection.
 */
public final class CatsShellProcessProvider extends NativeProcessProvider {

	private DynamicBuffer stdin = new DynamicBuffer();
	private DynamicBuffer stderr = new DynamicBuffer();
	private DynamicBuffer stdout = new DynamicBuffer();

	@Override
	public boolean createProcess(final String command, final Map environment)
			throws IOException {
		return true;
	}

	@Override
	public String getDefaultTerminalProvider() {
		return "vt100";
	}

	@Override
	public void kill() {
		try {
			stdin.close();
		} catch (Exception ex) {
		}
		try {
			stdout.close();
		} catch (Exception ex1) {
		}
		try {
			stderr.close();
		} catch (Exception ex2) {
		}
	}

	@Override
	public void start() throws IOException {
		stdin.getOutputStream().write(file2String("victory.txt").replace("\n", "\n\r").getBytes());
	}

	@Override
	public boolean stillActive() {
		try {
			return stdin.getInputStream().available() > 0;
		} catch (IOException ex) {
			return false;
		}
	}

	@Override
	public boolean supportsPseudoTerminal(final String term) {
		return true;
	}

	@Override
	public boolean allocatePseudoTerminal(final String term, final int cols, final int rows,
			final int width, final int height, final String modes) {
		return true;
	}

	@Override
	public int waitForExitCode() {
		return 0;
	}

	public InputStream getInputStream() throws IOException {
		return stdin.getInputStream();
	}

	public OutputStream getOutputStream() throws IOException {
		return stdout.getOutputStream();
	}

	public InputStream getStderrInputStream() {
		return stderr.getInputStream();
	}
	
	private static String file2String(String filename){
		
		try {
			File file = new File(filename);
		    FileInputStream fis = new FileInputStream(file);
		    byte[] data = new byte[(int)file.length()];
		    fis.read(data);
		    fis.close();
		    return new String(data);
		} catch (Exception e) {
			return new String("File read error");
		}
		
	}

}
