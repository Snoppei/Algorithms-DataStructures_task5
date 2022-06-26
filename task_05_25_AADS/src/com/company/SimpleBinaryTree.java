package com.company;
import java.util.function.Function;
import java.util.Random;

/**
 * Реализация простейшего бинарного дерева
 */
public class SimpleBinaryTree<T> implements BinaryTree<T> {

    protected class SimpleTreeNode implements BinaryTree.TreeNode<T> {
        public T value;
        public SimpleTreeNode left;
        public SimpleTreeNode right;
        public int leftP;
        public int rightP;
        public int maxHeight;
        public SimpleTreeNode children;

        public SimpleTreeNode(T value, SimpleTreeNode left, SimpleTreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
            Random rnd = new Random();
            this.leftP = rnd.nextInt(2);
            this.rightP = rnd.nextInt(2);
        }

        public SimpleTreeNode(T value) {
            this(value, null, null);
            Random rnd = new Random();
/*            this.leftP = rnd.nextInt(2);
            this.rightP = rnd.nextInt(2);*/
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public TreeNode<T> getLeft() {
            if(left == null) {
                return null;
            }
            return left;
        }

        @Override
        public TreeNode<T> getRight() {
            return right;
        }
    }

    protected SimpleTreeNode root = null;

    protected Function<String, T> fromStrFunc;
    protected Function<T, String> toStrFunc;

    public SimpleBinaryTree(Function<String, T> fromStrFunc, Function<T, String> toStrFunc) {
        this.fromStrFunc = fromStrFunc;
        this.toStrFunc = toStrFunc;
    }

    public SimpleBinaryTree(Function<String, T> fromStrFunc) {
        this(fromStrFunc, Object::toString);
    }

    public SimpleBinaryTree() {
        this(null);
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    public void clear() {
        root = null;
    }

    private T fromStr(String s) throws Exception {
        s = s.trim();
        if (s.length() > 0 && s.charAt(0) == '"') {
            s = s.substring(1);
        }
        if (s.length() > 0 && s.charAt(s.length() - 1) == '"') {
            s = s.substring(0, s.length() - 1);
        }
        if (fromStrFunc == null) {
            throw new Exception("Не определена функция конвертации строки в T");
        }
        return fromStrFunc.apply(s);
    }

    private static class IndexWrapper {
        public int index = 0;
    }

/*    private void skipSpaces(String bracketStr, IndexWrapper iw) {
        while (iw.index < bracketStr.length() && Character.isWhitespace(bracketStr.charAt(iw.index))) {
            iw.index++;
        }
    }*/

    public Integer readValue(int[] array, IndexWrapper iw) throws Exception {
        // пропуcкаем возможные пробелы
        //skipSpaces(bracketStr, iw);
        if (iw.index >= array.length) {
            return null;
        }
        int from = iw.index;
        //boolean quote = bracketStr.charAt(iw.index) == '"';
/*        while (iw.index < array.length && (
                        !quote && !Character.isWhitespace(bracketStr.charAt(iw.index)) && "(),".indexOf(bracketStr.charAt(iw.index)) < 0
        )) {
            iw.index++;
        }*/
/*        if (quote && bracketStr.charAt(iw.index) == '"') {
            iw.index++;
        }*/
        //String valueStr = bracketStr.substring(from, iw.index);
        Integer value = array[iw.index];
        iw.index++;
        //skipSpaces(bracketStr, iw);
        return value;
    }

    public SimpleTreeNode fromIntArray(int[] array, IndexWrapper iw, int maxHeight) throws Exception {
        Integer parentValue = readValue(array, iw);
        SimpleTreeNode parentNode = new SimpleTreeNode((T) parentValue);
        Random cmp = new Random();
        //Random right = new Random();
        int right = cmp.nextInt(2);
        if(iw.index >= Math.pow(2, maxHeight))
            return null;
        if(parentNode.rightP == 1){
            parentNode.right = fromIntArray(array, iw, maxHeight);
        }
        if(parentNode.leftP == 1){
            parentNode.left = fromIntArray(array, iw, maxHeight);
        }
/*        if(parentNode.leftP == 0 && parentNode.rightP == 0){
            parentNode = null;
        }*/
/*        if(parentNode.leftP == 0 && parentNode.rightP == 0){

        }*/
        //int left = cmp.nextInt(2);



/*        if (bracketStr.charAt(iw.index) == '(') {
            iw.index++;
            skipSpaces(bracketStr, iw);
            if (bracketStr.charAt(iw.index) != ',') {
                parentNode.left = fromBracketStr(bracketStr, iw);
                skipSpaces(bracketStr, iw);
            }
            if (bracketStr.charAt(iw.index) == ',') {
                iw.index++;
                skipSpaces(bracketStr, iw);
            }
            if (bracketStr.charAt(iw.index) != ')') {
                parentNode.right = fromBracketStr(bracketStr, iw);
                skipSpaces(bracketStr, iw);
            }*/
/*            if (array[iw.index]) {
                throw new Exception(String.format("Ожидалось ')' [%d]", iw.index));
            }*/
            //iw.index++;
        return parentNode;
    }

    public void fromArrayNotation(int maxValue, int minValue, int maxHeight) throws Exception {
        int[] array = new int[1000];
        int diff = maxValue - minValue;
        Random random = new Random();
        for(int j = 0; j < 1000; j++){
            int i = random.nextInt(diff + 1);
            i += minValue;
            array[j] = i;
        }
        IndexWrapper iw = new IndexWrapper();
        SimpleTreeNode root = fromIntArray(array, iw, maxHeight);
/*        if (iw.index < bracketStr.length()) {
            throw new Exception(String.format("Ожидался конец строки [%d]", iw.index));
        }*/
        if (iw.index > array.length){
            throw new Exception(String.format("Ожидался конец строки [%d]", iw.index));
        }
        this.root = root;
    }



/*    public void fromBracketNotation(String bracketStr) throws Exception {
        IndexWrapper iw = new IndexWrapper();
        SimpleTreeNode root = fromBracketStr(bracketStr, iw);
        if (iw.index < bracketStr.length()) {
            throw new Exception(String.format("Ожидался конец строки [%d]", iw.index));
        }
        this.root = root;
    }*/
}

