package com.CK;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(54581641);
        a.add(64080174);
        a.add(24346381);
        a.add(69107959);
        List<Integer> b = new ArrayList<>();
        b.add(86374198);
        b.add(61363882);
        b.add(68783324);
        b.add(79706116);
        List<Integer> c = new ArrayList<>();
        c.add(668150);
        c.add(92178815);
        c.add(89819108);
        c.add(94701471);
        List<Integer> d = new ArrayList<>();
        d.add(83920491);
        d.add(22724204);
        d.add(46281641);
        d.add(47531096);
        List<Integer> e = new ArrayList<>();
        e.add(89078499);
        e.add(18904913);
        e.add(25462145);
        e.add(60813308);

//        List<Integer> a = new ArrayList<>();
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        List<Integer> b = new ArrayList<>();
//        b.add(0);
//        b.add(0);
//        b.add(4);
//        List<Integer> c = new ArrayList<>();
//        c.add(7);
//        c.add(6);
//        c.add(5);

//        List<Integer> a = new ArrayList<>();
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        List<Integer> b = new ArrayList<>();
//        b.add(0);
//        b.add(0);
//        b.add(0);
//        List<Integer> c = new ArrayList<>();
//        c.add(7);
//        c.add(6);
//        c.add(5);

        List<List<Integer>> forest = new ArrayList<>();
        forest.add(a);
        forest.add(b);
        forest.add(c);
        forest.add(d);
        forest.add(e);


        Solution solution = new Solution();
        System.out.println(solution.cutOffTree(forest));
    }
}

class Solution {
    int[] directR = {1, 0, 0, -1};
    int[] directC = {0, 1, -1, 0};

    boolean[][] visited;
    static int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};


    public int cutOffTree(List<List<Integer>> forest) {
        if (forest.size() == 0) return 0;
        PriorityQueue<int[][]> heap = new PriorityQueue<>((o1, o2) -> o1[0][0] - o2[0][0]);
        int res = 0;
        for (int r = 0; r < forest.size(); r++) {
            for (int c = 0; c < forest.get(0).size(); c++) {
                if (forest.get(r).get(c) <= 1) continue;
                int[] height = {forest.get(r).get(c), 0};
                int[] coordinate = {r, c};
                int[][] value = {height, coordinate};
                heap.offer(value);
            }
        }
        int[] des;
        int[] current = {0, 0};
        int bfsStep = 0;
        Queue<int[]> bfsList;
        while (!heap.isEmpty()) {
            des = heap.poll()[1];
            bfsList = new LinkedList<>();
            bfsList.offer(current);
            visited = new boolean[forest.size()][forest.get(0).size()];
            visited[current[0]][current[1]] = true;
            bfsStep = bfs(forest, bfsList, des);
            if (bfsStep == -1) return res = -1;
            else res += bfsStep;
            forest.get(des[0]).set(des[1], 1);
            current = des;
        }

        return res;
    }

    private int bfs(List<List<Integer>> forest, Queue<int[]> bfsList, int[] des) {
        int step = 0;
        while (!bfsList.isEmpty()) {
            int count = bfsList.size();
            for (int c = 0; c < count; c++) {
                int[] origin = bfsList.poll();
                if (origin[0] == des[0] && origin[1] == des[1]) return step;
                for (int i = 0; i < 4; i++) {
                    int nextR = origin[0] + directR[i];
                    int nextC = origin[1] + directC[i];
                    int[] nextMove = {nextR, nextC};
                    if (nextMove[0] < 0 || nextMove[0] >= forest.size() || nextMove[1] < 0 || nextMove[1] >= forest.get(0).size()
                            || forest.get(nextMove[0]).get(nextMove[1]) == 0 || visited[nextMove[0]][nextMove[1]])
                        continue;
                    bfsList.add(nextMove);
                    visited[nextMove[0]][nextMove[1]] = true;
                }
            }
            step++;
        }
        return -1;
    }
}


class Solution2 {
    static int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int cutOffTree(List<List<Integer>> forest) {
        if (forest == null || forest.size() == 0) return 0;
        int m = forest.size(), n = forest.get(0).size();

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (forest.get(i).get(j) > 1) {
                    pq.add(new int[]{i, j, forest.get(i).get(j)});
                }
            }
        }

        int[] start = new int[2];
        int sum = 0;
        while (!pq.isEmpty()) {
            int[] tree = pq.poll();
            int step = minStep(forest, start, tree, m, n);

            if (step < 0) return -1;
            sum += step;

            start[0] = tree[0];
            start[1] = tree[1];
        }

        return sum;
    }

    private int minStep(List<List<Integer>> forest, int[] start, int[] tree, int m, int n) {
        int step = 0;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                if (curr[0] == tree[0] && curr[1] == tree[1]) return step;

                for (int[] d : dir) {
                    int nr = curr[0] + d[0];
                    int nc = curr[1] + d[1];
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n
                            || forest.get(nr).get(nc) == 0 || visited[nr][nc]) continue;
                    queue.add(new int[]{nr, nc});
                    visited[nr][nc] = true;
                }
            }
            step++;
        }

        return -1;
    }
}

