// Part of SourceAFIS for Java: https://sourceafis.machinezoo.com/java
package com.machinezoo.sourceafis;

import java.io.*;
import java.util.*;
import com.machinezoo.noexception.*;
import it.unimi.dsi.fastutil.ints.*;

class IndexedEdge extends EdgeShape {
	final int reference;
	final int neighbor;
	IndexedEdge(ImmutableMinutia[] minutiae, int reference, int neighbor) {
		super(minutiae[reference], minutiae[neighbor]);
		this.reference = reference;
		this.neighbor = neighbor;
	}
	void write(DataOutputStream stream) {
		Exceptions.sneak().run(() -> {
			stream.writeInt(reference);
			stream.writeInt(neighbor);
			stream.writeInt(length);
			stream.writeDouble(referenceAngle);
			stream.writeDouble(neighborAngle);
		});
	}
	static byte[] serialize(Int2ObjectMap<List<IndexedEdge>> hash) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		DataOutputStream formatter = new DataOutputStream(buffer);
		int[] keys = hash.keySet().toIntArray();
		Arrays.sort(keys);
		Exceptions.sneak().run(() -> {
			formatter.writeInt(keys.length);
			for (int key : keys) {
				formatter.writeInt(key);
				List<IndexedEdge> edges = hash.get(key);
				formatter.writeInt(edges.size());
				for (IndexedEdge edge : edges)
					edge.write(formatter);
			}
			formatter.close();
		});
		return buffer.toByteArray();
	}
}
