package jan.mongometer;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jan
 * Date: 17/06/12
 * Time: 20:48
 */
public class ScriptSampler
        extends AbstractSampler
        implements TestBean, TestListener {

    //private static final long serialVersionUID = -678108159079724396L;

    private static final Logger log = LoggingManager.getLoggerForClass();

    public final static String CONNECTION = "ScriptSampler.connection"; //$NON-NLS-1$
    public final static String DATABASE = "ScriptSampler.database"; //$NON-NLS-1$
    public final static String USERNAME = "ScriptSampler.username"; //$NON-NLS-1$
    public final static String PASSWORD = "ScriptSampler.password"; //$NON-NLS-1$

    public final static String CONNECTIONS_PER_HOST = "ScriptSampler.connectionsPerHost"; //$NON-NLS-1$
    public final static String THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER = "ScriptSampler.threadsAllowedToBlockForConnectionMultiplier"; //$NON-NLS-1$
    public final static String MAX_AUTO_CONNECT_RETRY_TIME = "ScriptSampler.maxAutoConnectRetryTime"; //$NON-NLS-1$
    public final static String MAX_WAIT_TIME = "ScriptSampler.maxWaitTime"; //$NON-NLS-1$
    public final static String CONNECT_TIMEOUT = "ScriptSampler.connectTimeout"; //$NON-NLS-1$
    public final static String SOCKET_TIMEOUT = "ScriptSampler.socketTimeout"; //$NON-NLS-1$
    public final static String SOCKET_KEEP_ALIVE = "ScriptSampler.socketKeepAlive"; //$NON-NLS-1$
    public final static String AUTO_CONNECT_RETRY = "ScriptSampler.autoConnectRetry"; //$NON-NLS-1$
    public final static String FSYNC = "ScriptSampler.fsync"; //$NON-NLS-1$
    public final static String J = "ScriptSampler.j"; //$NON-NLS-1$
    public final static String SAFE = "ScriptSampler.safe"; //$NON-NLS-1$
    public final static String W = "ScriptSampler.w"; //$NON-NLS-1$
    public final static String W_TIMEOUT = "ScriptSampler.wTimeout"; //$NON-NLS-1$

    public final static String SCRIPT = "ScriptSampler.script"; //$NON-NLS-1$

    public static MongoDB mongoDB;

    private static int classCount = 0; // keep track of classes created

    public ScriptSampler() {
        classCount++;
        trace("ScriptSampler()");
    }

    public SampleResult sample(Entry e) {
        trace("sample()");

        SampleResult res = new SampleResult();
        String data = getScript();
        String response = null;

        res.setSampleLabel(getTitle());
        res.sampleStart();
        try {

            mongoDB.evaluate(getScript());

            response = Thread.currentThread().getName();
            res.setSamplerData(data);
            res.setResponseData(response.getBytes());
            res.setDataType(SampleResult.TEXT);

            res.setResponseCodeOK();
            res.setResponseCode("200");
            res.setSuccessful(true);
            res.setResponseMessage("OK");// $NON-NLS-1$
        }
        catch (Exception ex) {
            log.warn("", ex);
            res.setResponseCode("500");// $NON-NLS-1$
            res.setSuccessful(false);
            res.setResponseMessage(ex.toString());
        }
        finally {
            res.sampleEnd();
        }

        return res;
    }

    private String getTitle() {
        return this.getName();
    }

    public String getScript() {
        return getPropertyAsString(SCRIPT);
    }

    public void setScript(String script) {
        setProperty(SCRIPT, script);
    }

    public String getConnection() {
        return getPropertyAsString(CONNECTION);
    }

    public void setConnection(String connection) {
        setProperty(CONNECTION, connection);
    }

    public String getConnectionsPerHost() {
        return getPropertyAsString(CONNECTIONS_PER_HOST);
    }

    public void setConnectionsPerHost(String connectionsPerHost) {
        setProperty(CONNECTIONS_PER_HOST, connectionsPerHost);
    }

    public String getThreadsAllowedToBlockForConnectionMultiplier() {
        return getPropertyAsString(THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER);
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(String threadsAllowedToBlockForConnectionMultiplier) {
        setProperty(THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER, threadsAllowedToBlockForConnectionMultiplier);
    }

    public String getDatabase() {
        return getPropertyAsString(DATABASE);
    }

    public void setDatabase(String database) {
        setProperty(DATABASE, database);
    }

    public String getUsername() {
        return getPropertyAsString(USERNAME);
    }

    public void setUsername(String username) {
        setProperty(USERNAME, username);
    }

    public String getPassword() {
        return getPropertyAsString(PASSWORD);
    }

    public void setPassword(String password) {
        setProperty(PASSWORD, password);
    }

    public String getMaxWaitTime() {
        return getPropertyAsString(MAX_WAIT_TIME);
    }

    public void setMaxWaitTime(String maxWaitTime) {
        setProperty(MAX_WAIT_TIME, maxWaitTime);
    }

    public String getConnectTimeout() {
        return getPropertyAsString(CONNECT_TIMEOUT);
    }

    public void setConnectTimeout(String connectTimeout) {
        setProperty(CONNECT_TIMEOUT, connectTimeout);
    }

    public String getSocketTimeout() {
        return getPropertyAsString(SOCKET_TIMEOUT);
    }

    public void setSocketTimeout(String socketTimeout) {
        setProperty(SOCKET_TIMEOUT, socketTimeout);
    }

    public String getSocketKeepAlive() {
        return getPropertyAsString(SOCKET_KEEP_ALIVE);
    }

    public void setSocketKeepAlive(String socketKeepAlive) {
        setProperty(SOCKET_KEEP_ALIVE, socketKeepAlive);
    }

    public String getAutoConnectRetry() {
        return getPropertyAsString(AUTO_CONNECT_RETRY);
    }

    public void setAutoConnectRetry(String autoConnectRetry) {
        setProperty(AUTO_CONNECT_RETRY, autoConnectRetry);
    }

    public String getWTimeout() {
        return getPropertyAsString(W_TIMEOUT);
    }

    public void setWTimeout(String wtimeout) {
        setProperty(W_TIMEOUT, wtimeout);
    }

    public String getJ() {
        return getPropertyAsString(J);
    }

    public void setJ(String j) {
        setProperty(J, j);
    }

    public String getMaxAutoConnectRetryTime() {
        return getPropertyAsString(MAX_AUTO_CONNECT_RETRY_TIME);
    }

    public void setMaxAutoConnectRetryTime(String maxAutoConnectRetryTime) {
        setProperty(MAX_AUTO_CONNECT_RETRY_TIME, maxAutoConnectRetryTime);
    }

    public String getW() {
        return getPropertyAsString(W);
    }

    public void setW(String w) {
        setProperty(W, w);
    }

    public String getFsync() {
        return getPropertyAsString(FSYNC);
    }

    public void setFsync(String fsync) {
        setProperty(FSYNC, fsync);
    }

    public String getSafe() {
        return getPropertyAsString(SAFE);
    }

    public void setSafe(String safe) {
        setProperty(SAFE, safe);
    }

    /*
    * Helper
    */
    private void trace(String s) {
        String tl = getTitle();
        String tn = Thread.currentThread().getName();
        String th = this.toString();
        log.debug(tn + " (" + classCount + ") " + tl + " " + s + " " + th);
    }

    @Override
    public void testIterationStart(LoopIterationEvent arg0) {
        if(log.isDebugEnabled()) {
            log.debug("testIterationStart : " + arg0);
        }
    }

    @Override
    public void testStarted() {
        testStarted("");
    }

    @Override
    public void testStarted(String arg0) {
        if(log.isDebugEnabled()) {
            log.debug("testStarted : " + arg0);
        }
        ArrayList<ServerAddress> addresses = new ArrayList<ServerAddress>();
        try {
            for(String connection : Arrays.asList(getConnection().split("\\s*,\\s*"))) {
                addresses.add(new ServerAddress(connection, 27017));
                if(log.isDebugEnabled()) {
                    log.debug("address : " + connection);
                }
            }
        }
        catch(UnknownHostException uhe) {
            if(log.isWarnEnabled()) {
                log.warn("", uhe);
            }
        }

        MongoOptions mongoOptions = new MongoOptions();
        mongoOptions.autoConnectRetry = Boolean.parseBoolean(getAutoConnectRetry());
        mongoOptions.connectTimeout = Integer.parseInt(getConnectTimeout());
        mongoOptions.connectionsPerHost = Integer.parseInt(getConnectionsPerHost());
        mongoOptions.fsync = Boolean.parseBoolean(getFsync());
        mongoOptions.j = Boolean.parseBoolean(getJ());
        mongoOptions.maxAutoConnectRetryTime = Integer.parseInt(getMaxAutoConnectRetryTime());
        mongoOptions.maxWaitTime = Integer.parseInt(getMaxWaitTime());
        mongoOptions.safe = Boolean.parseBoolean(getSafe());
        mongoOptions.socketKeepAlive = Boolean.parseBoolean(getSocketKeepAlive());
        mongoOptions.socketTimeout = Integer.parseInt(getSocketTimeout());
        mongoOptions.threadsAllowedToBlockForConnectionMultiplier = Integer.parseInt(getThreadsAllowedToBlockForConnectionMultiplier());
        mongoOptions.w = Integer.parseInt(getW());
        mongoOptions.wtimeout = Integer.parseInt(getWTimeout());

        mongoDB = new MongoDB(addresses,
                getDatabase(),
                getUsername(),
                getPassword(),
                mongoOptions);
    }


    @Override
    public void testEnded() {
        testEnded("");
    }

    @Override
    public void testEnded(String arg0) {
        if(log.isDebugEnabled()) {
            log.debug("testEnded : " + arg0);
        }
        mongoDB.clear();

        //why not....
        mongoDB = null;
        System.gc();
    }
}