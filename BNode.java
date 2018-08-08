class BNode{
    private Member m;
    private BNode left;
    private BNode right;

    public BNode(Member v){
        m = v;
        left = null;
        right = null;
    }

    public Member getMember(){
        return m;
    }

    public String getName(){
        return m.getName();
    }

    /*public int getNameValue(){
        return m.getName().hashCode();
    }*/

    public BNode getLeft(){
        return left;
    }
    public void setLeft(BNode b){
        left =  b;
    }
    public BNode getRight(){
        return right;
    }
    public void setRight(BNode b){
        right =  b;
    }
}