package com.eussi.ch12_heap;

import com.eussi.ch12_heap.util.Heap;
import com.eussi.ch12_heap.util.Node;
import com.eussi.util.Util;

import java.io.IOException;

/**
 * @author wangxueming
 * @create 2020-03-03 21:33
 * @description
 */
public class HeapApp {
    /**
     * 堆
     *      第4章中介绍了优先级队列,它是一种对最小(或最大)关键字的数据项提供便利访问
     * 的数据结构。
     *      优先级队列可以用于计算机中的任务调度,在计算机某些程序和活动需要比其他的程
     * 序和活动先执行,因此要给它们分配更高的优先级。
     *      另一个例子是在武器系统中,比如在一个海军巡洋舰系统中。会探测到许多对巡洋舰
     * 的威胁,如来自飞机、导弹、潜水艇等的攻击,需要为这些威胁分优先级。例如,离巡洋舰距
     * 离近的导弹就比距离远的飞机的优先级更高,这样对应的防范系统(例如地对空导弹)会首先
     * 对付它。
     *      优先级队列也可以用在其他计算机算法的内部。在第14章中,可以看到图算法中应用了
     * 优先级队列,例如在 Dijkstra算法中。
     *      优先级队列是一个抽象数据类型(ADT),它提供了删除最大(或最小)关键字值的数据项的
     * 方法,插入数据项的方法,有时还有一些其他操作的方法。配合不同的ADT,优先级队列可以用不
     * 同的内部结构来实现。第4章中可以看到,优先级队列是用有序数组来实现的。这种作法的问题是
     * 尽管删除最大数据项的时间复杂度为O(1),但是插入还是需要较长的O(N)时间。这是因为必须移
     * 动数组中平均一半的数据项以插入新数据项,并在完成插入后,数组依然有序。
     *      本章将介绍实现优先级队列的另一种结构:堆。堆是一种树,由它实现的优先级队列的插入和
     * 删除的时间复杂度都是O(logN)。尽管这样删除的时间变慢了一些,但是插入的时间快得多了。当
     * 速度非常重要,且有很多插入操作时,可以选择堆来实现优先级队列。
     *
     * 注意
     *      这里的“堆”是指一种特殊的二叉树,不要和Java和C++等编程语言里的“堆”混淆,后者指
     * 的是程序员用new能得到的计算机内存的可用部分。
     */
    public static void main(String[] args) {
        /**
         * 堆的介绍
         *      堆是有如下特点的二叉树
         *       - 它是完全二叉树。这也就是说,除了树的最后一层节点不需要是满的,其他的每一层从左
         * 到右都完全是满的。图121显示了完全二叉树和非完全二叉树。
         *       - 它常常用一个数组实现。第8章中介绍了如何用数组而不是由引用连接起来的各个节点来
         * 存储二叉树
         *       - 堆中的每一个节点都满足堆的条件,也就是说每一个节点的关键字都大于(或等于)这个
         * 节点子节点的关键字。
         *      堆在存储器中表示的是数组，堆只是概念上的一个表示。注意树是完全二叉树，并且所有的
         * 节点都满足堆的条件。
         *      堆是完全二叉树的事实说明了表示堆的数组中没有“洞”，从下标0到N-1,每一个数据单元都
         * 有数据项。
         *      本章中假设的最大的（而不是最小的）关键字在根节点上。基于这种堆的优先级队列是降序
         * 的优先级队列。
         *
         * 优先级队列、堆和抽象数据类型(ADT)
         *      本章讨论堆,堆主要用于实现优先级队列,优先级队列和实现它的堆之间有非常紧密的关系。
         *      优先级队列是一个可以用不同的方法实现的ADT,而堆是一种更为基础的数据结构。在本章中,
         * 为了简单起见,用的是没有封装在优先级队列中的堆的方法。
         *
         * 弱序
         *      堆和二叉搜索树相比是弱序的,在二叉搜索树中所有节点的左子孙的关键字都小于右子孙的关
         * 键字。这说明在一个二叉搜索树中通过一个简单的算法就可以按序遍历节点,正如以前看到的那样。
         *      在堆中,按序遍历节点是困难的,这是因为堆的组织规则(堆的条件)比二叉搜索树的组织规
         * 则弱。对于堆来说,只要求沿着从根到叶子的每一条路径,节点都是按降序排列的。指定节点的左边
         * 节点或者右边节点,以及上层节点或者下层节点由于不在同一条路径上,它们的关键字可能比指定节
         * 点的关键字或大或小。除了有共享节点的路径,路径之间都是相互独立的。
         *      由于堆是弱序的,所以一些操作是困难的或者是不可能的。除了不支持遍历以外,也不能在堆
         * 上便利地查找指定关键字,因为在查找的过程中,没有足够的信息来决定选择通过节点的两个子节
         * 点中的哪一个走向下一层。它也不能在少至O(logN)的时间内删除一个指定关键字的节点,因为没
         * 有办法能找到这个节点。(这些操作可以通过按顺序查找数组的每一个单元来执行,但是只能以较
         * 慢的O(N)时间执行。)
         *      因此,堆的这种组织似乎非常接近无序。不过,对于快速移除最大节点的操作以及快速插入新
         * 节点的操作,这种顺序已经足够了。这些操作是使用堆作为优先级队列时所需的全部操作。后面将
         * 主要讨论这些操作是如何完成的。
         *
         * 移除
         *      移除是指删除关键字最大的节点。这个节点总是根节点,所以移除是很容易的。根在堆数组中
         * 的索引总是0
         *      maxNode = heapArray[0]
         *      问题是一旦移除了根节点,树就不再是完全的了;数组里就有了一个空的数据单元。这个“洞
         * 必须要填上,可以把数组中所有数据项都向前移动一个单元,但是还有快得多的方法。下面就是移
         * 除最大节点的步骤:
         *      1.移走根
         *      2.把最后一个节点移动到根的位置。
         *      3.一直向下筛选这个节点,直到它在一个大于它的节点之下,小于它的节点之上为止。
         *      最后一个节点(last)是树最低层的最右端的数据项,它对应于数组中最后一个填入的数据单元。
         * 把这个节点直接复制到根节点处
         *      heapArray[0] = heapArray[N-1];
         *      N--;
         *      移除了根,使数组容量的大小减一
         *      向上或向下“筛选”(也可以用“冒泡”或者“渗滤”)一个节点是指沿着一条路径一步一步地
         * 移动此节点,和它前面的节点交换位置,每一步都检查它是否处在了合适的位置。在步骤3中,在
         * 根上的节点对根来说太小了,所以要把它筛选到堆的合适的位置。后面将看到它的代码。
         *      步骤2恢复了堆的完全性的特征(没有洞),而步骤3恢复了堆的条件(每个节点都大于它的
         * 子节点)。
         *      在被筛目标节点的每个暂时停留的位置上,向下筛选的算法都要检查哪一个子节点更大。然后
         * 目标节点和较大的子节点交换位置。如果要把目标节点和较小的子节点换位,那么这个子节点就会变
         * 成大子节点的父节点,这就违背了堆的条件，下图显示了正确的和不正确的交换情况：
         *                          30
         *                        /    \
         *                      63     70
         *
         *                63                    70
         *       错误：  /   \(此处63<70)       /   \   :正确
         *             30   70                63   30
         *
         *
         * 插入
         *      插入节点也是很容易的。插入使用向上筛选,而不是向下筛选。节点初始时插入到数组最后第
         * 一个空着的单元中,数组容量大小增一。
         *      heapArray[N] = newNode
         *      N++
         *      问题是这样可能会破坏堆的条件。如果新插入的节点大于它新得到的父节点时,就会发生这种
         * 情况。因为父节点在堆的底端,它可能很小,所以新节点就显得比较大。因此,需要向上筛选新节
         * 点,直到它在一个大于它的节点之下,在一个小于它的节点之上。
         *      向上筛选的算法比向下筛选的算法相对简单,因为它不用比较两个子节点的关键字大小。节点
         * 只有一个父节点,目标节点只要和它的父节点换位即可。
         *      另外，如果先移除一个节点再插入相同的一个节点,结果并不一定是恢复为原来的堆。一组给
         * 定的节点可以组成很多合法的堆,这取决于节点插入的顺序
         *
         * 不是真的交换
         *      换位是在概念上理解插入和删除最简单的方法,并且实际上某些堆的实现也确实使用了换位。
         * 下面左图显示了在向下筛选的过程中使用换位的简化版本。在三次换位之后,节点A将停在D的位置上,并且
         * 节点B、C和D都会向上移动一层。
         *                    A                        A
         *                   /                        /
         *                  B                        B     \
         *                 /                        /       temp
         *                C                        C      /
         *               /                        /     /
         *              D                        D
         *      但是,一次交换需要三次复制,因此在上面左图所示的三次交换中就需要九次复制。通过在
         * 筛选的算法中使用复制取代交换,可以减少所需的复制总数
         *      上面右图显示了如何用五次复制做三次换位。首先,暂时保存节点A:然后B覆盖A,C覆盖
         * B,D覆盖C:最后,再从临时存储中取出A复制到以前D的位置上。这样就把复制的次数从九次
         * 减少到了五次
         *      在图中节点A移动了三层。当层数增加时,复制节省的时间更多,因为从临时存储区复制以及
         * 复制到临时存储区的两次复制在复制的总数中所占的比例更少了。对于很多层的堆,节省的复制次
         * 数接近三的倍数
         *      另一种观察用复制执行向上筛选和向下筛选过程的方法是用“洞”的思想(空节点的数组元素
         * 单元),在向上筛选时洞向下移动,向下筛选时洞向上移动。例如,上面右图中把A复制到Temp中,
         * 在A点创建一个“洞”。“洞”实际上保留着被移走节点原来的值,它还在那,但这没关系。把B
         * 复制到A,“洞”由A移动到B,和节点位置的变化方向相反。“洞”一步一步地向下移动。
         *
         * 堆的Java代码
         *      下面是第8章中关于用数组表示一棵树的一些要点。若数组中节点的索引为x,则
         *       - 它的父节点的下标为(x-1)/2
         *       - 它的左子节点的下标为2*x+1。
         *       - 它的右子节点的下标为2*x+2。
         * 数组的大小
         *      应该注意,数组的大小,也就是记录堆中节点的数目,是Heap类中关于堆状态的一个很重要
         * 的信息,也是Heap类中关键的字段。从最后一个位置复制的节点并不清除,所以对于算法来说,
         * 判断最后一个堆占用单元位置的惟一方法是根据数组当前的大小求得。
         */
        TestHeap();
        Util.printSeparator();
        /**
         * 扩展堆数组
         *      在程序运行的过程中,如果插入太多的数据项,超出了堆数组的容量会发生什么情况呢?可以
         * 建一个新的数组,把数据从旧数组中复制到新的数组中。(和哈希表中的情况不同,改变堆的大
         * 小不需要重新排列数据。)执行复制操作的时间是线性的,但是增大数组容量的操作并不会经常发
         * 生,特别是当每次扩展数组容量时,数组的容量都充分地增大了(例如,增大一倍)。
         * 提示
         *      在Java中,用 Vector类对象取代数组对象; vector类对象可以动态地扩展
         *
         * 堆操作的效率
         *      对有足够多数据项的堆来说,向上筛选和向下筛选算法是本章已讲过的所有操作中最费时的部
         * 分。这两个算法的时间都花费在一个循环中,沿着一条路径重复地向上或者向下移动节点。所需的
         * 复制次数和堆的高度有关;如果树的高度为五层,需要执行四次复制把“洞”从顶层移动到底层。
         * (这里忽略将最后一个节点复制到临时存储区以及又复制回来的两次移动:它们都是必须做的,因
         * 此也会消耗一定的时间。)
         *      trickleUp()方法在它的循环里只有一个主要的操作:比较新插入节点的关键字和当前位置节点的
         * 关键字。 trickleDown()方法需要两次比较:一次找到最大的子节点,一次比较这个最大的子节点和“最
         * 后”的节点。它们必须都要从顶层到底层或者从底层到顶层复制节点来完成操作
         *      堆是一种特殊的二叉树,正如第8章中讲过的一样,二叉树的层数L等于log2(N+1),其中N
         * 为节点数。 trickleUp()和 trickleDown()例程中的循环执行了L-1次,所以 trickleUp()执行的时间和log2N
         * 成正比, trickleDown()执行时间略长一点,因为它需要执行额外的比较。总之,这里讨论的堆操作的
         * 时间复杂度都是O(logN)
         *
         * 基于树的堆
         *      在本章所显示的图中,堆显示的样子和树差不多,因为通过树的方式更容易让读者清楚堆,但
         * 是它的实现是基于数组的。不过,也可以基于真正的树来实现堆。这棵树可以是二叉树,但不会是
         * 二叉搜索树,因为前面已经讲过,它的有序规则不是那么强。它也是一棵完全树,没有空缺的节点
         * 称这样的树为树堆( tree heap)
         *      关于树堆的一个问题是找到最后一个节点。移除最大数据项的时候需要找到这个节点,因为这
         * 个节点将要插入到已移除的根的位置(然后再向下筛选)。同时,也需要找到第一个空节点,因为
         * 它是插入新节点位置(然后再向上筛选)。由于不知道它们的值,况且它不是一棵搜索树,不能直
         * 接查找这两个节点。但是,在完全树中它们的位置不是很难找的,只要根据树中的节点数目就可以
         * 找到。
         *      正如在第8章讨论哈夫曼树时看到的那样,可以用二进制码表示从根到叶子的路径,用二进制
         * 数字指示从每个父节点到它子节点的路径:0表示左子节点,1表示右子节点。
         *      事实证明,树中的节点数目和到最后一个节点路径的二进制编号存在简单的关系。假设根的编
         * 号为1;下一层节点的编号为2和3;第三层节点为4、5、6和7:以此类推。以要查找的节点的编
         * 号开始。它就是最后一个节点或者是第一个空节点。把这个节点的编号转变为二进制数。例如,假
         * 设树中有29个节点,现在想要查找最后一个节点。十进制29转化为二进制是11101，移除开始的
         * 1,保留1101。这就是从根到编号为29的节点的路径:向右,向右,向左,向右。第一个空的节点
         * 编号为30,它的路径也就是二进制序列1110(移除第一个1之后):向右,向右,向右,向左。
         *      为了执行这个运算,可以重复使用%操作符求出节点数目n被2整除时得出的余数(0或者
         * 1),并再用/操作符执行真正的整除n/2。当n小于1时,操作完成。所得的余数序列,可以保存
         * 在一个数组或者字符串中,就是二进制码字(最小有效位对应路径的最底端。)下面就是执行运算
         * 的代码
         *          while(n >=1) {
         *              path[j++] = n % 21
         *              n = n / 2;
         *          }
         *      也可以使用递归的方法来实现,每次由方法调用自身来求出余数并且每次返回时决定适当的方
         * 向
         *      找到适当的节点(或者空子节点)以后,堆操作就非常简单了。当向上或者向下筛选时,树的
         * 节构不改变,所以不需要移动实际的节点。只需要把一个节点复制另一个节点的位置上。用这种方
         * 法,不必为了一次移动连接或者断开所有父节点和子节点之间的关系。Node类中需要有一个数据域
         * 保存父节点的信息,因为当向上筛选时需要访问父节点。本章将把堆树的实现留作编程作业。
         *      树堆操作的时间复杂度为O(logN)。因为基于数组的堆操作的大部分时间都消耗在向上筛选和
         * 向下筛选的操作上了,它操作的时间和树的高度成正比。
         *
         * 堆排序
         *      堆数据结构的效率使它引出一种出奇简单,但却很有效率的排序算法,称为堆排序。
         *      堆排序的基本思想是使用普通的insert()例程在堆中插入全部无序的数据项,然后重复用
         * remove()例程,就可以按序移除所有数据项。
         *      因为 insert()和 remove()方法操作的时间复杂度都是o(logN),并且每个方法必须都要执行N次
         * 所以整个排序操作需要O(N*logN)时间,这和快速排序一样。但是,它不如快速排序快,部分原因
         * 是 trickleDown里while循环中的操作比快速排序里循环中的操作要多
         *      但是,几个技巧可以使堆排序更有效。其一是节省时间,其二是节省内存。
         * 向下筛选到适当的位置上
         *      如果在堆中插入N个新数据项,则需要应用 trickleUp()方法N次。但是,可能所有的数据项在
         * 数组中都是任意排列的,然后再重新排列成堆,这样就只应用了N/2次的 trickleDown方法。这可
         * 以使速度稍稍提升
         *  - 由两个正确的子堆形成一个正确的堆
         *      这个方法是这样工作的:如果有一个不遵守堆有序条件的项占据了根的位置,而它的两个子堆
         * 却都是正确的堆,用 trickledown()方法也能够创建一个正确的堆。(根本身可以是子堆的根,也可
         * 以是整个堆的根。)
         *      这就提出了一个把无序的数组变成堆的方法。对(潜在的)堆底层的节点应用 trickleDown()方
         * 法——也就是说,从数组末端的节点开始,然后上行直到(下标为0的)根的各个节点都应用此方
         * 法。在每一步应用方法时,该节点下面的子堆都是正确的堆,因为已经对它们应用过 trickleDown()
         * 方法了。在对根应用了 trickleDown()之后,无序的数组将转化成堆。
         *      不过,注意在底层的终端节点,就是那些没有子节点的节点,都已经是正确的堆了,因为它们
         * 是单节点的树,没有违背堆的条件。因此,不用对这些节点应用 trickleDown()。可以从节点N/2-1
         * 即最右一个有子节点的节点开始,而不是从节点N-1(最后一个节点)开始应用 trickleDown。这样
         * 筛选操作只需要执行 insert()方法N次的一半次数就够了。例如，使用向下筛选算法的次序堆中一共
         * 有15个节点,可以从节点6开始筛选。
         *
         * 递归的方法
         *      也可以用递归的方法把数组变成一个堆。 heapify()方法应用于根。它对根的两个子节点又调用
         * 自身,然后对这两个子节点的两个子节点分别调用自身,以此类推。最后,它执行到最底层,当它
         * 遇到没有子节点的节点时立即返回。
         *      当它对两个子堆调用自身之后, heapify对子树的根应用 trickleDown()。这保证了子树是正确
         * 的堆。然后 heapify()返回,对上一层子树运作
         *      递归方法可能不如简单的循环方法效率高
         *
         * 使用同一个数组
         *      原始代码片段显示了数组中的无序数据。然后把数据插入到堆中,最后从堆中移除它并把它有
         * 序地写回到数组中。这个过程需要两个大小为N的数组:初始数组和用于堆的数组
         *      事实上,堆和初始数组可以使用同一个数组。这样堆排序所需的存储空间减小了一半;除了初
         * 始数组之外不需要额外的存储空间。
         *      本章已经讲过可以通过对数组中的一半元素进行 trickledown()操作,而把整个数组转变成堆
         * 只需要一个数组就能把无序的数组数据项转移到堆的适当位置上。因此,堆排序的第一步只需要
         * 个数组。
         *      但是,堆重复应用 removed时的情况就复杂了。把移除的数据项放到哪里去呢?
         *      每从堆顶移除一个数据项,堆数组的末端单元就变为空的;堆减小一个节点。可以把最近一次
         * 移除的节点放到这个新空出的单元中。移除的节点越来越多,堆数组就越来越小,但是有序数据的
         * 数组却越来越大。因此,只要稍微计划一下,有序数组和堆数组就可以共同使用一块存储空间。
         */
        heapSort();
        /**
         *      Heap类为了实现堆排序，增加了 insert()方法,它可以把元素直接插入到堆数组中,insert()
         * 和trickle方法在此处是不需要的。
         *      注意这次增加的方法没有依照面向对象编程的思想。Heap类的接口应该对类的用户屏蔽掉堆内
         * 部的实现。内部的数组应该是不可见的,但是 insert()可以直接访问内部数组。这里允许违背ooP
         * 的原则是因为数组和堆结构的联系太紧密了。
         *      堆类中增加的另一个方法是 incrementSize()。看起来它似乎可以和 insertAt方法合并,但当插
         * 入时是把数组用作存储有序数据的数组的,并不希望增大堆的大小,所以仍然把这个两个方法分开。
         *      HeapSort执行如下操作:
         *          1.从用户那里得到数组的大小。
         *          2.用随机的数据填满数组
         *          3.执行N/2次 trickleDown方法,把数组转变为堆
         *          4.从堆中移除数据项,并把它们写回数组的末端。
         *
         * 堆排序的效率
         *      前面已经讲过,堆排序运行的时间复杂度为o(N*logN)。尽管它比快速排序略慢,但它比快速
         * 排序优越的一点是它对初始数据的分布不敏感。在关键字值按某种排列顺序的情况下,快速排序运
         * 行的时间复杂度可以降到O(N2)级,然而堆排序对任意排列的数据,其排序的时间复杂度都是
         * O(N*logN)。
         */
        /**
         * 小结
         *  - 在一个升序优先级队列中,最大关键字的数据项被称为有最高的优先级。(在降序优先级
         *  队列中优先级最高的是最小的数据项。
         *  - 优先级队列是提供了数据插入和移除最大(或者最小)数据项方法的抽象数据类型(ADT)。
         *  - 堆是优先级队列ADT的有效的实现形式
         *  - 堆提供移除最大数据项和插入的方法,时间复杂度都为O(logN)。
         *  - 最大数据项总是在根的位置上。
         *  - 堆不能有序地遍历所有的数据,不能找到特定关键字数据项的位置,也不能移除特定关键
         *  字的数据项
         *  - 堆通常用数组来实现,表现为一棵完全二叉树。根节点的下标为0,最后一个节点的下标
         *  为N-1。
         *  - 每个节点的关键字都小于它的父节点,大于它的子节点
         *  - 要插入的数据项总是先被存放到数组第一个空的单元中,然后再向上筛选它至适当的位
         *  置
         *  - 当从根移除一个数据项时,用数组中最后一个数据项取代它的位置,然后再向下筛选这个
         *  节点至适当的位置。
         *  - 向上筛选和向下筛选算法可以被看作是一系列的交换,但更有效的作法是进行一系列复
         *  制
         *  - 可以更改任一个数据项的优先级。首先,更改它的关键字。如果关键字增加了,数据项就
         *  向上筛选;而如果关键字减小了,数据项就向下筛选
         *  - 堆的实现可以基于二叉树(不是搜索树),它映射堆的结构:称为树堆。
         *  - 存在在树堆中查找最后一个节点或者第一个空的单元的算法
         *  - 堆排序是一种高效的排序过程,它的时间复杂度为O(N*logN)。
         *  - 在概念上堆排序的过程包括先在堆中插入N次,然后再作N次移除。
         *  - 通过对无序数组中的N/2个数据项施用向下筛选算法,而不作N次插入,可以使堆排序
         *  的运行速度更快。
         *  - 可以使用同一个数组来存放初始无序的数据、堆以及最后有序的数据。因此,堆排序不需
         *  要额外的存储空间
         */
    }

    public static void heapSort() {
        int size = 10, j;

        System.out.println("Enter number of items: " + size);
        Heap theHeap = new Heap(size);

        for (j = 0; j < size; j++)       // fill array with
        {                        //    random nodes
            int random = (int) (Math.random() * 100);
            Node newNode = new Node(random);
            theHeap.insertAt(j, newNode);
            theHeap.incrementSize();
        }

        System.out.print("Random: ");
        theHeap.displayArray();  // display random array

        for (j = size / 2 - 1; j >= 0; j--)  // make random array into heap
            theHeap.trickleDown(j);

        theHeap.displayHeap();      // display heap

        for (j = size - 1; j >= 0; j--)    // remove from heap and
        {                        //    store at array end
            Node biggestNode = theHeap.remove();
            theHeap.insertAt(j, biggestNode);
        }
        System.out.print("Sorted: ");
        theHeap.displayArray();     // display sorted array
    }  // end main()



    public static void TestHeap() {
        int value, value2;
        Heap theHeap = new Heap(31);  // make a HeapApp; max size 31
        boolean success;

        theHeap.insert(70);           // insert 10 items
        theHeap.insert(40);
        theHeap.insert(50);
        theHeap.insert(20);
        theHeap.insert(60);
        theHeap.insert(100);
        theHeap.insert(80);
        theHeap.insert(30);
        theHeap.insert(10);
        theHeap.insert(90);

        theHeap.displayHeap();

        int key = 11;
        System.out.println("Enter value to insert: " + key);
        success = theHeap.insert(key);
        if( !success )
            System.out.println("Can't insert; heap full:" + key);
        theHeap.displayHeap();

        key = 12;
        System.out.println("Enter value to insert: " + key);
        success = theHeap.insert(key);
        if( !success )
            System.out.println("Can't insert; heap full:" + key);
        theHeap.displayHeap();

        if( !theHeap.isEmpty() ) {
            System.out.println("remove:" + theHeap.remove());
            theHeap.displayHeap();
        } else
            System.out.println("Can't remove; heap empty");

        if( !theHeap.isEmpty() ) {
            System.out.println("remove:" + theHeap.remove());
            theHeap.displayHeap();
        } else
            System.out.println("Can't remove; heap empty");


        key = 8;
        int newKey = 200;
        System.out.println("Enter current index of item: " + key);
        System.out.println("Enter new key: " + newKey);
        success = theHeap.change(key, newKey);
        if( !success )
            System.out.println("Invalid index");
        else
            theHeap.displayHeap();

        key = 0;
        newKey = 3;
        System.out.println("Enter current index of item: " + key);
        System.out.println("Enter new key: " + newKey);
        success = theHeap.change(key, newKey);
        if( !success )
            System.out.println("Invalid index");
        else
            theHeap.displayHeap();
    }  // end main()
}
