/*
Необходимо превратить собранное на семинаре дерево поиска в полноценное левостороннее красно-черное дерево.
 И реализовать в нем метод добавления новых элементов с балансировкой.

        Красно-черное дерево имеет следующие критерии:
        • Каждая нода имеет цвет (красный или черный)
        • Корень дерева всегда черный
        • Новая нода всегда красная
        • Красные ноды могут быть только левым ребенком
        • У краной ноды все дети черного цвета

        Соответственно, чтобы данные условия выполнялись, после добавления элемента в дерево необходимо
        произвести балансировку, благодаря которой все критерии выше станут валидными.
        Для балансировки существует 3 операции – левый малый поворот, правый малый поворот и смена цвета.
*/

/*
        Добавил в класс Node поле color, определяющее цвет ноды (красный или черный)
        и методы для балансировки дерева:

        - leftRotate(Node x) - левый поворот
        - rightRotate(Node x) - правый поворот
        - flipColors(Node x) - смена цветов

        Изменил метод insert(Node node, int value), чтобы он добавлял новые ноды с красным цветом
        и производил балансировку дерева после каждой вставки.

*/

class BinaryTree{
    Node root;
    static final boolean RED = true;
    static final boolean BLACK = false;
    class Node{
        int value;
        Node left;
        Node right;
        boolean color = RED; // начальный цвет новой ноды всегда красный
    }

    public boolean find(int value){
        return find(root, value);
    }

    public boolean find(Node node, int value){
        if(node == null){
            return false;
        }
        if(node.value == value){
            return true;
        }
        if(node.value < value){
            return find(node.right, value);
        }else{
            return find(node.left, value);
        }
    }

    public boolean insert(int value){
        if(root == null){
            root = new Node();
            root.value = value;
            root.color = BLACK; // корень всегда черный
            return true;
        }
        return insert(root, value);
    }

    public boolean insert(Node node, int value) {
        if(node.value == value){
            return false;
        }

        if(node.value < value){
            if(node.right == null){
                node.right = new Node();
                node.right.value = value;
                node.right.color = RED; // новая нода всегда красная
                rebalance(node.right); // балансировка после вставки
                return true;
            }
            return insert(node.right, value);
        }else{
            if(node.left == null){
                node.left = new Node();
                node.left.value = value;
                node.left.color = RED; // новая нода всегда красная
                rebalance(node.left); // балансировка после вставки
                return true;
            }
            return insert(node.left, value);
        }
    }

    private Node rebalance(Node x) {
        Node result = x;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.right != null && result.right.color == RED &&
                    result.left == null || result.left.color == BLACK) {
                needRebalance = true;
                result = rightRotate(result);
            }

            if (result.left != null && result.left.color == RED &&
                    result.left.left != null && result.left.left.color == RED) {
                needRebalance = true;
                result = leftRotate(result);
            }

            if (result.left != null && result.left.color == RED &&
                    result.right != null && result.right.color == RED) {
                needRebalance = true;
                flipColors(result);
            }
        }
        while (needRebalance);
        return result;
    }


    private Node leftRotate(Node x) {
        Node left = x.left;
        Node beetweenChild = left.right;
        left.right = x;
        x.left = beetweenChild;
        left.color = x.color;
        x.color = RED;
        return left;
    }

    private Node rightRotate(Node x) {
        Node right = x.right;
        Node beetweenChild = right.left;
        right.left = x;
        x.right = beetweenChild;
        right.color = x.color;
        x.color = RED;
        return right;
    }

    private void flipColors(Node x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
    }
}

public class Main {
    public static void main(String[] args) {

        BinaryTree tree = new BinaryTree();
        tree.insert(5);
        tree.insert(3);
        tree.insert(1);
        tree.insert(2);
        tree.insert(4);
        tree.insert(7);
        tree.insert(6);
        tree.insert(8);
    }
}