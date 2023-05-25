package com.github.charlemaznable.eql.etcd;

import com.github.charlemaznable.eql.apollo.EqlApolloConfig;
import org.n3r.eql.Eql;
import org.n3r.eql.config.EqlConfig;

public class Etql extends Eql {

    public Etql() {
        super(createEqlConfig(), Eql.STACKTRACE_DEEP_FIVE);
    }

    public Etql(String connectionName) {
        super(createEqlConfig(connectionName), Eql.STACKTRACE_DEEP_FIVE);
    }

    public static EqlConfig createEqlConfig() {
        return createEqlConfig(Eql.DEFAULT_CONN_NAME);
    }

    public static EqlConfig createEqlConfig(String connectionName) {
        return new EqlEtcdConfig(connectionName);
    }
}
