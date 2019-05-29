package com.razorthink.bigbrain.blocks.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

/**
 * Created by mudassir on 30/01/19.
 */
public class MinimizeDeliveryTime {

	private static Map<Integer, List<Edge>> fromEdgeMap;

	private static Map<Integer, List<Edge>> toEdgeMap;

	private static List<List<Integer>> weighPaths = new ArrayList<>();

	public static void main( String[] args )
	{
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();

		int k = s.nextInt();

		List<Edge> edges = new ArrayList<>();

		List<Integer> vertex = new ArrayList<>();
		for( int i = 1; i <= n; i++ )
		{
			vertex.add(i);
		}

		for( int i = 1; i <= k; i++ )
		{
			int fromVertex = s.nextInt();
			int toVertex = s.nextInt();
			int weight = s.nextInt();

			edges.add(new Edge(fromVertex, toVertex, weight));
		}

		fromEdgeMap = edges.stream().collect(groupingBy(Edge::getFromVertex, mapping(Function.identity(), toList())));
		toEdgeMap = edges.stream().collect(groupingBy(Edge::getToVertex, mapping(Function.identity(), toList())));

		travel(1, vertex, n, new ArrayList<>());

		System.out.println(weighPaths.size());
		weighPaths.forEach(l -> {
			System.out.print(l.size() + " ");
			l.forEach(w -> System.out.print(w + " "));
			System.out.println();
		});

	}

	private static void travel( Integer startVertex, List<Integer> vertexesInput, Integer endVertex,
			List<Integer> visitedPathWeight )
	{

		if( startVertex.equals(endVertex) )
		{
			weighPaths.add(visitedPathWeight);
		}

		List<Integer> vertexes = new ArrayList<>(vertexesInput);
		vertexes.remove(startVertex);

		List<Edge> availablePathsFromSource = fromEdgeMap.get(startVertex);

		if( availablePathsFromSource != null )
		{
			for( Edge edge : availablePathsFromSource )
			{
				if( vertexes.contains(edge.toVertex) )
				{
					List<Integer> visitedPathWeight1 = new ArrayList<>(visitedPathWeight);
					visitedPathWeight1.add(edge.weight);
					travel(edge.toVertex, vertexes, endVertex, visitedPathWeight1);
				}
			}
		}

	}

	private static class Edge {

		int fromVertex;

		int toVertex;

		int weight;

		public Edge( int fromVertex, int toVertex, int weight )
		{
			this.fromVertex = fromVertex;
			this.toVertex = toVertex;
			this.weight = weight;
		}

		public int getFromVertex()
		{
			return fromVertex;
		}

		public void setFromVertex( int fromVertex )
		{
			this.fromVertex = fromVertex;
		}

		public int getToVertex()
		{
			return toVertex;
		}

		public void setToVertex( int toVertex )
		{
			this.toVertex = toVertex;
		}

		public int getWeight()
		{
			return weight;
		}

		public void setWeight( int weight )
		{
			this.weight = weight;
		}
	}

}
