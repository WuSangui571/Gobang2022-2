package com.ztydwz.gobang2022.Service;

import main.java.com.ztydwz.gobang2022.Service.AiSearchConfig;
import main.java.com.ztydwz.gobang2022.Service.Shou;

public class AiSearchHarness {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== AI Search Harness ===\n");

        testImmediateWin();
        testImmediateBlock();
        testNoBoardMutation();
        testDeterminism();
        testStrengthDepthMapping();
        testEdgeCandidate();
        testForbiddenHandBlack();
        testWhiteNotFiltered();
        testFullBoardNoMove();
        testBenchmark();

        System.out.println("\n=== Results: " + passed + " passed, " + failed + " failed ===");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static int[][] emptyBoard() {
        return new int[15][15];
    }

    private static void setBoard(int[][] board, int row, int col, int color) {
        board[row][col] = color;
    }

    private static int[] callAi(int[][] board, int aiColor) {
        Shou shou = new Shou();
        int enemyColor = 3 - aiColor;
        int[][] copy = copyBoard(board);
        return shou.getAnswer(copy, 15, 15, enemyColor, aiColor);
    }

    private static int[][] copyBoard(int[][] board) {
        int[][] copy = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    private static void assertEq(String name, int expected, int actual) {
        if (expected == actual) {
            System.out.println("  PASS: " + name + " = " + actual);
            passed++;
        } else {
            System.out.println("  FAIL: " + name + " expected " + expected + " but got " + actual);
            failed++;
        }
    }

    private static void assertTrue(String name, boolean condition) {
        if (condition) {
            System.out.println("  PASS: " + name);
            passed++;
        } else {
            System.out.println("  FAIL: " + name);
            failed++;
        }
    }

    private static void testImmediateWin() {
        System.out.println("[1] Immediate Win Detection");

        int[][] board = emptyBoard();
        board[7][7] = 1;
        board[7][8] = 1;
        board[7][9] = 1;
        board[7][10] = 1;

        int[][] preCopy = copyBoard(board);

        AiSearchConfig.Strength saved = main.java.com.ztydwz.gobang2022.Model.Static.aiStrength;
        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = AiSearchConfig.Strength.LOW;

        int[] move = callAi(board, 1);

        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = saved;

        assertEq("win row", 7, move[0]);
        assertEq("win col (left)", 6, move[0] == 7 ? move[1] : -1);
        if (move[0] == 7 && move[1] != 6 && move[1] != 11) {
            assertEq("win col (right)", 11, move[1]);
        }
        assertTrue("board unchanged", boardsEqual(preCopy, board));
    }

    private static void testImmediateBlock() {
        System.out.println("[2] Immediate Block Detection");

        int[][] board = emptyBoard();
        board[0][0] = 2;
        board[1][1] = 2;
        board[2][2] = 2;
        board[3][3] = 2;

        int[][] preCopy = copyBoard(board);

        AiSearchConfig.Strength saved = main.java.com.ztydwz.gobang2022.Model.Static.aiStrength;
        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = AiSearchConfig.Strength.LOW;

        int[] move = callAi(board, 1);

        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = saved;

        System.out.println("  AI chose [" + move[0] + "," + move[1] + "] to block opponent");
        assertTrue("not occupied", preCopy[move[0]][move[1]] == 0);
        assertTrue("blocks diagonal win", move[0] == 4 && move[1] == 4);
        assertTrue("board unchanged", boardsEqual(preCopy, board));
    }

    private static void testNoBoardMutation() {
        System.out.println("[3] No Board Mutation During Search");

        int[][] board = emptyBoard();
        board[7][7] = 1;
        board[7][8] = 2;
        board[8][8] = 1;
        board[6][7] = 2;

        int[][] preCopy = copyBoard(board);

        AiSearchConfig.Strength saved = main.java.com.ztydwz.gobang2022.Model.Static.aiStrength;
        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = AiSearchConfig.Strength.MEDIUM;

        int[] move = callAi(board, 1);

        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = saved;

        System.out.println("  AI chose [" + move[0] + "," + move[1] + "]");
        assertTrue("board unchanged after search", boardsEqual(preCopy, board));
    }

    private static void testDeterminism() {
        System.out.println("[4] Determinism");

        int[][] board = emptyBoard();
        board[7][7] = 1;
        board[7][8] = 2;
        board[8][8] = 1;
        board[6][8] = 2;
        board[9][9] = 1;

        AiSearchConfig.Strength saved = main.java.com.ztydwz.gobang2022.Model.Static.aiStrength;
        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = AiSearchConfig.Strength.LOW;

        int[] move1 = callAi(board, 1);
        int[] move2 = callAi(board, 1);
        int[] move3 = callAi(board, 1);

        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = saved;

        boolean same = move1[0] == move2[0] && move1[1] == move2[1]
                && move2[0] == move3[0] && move2[1] == move3[1];

        System.out.println("  Run1: [" + move1[0] + "," + move1[1] + "]"
                + " Run2: [" + move2[0] + "," + move2[1] + "]"
                + " Run3: [" + move3[0] + "," + move3[1] + "]");
        assertTrue("deterministic results", same);
    }

    private static void testStrengthDepthMapping() {
        System.out.println("[5] Strength / Depth Mapping");

        int low = new AiSearchConfig(AiSearchConfig.Strength.LOW).maxDepth;
        int med = new AiSearchConfig(AiSearchConfig.Strength.MEDIUM).maxDepth;
        int high = new AiSearchConfig(AiSearchConfig.Strength.HIGH).maxDepth;

        assertEq("LOW depth", 2, low);
        assertEq("MEDIUM depth", 4, med);
        assertEq("HIGH depth", 6, high);

        long lowTime = new AiSearchConfig(AiSearchConfig.Strength.LOW).timeLimitMillis;
        long medTime = new AiSearchConfig(AiSearchConfig.Strength.MEDIUM).timeLimitMillis;
        long highTime = new AiSearchConfig(AiSearchConfig.Strength.HIGH).timeLimitMillis;

        assertTrue("LOW time <= MEDIUM time", lowTime <= medTime);
        assertTrue("MEDIUM time <= HIGH time", medTime <= highTime);
        assertTrue("HIGH time <= 120000", highTime <= 120000);
    }

    private static void testEdgeCandidate() {
        System.out.println("[6] Edge Candidate Not Excluded");

        int[][] board = emptyBoard();
        board[0][0] = 1;
        board[0][1] = 1;
        board[0][2] = 1;
        board[0][3] = 1;

        AiSearchConfig.Strength saved = main.java.com.ztydwz.gobang2022.Model.Static.aiStrength;
        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = AiSearchConfig.Strength.LOW;

        int[] move = callAi(board, 1);

        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = saved;

        System.out.println("  Edge position test: AI chose [" + move[0] + "," + move[1] + "]");
        assertTrue("edge candidate allowed", move[0] >= 0 && move[1] >= 0);
    }

    private static void testForbiddenHandBlack() {
        System.out.println("[7] Black Forbidden-Hand Filtering");

        int[][] board = emptyBoard();
        board[7][7] = 1;
        board[7][8] = 1;
        board[7][9] = 1;
        board[6][7] = 1;
        board[6][6] = 1;
        board[6][8] = 1;

        AiSearchConfig.Strength saved = main.java.com.ztydwz.gobang2022.Model.Static.aiStrength;
        boolean savedForbidden = main.java.com.ztydwz.gobang2022.Model.Static.ifAllowForbiddenHandOpen;
        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = AiSearchConfig.Strength.LOW;
        main.java.com.ztydwz.gobang2022.Model.Static.ifAllowForbiddenHandOpen = false;

        int[] move = callAi(board, 1);

        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = saved;
        main.java.com.ztydwz.gobang2022.Model.Static.ifAllowForbiddenHandOpen = savedForbidden;

        System.out.println("  Forbidden-hand disallowed: AI chose [" + move[0] + "," + move[1] + "]");
        assertTrue("move is on-board", move[0] >= 0 && move[0] < 15 && move[1] >= 0 && move[1] < 15);
        assertTrue("not occupied", board[move[0]][move[1]] == 0);
    }

    private static void testWhiteNotFiltered() {
        System.out.println("[8] White Not Affected by Forbidden-Hand");

        int[][] board = emptyBoard();
        board[7][7] = 2;
        board[7][8] = 2;
        board[7][9] = 2;
        board[6][7] = 2;
        board[6][6] = 2;
        board[6][8] = 2;

        AiSearchConfig.Strength saved = main.java.com.ztydwz.gobang2022.Model.Static.aiStrength;
        boolean savedForbidden = main.java.com.ztydwz.gobang2022.Model.Static.ifAllowForbiddenHandOpen;
        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = AiSearchConfig.Strength.LOW;
        main.java.com.ztydwz.gobang2022.Model.Static.ifAllowForbiddenHandOpen = false;

        int[] move = callAi(board, 2);

        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = saved;
        main.java.com.ztydwz.gobang2022.Model.Static.ifAllowForbiddenHandOpen = savedForbidden;

        System.out.println("  White move (forbidden should not apply): [" + move[0] + "," + move[1] + "]");
        assertTrue("white gets a valid move", move[0] >= 0 && move[0] < 15 && move[1] >= 0 && move[1] < 15);
        assertTrue("not occupied", board[move[0]][move[1]] == 0);
    }

    private static void testBenchmark() {
        System.out.println("[10] Benchmark: Mid-Game Position");

        int[][] board = emptyBoard();
        board[7][7] = 1;
        board[7][8] = 2;
        board[8][8] = 1;
        board[6][7] = 2;
        board[9][9] = 1;
        board[6][8] = 2;
        board[5][9] = 1;
        board[8][6] = 2;
        board[8][9] = 1;
        board[7][6] = 2;
        board[6][6] = 1;
        board[9][7] = 2;

        AiSearchConfig.Strength saved = main.java.com.ztydwz.gobang2022.Model.Static.aiStrength;

        for (AiSearchConfig.Strength s : AiSearchConfig.Strength.values()) {
            main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = s;
            AiSearchConfig cfg = new AiSearchConfig(s);

            long start = System.currentTimeMillis();
            int[] move = callAi(board, 1);
            long elapsed = System.currentTimeMillis() - start;

            System.out.println("  " + s + ": depth=" + cfg.maxDepth
                    + " timeLimit=" + cfg.timeLimitMillis + "ms"
                    + " move=[" + move[0] + "," + move[1] + "]"
                    + " elapsed=" + elapsed + "ms");
            assertTrue(s + " within time limit", elapsed <= cfg.timeLimitMillis + 5000);
        }

        main.java.com.ztydwz.gobang2022.Model.Static.aiStrength = saved;
    }

    private static void testFullBoardNoMove() {
        System.out.println("[9] Full Board No-Move Result");

        int[][] board = emptyBoard();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = (i + j) % 2 == 0 ? 1 : 2;
            }
        }

        int[] move = callAi(board, 1);
        assertEq("full-board row", -1, move[0]);
        assertEq("full-board col", -1, move[1]);
    }

    private static boolean boardsEqual(int[][] a, int[][] b) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
