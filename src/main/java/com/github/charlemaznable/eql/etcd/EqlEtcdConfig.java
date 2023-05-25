package com.github.charlemaznable.eql.etcd;

import com.github.charlemaznable.etcdconf.EtcdConfigChangeListener;
import com.github.charlemaznable.etcdconf.EtcdConfigService;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.n3r.eql.config.EqlConfigManager;
import org.n3r.eql.config.EqlPropertiesConfig;
import org.n3r.eql.config.EqlTranFactoryCacheLifeCycle;
import org.n3r.eql.impl.DefaultEqlConfigDecorator;

import java.util.Properties;

import static com.github.charlemaznable.core.lang.Propertiess.parseStringToProperties;
import static com.github.charlemaznable.core.lang.Propertiess.tryDecrypt;

@EqualsAndHashCode(of = "connectionName", callSuper = false)
public class EqlEtcdConfig extends EqlPropertiesConfig
        implements EqlTranFactoryCacheLifeCycle {

    public static final String EQL_CONFIG_NAMESPACE = "EqlConfig";

    private final String connectionName;
    private EtcdConfigChangeListener changeListener;

    public EqlEtcdConfig(String connectionName) {
        this(tryDecrypt(parseStringToProperties(EtcdConfigService.getConfig(EQL_CONFIG_NAMESPACE)
                .getString(connectionName, "")), connectionName), connectionName);
    }

    public EqlEtcdConfig(Properties properties, String connectionName) {
        super(properties);
        this.connectionName = connectionName;
    }

    @Override
    public void onLoad() {
        val eqlConfig = new DefaultEqlConfigDecorator(this);
        changeListener = e -> EqlConfigManager.invalidateCache(eqlConfig);
        EtcdConfigService.getConfig(EQL_CONFIG_NAMESPACE)
                .addChangeListener(connectionName, changeListener);
    }

    @Override
    public void onRemoval() {
        EtcdConfigService.getConfig(EQL_CONFIG_NAMESPACE)
                .removeChangeListener(changeListener);
    }
}
