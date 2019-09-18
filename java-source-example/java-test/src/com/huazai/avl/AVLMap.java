package com.huazai.avl;

import org.junit.Assert;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class AVLMap<K, V> implements Iterable<AVLEntry<K, V>> {
    /**
     * 树元素的个数
     */
    private int size;
    /**
     * 数的根节点
     */
    public AVLEntry<K, V> root;
    /**
     * key的比较器
     */
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

            // 程序运行到此处代表是新增元素
            AVLEntry<K, V> entry = new AVLEntry<>(key, value, parent);
            if (comRes < 0) {
                parent.setLeft(entry);
            } else {
                parent.setRight(entry);
            }
            size++;

//            fixInsertion(key);
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
     * 第二种情况：p只有左子树left(或右子树right)，直接用p.left替换p（或用p.right替换p）
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
                    // (size & 1)：随机选择一二种方案
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
            // 删除调整
//            p=fixAfterDeletion(p);
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

    /**
     * 获取AVL树的高度，如果树为空则返回0
     *
     * @param p
     * @return
     */
    public int getHeight(AVLEntry<K, V> p) {
        return p == null ? 0 : p.height;
    }

    /**
     * 右旋
     * 思路：
     * 1、p左孩子变成p左孩子的右孩子
     * 2、p左孩子的右孩子变成p的父亲
     * <p>
     * 如果由父亲节点还需要关联父亲节点的关系
     *
     * @param p
     * @return
     */
    private AVLEntry<K, V> rotateRight(AVLEntry<K, V> p) {
        AVLEntry<K, V> left = p.left;
        p.left = left.right;
        left.right = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        left.height = Math.max(getHeight(left.left), p.height) + 1;
        return left;
    }

    /**
     * 左旋
     * 思路：
     * 1、p右孩子变成p右孩子的左孩子
     * 2、p右孩子的左孩子变成p的父亲
     * <p>
     * 如果由父亲节点还需要关联父亲节点的关系
     *
     * @param p
     * @return
     */
    private AVLEntry<K, V> rotateLeft(AVLEntry<K, V> p) {
        AVLEntry<K, V> right = p.right;
        p.right = right.left;
        right.left = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        right.height = Math.max(p.height, getHeight(right.right)) + 1;
        return right;
    }

    /**
     * 先左旋再右旋
     *
     * @param p
     * @return
     */
    private AVLEntry<K, V> firstLeftThenRight(AVLEntry<K, V> p) {
        p.left = rotateLeft(p.left);
        p = rotateRight(p);
        return p;
    }

    /**
     * 先右旋再左旋
     *
     * @param p
     * @return
     */
    private AVLEntry<K, V> firstRightThenLeft(AVLEntry<K, V> p) {
        p.right = rotateRight(p.right);
        p = rotateLeft(p);
        return p;
    }

    /**
     * stack保存了插入节点时候查找所经历的路径
     */
    private LinkedList<AVLEntry<K, V>> stack = new LinkedList<AVLEntry<K, V>>();

    /**
     * 插入元素后调整AVL数的平衡性，插入关键字key后，节点p的平衡因子由原来的1或者-1，变成了2或者-2，则需要旋转；
     *
     * 插入key到左子树left的情形，即平衡因子为2
     * 情况1-1：key<left.key, 即插入到left的左子树，需要进行单旋转，将节点p右旋
     * 情况2-1：key>left.key, 即插入到left的右子树，需要进行双旋转，先将left左旋，再将p右旋
     *
     * 插入key右子树right的情形，即平衡因子为-2
     * 情况1-2：key>right.key，即插入right的右子树，需要进行单旋转，将节点p左旋
     * 情况2-2：key<right.key，即插入right的左子树，需要进行双旋转，先将right节点右旋，再将p左旋
     *
     * @param key
     */
    private void fixAfterInsertion(K key) {
        AVLEntry<K, V> p = root;
        while (!stack.isEmpty()) {
            p = stack.pop();
            int newHeight = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
            // 如果插入后没有引起AVL树高度的变化，则无需调整。p.height > 1，不考虑叶子节点
            if (p.height > 1 && newHeight == p.height) {
                stack.clear();
                return;
            }
            p.height = newHeight;
            // 树的平衡因子
            int d = getHeight(p.left) - getHeight(p.right);
            // 树的平衡因子绝对值不大于1，则不需要调整
            if (Math.abs(d) <= 1) {
                continue;
            } else {
                // 插入到了左子树
                if (d == 2) {
                    if (compare(key, p.left.getKey()) < 0) {    // 情况1-1
                        p = rotateRight(p);
                    } else {    // 情况2-1
                        p = firstLeftThenRight(p);
                    }
                } else {
                    // 插入到了右子树
                    if (compare(key, p.right.getKey()) > 0) {   // 情况1-2
                        p = rotateLeft(p);
                    } else {    // 情况2-2
                        p = firstRightThenLeft(p);
                    }
                }
                if (!stack.isEmpty()) {
                    if (compare(key, stack.peek().getKey()) < 0) {
                        stack.peek().left = p;
                    } else {
                        stack.peek().right = p;
                    }
                }
            }
        }
        root = p;
    }

    /**
     * 检测AVL树是否平衡，如果不平衡则抛出异常
     */
    public void checkBalance() {
        postOrderCheckBalance(root);
    }

    /**
     * 后序遍历检测AVL树是否平衡
     *
     * @param p
     */
    private void postOrderCheckBalance(AVLEntry<K, V> p) {
        if (p != null) {
            postOrderCheckBalance(p.left);
            postOrderCheckBalance(p.right);
            Assert.assertTrue(Math.abs(getHeight(p.left) - getHeight(p.right)) <= 1);
        }
    }

    /**
     * AVL数的删除调整
     * <p>
     * 操作：删除p的右孩子，设p的左孩子为left
     * 情况1-1：left的平衡因子d[left]=1，将p右旋
     * 情况2-1：left的平衡因子d[left]=0，将p右旋
     * 情况3-1：left的平衡因子d[left]= -1，先左旋left，再右旋p
     * <p>
     * 操作：删除p的左孩子，设p的右孩子为right
     * 情况1-2：left的平衡因子d[right]=1，将p左旋
     * 情况2-2：left的平衡因子d[right]=0，将p左旋
     * 情况3-2：left的平衡因子d[right]= -1，先右旋right，再左旋p
     *
     * @param p
     * @return
     */
    public AVLEntry<K, V> fixAfterDeletion(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        } else {
            p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
            // d：p左右子树的平衡因子
            int d = getHeight(p.left) - getHeight(p.right);
            // 平衡因子绝对值等于2的时候才需要调整
            // d == 2则代表删除的是p的右子树的元素，否则删除的是p左子树的元素
            if (d == 2) {
                if (getHeight(p.left.left) - getHeight(p.left.right) >= 0) {    // >= 0，可能为0也可能为1，为情况1-1和情况2-1，都需要右旋
                    p = rotateRight(p);
                } else {    // 情况3-1，先左旋再右旋
                    p = firstLeftThenRight(p);
                }
            } else if (d == -2) {   // >= 0，可能为0也可能为1，为情况1-2和情况2-2，都需要左旋
                if (getHeight(p.right.right) - getHeight(p.right.left) >= 0) {
                    p = rotateLeft(p);
                } else {    // 情况3-2，先右旋再左旋
                    p = firstRightThenLeft(p);
                }
            }
            return p;
        }
    }

}
