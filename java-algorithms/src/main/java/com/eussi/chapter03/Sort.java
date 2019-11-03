package com.eussi.chapter03;

import com.eussi.chapter03.util.ArrayBub;
import com.eussi.chapter03.util.ArrayIns;
import com.eussi.chapter03.util.ArraySel;
import com.eussi.util.Util;

/**
 * @author wangxueming
 * @create 2019-10-21 16:46
 * @description
 */
public class Sort {

    /**
     * 计算机程序不能像人这样通览所有的数据。它只能根据计算机的“比较”操作原理,在同一
     * 时间内对两个队员进行比较。
     * 算法的这种“管视(狭隘的见识)”将是一个反复出现的问题。在人类看来很简单的
     * 事情,计算机的算法却不能看到全景,因此它只能一步一步地解决具体问题和遵循一些简单的规则
     * <p>
     * 本章中三个算法都包括如下的两个步骤,这两步循环执行,直到全部数据有序为止
     * 1.比较两个数据项
     * 2.交换两个数据项,或复制其中一项。
     * 但是,每种算法具体实现的细节有所不同
     */

    public static void main(String[] args) {
        /**
         * 冒泡排序
         *      冒泡排序算法运行起来非常慢,但在概念上它是排序算法中最简单的,因此冒泡排序算法在刚
         * 开始研究排序技术时是一个非常好的算法。
         *      冒泡算法名称的由来主要是因为在执行算法的时候，最大的数据项总是“冒泡”到数组的顶端。
         */
        testBubbleSort();
        Util.printDivide();
        /**
         *      这个算法的思路是要将最小的数据项放在数组的最开始(数组下标为0),并将最大的数据项放
         * 在数组的最后(数组下标为 nelms-1)。外层for循环的计数器out从数组的最后开始,即out等于
         * nelms-1,每经过一次循环out减一。下标大于out的数据项都已经是排好序的了。变量out在每完
         * 成一次内部循环(计数器为in)后就左移一位,因此算法就不再处理那些已经排好序的数据了。
         *      内层for循环计数器in从数组的最开始算起,即in=0,每完成一次内部循环体加一,当它等于
         * out时结束一次循环。在内层for循环体中,数组下标为in和in+1的两个数据项进行比较,如果下
         * 标为in的数据项大于下标为in+1的数据项,则交换两个数据项。
         *      为了清晰起见,使用了一个独立的swap(方法来执行交换操作。它只是交换数组中的两个数据
         * 项的值,使用一个临时变量来存储第一个数据项的值,然后把第二项的值赋给第一项,之后让第二
         * 项的值等于临时变量的值。实际上,使用一个独立的 swap()方法不一定好,因为方法调用会增加
         * 些额外的消耗。如果写自己使用的排序程序,最好将交换操作这段代码直接放到程序中,这样可以
         * 提高一些速度。
         *
         * 不变性
         *      在许多算法中,有些条件在算法执行时是不变的。这些条件被称为不变性。认识不变性对理解
         * 算法是有用的。在一定的情况下它们对调试也有用;可以反复地检查不变性是否为真,如果不是的
         * 话就标记出错
         *      在 冒泡排序程序中,不变性是指out右边的所有数据项为有序。在算法的整个运行过程
         * 中这个条件始终为真。(在第一趟排序开始前,尚未排序,因为out开始时在数据项的最右边,没有
         * 数据项在out的右边。)
         *
         * 冒泡排序效率：
         *      一般来说,数组中有N个数据项,则第一趟排序中有N-1次比较,第二趟中有N-2次,如此
         * 类推。这种序列的求和公式如下
         * (N-1)+(N-2)+(N-3)+...+2+1=N*(N-1)/2
         *      这样,算法作了约N²/2次比较(忽略减1,不会有很大差别,特别是当N很大时)
         * 因为两个数据只有在需要时才交换,所以交换的次数少于比较的次数。如果数据是随机的,那
         * 么大概有一半数据需要交换,则交换的次数为N²/4。(不过在最坏的情况下,即初始数据逆序时,
         * 每次比较都需要交换。)
         *      交换和比较操作次数都和N²成正比。由于常数不算在大O表示法中,可以忽略2和4,并且认
         * 为冒泡排序运行需要O(N²)时间级别。
         *      无论何时,只要看到一个循环嵌套在另一个循环里,例如在冒泡排序和本章中的其他排序算法
         * 中,就可以怀疑这个算法的运行时间为O(N²)级。外层循环执行N次,内部循环对于每一次外层循
         * 环都执行N次(或者几分之N次)。这就意味着将大约需要执行N*N或者N²次某个基本操作
         */


        /**
         * 选择排序
         *      选择排序改进了冒泡排序,将必要的交换次数从O(N²)减少到O(N)。不幸的是比较次数仍保持
         * 为O(N²)。然而,选择排序仍然为大记录量的排序提出了一个非常重要的改进,因为这些大量的记
         * 录需要在内存中移动,这就使交换的时间和比较的时间相比起来,交换的时间更为重要。(一般来
         * 说,在Java语言中不是这种情况,Java中只是改变了引用位置,而实际对象的位置并没有发生改变。)
         *      进行选择排序就是把所有的数据扫描一趟,从中挑出(或者说选择,这正是这个排序名字的由
         * 来)最小的一个数据。最小的这个数据和最左端的数据交换位置,即站到0号位置。现在
         * 最左端的数据是有序的了,不需要再交换位置了。注意,在这个算法中有序的数据都排列在队列的
         * 左边(较小的下标值),而在冒泡排序中则是排列在队列右边的。
         */
        testSelSort();
        Util.printDivide();
        /**
         * 算法描述：
         *      外层循环用循环变量out,从数组开头开始(数组下标为0)向高位增长。内层循环用循环变量
         * in,从out所指位置开始,同样是向右移位。
         *      在每一个in的新位置,数据项a[in]和a[min]进行比较。如果a[in]更小,则min被赋值为in的
         * 值。在内层循环的最后,min指向最小的数据项,然后交换out和min指向的数组数据项。
         *
         * 不变性
         *      在 选择排序程序中,下标小于或等于out的位置的数据项总是有序的。
         *
         * 选择排序的效率
         *      选择排序和冒泡排序执行了相同次数的比较:N(N-1)2。对于10个数据项,需要45次比较。
         * 然而,10个数据项只需要少于10次交换。对于100个数据项,需要4950次比较,但只进行了不到
         * 100次的交换。N值很大时,比较的次数是主要的,所以结论是选择排序和冒泡排序一样运行了O
         * (N²)时间。但是,选择排序无疑更快,因为它进行的交换少得多。当N值较小时,特别是如果交
         * 换的时间级比比较的时间级大得多时,选择排序实际上是相当快的
         */


        /**
         * 插入排序
         *      在大多数情况下,插入排序算法是本章描述的基本的排序算法中最好的一种。虽然插入排序算
         * 法仍然需要O(N²)的时间,但是在一般情况下,它要比冒泡排序快一倍,比选择排序还要快一点。
         * 尽管它比冒泡排序算法和选择排序算法都更麻烦一些,但它也并不很复杂。它经常被用在较复杂的
         * 排序算法的最后阶段,例如快速排序。
         *
         * 局部有序。
         *      局部有序在冒泡排序和选择排序中是不会出现的。在这两个算法中,一组数据项在某个
         * 时刻是完全有序的;在插入排序中,一组数据仅仅是局部有序的。
         * 被标记的队员
         */
        testInsertSort();
        /**
         * 算法描述：
         *      在外层的for循环中,out变量从1开始,向右移动。它标记了未排序部分的最左端的数据。而
         * 在内层的 while循环中,in变量从out变量开始,向左移动,直到temp变量小于i所指的数组数据
         * 项,或者它已经不能再往左移动为止。 while循环的每趟都向右移动了一个已排序的数据项
         *
         * 插入排序中的不变性
         *      在每趟结束时,在将temp位置的项插入后,比 outer变量下标号小的数据项都是局部有序的
         *
         * 插入排序的效率
         *      这个算法需要多少次比较和复制呢?在第一趟排序中,它最多比较一次,第二趟最多比较两次,
         * 依此类推。最后一趟最多,比较N-1次。因此有
         *      1+2+3+…+N-1=N*(N-1)/2
         * 然而,因为在每一趟排序发现插入点之前,平均只有全体数据项的一半真的进行了比较,我们
         * 除以2得到
         *      N*(N-1)4
         *      复制的次数大致等于比较的次数。然而,一次复制与一次交换的时间耗费不同,所以相对于随
         * 机数据,这个算法比冒泡排序快一倍,比选择排序略快。
         * 在任意情况下,和本章其他的排序例程一样,对于随机顺序的数据进行插入排序也需要O(N²)
         * 的时间级。
         *      对于已经有序或基本有序的数据来说,插入排序要好得多。当数据有序的时候, while循环的
         * 条件总是假,所以它变成了外层循环中的一个简单语句,执行N-1次。在这种情况下,算法运行只
         * 需要O(N)的时间。如果数据基本有序,插入排序几乎只需要O(N)的时间,这对把一个基本有序的
         * 文件进行排序是一个简单而有效的方法
         *      然而,对于逆序排列的数据,每次比较和移动都会执行,所以插入排序不比冒泡排序快。
         */


        /**
         * 稳定性
         *      有些时候,排序要考虑数据项拥有相同关键字的情况。例如,雇员数据按雇员的姓的字典序排
         * 序(排序以姓为关键字),现在又想按邮政编码排序,并希望具有相同邮政编码的数据仍然按姓排
         * 序。这种情况下,则只需要算法对需要排序的数据进行排序,让不需要排序的数据保持原来的顺序。
         * 某些算法满足这样的要求,它们就可以称为稳定的算法
         *      本章中所有的算法都是稳定的。
         *
         * 几种简单排序之间的比较
         *      除非手边没有算法书可参考,一般情况几乎不太使用冒泡排序算法。它过于简单了,以至于可
         * 以毫不费力地写出来。然而当数据量很小的时候它会有些应用的价值。
         *      选择排序虽然把交换次数降到了最低,但比较的次数仍然很大。当数据量很小,并且交换数据
         * 相对于比较数据更加耗时的情况下,可以应用选择排序
         *      在大多数情况下,假设当数据量比较小或基本上有序时,插入排序算法是三种简单排序算法中
         * 最好的选择。对于更大数据量的排序来说,快速排序通常是最快的方法;
         *
         *      除了在速度方面比较排序算法外,还有一种对各种算法的衡量标准是算法需要的内存空间有多
         * 大。本章中的三种算法都可以“就地”完成排序,即除了初始的数组外几乎不需要其他内存空间。
         * 所有排序算法都需要一个额外的变量来暂时存储交换时的数据项
         *
         */


    }

    private static void testInsertSort() {
        int maxSize = 100;            // array size
        ArrayIns arr;                 // reference to array
        arr = new ArrayIns(maxSize);  // create the array

        arr.insert(77);               // insert 10 items
        arr.insert(99);
        arr.insert(44);
        arr.insert(55);
        arr.insert(22);
        arr.insert(88);
        arr.insert(11);
        arr.insert(00);
        arr.insert(66);
        arr.insert(33);

        arr.display();                // display items

        arr.insertionSort();          // insertion-sort them

        arr.display();                // display them again
    }

    private static void testSelSort() {
        int maxSize = 100;            // array size
        ArraySel arr;                 // reference to array
        arr = new ArraySel(maxSize);  // create the array

        arr.insert(77);               // insert 10 items
        arr.insert(99);
        arr.insert(44);
        arr.insert(55);
        arr.insert(22);
        arr.insert(88);
        arr.insert(11);
        arr.insert(00);
        arr.insert(66);
        arr.insert(33);

        arr.display();                // display items

        arr.selectionSort();          // selection-sort them

        arr.display();                // display them again
    }

    private static void testBubbleSort() {
        int maxSize = 100;            // array size
        ArrayBub arr;                 // reference to array
        arr = new ArrayBub(maxSize);  // create the array

        arr.insert(77);               // insert 10 items
        arr.insert(99);
        arr.insert(44);
        arr.insert(55);
        arr.insert(22);
        arr.insert(88);
        arr.insert(11);
        arr.insert(00);
        arr.insert(66);
        arr.insert(33);

        arr.display();                // display items

        arr.bubbleSort();             // bubble sort them

        arr.display();                // display them again
    }
}
