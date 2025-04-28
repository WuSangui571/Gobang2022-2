package main.java.com.ztydwz.gobang2022.Service;

import main.java.com.ztydwz.gobang2022.Model.Chess;
import main.java.com.ztydwz.gobang2022.Model.ChessType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static main.java.com.ztydwz.gobang2022.Model.Static.*;

public class ExportRecord {
    public static List<String> record = new ArrayList<>(); //创建棋谱列表

    public ExportRecord() {
        String first = "{[D2][先手参赛队B][后手参赛队W]";
        String second = "[禁手]";
        if (winFlag == 1) {
            second = "[先手胜]";
        } else if (winFlag == 2) {
            second = "[后手胜]";
        } else if (winFlag == 0) {
            second = "[平局]";
        }
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        String third = "[" + date.format(formatter) + " 沈阳]";
        String fourth = "[2022 CCGC];";
        for (int k = 0; k < chessList.size(); k++) {
            Chess chess = chessList.get(k);
            if (k % 2 == 0) {
                record.add("B");
            } else {
                record.add("H");
            }
            int j = (chess.getX() - start) / distance;
            int i = (chess.getY() - start) / distance;
            record.add(String.valueOf('(') + (char) (j + 1 + 64) + "," + (15 - i) + ')');
            record.add(";");
        }
        record.remove(record.size() - 1);
        record.add("}");
        File file;
        if (aiType == ChessType.BLACK) {
            file = new File("C:\\棋谱\\城建一队先手.txt");
        } else {
            file = new File("C:\\棋谱\\城建一队后手.txt");
        }

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(first);
            fw.write(second);
            fw.write(third);
            fw.write(fourth);
            Iterator iterator = record.iterator();

            while (iterator.hasNext()) {
                String i = (String) iterator.next();
                fw.write(i);
            }

            fw.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }
    }


}