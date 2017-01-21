package snp.spring.mybatis;

import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import snp.util.LoggerHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.*;

public class ConfigurationModifyObserverTimerTask extends TimerTask implements ConfigurationModifyObserver {
    private static final Logger L = LoggerHelper.getLogger();

    private Map<Resource, Long> lastModifiedTimes = new HashMap<>();

    private Configuration configuration;

    private int interval = 1000;

    private Timer timer = new Timer(true);

    private Set<ConfigurationModifyListener> listeners = new HashSet<>();

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void start() {
        timer.schedule(this, interval, interval);
    }

    @Override
    public void stop() {
        timer.cancel();
    }

    @Override
    public void addModifyListener(ConfigurationModifyListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeModifyListener(ConfigurationModifyListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void run() {
        if (configuration != null && isModified()) {
            for (ConfigurationModifyListener listener : listeners) {
                listener.onConfigurationModify();
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected boolean isModified() {
        boolean modified = false;
        try {
            Field loadedResourcesField = Configuration.class.getDeclaredField("loadedResources");
            loadedResourcesField.setAccessible(true);

            Set<String> loadedResources = (Set<String>) loadedResourcesField.get(configuration);
            List<String> modifiedResourceDescriptions = new ArrayList<String>();
            for (String loadedResource : loadedResources) {
                Resource resource = null;
                if (loadedResource.startsWith("file [") && loadedResource.endsWith("]")) {
                    resource = new FileSystemResource(loadedResource.substring(6, loadedResource.length() - 1));
                } else if (loadedResource.startsWith("URL [") && loadedResource.endsWith("]")) {
                    resource = new UrlResource(loadedResource.substring(5, loadedResource.length() - 1));
                } else if (loadedResource.startsWith("class path resource [") && loadedResource.endsWith("]")) {
                    resource = new ClassPathResource(loadedResource.substring(21, loadedResource.length() - 1));
                } else if (loadedResource.endsWith(".xml")) {
                    resource = new ClassPathResource(loadedResource);
                }
                if (resource != null && isModifiedResource(resource)) {
                    modifiedResourceDescriptions.add(resource.getDescription());
                    modified = true;
                }
            }
            if (modifiedResourceDescriptions.size() > 0) {
                if (L.isInfoEnabled()) {
                    for (String modifiedResourceDescription : modifiedResourceDescriptions) {
                        L.info("modified resource : " + modifiedResourceDescription);
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            L.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            L.error(e.getMessage(), e);
        } catch (MalformedURLException e) {
            L.error(e.getMessage(), e);
        }

        return modified;
    }

    private boolean isModifiedResource(Resource resource) {
        try {
            long modified = resource.lastModified();
            if (lastModifiedTimes.containsKey(resource)) {
                long lastModified = lastModifiedTimes.get(resource).longValue();

                if (lastModified != modified) {
                    lastModifiedTimes.put(resource, modified);
                    return true;
                }
            } else {
                lastModifiedTimes.put(resource, modified);
            }
        } catch (IOException e) {
            L.error(e.getMessage(), e);
        }

        return false;
    }

}
