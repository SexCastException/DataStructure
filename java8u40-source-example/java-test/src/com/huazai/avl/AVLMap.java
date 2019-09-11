package com.huazai.avl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class AVLMap<K, V> implements Iterable<AVLEntry<K, V>> {
    private int size;
    public AVLEntry<K, V> root;
    private Comparator<K> comp;

    public AVLMap() {
        super();
    }

    /**
     * 构造方法传入自定义校验器，如果没有传入则使用默认的校验器
     *
     * @param comp
     */
    public AVLMap(Comparator<K> comp) {
        this.comp = comp;
    }

    /**
     * 比较器
     *
     * @param a
     * @param b
     * @return
     */
    private int compare(K a, K b) {
        if (comp != null) {
            return comp.compare(a, b);
        }
        Comparable<K> o = (Comparable<K>) a;
        return ((Comparable<K>) a).compareTo(b);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    public V put(K key, V value) {
        // 根节点为空则插入的元素就是根节点
        AVLEntry<K, V> t = root;
        if (t == null) {
            root = new AVLEntry<>(key, value, null);
            size = 1;
            return null;
        } else {
            int comRes;
            AVLEntry<K, V> parent;
            do {
                parent = t;
                comRes = compare(key, t.getKey());
                if (comRes < 0) {
                    t = t.getLeft();
                } else if (comRes > 0) {
                    t = t.getRight();
                } else {
                    return t.setValue(value);
                }
            } while (t != null);

            AVLEntry<K, V> entry = new AVLEntry<>(key, value, parent);
            if (comRes < 0) {
                parent.setLeft(entry);
            } else {
                parent.setRight(entry);
            }
            size++;
            return null;
        }
    }

    @Override
    public Iterator<AVLEntry<K, V>> iterator() {
        return new AVLIterator<>(root);
    }

    public AVLEntry<K, V> getEntry(K key) {
        // 非迭代方式
        return getEntryByComparatory(key);
        // 迭代方式
//        return getEntryByTraversing(root, key);
    }

    /**
     * 非迭代方式查找
     *
     * @param key
     * @return
     */
    private AVLEntry<K, V> getEntryByComparatory(K key) {
        if (key == null) {
            throw new NullPointerException("key:" + key + " is required not null");
        }
        AVLEntry<K, V> p = root;
        while (p != null) {
            int compare = compare(key, p.getKey());
            if (compare == 0) {
                return p;
            } else if (compare < 0) {
                p = p.getLeft();
            } else {
                p = p.getRight();
            }
        }
        return null;
    }

    /**
     * 迭代方式查找
     *
     * @param root
     * @param key
     * @return
     */
    private AVLEntry<K, V> getEntryByTraversing(AVLEntry<K, V> root, K key) {
        if (key == null) {
            throw new NullPointerException("key:" + key + " is required not null");
        }
        if (root != null) {
            int compare = compare(key, root.getKey());
            if (compare == 0) {
                return root;
            } else if (compare < 0) {
                getEntryByTraversing(root.getLeft(), key);
            } else {
                getEntryByTraversing(root.getRight(), key);
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    public boolean containsValue(V value) {
        Iterator<AVLEntry<K, V>> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public V get(K key) {
        AVLEntry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    /**
     * 获取节点root的最左子节点（即参数root的最小子节点)
     *
     * @param p
     * @return
     */
    public AVLEntry<K, V> getFirstEntry(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        }
        while (p.getLeft() != null) {
            p = p.getLeft();
        }
        return p;
    }

    /**
     * 获取节点root最右子节点(即参数root的最大子节点)
     *
     * @param p
     * @return
     */
    public AVLEntry<K, V> getLastEntry(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        }
        while (p.getRight() != null) {
            p = p.getRight();
        }
        return p;
    }

    /**
     * 删除节点
     * 第一种情况：p是叶子节点，直接删除
     * 第二种情况：p只有左子树left(或右子树right)，直接用p.left替换p
     * 第三种情况：p既有左子树left，又有右子树right，找到右子树的最小节点rightMin(需要借助于getFirstEntry，或找到left的最大节点leftMax)，用rightMin的值替换p的值，再根据以上两种情况删除rightMin
     *
     * @param p
     * @param key
     * @return
     */
    private AVLEntry<K, V> deleteEntry(AVLEntry<K, V> p, K key) {
        if (p == null) {
            return null;
        } else {
            int compareResult = compare(key, p.getKey());
            if (compareResult == 0) {
                if (p.left == null && p.right == null) {// 第一种情况
                    p = null;
                } else if (p.left != null && p.right == null) { // 第二种情况
                    p = p.left;
                } else if (p.left == null && p.right != null) { // 第二种情况
                    p = p.right;
                } else { //第三种情况
                    if ((size & 1) == 0) {
                        AVLEntry<K, V> rightMin = getFirstEntry(p.getRight());
                        p.setKey(rightMin.getKey());
                        p.setValue(rightMin.getValue());
                        AVLEntry<K, V> newRight = deleteEntry(p.right, p.getKey());
                        p.right = newRight;
                    } else {
                        AVLEntry<K, V> leftMax = getLastEntry(p.left);
                        p.setKey(leftMax.getKey());
                        p.setValue(leftMax.getValue());
                        AVLEntry<K, V> newLeft = deleteEntry(p.left, p.getKey());
                        p.left = newLeft;
                    }
                }
            } else if (compareResult < 0) {
                AVLEntry<K, V> newLeft = deleteEntry(p.left, key);
                p.left = newLeft;
            } else {
                AVLEntry<K, V> newRight = deleteEntry(p.right, key);
                p.right = newRight;
            }
            return p;
        }
    }

    public V remove(K key) {
        AVLEntry<K, V> entry = getEntry(key);
        if (entry == null) {
            return null;
        }
        V oldValue = entry.getValue();
        root = deleteEntry(root, key);
        size--;
        return oldValue;
    }

    /**
     * 层序遍历输出节点
     */
    public void levelOrder() {
        Queue<AVLEntry<K, V>> queue = new LinkedList();
        if (root == null) {
            return;
        }
        queue.offer(root);
        // 保存上一层的个数，默认为根节点为一层
        int preCount = 1;
        // 计算当前一层的个数
        int count = 0;
        while (!queue.isEmpty()) {
            preCount--;
            AVLEntry<K, V> entry = queue.poll();
            System.out.print(entry + " ");
            if (entry.getLeft() != null) {
                queue.offer(entry.getLeft());
                count++;
            }
            if (entry.getRight() != null) {
                queue.offer(entry.getRight());
                count++;
            }
            if (preCount == 0) {
                preCount = count;
                count = 0;
                System.out.println();
            }
        }
    }

    /**
     * 中序遍历查找节点t的后继节点
     * 情况0：t是最大节点，没有后继节点
     * 情况1：t没有右孩子，查找孩子是左孩子的父节点p（即比t大的第一个节点）
     * 情况2：t有右孩子，查找右孩子的firstEntry（即以右孩子为根节点的最小节点）
     *
     * @param t
     * @return
     */
    public AVLEntry<K, V> successor(AVLEntry<K, V> t) {
        if (t == null) {
            return null;
        }
        // 情况0
        if (getLastEntry(root) == t) {
            return null;
        }
        if (t.getRight() != null) { // 情况2
            return getFirstEntry(t.getRight());
        } else {    // 情况1
            AVLEntry<K, V> p = t.getParent();
            AVLEntry<K, V> ch = t;
            while (p != null && ch == p.getRight()) {
                ch = p;
                p = p.getParent();
            }
            return p;
        }
    }

    /**
     * 中序遍历查找t的前驱节点，算法和查找后继节点方法successor对称
     * 情况0：t是最小节点，则t没有前驱节点
     * 情况1：t没有左孩子，查找孩子是右孩子的父节点p（即比t小的第后一个节点）
     * 情况2：t有左孩子，查找左孩子的lastEntry（即以左孩子为根节点的最大节点）
     *
     * @param t
     * @return
     */
    public AVLEntry<K, V> predecessor(AVLEntry<K, V> t) {
        if (t == null) {
            return null;
        }
        // 情况0
        if (getFirstEntry(root) == t) {
            return null;
        }
        if (t.getLeft() != null) {  // 情况2
            return getLastEntry(t.getLeft());
        } else { // 情况1
            AVLEntry<K, V> p = t.getParent();
            AVLEntry<K, V> ch = p;
            while (p != null && ch == p.getLeft()) {
                ch = p;
                p = p.getParent();
            }
            return p;
        }
    }

}
