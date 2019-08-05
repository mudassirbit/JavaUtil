package com.razorthink.bigbrain.blocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mudassir on 02/08/19.
 */
public class Subset {

	public static void main( String[] args )
	{
		List<Integer> n = new ArrayList<>();

		int sumToGet = 100;

		for( int i = 1; i <= sumToGet; i++ )
		{
			n.add(i);
		}

		Set<List<Integer>> solutions = new HashSet<>();

		long start = System.currentTimeMillis();
		find(n, solutions, sumToGet);

		System.out.println(solutions);
		long end = System.currentTimeMillis();
		System.out.println("Time taken recursive" + (end - start) + " ms");
		System.out.println("Total Sol: "+solutions.size());


		solutions = new HashSet<>();

		Boolean[][] table = new Boolean[n.size()][sumToGet + 1];

		table[0][0] = true;
		for( int i = 1; i <= sumToGet; i++ )
		{
			if( n.get(0).equals(i) )
			{
				table[0][i] = true;
			}
			else
			{
				table[0][i] = false;
			}
		}

		for( int i = 1; i < table.length; i++ )
		{
			for( int j = 0; j < table[0].length; j++ )
			{
				if( j == 0 )
				{
					table[i][j] = true;
				}
				else if( n.get(i) > j )
				{
					table[i][j] = table[i - 1][j];
				}
				else
				{
					table[i][j] = table[i - 1][j] || table[i - 1][j - n.get(i)];
				}

			}

		}
		printTable(table, n);

		findSubsetInTable(table, sumToGet, solutions, n, table.length, new ArrayList());
		System.out.println(solutions);

		System.out.println("Time taken dynamic: " + (System.currentTimeMillis() - end) + " ms");
		System.out.println("Total Sol:"+solutions.size());


	}

	private static void findSubsetInTable( Boolean[][] table, int sumToGet, Set<List<Integer>> solutions,
			List<Integer> n, int upperBound, List subsetPrev )
	{
		if( sumToGet == 0 )
		{
			solutions.add(subsetPrev);
		}
		if( sumToGet < 0 )
		{
			return;
		}
		for( int i = 0; i < upperBound; i++ )
		{

			if( table[i][sumToGet] )
			{
				List<Integer> subset = new ArrayList<>(subsetPrev);
				//				System.out.println(n.get(i));
				subset.add(0, n.get(i));
				findSubsetInTable(table, sumToGet - n.get(i), solutions, n, n.get(i) - 1, subset);

			}
		}
	}

	private static void printTable( Boolean[][] table, List<Integer> n )
	{
		for( int i = 0; i < table.length; i++ )
		{
			System.out.print(n.get(i) + " - ");
			for( int j = 0; j < table[0].length; j++ )
			{
				if( table[i][j] == null )
				{
					System.out.print("N ");
				}
				else if( table[i][j] )
				{
					System.out.print("T ");
				}
				else
				{
					System.out.print("F ");
				}
			}
			System.out.println();
		}
	}

	private static void fix( int[] a )
	{
		a[1] = 9;
	}

	private static void find( List<Integer> n, Set<List<Integer>> solutions, int sumToGet )
	{
		for( int j = 0; j < n.size(); j++ )
		{

			findSubset(n, solutions, sumToGet, j, new ArrayList<>());
		}
	}

	private static void findSubset( List<Integer> n, Set<List<Integer>> solutions, int sumToGet, int index,
			List<Integer> subset )
	{
		int sumOfSub = sumOfSubset(subset);
		if( sumOfSub == sumToGet )
		{
			solutions.add(subset);
			return;
		}
		if( index >= n.size() || sumOfSub > sumToGet )
		{
			return;
		}

		List<Integer> newSet = new ArrayList<>(subset);
		newSet.add(n.get(index));
		for( int i = index; i < n.size(); i++ )
		{
			findSubset(n, solutions, sumToGet, i + 1, newSet);
		}

	}

	private static int sumOfSubset( List<Integer> subset )
	{
		int sum = 0;
		for( Integer integer : subset )
		{
			sum += integer;
		}
		return sum;
	}

}
