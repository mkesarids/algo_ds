package com.avl2;

import java.util.Random;


public class BalancedBSTUtil {

	BBSTNode root=null;
	BBSTNode parent;

	/**
	 * If value occur ,return that node otherwise return null . But in any case set the parent node.  
	 * @param value
	 * @return
	 */
	public BBSTNode find(double value) {
		BBSTNode temp = root;
		parent=root;
		while (temp != null) {
			if (value == temp.value) {
				return temp;
			} else if (value > temp.value) {
				parent=temp;
				temp = temp.right;
			} else {
				parent=temp;
				temp = temp.left;
			}
		}
		return null;
	}
	
	/**
	 * This 
	 * @param size : Provide BST Size. Those many elements will be generated by RandomAPI.
	 */
	public void insert(int size) {

		Random r = new Random();
		for (int i = 0; i < size; i++) {
			double nodevalue = Math.ceil(  r.nextDouble()*100);
			System.out.println(nodevalue+"*Inserting*");
			insertValue(nodevalue);
		}

	}
	
	private void insertValue(double nodevalue) {
		
		if(root==null) {
			root=new BBSTNode(nodevalue);
			return;
		}
		BBSTNode ref = find(nodevalue);
		if (ref != null) {
			System.out.println("Value Already Available");
		} else {
			BBSTNode n=new BBSTNode(nodevalue);
			n.parent=parent;
			if (nodevalue > parent.value) {
				parent.right = n;
			} else {
				parent.left = n;
			}
			recomputeHeight(parent);
			
			/**
			 * rebalancing 
			 */
			rebalancing(n);
			
		}
		
	}
	
	private void rebalancing(BBSTNode n) {
		
		BBSTNode x=n;
		BBSTNode y=n;
		BBSTNode z=n;
		
		
		while(x!=null) {
			
			int lheight=x.left.height;
			int rheight=x.right.height;
			int diff= Math.abs(lheight-rheight);
			if(diff>=2) {
				/** imbalance happend *
				 * 
				 */
				imbalanceCorrection(x,y,z);
			}else{
				z=y;
				y=x;
				x=x.parent;
			}
			
		}
	}
		
		private void imbalanceCorrection(BBSTNode x,BBSTNode y,BBSTNode z) {
			BBSTNode a,b,c;
			BBSTNode t0,t1,t2,t3;
			
			if(y==x.left && z==y.left) {
				b=y;
				a=z;
				c=x;
				t0=z.left;
				t1=z.right;
				t2=y.right;
				t3=x.right;
			}
			else if(y==x.right && z==y.right) {
				b=y;
				a=x;
				c=z;
				t0=x.left;
				t1=y.left;
				t2=z.left;
				t3=z.right;
			}
			else if(y==x.left && z==y.right) {
				b=z;
				a=y;
				c=x;
				t0=y.left;
				t1=z.left;
				t2=z.right;
				t3=x.right;
			}else {
				b=z;
				a=x;
				c=y;
				t0=x.left;
				t1=z.left;
				t2=z.right;
				t3=y.right;
			}
			
			b.left=a;
			b.right=c;
			a.parent=b;
			c.parent=b;
			a.left=t0;
			a.right=t1;
			c.left=t2;
			c.right=t3;
			b.parent=x.parent;
			
		}
		

	private void recomputeHeight(BBSTNode parent) {

		while (parent != null) {
			int leftheight = parent.left != null ? parent.left.height : 0;
			int rightheight = parent.right != null ? parent.right.height : 0;
			int maxHeight = Math.max(leftheight, rightheight) + 1;
			
			if(parent.height>=maxHeight)
				return;
			parent.height=maxHeight;
			parent = parent.parent;
		}
	}
	
}
