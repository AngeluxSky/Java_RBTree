package org.example;

/**
 * enum Color - Перечисление для цветов узлов.
 * RBNode<T> - Класс узла красно-черного дерева.
 * RBTree<T extends Comparable<T>> - Класс красно-черного дерева.
 * inOrderTraversal - Метод для выполнения обхода дерева в порядке возрастания.
 * leftRotate - Реализация левого вращения.
 * rightRotate -  Реализация правого вращения.
 * insertFixUp - Метод для восстановления свойств красно-черного дерева после вставки.
 */

public class Main {
    public static void main(String[] args) {
        RBTree<Integer> tree = new RBTree<>();
        tree.insert(3);
        tree.insert(25);
        tree.insert(6);
        tree.insert(50);
        tree.insert(15);
        // Выводим результаты обхода дерева в порядке возрастания
        System.out.println("In-order traversal of the tree:");
        tree.inOrderTraversal(tree.root);
    }


    enum Color {
        RED,
        BLACK
    }

    static class RBNode<T> {
        T data;
        RBNode<T> left;
        RBNode<T> right;
        RBNode<T> parent;
        Color color;

        public RBNode(T data) {
            this.data = data;
            this.color = Color.RED;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    static class RBTree<T extends Comparable<T>> {
        private RBNode<T> root;

        public RBTree() {
            this.root = null;
        }

        public void inOrderTraversal(RBNode<T> node) {
            if (node != null) {
                inOrderTraversal(node.left);
                System.out.print(node.data + " ");
                inOrderTraversal(node.right);
            }
        }

        private void leftRotate(RBNode<T> x) {
            RBNode<T> y = x.right;
            x.right = y.left;
            if (y.left != null) {
                y.left.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == null) {
                this.root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.left = x;
            x.parent = y;
        }

        private void rightRotate(RBNode<T> x) {
            RBNode<T> y = x.left;
            x.left = y.right;
            if (y.right != null) {
                y.right.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == null) {
                this.root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.right = x;
            x.parent = y;
        }

        private void insertFixUp(RBNode<T> z) {
            while (z.parent != null && z.parent.color == Color.RED) {
                if (z.parent == z.parent.parent.left) {
                    RBNode<T> y = z.parent.right;
                    if (y != null && y.color == Color.RED) {
                        z.parent.color = Color.BLACK;
                        y.color = Color.BLACK;
                        z.parent.parent.color = Color.RED;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.right) {
                            z = z.parent;
                            leftRotate(z);
                        }
                        z.parent.color = Color.BLACK;
                        z.parent.parent.color = Color.RED;
                        rightRotate(z.parent.parent);
                    }
                } else {
                    RBNode<T> y = z.parent.parent.left;
                    if (y != null && y.color == Color.RED) {
                        z.parent.color = Color.BLACK;
                        y.color = Color.BLACK;
                        z.parent.parent.color = Color.RED;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.left) {
                            z = z.parent;
                            rightRotate(z);
                        }
                        z.parent.color = Color.BLACK;
                        z.parent.parent.color = Color.RED;
                        leftRotate(z.parent.parent);
                    }
                }
            }
            this.root.color = Color.BLACK;
        }

        public void insert(T data) {
            RBNode<T> z = new RBNode<>(data);
            RBNode<T> y = null;
            RBNode<T> x = this.root;
            while (x != null) {
                y = x;
                if (z.data.compareTo(x.data) <= 0) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            z.parent = y;
            if (y == null) {
                this.root = z;
            } else if (z.data.compareTo(y.data) <= 0) {
                y.left = z;
            } else {
                y.right = z;
            }

            insertFixUp(z);
        }
    }
}
