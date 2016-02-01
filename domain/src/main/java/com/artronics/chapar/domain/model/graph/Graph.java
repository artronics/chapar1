package com.artronics.chapar.domain.model.graph;

import java.util.Set;

public interface Graph<V extends Vertex,E extends Edge>{
    /**
     * Adds the specified vertex to this graph if not already present. More
     * formally, adds the specified vertex, <code>v</code>, to this graph if
     * this graph contains no vertex <code>u</code> such that <code>
     * u.equals(v)</code>. If this graph already contains such vertex, the call
     * leaves this graph unchanged and returns <tt>false</tt>. In combination
     * with the restriction on constructors, this ensures that graphs never
     * contain duplicate vertices.
     *
     * @param vertex vertex to be added to this graph.
     *
     * @return <tt>true</tt> if this graph did not already contain the specified
     * vertex.
     *
     * @throws NullPointerException if the specified vertex is <code>
     * null</code>.
     */

    boolean addVertex(V vertex);

    /**
     * Returns <tt>true</tt> if this graph contains the specified vertex. More
     * formally, returns <tt>true</tt> if and only if this graph contains a
     * vertex <code>u</code> such that <code>u.equals(v)</code>. If the
     * specified vertex is <code>null</code> returns <code>false</code>.
     *
     * @param vertex vertex whose presence in this graph is to be tested.
     *
     * @return <tt>true</tt> if this graph contains the specified vertex.
     */
    boolean containsVertex(V vertex);

    /**
     * Returns a set of all edges touching the specified vertex. If no edges are
     * touching the specified vertex returns an empty set.
     *
     * @param vertex the vertex for which a set of touching edges is to be
     * returned.
     *
     * @return a set of all edges touching the specified vertex.
     *
     * @throws IllegalArgumentException if vertex is not found in the graph.
     * @throws NullPointerException if vertex is <code>null</code>.
     */
     Set<E> edgesOf(V vertex);

    /**
     * Returns <tt>true</tt> if and only if this graph contains an edge going
     * from the source vertex to the target vertex. In undirected graphs the
     * same result is obtained when source and target are inverted. If any of
     * the specified vertices does not exist in the graph, or if is <code>
     * null</code>, returns <code>false</code>.
     *
     * @param sourceVertex source vertex of the edge.
     * @param targetVertex target vertex of the edge.
     *
     * @return <tt>true</tt> if this graph contains the specified edge.
     */
     boolean containsEdge(V sourceVertex, V targetVertex);

}
