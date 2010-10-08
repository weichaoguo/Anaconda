/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2009 INRIA/University of
 * 						   Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$PROACTIVE_INITIAL_DEV$$
 */
package org.ow2.proactive.resourcemanager.frontend.topology;

import java.util.List;

import org.objectweb.proactive.annotation.PublicAPI;
import org.objectweb.proactive.core.node.Node;


/**
 * The descriptor allows to select a set of closest nodes which is currently
 * available in the resource manager.
 *
 * In order to specify more precisely what "closets" means user may select various distance
 * functions (for details see http://en.wikipedia.org/wiki/Cluster_analysis#Agglomerative_hierarchical_clustering)
 *
 * AVG - the mean distance between elements of each cluster (also called average linkage clustering).
 * MAX - the maximum distance between elements of each cluster (also called complete linkage clustering).
 * MIN - the minimum distance between elements of each cluster (also called single-linkage clustering).
 *
 */
@PublicAPI
public class BestProximityDescriptor extends TopologyDescriptor {

    // pivot nodes
    protected List<Node> pivot = null;
    protected DistanceFunction function;

    /** AVG - the mean distance between elements of each cluster (also called average linkage clustering) */
    public final static DistanceFunction AVG = new DistanceFunction() {
        public long distance(long d1, long d2) {
            // not connected
            if (d1 < 0 || d2 < 0)
                return -1;

            return (d1 + d2) / 2;
        }
    };

    /** MAX - the maximum distance between elements of each cluster (also called complete linkage clustering) */
    public final static DistanceFunction MAX = new DistanceFunction() {
        public long distance(long d1, long d2) {
            // not connected
            if (d1 < 0 || d2 < 0)
                return -1;

            return Math.max(d1, d2);
        }
    };

    /** MIN - the minimum distance between elements of each cluster (also called single-linkage clustering) */
    public final static DistanceFunction MIN = new DistanceFunction() {
        public long distance(long d1, long d2) {
            return Math.min(d1, d2);
        }
    };

    /**
     * Constructs new instance of the class.
     */
    public BestProximityDescriptor() {
        super(true);
    }

    /**
     * Constructs new instance of the class with specified distance function.
     *
     * @param function - the distance function used for clustering
     */
    public BestProximityDescriptor(DistanceFunction function) {
        super(true);
        this.function = function;
    }

    /**
     * Constructs new instance of the class with specified distance function and pivot.
     *
     * @param function - the distance function used for clustering
     * @param pivot - the set of nodes from which the proximity should be the best.
     */
    public BestProximityDescriptor(DistanceFunction function, List<Node> pivot) {
        this(function);
        this.pivot = pivot;
    }

    /**
     * Gets pivot nodes
     * @return pivot nodes
     */
    public List<Node> getPivot() {
        return pivot;
    }

    /**
     * Gets the distance function. AVG by default.
     * @return the distance function
     */
    public DistanceFunction getDistanceFunction() {
        return function == null ? AVG : function;
    }
}
