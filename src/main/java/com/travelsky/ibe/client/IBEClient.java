package com.travelsky.ibe.client;

import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.ibe.exceptions.IBELocalException;
import com.travelsky.ibe.exceptions.NetworkConnectionException;
import com.travelsky.util.Base64;
import com.travelsky.util.CommandLog;
import com.travelsky.util.CommandParser;
import com.travelsky.util.CommandParser3;
import com.travelsky.util.CommandReader2;
import com.travelsky.util.CommandWriter2;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class IBEClient {
	protected boolean trace = false;
	private static final SecureRandom rd = new SecureRandom();

	protected final String cId = String.valueOf(Math.abs(rd.nextLong()));
	private static ResourceBundle rb;
	private static volatile long lastReadCfg = 0L;
	private static volatile boolean enableLog = false;
	private volatile long soTimeOut = 120000L;
	private volatile long timeOut = 60000L;
	static CommandLog log = null;
	protected String serverip;
	protected static String version = "0.9.1.0build110513.1113";
	protected int serverport;
	protected String app;
	protected String office;
	protected String customno;
	protected String validationno;
	protected String uc = null;
	private Socket socket;
	private CommandReader2 in;
	private CommandWriter2 out;
	private String localSocketAddress = "";
	private String handshakingInfo = "";

	protected String traceStr = "";
	protected String localIP = null;

	protected boolean supportSecurity = false;
	protected String serverVer = null;
	protected StringBuffer sb = null;

	static {
		rd.setSeed(System.currentTimeMillis());

		rb = null;
		try {
			rb = ResourceBundle.getBundle("ibeclient", Locale.US);
		} catch (Exception localException) {
		}
	}

	private static final void doLogging(String s) {
		if (log != null)
			log.writeString(s);
	}

	public IBEClient() {
		this(null, 0, null, null, null, null);
	}

	public IBEClient(String serverip, int serverport, String app, String office, String customno, String validationno) {
		try {
			long l;
			if ((l = System.currentTimeMillis()) - lastReadCfg > 30000L) {
				try {
					rb = ResourceBundle.getBundle("ibeclient", Locale.US);
				} catch (Exception localException1) {
				}
				lastReadCfg = l;
			}
			if (rb == null)
				throw new Exception("No config file");
			try {
				enableLog = "true".equalsIgnoreCase(rb.getString("ibe.client.enablelog"));
			} catch (Exception localException2) {
			}
			this.serverip = serverip;
			if ((this.serverip == null) && (rb != null)) {
				this.serverip = rb.getString("ibe.server.ip");
			}
			this.serverport = serverport;
			if ((this.serverport < 1) && (rb != null)) {
				this.serverport = Integer.parseInt(rb.getString("ibe.server.port"));
			}
			this.app = app;
			if ((this.app == null) && (rb != null)) {
				this.app = rb.getString("ibe.client.app");
			}
			this.office = office;
			if ((this.office == null) && (rb != null)) {
				this.office = rb.getString("ibe.client.office");
			}
			this.customno = customno;
			if ((this.customno == null) && (rb != null)) {
				this.customno = rb.getString("ibe.client.customno");
			}
			this.validationno = validationno;
			if ((this.validationno == null) && (rb != null)) {
				this.validationno = rb.getString("ibe.client.validationno");
			}
			if (rb != null)
				try {
					this.soTimeOut = Integer.parseInt(rb.getString("ibe.client.sockettimeout"));
					if ((this.soTimeOut <= 0L) || (this.soTimeOut > 120000L))
						this.soTimeOut = 120000L;
				} catch (Exception e) {
					this.soTimeOut = 120000L;
				}
			if (rb != null)
				try {
					this.timeOut = Integer.parseInt(rb.getString("ibe.client.connecttimeout"));
					if ((this.timeOut <= 0L) || (this.timeOut > 60000L))
						this.timeOut = 60000L;
				} catch (Exception e) {
					this.timeOut = 60000L;
				}
		} catch (Exception localException3) {
		}
	}

	public void setConnectionInfo(String ip, int port) {
		this.serverip = ip;
		this.serverport = port;
	}

	public void setAppName(String name) {
		this.app = name;
	}

	public void setAgentInfo(String office, String customno, String validationno) {
		this.office = office;
		this.customno = customno;
		this.validationno = validationno;
	}

	public String getConnectionIP() {
		return this.serverip;
	}

	public int getConnectionPort() {
		return this.serverport;
	}

	public String getOfficeCode() {
		return this.office;
	}

	public String getCustomNumber() {
		return this.customno;
	}

	public String getValidationNumber() {
		return this.validationno;
	}

	private void connect() throws Exception {
		this.socket = new Socket();
		SocketAddress addr = new InetSocketAddress(this.serverip, this.serverport);
		if ((this.localIP != null) && (this.localIP.length() > 0))
			this.socket.bind(new InetSocketAddress(this.localIP, 0));
		try {
			this.localSocketAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception localException) {
		}
		if (this.trace) {
			this.traceStr = (this.traceStr + "\r\n[" + System.currentTimeMillis() + "]");
			this.traceStr = (this.traceStr + "connect: using local ip " + this.localIP);
			this.traceStr = (this.traceStr + "\r\nremote: " + addr);
		}
		this.socket.connect(addr, (int) this.timeOut);

		this.socket.setTcpNoDelay(true);
		this.socket.setSoTimeout((int) this.soTimeOut);
		if (this.trace) {
			this.traceStr = (this.traceStr + "\r\n[" + System.currentTimeMillis() + "]");
			this.traceStr = (this.traceStr + "\r\nsocketConnected: using local ip "
					+ this.socket.getLocalSocketAddress());
			this.traceStr = (this.traceStr + "\r\nsocketConnected: remote ip " + this.socket.getRemoteSocketAddress());
		}

		this.in = new CommandReader2(this.socket);
		this.out = new CommandWriter2(this.socket);
		handShaking();
	}

	protected void handShaking() throws Exception {
		String[] args = new String[9];
		args[0] = this.app;
		args[1] = this.office;
		args[2] = this.customno;
		args[3] = this.validationno;
		args[4] = "COMPRESSED";
		args[5] = this.uc;
		args[6] = version;
		args[7] = "Security";
		args[8] = this.cId;

		String cmd = handShaking(args);
		String[] serverinfo = CommandParser.parse(cmd);
		if (serverinfo.length >= 2)
			this.serverVer = serverinfo[1];
		if (serverinfo.length >= 3)
			this.supportSecurity = "true".equalsIgnoreCase(serverinfo[2]);
	}

	protected String handShaking(String[] handshakinginfo) throws Exception {
		String s = CommandParser.encode(handshakinginfo);

		if (this.sb != null)
			this.sb.append("VALIDATION Request: " + s + "\r\n\r\n");
		if (this.trace) {
			this.traceStr = (this.traceStr + "\r\n[" + System.currentTimeMillis() + "]");
			this.traceStr = (this.traceStr + "\r\nHandshaking input:" + s);
		}
		this.out.sendCommand(s);
		String cmd = this.in.getCommand((int) this.soTimeOut);
		this.handshakingInfo = cmd;
		if (this.trace) {
			this.traceStr = (this.traceStr + "\r\n[" + System.currentTimeMillis() + "]");
			this.traceStr = (this.traceStr + "\r\nHandshaking output:" + this.handshakingInfo);
		}
		if (this.sb != null)
			this.sb.append("VALIDATION Response: " + cmd + "\r\n\r\n");
		return cmd;
	}

	public synchronized String query(String[] args) throws Exception {
		if (enableLog)
			this.sb = new StringBuffer();
		try {
			URL s = null;
			if (this.sb != null) {
				s = getConfigFilePath();

				this.sb.append("CONFIGFILEPATH:" + (s == null ? "NO CONFIG FILE" : String.valueOf(s)) + "\r\n\r\n");
			}
			String queryStr = CommandParser3.encode(args);
			if (this.sb != null)
				this.sb.append("QUERY:" + queryStr + "\r\n\r\n");
			if ((this.serverip == null) || (this.serverip.length() == 0))
				throw new IBELocalException(0,
						new String[] { "  配置文件路径：" + (s == null ? "没有配置文件或无需使用配置文件" : String.valueOf(s)) });
			if ((this.app == null) || (this.app.length() == 0))
				throw new IBELocalException(1,
						new String[] { "  配置文件路径：" + (s == null ? "没有配置文件或无需使用配置文件" : String.valueOf(s)) });
			try {
				connect();
			} catch (IBEException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw new IBELocalException(2,
						new String[] { this.serverip, String.valueOf(this.serverport), this.app, e.getMessage(),
								"  配置文件路径："
										+ (s == null
												? "没有或未使用配置文件(ibeclient.properties)" + this.localSocketAddress
														+ "\r\n配置文件信息供错误定位参考，服务器IP与端口正确时可忽略关于配置文件位置的信息"
												: String.valueOf(s)) });
			}
			if (this.trace) {
				this.traceStr = (this.traceStr + "\r\n[" + System.currentTimeMillis() + "]");
				this.traceStr = (this.traceStr + "\r\nCommand input:" + queryStr);
			}
			this.out.sendCommand2(queryStr);
			String result = CommandParser3.decString(this.in.getCommand2((int) this.soTimeOut));
			if (this.trace) {
				this.traceStr = (this.traceStr + "\r\n[" + System.currentTimeMillis() + "]");
				this.traceStr = (this.traceStr + "\r\nCommand output:" + result);
			}
			if (this.sb != null)
				this.sb.append("SERVERRESP:" + result + "\r\n\r\n");
			if (result != null) {
				if ((result.startsWith("IBE_EXCEPTION")) || (result.startsWith("IBE_DISCONNECT"))) {
					if (result.indexOf(':') < 0)
						throw new IBEException(result);
					if (result.startsWith("IBE_EXCEPTION")) {
						String exname = result
								.substring(14, result.indexOf(':', 14) < 0 ? result.length() : result.indexOf(':', 14))
								.trim();
						String exMessage = result
								.substring(result.indexOf(':', 14) < 0 ? result.length() : result.indexOf(':', 14) + 1)
								.trim();
						StringBuffer configInfo = new StringBuffer();
						configInfo.append("app:");
						configInfo.append(this.app);
						configInfo.append("\r\ncustomno:" + this.customno);
						configInfo.append("\r\nvalidationno:" + this.validationno);
						configInfo.append("\r\nServerAddress:");
						configInfo.append(this.serverip + ":" + this.serverport);
						configInfo.append("\r\n");
						configInfo.append(this.handshakingInfo);
						configInfo.append("\r\n");
						try {
							Class[] params = new Class[1];
							params[0] = String.class;
							Object[] message = new Object[1];
							message[0] = (exMessage + "\r\n" + configInfo);
							Exception e = (Exception) Class.forName(exname).getConstructor(params).newInstance(message);
							throw e;
						} catch (IBEException ie) {
							throw ie;
						} catch (Exception ee) {
							try {
								Exception ex = (IBEException) Class.forName(exname).newInstance();
								throw ex;
							} catch (ClassCastException ex) {
								throw new IBEException(result.substring(14) + "\r\n" + configInfo);
							} catch (ClassNotFoundException ex) {
								throw new IBEException(result.substring(14) + "\r\n" + configInfo);
							} catch (InstantiationException ex) {
								throw new IBEException(result.substring(14) + "\r\n" + configInfo);
							}
						}
					}
					throw new IBEException(result);
				}
				if (result.startsWith("ZIPPED:"))
					result = CommandParser3.decompressString(Base64.decode1(result.substring(7)));
			}
			return result;
		} catch (IBEException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			if (this.sb != null)
				this.sb.append("Exception: " + e.getClass().getName() + ":" + e.getMessage() + "\r\n" + sw.toString()
						+ "\r\n\r\n");
			if (this.trace) {
				this.traceStr = (this.traceStr + "\r\n[" + System.currentTimeMillis() + "]");
				this.traceStr = (this.traceStr + "\r\nException:" + e.getClass().getName() + ":" + e.getMessage()
						+ "\r\n" + sw.toString());
			}
			pw.close();
			throw e;
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter writer1 = new PrintWriter(writer);
			e.printStackTrace(writer1);
			if (this.sb != null)
				this.sb.append("Exception: " + e.getClass().getName() + ":" + e.getMessage() + "\r\n"
						+ writer.toString() + "\r\n\r\n");
			if (this.trace) {
				this.traceStr = (this.traceStr + "\r\n[" + System.currentTimeMillis() + "]");
				this.traceStr = (this.traceStr + "\r\nException:" + e.getClass().getName() + ":" + e.getMessage()
						+ "\r\n" + writer.toString());
			}
			writer1.close();
			Exception e1 = new NetworkConnectionException(writer.getBuffer().toString());
			throw e1;
		} finally {
			if ((enableLog) && (this.sb != null)) {
				doLogging(this.sb.toString());

				this.sb.setLength(0);
				this.sb = null;
			}
			close();
			if (this.trace) {
				this.traceStr = (this.cId + "\r\n" + Base64.encode(CommandParser3.compressString(this.traceStr)));
				System.out.println(this.traceStr);
			}
		}
	}

	private void close() {
		try {
			this.out.sendCommand2("IBE_DISCONNECT");
		} catch (Exception localException1) {
		}
		try {
			this.in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getAppName() {
		return this.app;
	}

	public boolean setCurConfig(int index) {

		boolean res = false;
		if (index < 1) {
			return res;
		}
		try {
			int serverport;
			String validationno;
			String customno;
			String office;
			String app;
			String serverip;
			try {
				enableLog = "true".equalsIgnoreCase(rb.getString("ibe.client.enablelog"));
			} catch (Exception localException1) {
			}
			if (index == 1) {
				serverip = rb.getString("ibe.server.ip");
				serverport = Integer.parseInt(rb.getString("ibe.server.port"));
				app = rb.getString("ibe.client.app");
				office = rb.getString("ibe.client.office");
				customno = rb.getString("ibe.client.customno");
				validationno = rb.getString("ibe.client.validationno");
			} else {
				try {
					serverip = rb.getString("ibe.server.ip" + index);
					serverport = Integer.parseInt(rb.getString("ibe.server.port" + index));
					app = rb.getString("ibe.client.app" + index);
					office = rb.getString("ibe.client.office" + index);
					customno = rb.getString("ibe.client.customno" + index);
					validationno = rb.getString("ibe.client.validationno" + index);
				} catch (MissingResourceException e) {
					serverip = rb.getString("ibe.server.ip." + index);
					serverport = Integer.parseInt(rb.getString("ibe.server.port." + index));
					app = rb.getString("ibe.client.app." + index);
					office = rb.getString("ibe.client.office." + index);
					customno = rb.getString("ibe.client.customno." + index);
					validationno = rb.getString("ibe.client.validationno." + index);
				} catch (Exception ex) {
					throw ex;
				}
			}

			this.serverip = serverip;
			this.serverport = serverport;
			this.app = app;
			this.office = office;
			this.customno = customno;
			this.validationno = validationno;
			res = true;
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	public long getRespTime() throws Exception {
		long l = System.currentTimeMillis();

		String ret = query(new String[] { "IBE_RESP" });
		if (!ret.startsWith("OK"))
			throw new IBEException("not compatible server");
		l = System.currentTimeMillis() - l;
		return l;
	}

	public URL getConfigFilePath() throws Exception {
		try {
			return getClass().getClassLoader().getResource("ibeclient.properties");
		} catch (Exception e) {
		}
		return null;
	}

	public void setUc(String uc) {
		this.uc = uc;
	}

	public boolean testConnect() {
		try {
			connect();
			return true;
		} catch (IBELocalException e) {
			return false;
		} catch (IBEException e) {
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			close();
		}
	}

	long getSoTimeOut() {
		return this.soTimeOut;
	}

	public void setSoTimeOut(long soTimeOut) {
		this.soTimeOut = soTimeOut;
	}

	long getConnTimeOut() {
		return this.timeOut;
	}

	public void setConnTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	protected String[] encodeRequest(Object req) throws Exception {
		throw new UnsupportedOperationException();
	}

	protected Object decodeResponse(String serverStr) throws Exception {
		return serverStr;
	}
}