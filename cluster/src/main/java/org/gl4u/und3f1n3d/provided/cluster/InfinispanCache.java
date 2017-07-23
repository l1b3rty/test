package org.gl4u.und3f1n3d.provided.cluster;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.remoting.transport.Transport;

//singleton
public class InfinispanCache {

    private DefaultCacheManager cacheManager;

    public InfinispanCache(Builder builder) {
        final GlobalConfigurationBuilder globalConfigurationBuilder = GlobalConfigurationBuilder.defaultClusteredBuilder();
        globalConfigurationBuilder
            .globalJmxStatistics()
            .allowDuplicateDomains(true);
        globalConfigurationBuilder
            .transport()
            .clusterName(builder.clusterName)
            .nodeName(builder.nodeName)
            .transport(builder.transport);

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.clustering().cacheMode(CacheMode.DIST_ASYNC);

        cacheManager = new DefaultCacheManager(globalConfigurationBuilder.build(), configurationBuilder.build());

        // need to get a cache, any cache to force the initialization
        cacheManager.getCache("distributedDirectory");

    }

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String clusterName;
        private String nodeName;
        private Transport transport;

        private Builder() { }

        public Builder setCLusterName(String clusterName) {
            this.clusterName = clusterName;
            return this;
        }

        public Builder setNodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public Builder setTransport(Transport transport) {
            this.transport = transport;
            return this;
        }

        public InfinispanCache build() {
            //check values is set
            return new InfinispanCache(this);
        }

    }


}
