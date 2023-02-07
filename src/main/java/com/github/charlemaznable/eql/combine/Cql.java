package com.github.charlemaznable.eql.combine;

import com.github.charlemaznable.core.lang.ClzPath;
import com.github.charlemaznable.eql.apollo.EqlApolloConfig;
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
    private static final String ENV_SOURCE = "${EqlConfigService:-diamond}";

    private static final boolean hasApollo;
    private static final boolean hasDiamond;

    private static final Properties classPathProperties;

    static {
        hasApollo = ClzPath.classExists(APOLLO_CLZ);
        hasDiamond = ClzPath.classExists(DIAMOND_CLZ);
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
        if (hasApollo && hasDiamond) {
            if ("apollo".equalsIgnoreCase(envSource())) {
                return new EqlApolloConfig(connectionName);
            }
            return new EqlDiamondConfig(connectionName);
        } else if (hasApollo) return new EqlApolloConfig(connectionName);
        else if (hasDiamond) return new EqlDiamondConfig(connectionName);
        else {
            throw new IllegalStateException("Neither Apollo nor Diamond found.");
        }
    }
}
