package com.github.charlemaznable.eql.apollo;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
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
public class EqlApolloConfig extends EqlPropertiesConfig
        implements EqlTranFactoryCacheLifeCycle {

    public static final String EQL_CONFIG_NAMESPACE = "EqlConfig";

    private String connectionName;
    private ConfigChangeListener changeListener;

    public EqlApolloConfig(String connectionName) {
        this(tryDecrypt(parseStringToProperties(ConfigService.getConfig(EQL_CONFIG_NAMESPACE)
                .getProperty(connectionName, "")), connectionName), connectionName);
    }

    public EqlApolloConfig(Properties properties, String connectionName) {
        super(properties);
        this.connectionName = connectionName;
    }

    @Override
    public void onLoad() {
        val eqlConfig = new DefaultEqlConfigDecorator(this);
        changeListener = changeEvent -> {
            if (!changeEvent.changedKeys().contains(connectionName)) return;
            EqlConfigManager.invalidateCache(eqlConfig);
        };
        ConfigService.getConfig(EQL_CONFIG_NAMESPACE).addChangeListener(changeListener);
    }

    @Override
    public void onRemoval() {
        ConfigService.getConfig(EQL_CONFIG_NAMESPACE).removeChangeListener(changeListener);
    }
}
