package com.yx.pc;

import java.io.*;
import java.util.Random;
import java.util.UUID;

/**
 * 抓取头像，生成脚本
 */
public class CatchHeadImg {
    public static void main(String[] args) throws IOException {
//        catchHeadImg();
//        genScript();
        chImg();
    }

    /**
     * 抓取头像
     *
     * @throws java.io.IOException
     */
    public static void catchHeadImg() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("e:/img.txt"));
        FileWriter fileWriter = new FileWriter("e:/img_jpg.sh", true);
        String line;
        String path = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/";
        int i = 1001;
        fileWriter.write("#!/bin/sh\n");
        while ((line = reader.readLine()) != null) {
            if (line.matches("u=.*\\.jpg")) {
                fileWriter.write("wget -c \"" + path + line + "\" -O ru_" + i + ".jpg");
                fileWriter.write("\n");
                i++;
            }
        }
    }

    public static void genScript() throws IOException {
        Random random = new Random();
        FileWriter fileWriter = new FileWriter("e:/genRobot.sql", true);
        for (int i = 1; i <= 5000; i++) {//10000000
            String str = "INSERT INTO `user_robot` (`user_id`, `nick_name`, `password`, `head_img`, `head_img_thumbnail`, `sex`, `birthday`, `address`, `phone_number`, `email`, `certification`, `ant_auth`, `apply_time`, `apply_check_result`, `id_card_no`, `id_card_name`, `id_card_img`, `id_card_img_back`, `id_card_img_hand`, `empirical_value`, `empirical_level`, `diamonds`, `coin_total`, `tang_coin`, `coin_withdraw`, `commission_scale`, `device_id`, `device_type`, `registration_id`, `hx_uuid`, `hx_username`, `chg_nick_name_free_time`, `stream_name`, `room_id`, `player_id`, `invitation_code`, `introduction`, `on_live`, `api_token`, `source`, `last_login_ip`, `last_login_time`, `create_time`, `status`, `house_close`, `frozen_account`) VALUES (" + (10000000 + i) + ", '', '62033986c99c1a518b9440baf3662f21', 'https://www.yongsn.net/file/img/ru/ru_" + i + ".jpg', 'https://www.yongsn.net/file/img/ru/ru_" + i + ".jpg', 0, '200" + (random.nextInt(10)) + "-0" + (random.nextInt(8) + 1) + "-1" + (random.nextInt(8) + 1) + "', 'bj', '" + (18888880000L + i) + "', '', 0, 0, 0, '', '', '', '', '', '', 16349, 16, 0, 0, 100, 0, 50.0, '', 0, '', '', '', 2, '', '', 0, '" + (UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8)) + "', '', 0, '', 0, '', 0, 1495083922655, 1, 0, 2);";
            fileWriter.write(str + "\n");
            fileWriter.flush();
        }
    }

    public static void chImg() {
        File file = new File("C:\\Users\\zjs\\Desktop\\img");
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i].renameTo(new File("e:/img/ru_" + (i + 1) + ".jpg"));
        }
    }
}
