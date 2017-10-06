package eu.sig.safari.exercises.stubs.simianarmy;

import java.util.Collection;
import java.util.List;

public interface ConformityClusterTracker {

	List<Cluster> getAllClusters(String[] array);

	void deleteClusters(Cluster[] array);

	void addOrUpdate(Cluster cluster);

	Collection<Cluster> getNonconformingClusters(String[] array);

}
