package com.uk.blog.utils;

import java.util.Collection;

public interface TreeNode{	
	<T extends TreeNode> T addChild(T child);

	<T extends TreeNode> T addChildren(Collection<? extends TreeNode> children);

	<T extends TreeNode> T removeChild(T child);

	<T extends TreeNode> T removeChildren(Collection<? extends TreeNode> children);

	<T extends TreeNode> T removeAllChildren();

	<T extends TreeNode> T addToParentNode(T parent);
}
