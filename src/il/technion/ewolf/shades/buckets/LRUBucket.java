package il.technion.ewolf.shades.buckets;


import il.technion.ewolf.kbr.Node;
import il.technion.ewolf.kbr.openkad.KadNode;
import il.technion.ewolf.kbr.openkad.bucket.Bucket;
import il.technion.utils.PrintsManager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

/**
 * A bucket with the following policy:
 * Any new node is inserted. 
 * If the bucket has reached its max size the oldest node in the bucket is removed.
 * 
 * @author eyal.kibbar@gmail.com
 *
 */
public class LRUBucket implements Bucket {


	private final List<KadNode> bucket;
	private final int maxSize;
	private PrintsManager printManager;

	@Inject
	public LRUBucket(int maxSize,
			NodesByColor nodesByColor, 
			PrintsManager printManager) {
		this.maxSize = maxSize;
		this.printManager = printManager;
		bucket = new LinkedList<KadNode>();
	}


	@Override
	public void insert(KadNode n) {
		// dont bother with other people wrong information
		if (n.hasNeverContacted())
			return;

		synchronized (bucket) {
			if (bucket.contains(n))
				return;

			if (bucket.size() < maxSize){
				bucket.add(n);
			}else{
				bucket.remove(0);
				bucket.add(n);
			}
		}
	}

	@Override
	public void addNodesTo(Collection<Node> c) {
		synchronized (bucket) {
			for (KadNode n : bucket) {
				c.add(n.getNode());
			}
		}
	}

	@Override
	public void markDead(Node n) {
		// nothing to do
	}
}
