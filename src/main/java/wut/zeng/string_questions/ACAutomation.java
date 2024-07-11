package wut.zeng.string_questions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-13 11:43
 * @Description AC自动机：解决在一个大字符串中，找到多个候选字符串的问题
 * @RelateMsg
 */
public class ACAutomation {

    /**
     * 候选字符串构建的定制前缀树的根节点
     */
    public Node root;

    public ACAutomation() {
        this.root = new Node();
    }

    /**
     * 前缀树节点对象
     */
    public static class Node {
        Node fail;  // 辅助指针(减少额外的匹配过程)
        boolean tag;  // 收集标识
        Node[] next;
        String str;

        public Node() {
            this.fail = null;
            this.tag = false;
            this.next = null;
            this.str = null;
        }
    }

    /**
     * 构建候选字符串的前缀树
     */
    public void insert(String candidate) {
        char[] chars = candidate.toCharArray();
        Node cur = root;
        int index;
        for(int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            if(cur.next[index] == null) {
                Node node = new Node();
                cur.next[index] = node;
            }
            cur = cur.next[index];
        }
    }

    /**
     * 初始化辅助指针fail
     */
    public void initial() {
        LinkedList<Node> nodes = new LinkedList<>();
        nodes.add(root);
        Node cur;
        Node cFail;
        while(!nodes.isEmpty()) {
            cur = nodes.poll();
            for (int i = 0; i < 26; i++) {
                if(cur.next[i] != null) {
                    cur.next[i].fail = root;  // 首先将当前节点子节点的fail初始化到root位置
                    cFail = cur.fail; // 查看当前节点的fail指向
                    while(cFail != null) {
                        // 发现fail指向的节点与当前节点存在相同的路径,则直接相连
                        if(cFail.next[i] != null) {
                            cur.next[i].fail = cFail.next[i];
                        }
                        // 如果没有则移动到下一个fail节点
                        cFail = cFail.fail;
                    }
                    nodes.add(cur.next[i]);
                }
            }
        }
    }

    /**
     * 在context中匹配多个候选字符串
     */
    public List<String> match(String context) {
        char[] chars = context.toCharArray();
        int index;
        Node collect;
        Node cur = root;
        List<String> res = new ArrayList<>();
        for(int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            // 不匹配时的路径跳转(跳转之后则表示当前路径的候选串匹配失败)
            // 1. 当前节点的下个节点中没有匹配成功,则将当前节点跳转到fail指向的节点(匹配另外的候选子串,并且可以不去匹配已经匹配的内容)
            // 2. 一旦由fail重置到root,则表明i开始的字符串content没有匹配成功
            while(cur.next[index] == null && cur != root) { // 类似于KMP中next数组的处理逻辑
                cur = cur.fail;
            }
            // 2. 匹配成功则继续向下匹配
            cur = cur.next[index] == null ? root : cur.next[index];
            // 收集结果(按照fail指针形成的环路进行收集)
            collect = cur;
            while(collect != root) {
                // 发现结果已经收集过了,直接跳过收集过程(该fail环已经遍历过了)
                if(collect.tag) {
                    break;
                }
                if(collect.str != null) {
                    collect.tag = true;
                    res.add(collect.str);
                }
                collect = collect.fail;
            }
        }
        return res;
    }
}
