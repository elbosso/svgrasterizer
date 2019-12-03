package de.elbosso.tools.svgrasterizer;

import java.io.File;
import java.util.Iterator;

public class MultiResolutionHelper extends java.lang.Object implements java.lang.Iterable<java.io.File>
{
	private java.io.File[] svgs;

	MultiResolutionHelper(java.io.File[] svgs)
	{
		super();
		this.svgs=svgs;
	}

	@Override
	public Iterator<File> iterator()
	{
		return new IteratorImpl();
	}

	class IteratorImpl extends java.lang.Object implements java.util.Iterator<java.io.File>
	{
		private int counter=0;

		@Override
		public boolean hasNext()
		{
			return counter<svgs.length;
		}

		@Override
		public File next()
		{
			return svgs[counter++];
		}
	}
}
