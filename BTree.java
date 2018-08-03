class BTree{
    private BNode root;	//the BNode at the top of the tree
    private int IN = 1;	//constant for IN order display
    private int PRE = 2; //constant for PRE order display
    private int POST = 3; //constant for POST order display

    //BTree constructor
    public BTree(){
        root = null;
    }
    //add adds a new BNode onto the tree

    public void add(Member v){
        if(root  == null){
            root = new BNode(v);
        }
        else{
            add(v,root);
        }
    }
    //returns root of BTree
    public BNode getRoot(){
        return root;
    }
    //recursive overload of add
    //recursively goes through the tree to find a spot for the value

    public void add(Member v, BNode branch){
        //base case: there is an open spot in the correct position
        //val is added to the left if it is smaller than its parent or is added to the right if it is larger
        if(v.hashCode()<branch.getHashCode()){
            if(branch.getLeft() == null){
                branch.setLeft(new BNode(v));
            }
            else{
                add(v, branch.getLeft());
            }
        }
        else if(v.hashCode()>branch.getHashCode()){
            if(branch.getRight() == null){
                branch.setRight(new BNode(v));
            }
            else{
                add(v, branch.getRight());
            }
        }
    }


    //returns a String of the values in the BTree
    public String toString(){
        return treeString(root);
    }
    //recursive version of toString
    public String treeString(BNode branch){
        //base case: the recursion hits a null BNode
        if(branch == null){
            return "";
        }
        else{
            return treeString(branch.getLeft()) + branch.getName()+ " " + treeString(branch.getRight());
        }
    }

    //find returns a BNode of a certain value
    public BNode find(Member v){
        return find(v,root);
    }

    //recursive overload of find
    private BNode find(Member val,BNode branch){
        //base case: if a null BNode is hit (the value isn't found) or the value is found
        if(branch == null || branch.getHashCode() == val.hashCode()){
            return branch;
        }
        //if not base case, continue to go down BTree to find the value
        else{
            if(val.hashCode()<branch.getHashCode()){
                return find(val, branch.getLeft());
            }
            else{
                return find(val, branch.getRight());
            }
        }
    }

/*
    //returns the number of BNodes down a value is
    public int depth(String n){
        return depth(n,root,1);
    }

    //recursive overload of depth
    public int depth(int n, BNode branch,int dep){
        //base case: the value isn't found or the branch is found and the counter dep, counting the depth, is returned
        if(branch == null){
            return -1;
        }
        else if(branch.getVal() == n){
            return dep;
        }
        //if not the base case, go down the BTree to find the node
        else{
            if(n<branch.getVal()){
                return depth(n,branch.getLeft(),dep+1);
            }
            return depth(n,branch.getRight(),dep+1);
        }
    }*/

    //display returns a string in IN order
    public String display(){
        return treeString(root);
    }
    //display is overloaded to take an int and disaply the corresponding display
    //if n = 1 it is displayed IN order
    //n = 2 it is displayed PRE order
    //n = 3 it is displated POST order
    public String display(int n){
        if(n == IN){
            return treeString(root);
        }
        if(n == PRE){
            return displayPre(root);

        }
        return displayPost(root);
    }

    //return the values of the BTree in PRE order
    public String displayPre(BNode branch){
        if(branch == null){
            return "";
        }
        else{
            return branch.getName()+ " " + displayPre(branch.getLeft()) + displayPre(branch.getRight());
        }
    }
    //return the values of the BTree in POST order
    public String displayPost(BNode branch){
        if(branch == null){
            return "";
        }
        else{
            return displayPost(branch.getLeft()) +  displayPost(branch.getRight()) + branch.getName()+ " ";
        }

    }

    //returns number of leaves (BNodes with no children)
    public int countLeaves(){
        return countLeaves(root);
    }
    //recursive overload of countLeaves
    public int countLeaves(BNode branch){
        //base case: a leaf is found so 1 is returned
        //or there are no nodes in the tree, 0 is returnd
        if(branch == null){
            return 0;
        }
        if(branch.getLeft() == null && branch.getRight() == null){
            return 1;
        }
        //if not base case, return the total number of leaves below
        else{
            if(branch.getLeft()==null){
                return countLeaves(branch.getRight());
            }
            else if(branch.getRight() == null){
                return countLeaves(branch.getLeft());
            }
            return countLeaves(branch.getLeft())+countLeaves(branch.getRight());

        }
    }
    //returns height of the tree
    public int height(){
        return height(root);
    }
    //recursive overload of height
    public int height(BNode branch){
        //base case: if a leaf is hit, only 1 is returned
        //or if there are no nodes in the tree, 0 is returned
        if(branch == null){
            return 0;
        }
        if(branch.getLeft() == null && branch.getRight() == null){
            return 1;
        }
        //every other time, 1 plus the height of the nodes under is returned
        else{
            if(branch.getLeft()==null){
                return 1+ height(branch.getRight());
            }
            if(branch.getRight() == null){
                return 1 + height(branch.getLeft());
            }
            if(height(branch.getLeft())>=height(branch.getRight())){
                return 1 + height(branch.getLeft());
            }
            return 1 + height(branch.getRight());

        }
    }

    //returns true if a value a is the ancestor of value b
    //returns false otherwise
    public boolean isAncestor(Member a, Member d){
        return find(d,find(a)) != null? true: false;
    }

    //adds a BNode, branch, onto a branch, trunk
    public void graft(BNode branch, BNode trunk){
        //base case: the branch's value fits into a null spot on the trunk
        if(branch.getHashCode()<trunk.getHashCode()){
            if(trunk.getLeft() == null){
                trunk.setLeft(branch);
            }
            else{
                graft(branch, trunk.getLeft());
            }
        }
        //if not the base case: go down the trunk to find a spot for branch
        else if(branch.getHashCode()>trunk.getHashCode()){
            if(trunk.getRight() == null){
                trunk.setRight(branch);
            }
            else{
                graft(branch, trunk.getRight());
            }
        }
    }
    //returns the BNode directly above the int v
    public BNode findBefore(Member v){
        return findBefore(v,root);
    }

    //recursive overload of findBefore
    public BNode findBefore(Member v, BNode branch){
        //base case: find the BNode that points to a BNode with the value v
        if(branch.getLeft() != null && branch.getLeft().getHashCode() == v.hashCode()){
            return branch;
        }
        if(branch.getRight() != null && branch.getRight().getHashCode() == v.hashCode()){
            return branch;
        }
        //if not the base case: goes down the BTree to find a Bnode that is base case
        else{
            if(v.hashCode()>branch.getHashCode()){
                return findBefore(v,branch.getRight());
            }
            return findBefore(v,branch.getLeft());
        }
    }
    //deletes a Bnode in the BTree
    public void delete(Member n){
        BNode dead = find(n); //the BNode that is to be deleted
        //check the case that dead is the root
        if(dead == root){
            graft(root.getLeft(),root.getRight());
            root = root.getRight();
        }
        //check case that dead is a leaf
        else if(dead.getLeft() == null && dead.getRight() == null){
            BNode parent = findBefore(n); //the BNode that points to dead
            boolean onRight = true;//true if the parent points to dead on the right
            if(dead.getHashCode()<parent.getHashCode()){
                onRight = false;
            }

            if(onRight){
                parent.setRight(null);
            }
            else{
                parent.setLeft(null);
            }

        }
        //check if dead points to one or two BNodes and deletes acordingly
        else{
            BNode parent = findBefore(n);//the BNode that points to dead
            boolean onRight = true; //true if the parent points to dead on the right
            if(dead.getHashCode()<parent.getHashCode()){
                onRight = false;
            }
            if(dead.getRight()!= null && dead.getLeft()!= null){
                if(onRight){
                    parent.setRight(dead.getRight());
                    graft(dead.getLeft(),parent.getRight());
                }
                else{
                    parent.setLeft(dead.getRight());
                    graft(dead.getLeft(),parent.getLeft());
                }
            }
            else if(dead.getLeft()!=null){
                if(onRight){
                    parent.setRight(dead.getLeft());
                }
                else{
                    parent.setLeft(dead.getLeft());
                }
            }
            else{
                if(onRight){
                    parent.setRight(dead.getRight());
                }
                else{
                    parent.setLeft(dead.getRight());
                }
            }
        }


    }
    /*
    //returns the number of BNode that point to null 2 depth before the height
    //if there are these BNodes, the tree isn't balanced
    public int countNullNodes(){
        return countNullNodes(root);
    }

    //recursive version of countNullNodes
    public int countNullNodes(BNode branch){
        //base case: the branch points to a null and it's depth is two less than the height one is returned
        //in an unbalanced BTree there wouldn't be such BNodes
        if(branch.getLeft() == null || branch.getRight() == null){
            if(depth(branch.getVal())<height()-1){
                return 1;
            }
            else{
                return 0;
            }
        }
        //if not the base case, goes down the BTree to see if there are any such BNodes
        else if(branch.getLeft()==null){
            return countNullNodes(branch.getRight());
        }
        else if(branch.getRight() == null){
            return countNullNodes(branch.getLeft());
        }
        return countNullNodes(branch.getLeft())+countNullNodes(branch.getRight());


    }

    //returns true if the BTree is balanced, false if it is not
    public boolean isBalanced(){
        if(countNullNodes()>0){
            return false;
        }
        return true;
    }

    //overload add to take a BTree
    //copies all the BNodes of a BTree and adds them onto the current BTree
    public void add(BTree tree){
        if(tree.getRoot()!= null){	//make sure the tree has BNodes in it
            addBranch(tree.getRoot());
        }

    }

    //add all the BNodes of another tree onto current tree
    public void addBranch(BNode branch){
        //base case: if branch is a leaf, add the value of the branch onto the current tree
        if(branch.getLeft() == null && branch.getRight() == null){
            add(branch);
        }
        //if not base case addBranch any branches that aren't null
        //add every branch's value onto the current Tree
        else{
            if(branch.getLeft() == null){
                addBranch(branch.getRight());
            }
            else if(branch.getRight() == null){
                addBranch(branch.getLeft());
            }
            else{
                addBranch(branch.getRight());
                addBranch(branch.getLeft());
            }
            add(branch.getVal());
        }
    }*/
}