/*
 *   Description
 *
 *  This project creates a custom linked list structure.
 *  It serves as an exercise in working with linked lists and nodes.
 *
 */


public class NameList
{
    private Node head;

   public class Node
    {
        private String name;
        private Node next;
        private boolean isCharNode;
        public Node(String n, Node next, boolean isCharNode)
        {
            this.name = n;
            this.next = next;
            this.isCharNode = isCharNode;
        }
        public String getName()
        {
            return name;
        }
        public void setName(String n)
        {
            this.name = name;
        }
        public Node getLink()
        {
            return next;
        }
        public void setLink(Node next)
        {
            this.next = next;
        }
        public boolean isCharNode()
        {
            return isCharNode;
        }
        public void setCharNode(boolean isCharNode)
        {
            this.isCharNode = isCharNode;
        }
    }

    public NameList()
    {
//        head = new Node(null);
        head = null;
    }
    public void add(String str)
    {

//       checks if we have elems in our Linked List.
        if(head == null) {
            head = new Node(str,null,false);
//            System.out.println("If Hitting");
        }

        else {

            Node temp = head;
//            System.out.println("Else console log");
//            System.out.println("CLG tmp" + temp);
//            while loop keeps us from having diplicates character nodes.
            while(temp.next != null && temp.next.name.compareTo(str) < 0) {
                temp = temp.next;

//                System.out.println("While loop");
            }
            if (temp.next != null && temp.next.name.compareTo(str) == 0)
                return;
            Node varI = new Node(str,null, false);
            varI.next = temp.next;
//            System.out.println("CLG varI" + varI);
            if(temp.name.charAt(0) != str.charAt(0)) {
//                System.out.println("CONSOLE.LOG since first Char Node not showing\n" +  temp.name.charAt(0));
                Node varJ = new Node(str.charAt(0) + "",null,true);
                temp.next = varJ;
                temp = varJ;
//                System.out.println("CLG Y" + temp);
            }

            temp.next = varI;

        }
        
    }

    public void remove(String str)
    {

        if(head.name.compareTo(str) == 0){
            head = head.next;
            return;
        }
        Node temp = head;
        while(temp.next != null && temp.next.name.compareTo(str) != 0){
            temp = temp.next;
        }
        if(temp.next == null) return;
        temp.next = temp.next.next;
    }
    public void removeLetter(String letter) {
        char l = letter.toCharArray()[0];
//        Node temp = head;
//        while(head != null){
//            if(head.name.charAt(0) == l) head.next = head.next.next;
//            head = head.next;
//        }
        if (head == null) {
            System.out.println("Unable to Remove - Letter Empty");
            return;
        }

        if (letter.compareTo(head.getName()) == 0) {
            Node temp = head.next;
            while (temp != null) {
                if (l == temp.getName().charAt(0)) {
                    temp = temp.next;
                } else {
                    head = temp;
                    return;
                }
            }
        }
        Node prev = head;
        Node curr = head.next;

        while(curr!=null){
            if(l == curr.getName().charAt(0)){
                prev.next = curr.next;
            }
            else{
                prev = curr;
            }
            curr = curr.next;
        }
    }
    public String find(String str)
    {
        Node curr = head;
        String search = "Found the Name!";
        while(curr!=null)
        {
            if(curr.name.equals(str))
            {
                search = "Name in List Found " + str;
                break;
            }
            else
            {
                curr = curr.next;
            }
        }
        return search;
    }


    public String toString()
    {

        if(head == null)
//            isEmpty()
            System.out.println("Linked List Empty");

        Node curr = head;
        while(curr!=null){
//            if(curr.name.length() < 1)
            if(curr.isCharNode())
                System.out.println(curr.getName());
            else
                System.out.println(" "+curr.getName());

            curr = curr.next;
        }
        return null;

    }
    public static void main(String args[])
    {

        NameList namelist = new NameList();

        namelist.add("===============================================================\n");

        namelist.add("Ben");
        namelist.add("Bob");
        namelist.add("Dan");
        namelist.add("Deb");
//        namelist.add("Dewy");
//        namelist.add("Donna");
//        namelist.add("Deb");
//        namelist.add("deWitt");
//        namelist.add("Dan");
        namelist.add("Sarah");
        namelist.toString();

        System.out.println("==============\nWill now be removing desired names along with finding!\n==============");

        namelist.toString();
        System.out.println("Looking for Bob" +namelist.find("Deb"));
        System.out.println("Looking for Dan" + namelist.find("Sarah"));
        System.out.println("\n Removing Deb \n");
        namelist.remove("Deb");
        System.out.println("Looking for Deb\n" +namelist.find("Deb"));
        System.out.println("\n Removing Sarah\n");
        namelist.remove("Sarah");
        System.out.println("Looking for Dan\n" + namelist.find("Sarah"));
        System.out.println("\n Removing letter\n");
        namelist.removeLetter("S");
        namelist.toString();

    }

}
