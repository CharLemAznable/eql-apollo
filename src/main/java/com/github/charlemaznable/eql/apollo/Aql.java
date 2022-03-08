package com.github.charlemaznable.eql.apollo;

import org.n3r.eql.Eql;
import org.n3r.eql.config.EqlConfig;

public class Aql extends Eql {

    public Aql() {
        super(createEqlConfig(), Eql.STACKTRACE_DEEP_FIVE);
    }

    public Aql(String connectionName) {
        super(createEqlConfig(connectionName), Eql.STACKTRACE_DEEP_FIVE);
    }

    public static EqlConfig createEqlConfig() {
        return createEqlConfig(Eql.DEFAULT_CONN_NAME);
    }

    public static EqlConfig createEqlConfig(String connectionName) {
        return new EqlApolloConfig(connectionName);
    }
}
