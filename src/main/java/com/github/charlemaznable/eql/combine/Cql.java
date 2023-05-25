package com.github.charlemaznable.eql.combine;

import com.github.charlemaznable.core.lang.ClzPath;
import com.github.charlemaznable.eql.apollo.EqlApolloConfig;
import com.github.charlemaznable.eql.etcd.EqlEtcdConfig;
import lombok.val;
import org.apache.commons.text.StringSubstitutor;
import org.n3r.eql.Eql;
import org.n3r.eql.config.EqlConfig;
import org.n3r.eql.config.EqlDiamondConfig;

import java.util.Properties;

import static com.github.charlemaznable.core.config.Arguments.argumentsAsProperties;
import static com.github.charlemaznable.core.lang.ClzPath.classResourceAsProperties;
import static com.github.charlemaznable.core.lang.Propertiess.ssMap;

public class Cql extends Eql {

    private static final String APOLLO_CLZ = "com.ctrip.framework.apollo.ConfigService";
    private static final String DIAMOND_CLZ = "org.n3r.diamond.client.Miner";
    private static final String ETCDCONF_CLZ = "com.github.charlemaznable.etcdconf.EtcdConfigService";
    private static final String ENV_SOURCE = "${EqlConfigService:-diamond}";

    private static final boolean hasApollo;
    private static final boolean hasDiamond;
    private static final boolean hasEtcdconf;
    private static final int count;

    private static final Properties classPathProperties;

    static {
        hasApollo = ClzPath.classExists(APOLLO_CLZ);
        hasDiamond = ClzPath.classExists(DIAMOND_CLZ);
        hasEtcdconf = ClzPath.classExists(ETCDCONF_CLZ);
        int ct = 0;
        if (hasApollo) ct++;
        if (hasDiamond) ct++;
        if (hasDiamond) ct++;
        count = ct;
        classPathProperties = classResourceAsProperties("cql.env.props");
    }

    static String envSource() {
        return new StringSubstitutor(ssMap(argumentsAsProperties(
                classPathProperties))).replace(ENV_SOURCE);
    }

    public Cql() {
        super(createEqlConfig(), Eql.STACKTRACE_DEEP_FIVE);
    }

    public Cql(String connectionName) {
        super(createEqlConfig(connectionName), Eql.STACKTRACE_DEEP_FIVE);
    }

    public static EqlConfig createEqlConfig() {
        return createEqlConfig(Eql.DEFAULT_CONN_NAME);
    }

    public static EqlConfig createEqlConfig(String connectionName) {
        val env = envSource();
        if (count == 3) { // has all 3 dependencies
            if ("apollo".equalsIgnoreCase(env)) {
                return new EqlApolloConfig(connectionName);
            } else if ("diamond".equalsIgnoreCase(env)) {
                return new EqlDiamondConfig(connectionName);
            } else if ("etcd".equalsIgnoreCase(env)) {
                return new EqlEtcdConfig(connectionName);
            } else {
                throw new IllegalStateException("Illegal env setting: " + env);
            }

        } else if (count == 2) { // has some 2 dependencies
            if (hasApollo && "apollo".equalsIgnoreCase(env)) {
                return new EqlApolloConfig(connectionName);
            } else if (hasDiamond && "diamond".equalsIgnoreCase(env)) {
                return new EqlDiamondConfig(connectionName);
            } else if (hasEtcdconf && "etcd".equalsIgnoreCase(env)) {
                return new EqlEtcdConfig(connectionName);
            } else {
                throw new IllegalStateException("Illegal env setting: " + env);
            }

        } else if (count == 1) { // has only 1 dependency
            if (hasApollo) {
                return new EqlApolloConfig(connectionName);
            } else if (hasDiamond) {
                return new EqlDiamondConfig(connectionName);
            } else if (hasEtcdconf) {
                return new EqlEtcdConfig(connectionName);
            } else {
                throw new IllegalStateException("None of Apollo/Diamond/EtcdConf found.");
            }

        } else {
            throw new IllegalStateException("None of Apollo/Diamond/EtcdConf found.");
        }
    }
}
