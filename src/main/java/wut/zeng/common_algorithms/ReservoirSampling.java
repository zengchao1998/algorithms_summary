package wut.zeng.common_algorithms;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-04 11:12
 * @Description 蓄水池采样算法
 * <a href="https://blog.csdn.net/BigData_Mining/article/details/103164084">link</a>
 * 1.数据流长度N很大且不可知，所以不能一次性存入内存
 * 2.时间复杂度为O(N)
 * 3.随机选取m个数，每个数被选中的概率为m/N
 * @RelateMsg
 * 1) 当i<=m时，数据直接放进蓄水池，所以第i个数据进入过蓄水池的概率=1
 * 2) 当i>m时，在[1,i]内选取随机数d，如果d<=m，则使用第i个数据替换蓄水池中第d个数据，因此第i个数据进入过蓄水池的概率=m/i
 * 3) 当i<=m时，程序从接收到第m+1个数据时开始执行替换操作，
 *      第m+1次处理会替换池中数据的为m/(m+1)，会替换掉第i个数据的概率为1/m，
 *      则第m+1次处理替换掉第i个数据的概率为(m/(m+1))*(1/m)=1/(m+1)，不被替换的概率为1-1/(m+1)=m/(m+1)。
 *      依次，第m+2次处理不替换掉第i个数据概率为(m+1)/(m+2)...第N次处理不替换掉第i个数据的概率为(N-1)/N。
 *      所以，之后第i个数据不被替换的概率=m/(m+1)*(m+1)/(m+2)*...*(N-1)/N=m/N
 * 4) 当i>m时，程序从接收到第i+1个数据时开始有可能替换第i个数据。则参考上述第3点，第i个数据不被替换的概率=i/N
 * ==>
 * 结合第1点和第3点可知，当i<=m时，第i个接收到的数据最后留在蓄水池中的概率=1 * m/N=m/N（被选中进入蓄水池的概率选中之后一直没有被替换的概率）
 * 结合第2点和第4点可知，当i>m时，第i个接收到的数据留在蓄水池中的概率=m/i * i/N=m/N（被选中进入蓄水池的概率选中之后一直没有被替换的概率）
 * 综上可知，每个数据最后被选中留在蓄水池中的概率为m/N
 */
public class ReservoirSampling {

    public int[] bag;  // 最终的抽样结果
    public int M;  // 需要的抽样数量
    public int index;  // 当前处理的信息流的序号位置

    public ReservoirSampling(int capacity) {
        this.bag = new int[capacity];
        this.M = capacity;
        this.index = 1;
    }

    /**
     * 随机生成 1-edge 的数
     */
    private int random(int edge) {
        return (int) (Math.random() * edge) + 1;
    }

    /**
     * 从index开始尝试获取M个元素(要求保证等概率),index的上限由数据流的上限来控制
     */
    public void add(int label) {
        if(index <= M) {
            bag[index - 1] = label;
        } else {
            int pos = random(index);  // 1...index
            if(pos <= M) { // M / index 的概率
                bag[pos - 1] = label; // 使用当前的label替换原始的数据
            }
        }
    }
}
